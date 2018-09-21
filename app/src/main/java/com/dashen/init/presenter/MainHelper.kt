package com.dashen.init.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import com.dashen.init.common.networkJava.model.UserInfoBean
import com.dashen.init.common.networkJava.request.InitDataNoParamRequest
import com.dashen.init.common.newNetwork.HttpUtil
import com.dashen.init.common.newNetwork.helper.RetrofitHelper
import com.dashen.init.common.newNetwork.service.RequestIntf
import com.dashen.init.presenter.viewinter.MainView
import com.dashen.utils.GsonUtils
import com.dashen.utils.LogUtils
import com.dashen.utils.ToastUtils
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
    private lateinit var lifecycle: Lifecycle

    fun getUserInfo(requestParam: InitDataNoParamRequest) {
        val dealData = HttpUtil.dealData(GsonUtils.toJson(requestParam))
        HttpUtil.request(RetrofitHelper.getRequest(RequestIntf::class.java).getUserInfo1(dealData),
                object : HttpUtil.OnResultListener<UserInfoBean?> {
                    override fun onSuccess(t: UserInfoBean?) {
                        LogUtils.e("-------refreshAvatar--t--" + t?.userUrl)
                        mMainView.setText(t?.realName?:"---")
                    }

                    override fun onError(error: Throwable, msg: String) {
                        LogUtils.e("-----------$msg")
                        ToastUtils.showToast(context, msg)

//                        mView.onUserInfoError(error, msg)
                    }

                    override fun onMessage(errorCode: Int, msg: String) {
                        LogUtils.e("-----------$msg")
                    }
                })


//        val dealData = HttpUtil.getInstance().dataDealWith(GsonUtils.toJson(requestParam))
//
//        val observable1 = RetrofitHelper.createRequest(RequestInterface::class.java).getUserInfo1(dealData)
//
//        HttpUtil.getInstance().request(observable1, object : HttpUtil.OnResultListener<UserInfoBean> {
//            override fun onSuccess(result: UserInfoBean) {
//                LogUtils.e("===============getUserInfo==========onSuccess=========")
//                //                System.out.println(result.getTranslateResult().get(0).get(0).getTgt());
//                mMainView.setText(result.realName)
//
//            }
//
//            override fun onError(error: Throwable) {
//                LogUtils.e("===============getUserInfo=============onError======")
//            }
//
//            override fun onMessage() {
//
//            }
//        })
    }

//    //然后再相应的回调方法中使用下面代码判断，保证数据回调回来，当前activity是存在的
//    if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
//        //这里只是示例，不一定是CREATED
//    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        LogUtils.d("onActivityCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        LogUtils.d("onActivityDestroy")
    }
}