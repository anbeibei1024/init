package com.beibei.init.common.newNetwork.helper

import com.beibei.init.common.constant.Constant
import com.beibei.init.common.newNetwork.convert.MyGsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by beibei on 2018/3/19.
 */

class RetrofitHelper private constructor() {

    private val mClient: OkHttpClient = OkHttpClientHelper.instance.okHttpClient
    private var mRetrofit: Retrofit? = null

    //步骤4：创建Retrofit对象
    fun getRetrofit(): Retrofit {
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                    .baseUrl(Constant.HOST)
                    .addConverterFactory(MyGsonConverterFactory.create())  //添加Gson支持
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //添加RxJava支持
                    .client(mClient)                                            //关联okhttp
                    .build()
        }
        return mRetrofit!!
    }

    companion object {
        private var helper: RetrofitHelper? = null

        //单例 保证对象唯一
        val instance: RetrofitHelper
            get() {
                if (helper == null) {
                    synchronized(RetrofitHelper::class.java) {
                        if (helper == null) {
                            helper = RetrofitHelper()
                        }
                    }
                }
                return helper!!
            }

        // 步骤5：创建 网络请求接口 的实例
        fun <T> createApiRequest(clazz: Class<T>): T {
            return RetrofitHelper.instance
                    .getRetrofit()
                    .create(clazz)
        }
    }
}