package com.beibei.init.presenter

import android.content.Context
import android.util.Log
import com.beibei.init.base.MessageEvent
import com.beibei.init.base.Presenter
import com.beibei.init.common.constant.Constant
import com.beibei.init.presenter.viewinter.LoginView
import com.dashen.demeter.common.utils.RxBus
import io.reactivex.functions.Consumer
import java.lang.ref.WeakReference

/**
 * 登陆
 *
 * Created by anbeibei on 2018/4/10.
 */
class LoginHelper(private val context: Context, private val mView: LoginView) : Presenter() {

    private val mContext: WeakReference<Context> = WeakReference(context)

    override fun onCreate() {}

    override fun onDestroy() {}

    fun initRxBus() {
        registerRxBus(MessageEvent::class.java, object : Consumer<MessageEvent> {
            override fun accept(messageEvent: MessageEvent) {
                when (messageEvent.tag) {
                    Constant.MINE_MODIFY_INFO -> {
//                        textView.text = "消息成功"
                    }
                }
            }
        })
    }

    //注册
    private fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>) {
        val disposable = RxBus.instance.doSubscribe(eventType, action, object : Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                Log.e("NewsMainPresenter", t.toString())
            }
        })
        RxBus.instance.addSubscription(this, disposable)
    }
}