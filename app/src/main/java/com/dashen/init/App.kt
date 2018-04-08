package com.dashen.init

import android.content.Context
import android.content.SharedPreferences
import android.support.multidex.MultiDexApplication

/**
 * Created by anbeibei on 2018/4/8.
 */

class App : MultiDexApplication() {

    /**
     * 静态代码
     */
    companion object {
        private var application: App? = null
        val context: Context
            get() = application!!.applicationContext

        var sp: SharedPreferences? = null//sp存储
        var isLogin: Boolean = false//是否登录
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }


}
