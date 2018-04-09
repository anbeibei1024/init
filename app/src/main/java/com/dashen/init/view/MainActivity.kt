package com.dashen.init.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dashen.init.App.Companion.context
import com.dashen.init.R
import com.dashen.init.presenter.MainHelper
import com.dashen.init.presenter.viewinter.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    private var mMainHelper: MainHelper? = null//MainActivity业务处理类

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMainHelper = MainHelper(context!!, this)
        mMainHelper?.getBannerData()

        tv.text = "hello"
    }
}
