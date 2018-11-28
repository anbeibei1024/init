package com.beibei.init.common.network.model

/**
 * 类描述：所有实体数据父类
 *
 * 备注：根据项目跟服务端确定 判断某字段状态 表示请求成功
 */
class ResultBean<T> {
    var message: String? = null
    var retcode: String? = null
    var data: T? = null

    var errcode: Int = -1
}
