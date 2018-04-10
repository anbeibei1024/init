package com.dashen.init.view.activity

import com.dashen.init.R
import com.dashen.init.base.BaseActivity
import com.dashen.init.presenter.LoginHelper
import com.dashen.init.presenter.viewinter.LoginView
import kotlinx.android.synthetic.main.activity_login.*


/**
 * Created by anbeibei on 2018/4/9.
 *
 * 登陆页面
 */
class LoginActivity : BaseActivity() ,LoginView{
    private var mHelper:LoginHelper? = null

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initView() {
        mHelper = LoginHelper(this, this)
        mHelper?.initRxBus()
        textView.setOnClickListener{
            startActivity(MainActivity::class.java)
        }
    }

    override fun initData() {
    }


}
