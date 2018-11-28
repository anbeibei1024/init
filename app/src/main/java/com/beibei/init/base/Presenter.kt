package com.beibei.init.base

/**
 * 页面展示逻辑基类
 */
abstract class Presenter protected constructor() {
    init {
        onCreate()
    }

    abstract fun onCreate()

    //销去持有外部的mContext;
    abstract fun onDestroy()
}
