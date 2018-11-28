package com.beibei.init.common.newNetwork.service

import com.beibei.init.common.network.model.ResultBean
import com.beibei.init.common.networkJava.model.UserInfoBean
import com.beibei.init.common.newNetwork.model.*
import com.beibei.init.common.newNetwork.request.EditUserInfoRequest
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Created by beibei on 2018/3/19.
 *
 * @Header & @Headers
1. 使用场景：@Header用于添加不固定的请求头，@Headers用于添加固定的请求头
2. 使用方式：@Header作用于方法的参数；@Headers作用于方法

Get  --> 参数注解  @Query @QueryMap
Post --> 参数注解  @Field @FieldMap 搭配标记注解  @FormUrlEncoded  请求体Form表单（常规键值对）
参数注解  @Part  @PartMap  搭配标记注解  @Multipart       请求体支持文件上传的Form表单

Put 与 Post 请求的区别 http://blog.csdn.net/mad1989/article/details/7918267

参数注解  @Path  URL地址的缺省值
参数注解  @Body  以Post方式 传递 自定义数据类型 给服务器
参数注解  @Url   直接传入一个请求的 URL变量 用于URL设置
 */

interface ApiService {

    //get请求 无参数
    // 查询用户中心的用户信息 审核通过的语料库数量 libCount 用户总积分 totalPoint 已完成任务数 taskCount  GET
    @GET("app/getuserinfo")
    fun getMineCount(@Header("token") token: String): Observable<ResultBean<MyTaskCountBean?>>

    //get请求 有一到两个参数 列出全部
    //城市列表
    @GET("app/citylist")
    fun getCityList(@Query("provinceId") provinceId: String): Observable<ResultBean<ArrayList<CityBean>?>>

    //get请求 有两个参数 用map请求
    //任务中心列表
    @GET("app/tasklist")
    fun getTaskList(@QueryMap map: HashMap<String, Any>, @Header("token") token: String): Observable<ResultBean<TaskListBean?>>

    //post请求 上传文件
    @POST("app/upload")
    @Multipart
    fun upLoadFile(@Part requestBody: MultipartBody.Part?, @Header("token") token: String): Observable<ResultBean<PicFileBean?>>

    //post请求 传递自定义数据类型 @Body
    //修改用户信息  POST
    @POST("app/updateuser")
    fun modifyUserInfo(@Body request: EditUserInfoRequest, @Header("token") token: String): Observable<ResultBean<Any?>>

    //put
    //(任务中心)领取任务
    @PUT("app/{id}/receivetask")
    fun receiveTask(@Path("id") id: Int, @Header("token") token: String): Observable<ResultBean<Any?>>

    //get 获取轮播图片
    @GET("appnew/images")
    fun getBannerData(@Header("token") token: String): Observable<ResultBean<ArrayList<BannerItem>?>>

    //get 首页统计
    @GET("appnew/homestatistics")
    fun getHomeStatistics(@Header("token") token: String): Observable<ResultBean<HomeStatistics?>>

    //GET  版本控制
    @GET("appnew/version")
    fun getNewVersion(@Query("type") type: String, @Query("version") version: Int): Observable<ResultBean<VersionData?>>

    //get请求 有两个参数 用map请求
    //我的任务的一个库的任务列表
    @GET("app/persontasklist")
    fun getMyTaskList(@QueryMap map: HashMap<String, Any>, @Header("token") token: String): Observable<ResultBean<MyTaskListBean?>>

    //问题反馈
    @GET("appnew/feedback")
    fun feedback(@QueryMap map: HashMap<String, String>, @Header("token") token: String): Observable<ResultBean<Any?>>

    //get请求 URL地址的缺省值
    //获取验证码
    @GET("app/{mobile}/getvalidatecode")
    fun sendCode(@Path("mobile") mobile: String): Observable<ResultBean<Any?>>

    //post请求 常规键值对 用map
    //用户密码登录 POST
    @POST("app/pwdlogin")
    @FormUrlEncoded
    fun toLoginPwd(@FieldMap map: HashMap<String, Any>): Observable<ResultBean<LoginDataBean?>>

    //post请求 常规键值对 用map
    //手机验证码登录 POST
    @POST("app/codelogin")
    @FormUrlEncoded
    fun toLoginCode(@FieldMap map: HashMap<String, Any>): Observable<ResultBean<LoginDataBean?>>

    //get请求 URL地址的缺省值
    //获取验证码
    @GET("appnew/{id}/sentence")
    fun sentenceDetails(@Path("id") id: Int, @Header("token") token: String): Observable<ResultBean<SentenceDetailsBean?>>

    @GET("api/login/selectUser.do")
    fun getUserInfo1(@QueryMap map: HashMap<String, String>): Observable<ResultBean<UserInfoBean?>>


    //get请求 无参数
    @GET("api/version/getVersionInfo.do")
    fun getUpdateInfo(@QueryMap map: HashMap<String, String>): Observable<ResultBean<UpdateBean?>>
}