package com.dashen.init.presenter.viewinter

import com.dashen.init.base.MvpView
import com.dashen.init.common.newNetwork.model.UpdateBean

/**
 * Created by anbeibei on 2018/4/9.
 */
interface SettingView : MvpView {

    /**
     * 获取版本更新
     */
    fun getUpdateInfoSuccess(it: UpdateBean) {}
    fun getUpdateInfoMessage(errorCode: Int, msg: String) {}
}