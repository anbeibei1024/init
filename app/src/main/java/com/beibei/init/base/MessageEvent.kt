package com.beibei.init.base

/**
 * 项目名称：demeter
 * 包名：com.beibei.init.network.model
 * 创建人：dashen
 * 创建时间：2016/12/8 11:27
 * 类描述：eventbus传递事件对象，tag为事件标签，t为传递对象,params1参数1，params2参数2
 * 备注：
 */
class MessageEvent {
    var tag: Int = 0//事件标签
    var params1: String? = null//参数1
    var params2: String? = null//参数2

    var t: Any? = null

    /**
     * 标签+对象

     * @param tag
     * *
     * @param t
     */
    constructor(tag: Int, t: Any) {
        this.tag = tag
        this.t = t
    }

    /**
     * 标签+参数1+参数2

     * @param params1
     * *
     * @param params2
     * *
     * @param tag
     */
    constructor(tag: Int, params1: String, params2: String) {
        this.params1 = params1
        this.params2 = params2
        this.tag = tag
    }

    /**
     * 标签+参数1+参数2+对象

     * @param params1
     * *
     * @param params2
     * *
     * @param tag
     */
    constructor(tag: Int, params1: String, params2: String, t: Any) {
        this.tag = tag
        this.params1 = params1
        this.params2 = params2
        this.t = t
    }
}
