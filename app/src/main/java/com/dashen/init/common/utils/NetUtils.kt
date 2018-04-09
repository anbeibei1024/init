package com.dashen.demeter.common.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 * 项目名称：demeter-Android
 * 包名：com.dashen.demeter.utils
 * 创建人：dashen
 * 创建时间：2016/12/5
 * 类描述：实时监听网络状态变化工具类
 * 备注：
 */
object NetUtils {
    /**
     * 没有连接网络
     */
    val NETWORK_NONE = -1
    /**
     * 移动网络
     */
    val NETWORK_MOBILE = 0
    /**
     * 无线网络
     */
    val NETWORK_WIFI = 1

    fun getNetWorkState(context: Context): Int {
        // 得到连接管理器对象
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager
                .activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {

            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_WIFI
            } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return NETWORK_MOBILE
            }
        } else {
            return NETWORK_NONE
        }
        return NETWORK_NONE
    }
}
