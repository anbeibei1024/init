package com.dashen.demeter.common.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * 类描述：获取网络状态工具类
 */
object NetUtils {
    /**
     * 没有连接网络
     */
    const val NETWORK_NONE = -1
    /**
     * 移动网络
     */
    const val NETWORK_MOBILE = 0
    /**
     * 无线网络
     */
    const val NETWORK_WIFI = 1

    fun getNetWorkState(context: Context): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return when (activeNetworkInfo.type) {
                ConnectivityManager.TYPE_WIFI -> NETWORK_WIFI
                ConnectivityManager.TYPE_MOBILE -> NETWORK_MOBILE
                else -> NETWORK_NONE
            }
        }
        return NETWORK_NONE
    }
}
