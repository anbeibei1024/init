package com.dashen.init.common.network.appUpdate;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.dashen.init.common.constant.Constant;
import com.dashen.init.common.utils.SharedPreferencesUtils;
import com.dashen.utils.LogUtils;
import com.dashen.utils.ToastUtils;

import java.io.File;
import java.lang.ref.WeakReference;

public class AppDownloadManager {
    public static final String TAG = "AppDownloadManager";
    private WeakReference<Activity> weakReference;
    private DownloadManager mDownloadManager;
    private DownloadChangeObserver mDownLoadChangeObserver;
    private DownloadReceiver mDownloadReceiver;
    private long mReqId;//标识当前下载任务的id，即downloadId。
    private OnUpdateListener mUpdateListener;

    public AppDownloadManager(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
        mDownloadManager = (DownloadManager) weakReference.get().getSystemService(Context.DOWNLOAD_SERVICE);
        mDownLoadChangeObserver = new DownloadChangeObserver(new Handler());
        mDownloadReceiver = new DownloadReceiver();
    }

    public void setUpdateListener(OnUpdateListener mUpdateListener) {
        this.mUpdateListener = mUpdateListener;
    }

    public void downloadApk(String apkUrl, String title, String desc) {
        //如果sp中有记录下载的新版本的apk
        long downloadId = (long) SharedPreferencesUtils.INSTANCE.get(weakReference.get(), Constant.NEW_VERSION_APK_DOWNLOAD_ID, -1L);
        if (downloadId != -1) {
            //存在downloadId
            DownLoadUtils downLoadUtils = DownLoadUtils.getInstance(weakReference.get());
            //获取当前状态
            int status = downLoadUtils.getDownloadStatus(downloadId);
            if (DownloadManager.STATUS_SUCCESSFUL == status) {
                //状态为下载成功
                //获取下载路径URI
                Uri downloadUri = downLoadUtils.getDownloadUri(downloadId);
                if (null != downloadUri) {
                    //存在下载的APK，如果两个APK相同，启动更新界面。否之则删除，重新下载。
                    if (compare(getApkInfo(weakReference.get(), downloadUri.getPath()), weakReference.get())) {

                        Intent intent = new Intent();
                        intent.putExtra(DownloadManager.EXTRA_DOWNLOAD_ID, downloadId);
                        installApk(weakReference.get(), intent);
//                        startInstall(weakReference.get(), downloadUri);
                        return;
                    } else {
                        //删除下载任务以及文件
                        downLoadUtils.getDownloadManager().remove(downloadId);
                    }
                }
                downloadStart(apkUrl, title, desc);
            } else if (DownloadManager.STATUS_FAILED == status) {
                //下载失败,重新下载
                downloadStart(apkUrl, title, desc);
            } else {
                LogUtils.e("apk is already downloading");
            }
        } else {
            //不存在downloadId，没有下载过APK
            downloadStart(apkUrl, title, desc);
        }
    }

    private void downloadStart(String apkUrl, String title, String desc) {
        // fix bug : 装不了新版本，在下载之前应该删除已有文件
        File apkFile = new File(weakReference.get().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "app_name.apk");

        if (apkFile != null && apkFile.exists()) {
            apkFile.delete();
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        //设置title
        request.setTitle(title);
        // 设置描述
        request.setDescription(desc);
        // 完成后显示通知栏
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(weakReference.get(), Environment.DIRECTORY_DOWNLOADS, "app_name.apk");
        //在手机SD卡上创建一个download文件夹
        // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir() ;
        //指定下载到SD卡的/download/my/目录下
        // request.setDestinationInExternalPublicDir("/codoon/","codoon_health.apk");

        request.setMimeType("application/vnd.android.package-archive");
        //
        mReqId = mDownloadManager.enqueue(request);
        DownLoadUtils downLoadUtils = DownLoadUtils.getInstance(weakReference.get());
        Uri downloadUri = downLoadUtils.getDownloadUri(mReqId);
        LogUtils.e("————————" + downloadUri);
        if (downloadUri != null) {
            LogUtils.e("————————" + downloadUri.getPath());
        }
        SharedPreferencesUtils.INSTANCE.put(weakReference.get(), Constant.NEW_VERSION_APK_DOWNLOAD_ID, mReqId);
    }

    /**
     * 取消下载
     */
    public void cancel() {
        mDownloadManager.remove(mReqId);
    }

    /**
     * 对应 {@link Activity }
     */
    public void resume() {
        //设置监听Uri.parse("content://downloads/my_downloads")
        weakReference.get().getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true,
                mDownLoadChangeObserver);
        // 注册广播，监听APK是否下载完成
        weakReference.get().registerReceiver(mDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    /**
     * 对应{@link Activity#onPause()} ()}
     */
    public void onPause() {
        weakReference.get().getContentResolver().unregisterContentObserver(mDownLoadChangeObserver);
        weakReference.get().unregisterReceiver(mDownloadReceiver);
    }

    private void updateView() {
        int[] bytesAndStatus = new int[]{0, 0, 0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(mReqId);
        Cursor c = null;
        try {
            c = mDownloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                //已经下载的字节数
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //总需下载的字节数
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //状态所在的列索引
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }

        if (mUpdateListener != null) {
            mUpdateListener.update(bytesAndStatus[0], bytesAndStatus[1]);
        }

        Log.i(TAG, "下载进度：" + bytesAndStatus[0] + "/" + bytesAndStatus[1] + "");
    }

    class DownloadChangeObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DownloadChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            updateView();
        }
    }

    class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            boolean haveInstallPermission;
            // 兼容Android 8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                //先获取是否有安装未知来源应用的权限
                haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {//没有权限
                    // 弹窗，并去设置页面授权
                    final AndroidOInstallPermissionListener listener = new AndroidOInstallPermissionListener() {
                        @Override
                        public void permissionSuccess() {
                            installApk(context, intent);
                        }

                        @Override
                        public void permissionFail() {
                            ToastUtils.showToast(context, "授权失败，无法安装应用");
                        }
                    };

                    AndroidOPermissionActivity.sListener = listener;
                    Intent intent1 = new Intent(context, AndroidOPermissionActivity.class);
                    context.startActivity(intent1);


                } else {
                    installApk(context, intent);
                }
            } else {
                installApk(context, intent);
            }

        }
    }


    /**
     * @param context
     * @param intent
     */
    private void installApk(Context context, Intent intent) {
        long completeDownLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

        LogUtils.e("收到广播");
        Uri uri;
        Intent intentInstall = new Intent();
        intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentInstall.setAction(Intent.ACTION_VIEW);

//        if (completeDownLoadId == mReqId) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { // 6.0以下
                uri = mDownloadManager.getUriForDownloadedFile(completeDownLoadId);
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) { // 6.0 - 7.0
                File apkFile = queryDownloadedApk(context, completeDownLoadId);
                uri = Uri.fromFile(apkFile);
            } else { // Android 7.0 以上
                uri = FileProvider.getUriForFile(context,
                        "com.dashen.init.fileProvider",
                        new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "app_name.apk"));
                intentInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            // 安装应用
            LogUtils.e("下载完成了");

            intentInstall.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intentInstall);
//        }
    }

    //通过downLoadId查询下载的apk，解决6.0以后安装的问题
    public static File queryDownloadedApk(Context context, long downloadId) {
        File targetApkFile = null;
        DownloadManager downloader = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        if (downloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloader.query(query);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;
    }

    public interface OnUpdateListener {
        void update(int currentByte, int totalByte);
    }

    public interface AndroidOInstallPermissionListener {
        void permissionSuccess();

        void permissionFail();
    }

    /**
     * 比较两个APK的信息
     *
     * @param apkInfo
     * @param context
     * @return
     */
    private static boolean compare(PackageInfo apkInfo, Context context) {

        if (null == apkInfo) {
            return false;
        }
        String localPackageName = context.getPackageName();
//        if (localPackageName.equals(apkInfo.packageName)) {
        if ("com.joyou.smartcity".equals(apkInfo.packageName)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(localPackageName, 0);
                //比较当前APK和下载的APK版本号
                if (apkInfo.versionCode > packageInfo.versionCode) {
                    //如果下载的APK版本号大于当前安装的APK版本号，返回true
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 获取APK程序信息
     *
     * @param context
     * @param path
     * @return
     */
    private static PackageInfo getApkInfo(Context context, String path) {

        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (null != pi) {
            return pi;
        }
        return null;
    }

}
