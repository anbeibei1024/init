package com.beibei.init.common.networkJava.request;


import com.beibei.init.common.network.model.ResultBean;
import com.beibei.init.common.networkJava.model.Translation;
import com.beibei.init.common.networkJava.model.Translation1;
import com.beibei.init.common.networkJava.model.UserInfoBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 创建 用于描述网络请求 的接口
 */
public interface RequestInterface {

    @GET("api/login/selectUser.do")
    Observable<ResultBean<Translation1>> getUserInfo(@QueryMap HashMap<String, String> map);
    @GET("api/login/selectUser.do")
    Observable<ResultBean<UserInfoBean>> getUserInfo1(@QueryMap HashMap<String, String> map);


    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<Translation> getCall();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法


    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Observable<Translation1> getCall(@Field("i") String targetSentence);
    //采用@Post表示Post方法进行请求（传入部分url地址）
    // 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
    // 需要配合@Field 向服务器提交需要的字段



}
