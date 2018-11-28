//package com.beibei.init.base
//
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//
//import com.jph.takephoto.app.TakePhoto
//import com.jph.takephoto.app.TakePhotoImpl
//import com.jph.takephoto.model.InvokeParam
//import com.jph.takephoto.model.TContextWrap
//import com.jph.takephoto.model.TResult
//import com.jph.takephoto.permission.InvokeListener
//import com.jph.takephoto.permission.PermissionManager
//import com.jph.takephoto.permission.TakePhotoInvocationHandler
//
///**
// * 项目名称:  Mars-Android
// * 包名:     com.qicai.mars.com.poseidon.seller.view.activity
// * 创建人 :   whj
// * 创建时间:  2017/5/22 16:39
// * 类描述:
// * 备注:
// */
//abstract class TakePhotoActivity : BaseActivity(), TakePhoto.TakeResultListener, InvokeListener {
//    private var takePhoto: TakePhoto? = null
//    private var invokeParam: InvokeParam? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        getTakePhoto().onCreate(savedInstanceState)
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onSaveInstanceState(outState: Bundle?) {
//        getTakePhoto().onSaveInstanceState(outState)
//        super.onSaveInstanceState(outState)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        getTakePhoto().onActivityResult(requestCode, resultCode, data)
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        val type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        PermissionManager.handlePermissionsResult(this, type, invokeParam, this)
//    }
//
//    /**
//     * 获取TakePhoto实例
//     * @return
//     */
//    fun getTakePhoto(): TakePhoto {
//        if (takePhoto == null) {
//            takePhoto = TakePhotoInvocationHandler.of(this).bind(TakePhotoImpl(this, this)) as TakePhoto
//        }
//        return takePhoto as TakePhoto
//    }
//
//    override fun takeSuccess(result: TResult?) {
//        Log.i(TAG, "takeSuccess：" + result!!.image!!.compressPath)
//    }
//
//    override fun takeFail(result: TResult?, msg: String) {
//        Log.i(TAG, "takeFail:" + msg)
//    }
//
//    override fun takeCancel() {
//        Log.i(TAG, resources.getString(com.jph.takephoto.R.string.msg_operation_canceled))
//    }
//
//    override fun invoke(invokeParam: InvokeParam): PermissionManager.TPermissionType {
//        val type = PermissionManager.hasPermissions(TContextWrap.of(this), invokeParam.method!!)
//        if (PermissionManager.TPermissionType.WAIT == type) {
//            this.invokeParam = invokeParam
//        }
//        return type
//    }
//
//    companion object {
//        private val TAG = com.jph.takephoto.app.TakePhotoActivity::class.java.name
//    }
//}
