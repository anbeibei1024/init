package com.dashen.init.view.activity

import com.dashen.init.R
import com.dashen.init.base.BaseActivity
import com.dashen.init.common.networkJava.request.InitDataNoParamRequest
import com.dashen.init.presenter.MainHelper
import com.dashen.init.presenter.viewinter.MainView

class MainActivity : BaseActivity(), MainView {
    private var mMainHelper: MainHelper? = null//MainActivity业务处理类

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {
        mMainHelper = MainHelper(this, this)
        mMainHelper?.getUserInfo(InitDataNoParamRequest("126"))






//        tv.setOnClickListener{
//            RxBus.instance.post(MessageEvent(Constant.MINE_MODIFY_INFO, ""))
//        }
    }

    override fun initData() {
    }
}
