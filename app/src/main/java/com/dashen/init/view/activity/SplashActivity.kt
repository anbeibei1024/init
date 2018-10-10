package com.dashen.init.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Handler
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
            Manifest.permission.CAMERA,
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
                            when (permissions[i]) {
                                mPerms[0] -> {
                                    showPermissionSetting("相机权限", "保障头像设置功能的正常使用")
                                }

                                mPerms[1], mPerms[2] -> {
                                    showPermissionSetting("存储权限", "读写个性化信息等")
                                }
                            }
                            break
                        }
                    }

                    if (isAllPermission) {
                        enterHomeActivity()
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
////                                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
////                                    showPermissionRationale("该功能需要读取联系人权限，请授权后使用", Constant.REQUEST_CODE_EXTERNAL_STORAGE, *mPerms)
////                                } else {
//                                showPermissionSetting("相机权限", "保障头像设置功能的正常使用")
////                                }
//                            }
//
//                            mPerms[1], mPerms[2] -> {
////                                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
////                                    showPermissionRationale("该应用需要读写存储权限，请授权后使用", Constant.REQUEST_CODE_EXTERNAL_STORAGE, *mPerms)
////                                } else {
//                                showPermissionSetting("存储权限", "读写个性化信息等")
////                                }
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
