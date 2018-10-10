package com.dashen.init.base

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.Size
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import cn.testin.analysis.bug.BugOutApi
import com.dashen.demeter.common.utils.NetUtils
import com.dashen.init.App
import com.dashen.init.R
import com.dashen.init.common.utils.ActivityManagerUtils
import com.dashen.init.common.utils.StatusBarUtil
import com.dashen.init.view.activity.SplashActivity
import com.dashen.init.view.broadcast.NetBroadcastReceiver
import com.dashen.utils.LogUtils
import kotlinx.android.synthetic.main.head_view.*

/**
 * 项目名称：demeter-Android
 * 包名：com.dashen.demeter.base
 * 创建人：whj
 * 创建时间：2017/10/05
 * 类描述：activity基类(所有类都继承自这)
 * 备注：
 */
abstract class BaseActivity : AppCompatActivity(), NetBroadcastReceiver.OnNetChangeListener {
    private var netBroadCastReceiver: NetBroadcastReceiver? = null//网络状态监听的广播接受者
    private var netWorkState: Int = NetUtils.NETWORK_NONE// 网络状态类型 默认无网

    var mApp: App? = null
    var sp: SharedPreferences? = null
    var isContainFragment: Boolean = false//是否包含fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mApp = application as App
        sp = mApp!!.getSharedPreferences()
        setContentView(layoutId)
        //acitivity管理类
        ActivityManagerUtils.instance.addActivity(this)
//        Bugout.init(this, Constant.bugOutAK)

        initView()
        initData()
        LogUtils.e("===================onCreate===================" + this.javaClass.simpleName)
    }

    /**
     * 获取布局id
     */
    protected abstract val layoutId: Int

    /**
     * 初始化控件
     */
    protected abstract fun initView()

    /**
     * 初始化数据
     */
    protected abstract fun initData()


    fun onSuperBackPressed() {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        ActivityManagerUtils.instance.killActivity(this)
        //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

    fun setFragmentFlag(flag: Boolean) {
        this.isContainFragment = flag
    }

    /*-------权限-----*/
    /**
     * 检测是否有权限
     */

    fun hasPermissions(@Size(min = 1) vararg perms: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        for (perm in perms) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun requestPermissions(requestCode: Int, @Size(min = 1) vararg perms: String) {
        ActivityCompat.requestPermissions(this,
                perms,
                requestCode)
    }

    /**
     * 权限被拒绝--用户没有选择不再询问 -- 说明一下需要权限的原因
     */
    fun showPermissionRationale(explainReason: String, requestCode: Int, @Size(min = 1) vararg perms: String) {
        AlertDialog.Builder(this)
//                .setTitle("权限申请说明")
                .setMessage(explainReason)
                .setNegativeButton(getString(R.string.permission_cancel)) { dialog, which -> dialog.dismiss() }
                .setPositiveButton(getString(R.string.permission_grant)) { dialog, which ->
                    ActivityCompat.requestPermissions(this,
                            perms,
                            requestCode)
                }
                .show()
    }


    /**
     * 权限被拒绝--用户选择了不再询问 -- 让用户去设置界面开启权限
     */
    fun showPermissionSetting(permissionName: String, functionName: String) {
        AlertDialog.Builder(this)
//                .setTitle(getString(R.string.permission_title))
//                .setMessage("在设置-应用-${getString(R.string.app_name)}-权限中开启$permissionName，以正常$functionName")
                .setMessage("${getString(R.string.app_name)}向您申请$permissionName，以$functionName")//仿高德
                .setNegativeButton(getString(R.string.permission_cancel)) { dialog, which ->
                    dialog.dismiss()
                    (this as? SplashActivity)?.finish()
                }
                .setPositiveButton(getString(R.string.permission_setting)) { dialog, which ->
                    val mIntent = Intent()
                    mIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mIntent.data = Uri.fromParts("package", packageName, null)
                    startActivity(mIntent)
                    (this as? SplashActivity)?.finish()
                }
                .show()
    }


    //    ------------------------重构-----------------------
    /**
     * 状态栏的统一设置，如果个别页面为不同的状态栏时，重写此方法即可
     */
    open fun setStatusBar() {
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorPrimary), 0)
    }

    /**
     * 初始化标题栏
     *
     * 默认格式   左 返回键  中 标题  右 无
     *
     * 调用 initHeadView（title）即可
     *
     */
    protected fun initHeadView(title: String, leftText: String? = null, rightText: String? = null, rightIv: Int = 0) {
        if (!leftText.isNullOrEmpty()) {
            head_iv_back.visibility = View.GONE
            head_tv_left.text = leftText
            head_tv_left.visibility = View.VISIBLE
        } else {
            head_iv_back.visibility = View.VISIBLE
            head_iv_back.setOnClickListener { finish() }
        }

        if (!rightText.isNullOrEmpty()) {
            head_tv_right.text = rightText
            head_tv_right.visibility = View.VISIBLE
        } else if (rightIv != 0) {
            head_iv_right.setImageResource(rightIv)
            head_iv_right.visibility = View.VISIBLE
        }

        head_tv_title.text = title
    }

    /**
     * 跳转页面
     */
    fun startActivity(cls: Class<*>, bundle: Bundle? = null, isFinish: Boolean = false) {
        val intent = Intent(this, cls)
        bundle?.let { intent.putExtras(bundle) }
        startActivity(intent)
        if (isFinish)
            ActivityManagerUtils.instance.killActivity(this)
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

    fun startActivityForResult(cls: Class<*>, requestCode: Int, bundle: Bundle? = null) {
        val intent = Intent(this, cls)
        bundle?.let { intent.putExtras(bundle) }
        startActivityForResult(intent, requestCode)
    }

    /**
     * finish()和ActivityManagerUtils.getInstance().killActivity(this);二选一用
     */
    override fun finish() {
        super.finish()
        ActivityManagerUtils.instance.killActivity(this)
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

    override fun onResume() {
        super.onResume()
        BugOutApi.onResume(this) //BugOut3个回调监听用户操作步骤 注：回调 1


        //初始化Logutils的tag
        LogUtils.setTag(this.javaClass.simpleName)
        LogUtils.e("===============onResume===================" + this.javaClass.simpleName)
//        if (!isContainFragment) {
//            MobclickAgent.onPageStart(TAG) //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
//        }
//        MobclickAgent.onResume(this)          //统计时长
    }

    override fun onPause() {
        super.onPause()
        BugOutApi.onPause(this) //BugOut3个回调监听用户操作步骤 注：回调 2


        LogUtils.e("===============onPause===================" + this.javaClass.simpleName)
//        if (!isContainFragment) {
//            MobclickAgent.onPageEnd(TAG) // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
//        }
//        MobclickAgent.onPause(this)
        //隐藏软键盘
        val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (this.currentFocus != null) {
            mInputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        BugOutApi.onPause(this) //BugOut3个回调监听用户操作步骤 注：回调 3
        return super.dispatchTouchEvent(event)
    }

    /**
     * 注册网络状态监听的广播接收者
     *
     * 在需要关注网络状态式调用，不需要每个界面都调用
     */
    fun registerNetBroadcastReceiver() {
        netBroadCastReceiver = NetBroadcastReceiver(this)
        val filter = IntentFilter()
        filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(netBroadCastReceiver, filter)
    }

    /**
     * 网络状态变化监听
     * @param netWorkState
     */
    override fun onNetChange(netWorkState: Int) {
        this.netWorkState = netWorkState
        if (getCurrentNetStatus()) {
            reConnectNet()
        }
    }

    /**
     * 获取当前是否为联网状态
     *
     * 需要在当前界面注册网络广播
     */
    fun getCurrentNetStatus(): Boolean {
        return isNetConnect
    }

    /**
     * 当前的联网状态
     */
    private val isNetConnect: Boolean
        get() {
            return when (netWorkState) {
                NetUtils.NETWORK_WIFI -> true
                NetUtils.NETWORK_MOBILE -> true
                NetUtils.NETWORK_NONE -> false
                else -> false
            }
        }

    /**
     * 有的页面，当网络重新连接时，需刷新时，复写该方法
     */
    open fun reConnectNet() {
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.e("===============onDestroy===================" + this.javaClass.simpleName)
        netBroadCastReceiver?.remove(this)
    }
}