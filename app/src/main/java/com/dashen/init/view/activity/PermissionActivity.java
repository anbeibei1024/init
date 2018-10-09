package com.dashen.init.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.dashen.init.R;
import com.dashen.utils.ToastUtils;

/**
 * project_name:   init
 * package_name:   com.dashen.init.view.activity
 * author:   beibei
 * create_time:    2018/10/9 17:10
 * class_desc:
 * remarks:
 */
public class PermissionActivity extends Activity {
    private Activity instance;
    private Activity thisActivity;
    private final int REQUEST_PERMISSION_CAMERA_CODE = 0;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        instance = this;
        thisActivity = this;

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, Manifest.permission.READ_CONTACTS)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);
                        builder.setMessage("我们应用需要该权限，给一个吧");
                        builder.setPositiveButton("好好", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(thisActivity,
                                        new String[]{Manifest.permission.READ_CONTACTS},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        });
                        builder.show();
                    } else {
                        ActivityCompat.requestPermissions(thisActivity,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    }
                } else {
                    openContact();
                }
            }
        });
    }

    private void openContact() {
        ToastUtils.showToast(thisActivity, "打开联系人");
    }


    /**
     * 测试：原生动态权限管理
     */
    private void testOrgPermission() {
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.READ_CONTACTS)) {
                ToastUtils.showToast(thisActivity, "我们应用需要该权限，给一个吧");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            ToastUtils.showToast(instance, "有权限了");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.showToast(instance, "有权限了");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    ToastUtils.showToast(instance, "你用不了一些功能了");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
//
//    /**
//     * 询问权限的回调函数
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case REQUEST_PERMISSION_CAMERA_CODE:
//                int cameraResult = grantResults[0];//相机权限
//                boolean cameraGranted = cameraResult == PackageManager.PERMISSION_GRANTED;//拍照权限
//
//                //注册权限
//                if (cameraGranted) {
//                    LogUtils.i("onRequestPermissionsResult() : " + permissions[0] +
//                            " request granted , to do something...");
//                    //todo something
//                }
//
//                //拒绝注册权限
//                else {
//
//                    //无权限，且被选择"不再提醒"：提醒客户到APP应用设置中打开权限
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(instance, Manifest.permission.CAMERA)) {
//                        LogUtils.e("onRequestPermissionsResult() : this " + permissions[0] + " is denied " +
//                                "and never ask again");
//                        ToastUtils.showToast(instance, "拒绝权限，不再弹出询问框，请前往APP应用设置中打开此权限");
//                        //todo nothing
//                    }
//
//                    //无权限，只是单纯被拒绝
//                    else {
//                        LogUtils.e("onRequestPermissionsResult() : " + permissions[0] + "request denied");
//                        ToastUtils.showToast(instance, "拒绝权限，等待下次询问哦");
//                        //todo request permission again
//                    }
//
//
//                }
//                break;
//
//            default:
//                break;
//        }
//
//    }

}
