package com.dashen.init.presenter.viewinter

import com.dashen.init.base.MvpView

/**
 * Created by anbeibei on 2018/4/9.
 */
interface MainView : MvpView {

    //获取首页广告
    fun setText(type: String) {}
}