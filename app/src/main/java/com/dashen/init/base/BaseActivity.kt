package com.dashen.init.base

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.dashen.demeter.common.utils.NetUtils
import com.dashen.init.App
import com.dashen.init.R
import com.dashen.init.common.utils.ActivityManagerUtils
import com.dashen.init.common.utils.StatusBarUtil
import com.dashen.init.common.utils.StatusUiTextUtils
import com.dashen.init.view.broadcast.NetBroadcastReceiver
import com.dashen.utils.LogUtils
import kotlinx.android.synthetic.main.head_view.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 * 项目名称：demeter-Android
 * 包名：com.dashen.demeter.base
 * 创建人：whj
 * 创建时间：2017/10/05
 * 类描述：activity基类(所有类都继承自这)
 * 备注：
 */
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener,
        NetBroadcastReceiver.NetEvevt, EasyPermissions.PermissionCallbacks {
    var mApp: App? = null
    var sp: SharedPreferences? = null
    //网络状态监听
    private var netBroadCast: NetBroadcastReceiver? = null//网络监听
    var netMobile: Int = -1// 网络类型(1.wifi 0.mobile -1.无网)
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
//        Bugout.init(this, Constant.BUGOUT_APPKEY)
        //网络状态监听
        receiveBroadCast()
        initView()
        initData()
        LogUtils.e("===============onCreate===================" + this.javaClass.simpleName)
    }

    /**
     * 获取布局id

     * @return
     */
    protected abstract val layoutId: Int

    /**
     * 注册广播接收
     */
    private fun receiveBroadCast() {
        netBroadCast = NetBroadcastReceiver(this)
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        this.registerReceiver(netBroadCast, filter)
    }


    /**
     * 初始化控件
     */
    protected abstract fun initView()

    /**
     * 初始化数据
     */
    protected abstract fun initData()


    /**
     * 初始化跳转
     */
    fun startActivity(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

    /**
     * 初始化跳转
     */
    fun startActivity(cls: Class<*>, isAnim: Boolean) {
        val intent = Intent(this, cls)
        startActivity(intent)
        if (isAnim) {
            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
        }
    }

    /**
     * 初始化跳转
     */
    fun startActivity(cls: Class<*>, bundle: Bundle) {
        val intent = Intent(this, cls)
        intent.putExtras(bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

    protected fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        val intent = Intent(this, cls)
        startActivityForResult(intent, requestCode)
    }

    protected fun startActivityForResult(cls: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent = Intent(this, cls)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    protected fun startActivityForResult(cls: Class<*>, bundle: Bundle) {
        val intent = Intent(this, cls)
        intent.putExtras(bundle)
        startActivityForResult(intent, 0)
    }

    /**
     * 初始化跳转
     */
    fun startActivityFinish(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
        startFinish()
    }

    /**
     *start后finish
     */
    fun startFinish() {
        super.finish()
        ActivityManagerUtils.instance.killActivity(this)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    /**
     * 初始化跳转
     */
    fun startActivityFinish(cls: Class<*>, bundle: Bundle) {
        val intent = Intent(this, cls)
        intent.putExtras(bundle)
        startActivity(intent)
        startFinish()
    }


    fun onSuperBackPressed() {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        ActivityManagerUtils.instance.killActivity(this)
        //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

    override fun onResume() {
        super.onResume()
        //初始化Logutils的tag
        LogUtils.setTag(this.javaClass.simpleName)
        LogUtils.e("===============onResume===================" + this.javaClass.simpleName)
        //注：回调 1
//        Bugout.onResume(this)
//        if (!isContainFragment) {
//            MobclickAgent.onPageStart(TAG) //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
//        }
//        MobclickAgent.onResume(this)          //统计时长
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        //注：回调 3
//        Bugout.onDispatchTouchEvent(this, event)
        return super.dispatchTouchEvent(event)
    }

    override fun onPause() {
        super.onPause()
        LogUtils.e("===============onPause===================" + this.javaClass.simpleName)
//        if (!isContainFragment) {
//            MobclickAgent.onPageEnd(TAG) // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
//        }
//        MobclickAgent.onPause(this)
//        //注：回调 2
//        Bugout.onPause(this)
        //隐藏软键盘
        val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (this.currentFocus != null) {
            mInputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.e("===============onDestroy===================" + this.javaClass.simpleName)
        netBroadCast!!.remove(this)//移除广播
//        App.refWatcher!!.watch(this)
    }

    override fun onClick(v: View) {
    }

    /**
     * finish()和ActivityManagerUtils.getInstance().killActivity(this);二选一用
     */
    override fun finish() {
        super.finish()
        ActivityManagerUtils.instance.killActivity(this)
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)

    }


    /**
     * 网络变化监听

     * @param netMobile
     */
    override fun onNetChange(netMobile: Int) {
        this.netMobile = netMobile
    }

    /**
     * 初始化时判断有没有网络
     */
    fun inspectNet(): Boolean {
        this.netMobile = NetUtils.getNetWorkState(this@BaseActivity)
        return isNetConnect
    }

    /**
     * 判断有无网络 。

     * @return true 有网, false 没有网络.
     */
    private val isNetConnect: Boolean
        get() {
            if (netMobile == 1) {
                return true
            } else if (netMobile == 0) {
                return true
            } else if (netMobile == -1) {
                return false
            }
            return false
        }

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

    fun setFragmentFlag(flag: Boolean) {
        this.isContainFragment = flag
    }


    /*-------权限-----*/
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //申请权限成功
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        // Some permissions have been granted
        // ...
    }

    //申请权限失败(和deniedDialog一起使用)
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        // Some permissions have been denied
        // ...
    }

    /**
     * 请求权限失败时调用
     *
     * @param details 要求的理由
     */
    fun deniedDialog(details: String) {
        AppSettingsDialog.Builder(this)
                .setTitle(getString(R.string.permission_title))
                .setPositiveButton(getString(R.string.permission_setting))
                .setRationale(details)
                .setNegativeButton(getString(R.string.permission_cancle))
                .build()
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
}