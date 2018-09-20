package com.dashen.init.view.activity

import android.Manifest
import android.content.Intent
import android.os.Handler
import android.view.View
import com.dashen.init.R
import com.dashen.init.base.BaseActivity
import com.dashen.init.common.constant.Constant
import com.dashen.utils.LogUtils
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 * Created by anbeibei on 2018/4/10.
 *
 * 启动页
 */

class SplashActivity : BaseActivity() {

    private val mPerms = arrayOf(Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE)

    private var handler: Handler? = Handler()
    private var runnable: Runnable? = Runnable {
        if (mApp!!.getLoginStatus()) {
            startActivity(MainActivity::class.java, isFinish = true)
        } else {
            startActivity(MainActivity::class.java, isFinish = true)
//            startActivity(LoginActivity::class.java, isFinish = true)
        }
    }

    override fun initData() {}

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun initView() {
        enterHomeActivity()
//        checkPermission()
    }

    private fun enterHomeActivity() {
        handler?.postDelayed(runnable, 1000)
    }

    @AfterPermissionGranted(Constant.REQUEST_PERMISSION1)
    private fun checkPermission() {
        LogUtils.e("----checkPermission--")
        if (EasyPermissions.hasPermissions(this, *mPerms)) {//有权限
            enterHomeActivity()
        } else {//无权限
            EasyPermissions.requestPermissions(this@SplashActivity,
                    getString(R.string.permission_need),
                    Constant.REQUEST_PERMISSION1,
                    *mPerms)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        LogUtils.e("----onPermissionsGranted--${perms.size}")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        LogUtils.e("----onPermissionsDenied--${perms.size}")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            deniedDialog(getString(R.string.permission_need))
        } else {
            checkPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            checkPermission()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable)
        runnable = null
        handler = null
    }
}
