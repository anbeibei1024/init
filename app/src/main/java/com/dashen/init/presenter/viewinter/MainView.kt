package com.dashen.init.presenter.viewinter

import com.dashen.init.base.MvpView
import com.dashen.init.common.newNetwork.model.BannerItem
import com.dashen.init.common.newNetwork.model.HomeStatistics
import com.dashen.init.common.newNetwork.model.PushAdBean

/**
 * Created by anbeibei on 2018/4/9.
 */
interface MainView : MvpView {

    //获取首页广告
    fun onGetPushAdSuccess(t: PushAdBean){}

    fun onGetPushAdError(error: Throwable, msg: String){}

    fun onGetPushAdMeassage(errorCode: Int, msg: String){}

    //获取首页统计数据
    fun getStatisticsSuccess(t: HomeStatistics){}

    fun getStatisticsError(error: Throwable, msg: String){}

    fun refreshTaskCenter(){}
    fun toTetTaskRefresh(){}

    //获取到的轮播图数据
    fun getBannerDataSuccess(banner:ArrayList<BannerItem>){}
    fun getBannerDataError(){}

}