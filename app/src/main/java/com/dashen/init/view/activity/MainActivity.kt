package com.dashen.init.view.activity

import com.dashen.init.R
import com.dashen.init.base.BaseActivity
import com.dashen.init.common.networkJava.request.InitDataNoParamRequest
import com.dashen.init.common.utils.StatusBarUtil
import com.dashen.init.presenter.MainHelper
import com.dashen.init.presenter.viewinter.MainView
import com.dashen.utils.ToastUtils
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
//            startActivity(SettingActivity::class.java)
            val inspectNet = getCurrentNetStatus()
            ToastUtils.showToast(this, "-----" + inspectNet.toString())
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
