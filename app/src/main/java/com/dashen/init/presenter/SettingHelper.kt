package com.dashen.init.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import com.dashen.init.common.newNetwork.HttpUtil
import com.dashen.init.common.newNetwork.helper.RetrofitHelper
import com.dashen.init.common.newNetwork.model.UpdateBean
import com.dashen.init.common.newNetwork.request.VersionUpdateRequest
import com.dashen.init.common.newNetwork.service.ApiService
import com.dashen.init.presenter.viewinter.SettingView
import com.dashen.utils.GsonUtils
import com.dashen.utils.LogUtils
import java.lang.ref.WeakReference

/**
 * Created by anbeibei on 2018/4/9.
 */

class SettingHelper(var context: Context, val mView: SettingView, lifecycle: Lifecycle) : LifecycleObserver {
    private val mContext: WeakReference<Context> = WeakReference(context)

    private var exitTime: Long = 0//退出时间记录
    private lateinit var lifecycle: Lifecycle

    /**
     * 获取版本更新
     * @param params
     */
    fun getUpdateInfo(request: VersionUpdateRequest) {
        val dealData = HttpUtil.dealData(GsonUtils.toJson(request))
        HttpUtil.request(RetrofitHelper.createApiRequest(ApiService::class.java).getUpdateInfo(dealData),
                object : HttpUtil.OnResultListener<UpdateBean?> {
                    override fun onSuccess(t: UpdateBean?) {
                        t?.let { mView.getUpdateInfoSuccess(it) }
                    }

                    override fun onError(error: Throwable, msg: String) {
                    }

                    override fun onMessage(errorCode: Int, msg: String) {
                        mView.getUpdateInfoMessage(errorCode, msg)
                    }

                })
    }

//    fun getUserInfo(requestParam: InitDataNoParamRequest) {
//        val dealData = HttpUtil.dealData(GsonUtils.toJson(requestParam))
//        HttpUtil.request(RetrofitHelper.createApiRequest(ApiService::class.java).getUserInfo1(dealData),
//                object : HttpUtil.OnResultListener<UserInfoBean?> {
//                    override fun onSuccess(t: UserInfoBean?) {
//                        LogUtils.e("-------refreshAvatar--t--" + t?.userUrl)
//                        mView.setText(t?.realName?:"---")
//                    }
//
//                    override fun onError(error: Throwable, msg: String) {
//                        LogUtils.e("-----------$msg")
//                        ToastUtils.showToast(context, msg)
//
////                        mView.onUserInfoError(error, msg)
//                    }
//
//                    override fun onMessage(errorCode: Int, msg: String) {
//                        LogUtils.e("-----------$msg")
//                    }
//                })


//        val dealData = HttpUtil.getInstance().dataDealWith(GsonUtils.toJson(requestParam))
//
//        val observable1 = RetrofitHelper.createRequest(RequestInterface::class.java).getUserInfo1(dealData)
//
//        HttpUtil.getInstance().request(observable1, object : HttpUtil.OnResultListener<UserInfoBean> {
//            override fun onSuccess(result: UserInfoBean) {
//                LogUtils.e("===============getUserInfo==========onSuccess=========")
//                //                System.out.println(result.getTranslateResult().get(0).get(0).getTgt());
//                mView.setText(result.realName)
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
//    }

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
