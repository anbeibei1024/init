package com.beibei.init.view.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dashen.demeter.common.utils.NetUtils


/**
 * 类描述：监听网络状态改变的广播接受者
 */
class NetBroadcastReceiver(private val onNetChangeListener: NetBroadcastReceiver.OnNetChangeListener) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == android.net.ConnectivityManager.CONNECTIVITY_ACTION) {
            val netWorkState = NetUtils.getNetWorkState(context)
            // 接口回调传过去网络状态的类型
            onNetChangeListener.onNetChange(netWorkState)
        }
    }

    /**
     * 移除广播
     */
    fun remove(context: Context) {
        context.unregisterReceiver(this)
    }

    // 自定义接口
    interface OnNetChangeListener {
        fun onNetChange(netWorkState: Int)
    }
}
