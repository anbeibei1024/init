package com.dashen.init.presenter

import android.content.Context
import com.dashen.init.base.Presenter
import com.dashen.init.common.networkJava.HttpUtil
import com.dashen.init.common.networkJava.helper.RetrofitHelper
import com.dashen.init.common.networkJava.model.Translation1
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
class MainHelper(var context: Context, val mMainView: MainView) : Presenter() {
    private val mContext: WeakReference<Context> = WeakReference(context)
    private var exitTime: Long = 0//退出时间记录

    override fun onCreate() {}

    fun getUserInfo(requestParam: InitDataNoParamRequest) {
        val dealData = HttpUtil.getInstance().dataDealWith(GsonUtils.toJson(requestParam))

        val observable1 = RetrofitHelper.createRequest(RequestInterface::class.java).getUserInfo(dealData)

        HttpUtil.getInstance().request(observable1, object : HttpUtil.OnResultListener<Translation1> {
            override fun onSuccess(result: Translation1) {
                LogUtils.e("===============getUserInfo==========onSuccess=========")
                //                System.out.println(result.getTranslateResult().get(0).get(0).getTgt());
            }

            override fun onError(error: Throwable) {
                LogUtils.e("===============getUserInfo=============onError======")
            }

            override fun onMessage() {

            }
        })
    }


    /**
     * 退出销毁
     */
    override fun onDestroy() {
    }

}