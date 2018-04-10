package com.dashen.init.view.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.dashen.demeter.common.utils.NetUtils


/**
 * 项目名称：demeter-Android
 * 包名：com.dashen.demeter.broadcast
 * 创建人：dashen
 * 创建时间：2017/11/28 11:18
 * 类描述：实时监听网络状态改变
 * 备注：
 */
class NetBroadcastReceiver(private val evevt: NetBroadcastReceiver.NetEvevt) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val netWorkState = NetUtils.getNetWorkState(context)
            // 接口回调传过去状态的类型
            evevt.onNetChange(netWorkState)
        }
    }

    /**
     * 移除广播
     */
    fun remove(context: Context) {
        context.unregisterReceiver(this)
    }

    // 自定义接口
    interface NetEvevt {
        fun onNetChange(netMobile: Int)
    }
}
