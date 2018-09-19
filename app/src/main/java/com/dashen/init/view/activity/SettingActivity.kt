package com.dashen.init.view.activity

import com.dashen.init.R
import com.dashen.init.base.BaseActivity

/**
 * project_name:   init
 * package_name:   com.dashen.init.view.activity
 * author:   beibei
 * create_time:    2018/9/17 17:22
 * class_desc:  设置页面
 * remarks:
 */
class SettingActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.layout_setting

    override fun initView() {
        initHeadView("设置")
    }

    override fun initData() {
    }
}