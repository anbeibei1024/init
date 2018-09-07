package com.dashen.init.base

/**
 * 项目名称：demeter
 * 包名：com.dashen.init.base
 * 创建人：dashen
 * 创建时间：2017/3/8 11:20
 * 类描述：所有实体数据父类
 * 备注：
 */
class ResultBean<T> {
    /**
     * message : 获取成功
     * errorCode : 0
     * data :
     */
    var message: String? = null
    var errorCode: String? = null
    var data: T? = null

}
