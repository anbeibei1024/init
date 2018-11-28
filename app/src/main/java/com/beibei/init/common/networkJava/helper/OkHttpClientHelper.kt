package com.joyou.smartcity.common.newNetwork.helper

import com.beibei.init.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by beibei on 2018/3/19.
 */

class OkHttpClientHelper private constructor() {
    private val cache: Cache = CacheHelper.instance.cache
    private var mClient: OkHttpClient? = null

    //获取OKHttpClicent对象
    //                    .cache(cache)      //设置缓存
    val okHttpClient: OkHttpClient
        get() {
            if (mClient == null) {
                mClient = OkHttpClient.Builder()
                        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
//                        .addInterceptor(BasicParamsInterceptor.Builder()
//                                .addHeaderParam("token", Constant.TOKEN)
//                                .build())
                        .addInterceptor(HttpLoggingInterceptor().
                                setLevel(if (BuildConfig.DEBUG) {//Log输出请求url
                                    HttpLoggingInterceptor.Level.BODY
                                } else {
                                    HttpLoggingInterceptor.Level.NONE
                                }))
                        .build()
            }
            return mClient!!
        }

    companion object {
        private val TIMEOUT: Long = 10000  //超时时间
        private var clientHelper: OkHttpClientHelper? = null

        val instance: OkHttpClientHelper
            get() {
                if (clientHelper == null) {
                    synchronized(OkHttpClientHelper::class.java) {
                        if (clientHelper == null) {
                            clientHelper = OkHttpClientHelper()
                        }
                    }
                }
                return clientHelper!!
            }
    }
}
