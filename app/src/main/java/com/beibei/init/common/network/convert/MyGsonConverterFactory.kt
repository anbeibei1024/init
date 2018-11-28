package com.beibei.init.common.newNetwork.convert

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * 项目名称：demeter
 * 包名：com.beibei.init.common.network.convert
 * 创建人：dashen
 * 创建时间：2017/3/9 09:50
 * 类描述：gson转换工厂
 * 备注：
 */
class MyGsonConverterFactory private constructor(private val gson: Gson?) : Converter.Factory() {

    init {
        if (gson == null) throw NullPointerException("gson == null")
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *> {
        val adapter = gson!!.getAdapter(TypeToken.get(type!!))
        return MyGsonResponseBodyConverter(gson, adapter)//创建响应转换器
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody> {
        val adapter = gson!!.getAdapter(TypeToken.get(type!!))
        return MyGsonRequestBodyConverter(gson, adapter)//创建请求转换器
    }

    companion object {

        @JvmOverloads fun create(gson: Gson = Gson()): MyGsonConverterFactory {
            return MyGsonConverterFactory(gson)
        }
    }

}
