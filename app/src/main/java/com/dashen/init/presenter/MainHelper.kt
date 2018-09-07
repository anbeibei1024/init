package com.dashen.init.presenter

import android.content.Context
import com.dashen.init.base.Presenter
import com.dashen.init.common.constant.Constant
import com.dashen.init.common.newNetwork.HttpUtil
import com.dashen.init.common.newNetwork.helper.RetrofitHelper
import com.dashen.init.common.newNetwork.model.BannerItem
import com.dashen.init.common.newNetwork.service.RequestIntf
import com.dashen.init.presenter.viewinter.MainView
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

    /**
     * 获取轮播图片
     */
    fun getBannerData() {
//        HttpUtil.request(RetrofitHelper.getRequest(RequestIntf::class.java).getBannerData(Constant.TOKEN),
//                object : HttpUtil.OnResultListener<ArrayList<BannerItem>?> {
//                    override fun onSuccess(t: ArrayList<BannerItem>?) {
//                        t?.let { mMainView.getBannerDataSuccess(it) }
//                    }
//
//                    override fun onError(error: Throwable, msg: String) {
//                        mMainView.getBannerDataError()
//                    }
//
//                    override fun onMessage(errorCode: Int, msg: String) {
//                    }
//
//                })
    }



    /**
     * 退出销毁
     */
    override fun onDestroy() {
    }

}