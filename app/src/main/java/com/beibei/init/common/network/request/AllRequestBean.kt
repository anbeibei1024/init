package com.beibei.init.common.newNetwork.request

/**
 *
 * 项目名称:  Demeter-Android
 * 包名:     com.beibei.init.common.network.model
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


data class InitDataNoParamRequest(
        val userId: String = ""
)

//版本更新
data class VersionUpdateRequest(
        val versionCode: String = ""
)
