package com.dashen.init.view.activity

import com.dashen.demeter.common.utils.RxBus
import com.dashen.init.R
import com.dashen.init.base.BaseActivity
import com.dashen.init.base.MessageEvent
import com.dashen.init.common.constant.Constant
import com.dashen.init.presenter.MainHelper
import com.dashen.init.presenter.viewinter.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {
    private var mMainHelper: MainHelper? = null//MainActivity业务处理类

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {
        mMainHelper = MainHelper(this, this)
        mMainHelper?.getBannerData()

        tv.setOnClickListener{
            RxBus.instance.post(MessageEvent(Constant.MINE_MODIFY_INFO, ""))
        }
    }

    override fun initData() {
    }
}
