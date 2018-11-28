package com.beibei.init.common.newNetwork.convert

import com.beibei.init.common.network.model.ResultBean
import com.beibei.init.common.newNetwork.exception.ServerException
import com.dashen.utils.LogUtils
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset


/**
 * 项目名称：demeter
 * 包名：com.beibei.init.common.network.convert
 * 创建人：dashen
 * 创建时间：2017/3/8 17:57
 * 类描述：响应体,输出获取的json串，并处理异常
 * 备注：
 */
class MyGsonResponseBodyConverter<T>(private val mGson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val response = value.string()
        LogUtils.d("----api获取数据成功---json---" + response)
        val re = mGson.fromJson(response, ResultBean::class.java)
        //todo 关注的重点，自定义响应码中非0的情况，一律抛出ApiException异常。
        //这样，我们就成功的将该异常交给onError()去处理了。
        if (re.errcode != 0) {//服务器返回错误信息
            value.close()
            throw ServerException(re.message!!, re.errcode)
        } else {
            val mediaType = value.contentType()
            val charset = if (mediaType != null) mediaType.charset(UTF_8) else UTF_8
            val bis = ByteArrayInputStream(response.toByteArray())
            val reader = InputStreamReader(bis, charset)
            val jsonReader = mGson.newJsonReader(reader)
            try {
                return adapter.read(jsonReader)
            } finally {
                value.close()
            }
        }
    }

    companion object {
        private val UTF_8 = Charset.forName("UTF-8")
    }
}
