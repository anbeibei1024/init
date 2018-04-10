package com.dashen.init.view.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.dashen.init.App
import com.dashen.utils.LogUtils

/**
 * Created by MSI on 2017/6/9.
 */

class NetStatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (ConnectivityManager.CONNECTIVITY_ACTION == action) {

            // 获取网络连接管理器
            val connectivityManager = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // 获取当前网络状态信息
            val info = connectivityManager.activeNetworkInfo

            if (info != null && info.isAvailable) {
                LogUtils.e("record***WsManager-->"+"-----"+"监听到可用网络切换,调用重连方法")
//                WsManager.instance.reconnect()//wify 4g切换重连websocket
                //TODO 网络变化  断开了连接
//                RxBus.instance.post(MessageEvent(Constant.STOP_AUDIO,"stop"))
            }

        }
    }
}
