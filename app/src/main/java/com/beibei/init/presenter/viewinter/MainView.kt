package com.beibei.init.presenter.viewinter

import com.beibei.init.base.MvpView

/**
 * Created by anbeibei on 2018/4/9.
 */
interface MainView : MvpView {

    //获取首页广告
    fun setText(type: String) {}
}