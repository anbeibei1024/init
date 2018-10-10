package com.dashen.init.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Handler
import android.support.v4.app.ActivityCompat
import com.dashen.init.R
import com.dashen.init.base.BaseActivity
import com.dashen.init.common.constant.Constant


/**
 * Created by anbeibei on 2018/4/10.
 *
 * 启动页
 */

class SplashActivity : BaseActivity() {
    private val mPerms = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

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
        if (hasPermissions(*mPerms)) {
            enterHomeActivity()
        } else {
            requestPermissions(Constant.REQUEST_CODE_EXTERNAL_STORAGE, *mPerms)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Constant.REQUEST_CODE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty()) {
                    var isAllPermission = true
                    for (i in 0 until grantResults.size) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            isAllPermission = false
                        }
                    }

                    if (isAllPermission) {
                        enterHomeActivity()
                    } else {
                        showPermissionSetting("读取联系人权限，读写存储", "应用")
                    }
                }
            }
        }
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when (requestCode) {
//            Constant.REQUEST_CODE_EXTERNAL_STORAGE -> {
//                if (grantResults.isNotEmpty()) {
//                    var isAllPermission = true
//                    var noPermission: String = ""
//                    for (i in 0 until grantResults.size) {
//                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                            isAllPermission = false
//                            noPermission = permissions[i]
//                        }
//                    }
//
//                    if (isAllPermission) {
//                        enterHomeActivity()
//                    } else {
//                        when (noPermission) {
//                            mPerms[0] -> {
//                                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
//                                    showPermissionRationale("该功能需要读取联系人权限，请授权后使用", Constant.REQUEST_CODE_EXTERNAL_STORAGE, *mPerms)
//                                } else {
//                                    showPermissionSetting("读取联系人权限", "打开联系人功能")
//                                }
//                            }
//
//                            mPerms[1], mPerms[2] -> {
//                                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
//                                    showPermissionRationale("该应用需要读写存储权限，请授权后使用", Constant.REQUEST_CODE_EXTERNAL_STORAGE, *mPerms)
//                                } else {
//                                    showPermissionSetting("读写存储权限", "正常使用应用")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun enterHomeActivity() {
        handler?.postDelayed(runnable, 1000)
    }


    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable)
        runnable = null
        handler = null
    }
}
