package com.beibei.init.common.newNetwork.exception

/**
 * 项目名称：demeter
 * 包名：com.beibei.init.common.network.exception
 * 创建人：dashen
 * 创建时间：2017/3/10 10:17
 * 类描述：http服务错误
 * 备注：异常处理，为速度，不必要设置getter和setter
 */
class ServerException(override var message: String, var errorCode: Int) : RuntimeException(message)
