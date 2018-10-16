package com.dashen.init.common.network.appUpdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 *
 */

public class ApkBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
//            comm.rmoveFile("http://imtt.dd.qq.com/16891/7C7BB50B68B684A36339AF1F615E2848.apk");
            Toast.makeText(context, "监听到系统广播添加", Toast.LENGTH_LONG).show();
        }

        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
//            comm.rmoveFile("http://imtt.dd.qq.com/16891/7C7BB50B68B684A36339AF1F615E2848.apk");
            Toast.makeText(context, "监听到系统广播移除", Toast.LENGTH_LONG).show();
        }

        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
//            comm.rmoveFile("http://imtt.dd.qq.com/16891/7C7BB50B68B684A36339AF1F615E2848.apk");
            Toast.makeText(context, "监听到系统广播替换", Toast.LENGTH_LONG).show();
        }
    }
}
