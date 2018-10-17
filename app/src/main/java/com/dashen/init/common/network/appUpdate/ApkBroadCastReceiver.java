package com.dashen.init.common.network.appUpdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.dashen.utils.LogUtils;

/**
 * 安卓8.0后不能静态注册服务
 */

public class ApkBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Uri uri = intent.getData();
        if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
//            comm.rmoveFile("http://imtt.dd.qq.com/16891/7C7BB50B68B684A36339AF1F615E2848.apk");
            Toast.makeText(context, "监听到系统广播添加"+uri.toString(), Toast.LENGTH_LONG).show();
            LogUtils.e("-------------监听到系统广播添加"+uri.toString());
        }

        if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
//            comm.rmoveFile("http://imtt.dd.qq.com/16891/7C7BB50B68B684A36339AF1F615E2848.apk");
            Toast.makeText(context, "监听到系统广播移除"+uri.toString(), Toast.LENGTH_LONG).show();
            LogUtils.e("-------------监听到系统广播添加"+uri.toString());
        }

        if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
//            comm.rmoveFile("http://imtt.dd.qq.com/16891/7C7BB50B68B684A36339AF1F615E2848.apk");
            Toast.makeText(context, "监听到系统广播替换"+uri.toString(), Toast.LENGTH_LONG).show();
            LogUtils.e("-------------监听到系统广播添加"+uri.toString());
        }
    }
}
