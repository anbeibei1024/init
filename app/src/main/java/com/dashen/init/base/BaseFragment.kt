package com.dashen.init.base

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dashen.init.App
import com.dashen.init.R
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * 项目名称：demeter
 * 包名：com.dashen.init.base
 * 创建人：whj
 * 创建时间：2017/9/29 10:16
 * 类描述：fragment基类
 * 备注：
 */
abstract class BaseFragment : Fragment(), View.OnClickListener, EasyPermissions.PermissionCallbacks {
    /**
     * 获取fragment中view

     * @return
     */
    private var rootView: View? = null
    var sp: SharedPreferences? = null
    var mApp: App? = null
    protected var activity: BaseActivity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mApp = getActivity()!!.application as App
        sp = mApp!!.getSharedPreferences()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = View.inflate(getActivity(), layoutView!!, null /* click listener */)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    /**
     * 初始化布局
     */
    protected abstract fun initView()

    /**
     * 获取布局id
     */
    abstract val layoutView: Int?


    /**
     * 初始化数据
     */
    protected abstract fun initData()


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        this.activity = activity as BaseActivity?
    }

    /**
     * 初始化跳转
     */
    protected fun startActivity(cls: Class<*>, bundle: Bundle) {
        val intent = Intent(activity, cls)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    /**
     * 初始化跳转
     */
    protected fun startActivity(cls: Class<*>) {
        val intent = Intent(activity, cls)
        startActivity(intent)
        activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    protected fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        val intent = Intent(activity, cls)
        this.startActivityForResult(intent, requestCode)
    }

    protected fun startActivityForResult(cls: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent = Intent(activity, cls)
        intent.putExtras(bundle)
        this.startActivityForResult(intent, requestCode)
    }


    override fun onResume() {
        super.onResume()
        //初始化Logutils的tag
        //LogUtils.setTag(this.javaClass.simpleName)
//        MobclickAgent.onPageStart(this.javaClass.simpleName) //统计页面，"MainScreen"为页面名称，可自定义
    }

    override fun onPause() {
        super.onPause()
//        MobclickAgent.onPageEnd(this.javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
//        App.refWatcher!!.watch(this)
    }

    override fun onClick(v: View) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //申请权限成功
    override fun onPermissionsGranted(requestCode: Int, list: List<String>) {
        // Some permissions have been granted
        // ...
    }

    //申请权限失败(和deniedDialog一起使用)
    override fun onPermissionsDenied(requestCode: Int, list: List<String>) {
        // Some permissions have been denied
        // ...
    }

    /**
     * 请求权限失败时调用

     * @param perms
     * *
     * @param details
     */
    fun deniedDialog(perms: List<String>, details: String) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.permission_title))
                    .setPositiveButton(getString(R.string.permission_setting))
                    .setNegativeButton(getString(R.string.permission_cancle))
                    .build()
                    .show()
        }
    }
}
