package com.beibei.init.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beibei.init.App
import com.beibei.init.R

/**
 * 项目名称：demeter
 * 包名：com.beibei.init.base
 * 创建人：whj
 * 创建时间：2017/9/29 10:16
 * 类描述：fragment基类
 * 备注：
 */
abstract class BaseFragment : Fragment(), View.OnClickListener {
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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.activity = activity
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
    }

    override fun onClick(v: View) {

    }
    /*-------权限-----*/
    /**
     * 检测是否有权限
     *
     * 返回 true 有权限 false 没有权限，请求权限
     *
     */
    fun checkHasPermission(readContacts: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(activity!!, readContacts) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(readContacts), requestCode)
        }
        return false
    }

    /**
     * 权限被拒绝--用户没有选择不再询问 -- 说明一下需要权限的原因
     */
    fun showPermissionRationale(explainReason: String, requestCode: Int) {
        AlertDialog.Builder(activity!!)
//                .setTitle("权限申请说明")
                .setMessage(explainReason)
                .setNegativeButton(getString(R.string.permission_cancel)) { dialog, which -> dialog.dismiss() }
                .setPositiveButton(getString(R.string.permission_grant)) { dialog, which ->
                    ActivityCompat.requestPermissions(activity!!,
                            arrayOf(Manifest.permission.READ_CONTACTS),
                            requestCode)
                }
                .show()
    }


    /**
     * 权限被拒绝--用户选择了不再询问 -- 让用户去设置界面开启权限
     */
    fun showPermissionSetting(permissionName: String, functionName: String) {
        AlertDialog.Builder(activity!!)
                .setTitle(getString(R.string.permission_title))
                .setMessage("在设置-应用-${getString(R.string.app_name)}-权限中开启$permissionName,以正常使用$functionName")
                .setNegativeButton(getString(R.string.permission_cancel)) { dialog, which -> dialog.dismiss() }
                .setPositiveButton(getString(R.string.permission_setting)) { dialog, which ->
                    val mIntent = Intent()
                    mIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mIntent.data = Uri.fromParts("package", activity?.packageName, null)
                    startActivity(mIntent)
                }
                .show()
    }


}
