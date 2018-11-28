package com.beibei.init.presenter.viewinter

import com.beibei.init.base.MvpView
import com.beibei.init.common.newNetwork.model.LoginDataBean

/**
 * Created by anbeibei on 2018/4/10.
 */

interface LoginView : MvpView {

    /**
     * 计时完成
     */
    fun onTimeFinish() {}

    /**
     * 计时
     */
    fun onTimeTick(millisUntilFinished: Long) {}

    /**
     * 注册成功
     */
    fun toRegisterSuccess(t: LoginDataBean) {}

    /**
     * 注册失败
     */
    fun onRegisterError(error: Throwable, msg: String) {}

    /**
     * 重置密码成功
     */
    fun toResetPwdSuccess() {}

    /**
     * 重置密码成功
     */
    fun toResetPwdError(error: Throwable, msg: String) {}


    /**
     * 修改密码成功
     */
    fun toModifyPasswordSuccess() {}

}