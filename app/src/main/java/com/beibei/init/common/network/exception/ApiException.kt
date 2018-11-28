package com.beibei.init.common.newNetwork.exception

/**
 * 项目名称：demeter
 * 包名：com.beibei.init.common.network
 * 创建人：dashen
 * 创建时间：2017/3/9 11:27
 * 类描述：api异常(包含http异常，解析异常，服务器返回errcode异常)
 * 备注：  异常处理，为速度，不必要设置getter和setter
 */
class ApiException(throwable: Throwable, var errorCode: Int) : RuntimeException(throwable) {
    override var message: String? = null
}
