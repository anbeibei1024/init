package com.dashen.init.common.newNetwork

import com.dashen.demeter.common.utils.NetUtils
import com.dashen.init.App
import com.dashen.init.base.ResultBean
import com.dashen.init.common.newNetwork.exception.ErrorType
import com.dashen.init.common.newNetwork.exception.ExceptionEngine
import com.dashen.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by beibei on 2018/3/15.
 */
object HttpUtil {
    /**
     *  所有的请求
     */
    fun <T> request(observable: Observable<ResultBean<T>>, resultListener: OnResultListener<T>) {
        setSubscriber(observable, object : Observer<ResultBean<T>> {
            override fun onComplete() {
                LogUtils.e("-----onCompleted-----" + "读取完成")
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(bean: ResultBean<T>) {
                resultListener.onSuccess(bean.data)
                resultListener.onMessage(bean.errorCode!!.toInt(), bean.message ?: "----exception---后台没给传message--")
            }

            override fun onError(error: Throwable) {
                val apiException = ExceptionEngine.handleException(error)
                LogUtils.e("---------exception--------errorCode:" + apiException.errorCode + ",message:" + apiException.message)
                var msg: String
                msg = when (apiException.errorCode) {
                    ErrorType.HTTP_ERROR -> "服务器连接异常，请稍后重试"//协议错误
                    ErrorType.NETWORD_ERROR -> {//网络错误
                        if (NetUtils.getNetWorkState(App.context) == NetUtils.NETWORK_NONE) { //网络连接
                            "网络不给力，请检查网络连接"
                        } else {  //服务器异常
                            "服务器连接异常，请稍后重试"
                        }
                    }
                    ErrorType.PARSE_ERROR -> "数据加载错误，请稍后再试"//解析错误
                    ErrorType.UNKNOWN -> "数据加载错误，请稍后再试"//未知错误
                    else -> apiException.message!!//服务器返回errocode不为0
                }
                resultListener!!.onError(apiException, msg)
                resultListener.onMessage(apiException.errorCode, msg)
            }
        })
    }

    //文件转为可上传的文件流体
    fun covertFile2Body(fileParamName: String, file: File): MultipartBody.Part {
        //构建requestbody
        val requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file)
        //将resquestbody封装为MultipartBody.Part对象
        return MultipartBody.Part.createFormData(fileParamName, file.name, requestFile)
    }

    //订阅事件
    private fun <T> setSubscriber(observable: Observable<ResultBean<T>>, observer: Observer<ResultBean<T>>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    //接口回调
    interface OnResultListener<T> {
        /**
         * 成功回调方法
         * @param t
         */
        fun onSuccess(t: T?)

        /**
         * 失败回调方法
         * @param error
         * *
         * @param msg
         */
        fun onError(error: Throwable, msg: String)

        /**
         * 输出msg(肯定会执行)
         */
        fun onMessage(errorCode: Int, msg: String)
    }
}