package com.dashen.init.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
 * class_desc: 原生的权限请求
 * remarks:
 */
public class PermissionActivity extends Activity {
    private Activity thisActivity;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        thisActivity = this;
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(thisActivity,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                } else {
                    openContact();
                }
            }
        });
    }

    private void openContact() {
        ToastUtils.showToast(thisActivity, "打开联系人");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openContact();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, Manifest.permission.READ_CONTACTS)) {
//                        showRationale1();
                        snackBarReason();
                    } else {
//                        showRationale();
                        snackBarSetting();
                    }
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * 权限被拒绝--用户选择了不再询问 -- 让用户去设置界面开启权限
     */
    private void showRationale() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);
        builder.setTitle("权限申请");
        builder.setMessage("在设置-应用-初始库-权限中开启读取联系人权限，以正常使用打开联系人功能");
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mIntent = new Intent();
                mIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(mIntent);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 权限被拒绝--用户没有选择不再询问 -- 说明需要权限的原因
     */
    private void showRationale1() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);
        builder.setTitle("权限申请说明");
        builder.setMessage("该功能需要读取联系人权限，请授权后使用");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 模仿知乎用snackBar
     */

    private void snackBarReason() {
        Snackbar.make(button, "获取相机权限失败，请授权后使用。", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.color_white))
                .setAction("授权", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(thisActivity,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    }
                })
                .show();
    }


    /**
     * 模仿知乎用snackBar
     */
    private void snackBarSetting() {
        final Snackbar snackbar = Snackbar.make(button, "您永久禁止了该权限，如需授权请到应用设置中主动打开读取联系人权限。", Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.color_red));
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.base_green));

        snackbar.setAction("去设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                snackbar.dismiss();
                Intent mIntent = new Intent();
                mIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(mIntent);
            }
        });
        snackbar.show();
    }
}
