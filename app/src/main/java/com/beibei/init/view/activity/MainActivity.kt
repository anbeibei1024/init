package com.beibei.init.view.activity

import com.beibei.init.R
import com.beibei.init.base.BaseActivity
import com.beibei.init.common.networkJava.request.InitDataNoParamRequest
import com.beibei.init.common.utils.StatusBarUtil
import com.beibei.init.presenter.MainHelper
import com.beibei.init.presenter.viewinter.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {
    private var mMainHelper: MainHelper? = null//MainActivity业务处理类

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {
        mMainHelper = MainHelper(this, this, lifecycle)
        lifecycle.addObserver(mMainHelper!!)
        registerNetBroadcastReceiver()

        mMainHelper?.getUserInfo(InitDataNoParamRequest("126"))

        tv.setOnClickListener {
//            startActivity(PermissionActivity::class.java)
//            startActivity(PermissionActivity1::class.java)
            startActivity(SettingActivity::class.java)


//            val inspectNet = getCurrentNetStatus()
//            ToastUtils.showToast(this, "-----" + inspectNet.toString())



//            RxBus.instance.post(MessageEvent(Constant.MINE_MODIFY_INFO, ""))
        }
    }

    override fun initData() {
    }

    override fun setStatusBar() {
        StatusBarUtil.setColor(this, resources.getColor(R.color.base_green), 0)
    }

    override fun setText(type: String) {
        tv.text = "______$type"
    }

    override fun reConnectNet() {
        mMainHelper?.getUserInfo(InitDataNoParamRequest("126"))
    }
}
