package com.beibei.init.common.networkJava.helper;

import com.beibei.init.common.constant.Constant;
import com.joyou.smartcity.common.newNetwork.helper.OkHttpClientHelper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private OkHttpClient mClient = OkHttpClientHelper.Companion.getInstance().getOkHttpClient();

    private static volatile RetrofitHelper retrofitHelper = null;

    private RetrofitHelper() {
    }

    private static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (retrofitHelper == null) {
                    retrofitHelper = new RetrofitHelper();
                }
            }
        }
        return retrofitHelper;
    }

    private Retrofit retrofit = null;

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.HOST) // 设置 网络请求 Url
                    .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                    .client(mClient)   //关联自定义的OkHttp
                    .build();
        }
        return retrofit;
    }

    public static <T> T createRequest(Class<T> requestInterfaceClass) {
        return RetrofitHelper.getInstance()
                .getRetrofit()
                .create(requestInterfaceClass);
    }
}
