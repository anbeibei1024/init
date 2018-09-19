package com.dashen.init.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import com.dashen.init.common.networkJava.HttpUtil
import com.dashen.init.common.networkJava.helper.RetrofitHelper
import com.dashen.init.common.networkJava.model.UserInfoBean
import com.dashen.init.common.networkJava.request.InitDataNoParamRequest
import com.dashen.init.common.networkJava.request.RequestInterface
import com.dashen.init.presenter.viewinter.MainView
import com.dashen.utils.GsonUtils
import com.dashen.utils.LogUtils
import java.lang.ref.WeakReference

/**
 * Created by anbeibei on 2018/4/9.
 */

/**
 * 项目名称:  Demeter-Android
 * 包名:     com.dashen.demeter.presenter
 * 创建人 :   whj
 * 创建时间:  2017/8/14 15:58
 * 类描述:    主页业务逻辑类
 * 备注:
 */
class MainHelper(var context: Context, val mMainView: MainView, lifecycle: Lifecycle) : LifecycleObserver {
    private val mContext: WeakReference<Context> = WeakReference(context)
    private var exitTime: Long = 0//退出时间记录

    fun getUserInfo(requestParam: InitDataNoParamRequest) {
        val dealData = HttpUtil.getInstance().dataDealWith(GsonUtils.toJson(requestParam))

        val observable1 = RetrofitHelper.createRequest(RequestInterface::class.java).getUserInfo1(dealData)

        HttpUtil.getInstance().request(observable1, object : HttpUtil.OnResultListener<UserInfoBean> {
            override fun onSuccess(result: UserInfoBean) {
                LogUtils.e("===============getUserInfo==========onSuccess=========")
                //                System.out.println(result.getTranslateResult().get(0).get(0).getTgt());
                android.os.Handler().postDelayed({
                    mMainView.setText(result.realName)
                }, 10000)

            }

            override fun onError(error: Throwable) {
                LogUtils.e("===============getUserInfo=============onError======")
            }

            override fun onMessage() {

            }
        })
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        LogUtils.d("onActivityCreate")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        LogUtils.d("onActivityDestroy")
    }
}