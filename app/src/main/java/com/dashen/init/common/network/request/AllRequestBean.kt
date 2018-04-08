package com.dashen.init.common.newNetwork.request

/**
 *
 * 项目名称:  Demeter-Android
 * 包名:     com.dashen.init.common.network.model
 * 创建人 :   whj
 * 创建时间:  2018/2/7 11:19
 * 类描述:
 * 备注:
 *
 */
data class RegisterRequset(
        val mobile: String = "",
        val pwd: String = "",
        val code: String = "",
        val pwdconfirm: String = ""
)

data class SendCodeRequest(val mobile:String = "")