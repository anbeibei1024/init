package com.beibei.init.common.utils

import android.util.Base64
import com.beibei.init.common.constant.Constant
import com.dashen.utils.BaSe64
import org.apaches.commons.codec.digest.DigestUtils
import java.io.UnsupportedEncodingException
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * author: anbeibei
 *
 * date: 2018/5/9
 *
 * eventDesc:
 */


object SignUtil {

    // 商户的密钥
    var key = Constant.PARTNER_KEY
    // 字符编码格式 目前支持 gbk 或 utf-8
    var input_charset = "utf-8"
    private val ALGORITHM = "RSA"
    private val SIGN_ALGORITHMS = "SHA1WithRSA"
    private val DEFAULT_CHARSET = "UTF-8"

    /**
     * 签名字符串

     * @param text 需要签名的字符串
     * *
     * @return 签名结果
     */
    fun sign(text: String, partner: String): String {
        var text = text
        text += key
        return DigestUtils.md5Hex(getContentBytes(text, input_charset))
    }

    /**
     * 签名字符串

     * @param text 需要签名的字符串
     * *
     * @return 签名结果
     */
    fun verify(text: String, partner: String): String {
        //	    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset))
    }

    /**
     * @param content
     * *
     * @param charset
     * *
     * @return
     * *
     * @throws UnsupportedEncodingException
     */
    private fun getContentBytes(content: String, charset: String?): ByteArray {
        if (charset == null || "" == charset) {
            return content.toByteArray()
        }
        try {
            return content.toByteArray(charset(charset))
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset)
        }

    }

    fun signPay(content: String, privateKey: String): String? {
        try {
            val priPKCS8 = PKCS8EncodedKeySpec(
                    BaSe64.decode(privateKey))
            val keyf = KeyFactory.getInstance(ALGORITHM)
            val priKey = keyf.generatePrivate(priPKCS8)

            val signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS)

            signature.initSign(priKey)
            signature.update(content.toByteArray(charset(DEFAULT_CHARSET)))

            val signed = signature.sign()

            return BaSe64.encode(signed)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 数据加密处理
     */
    @JvmStatic
    fun dataDealWith(data: String): String {
        val sb = StringBuilder()
        sb.append("partner=" + Constant.PARTNER)
                .append("&appkey=" + Constant.PARTNER_KEY)
        if (data.isNotEmpty())
            sb.append("&data=$data")
        return SignUtil.verify(sb.toString(), Constant.PARTNER)
    }

    //Base64加密
    @Throws(UnsupportedEncodingException::class)
    fun getEncoder(str: ByteArray): String {
        return Base64.encodeToString(str, Base64.DEFAULT)
    }
    //Base64解密
    @Throws(UnsupportedEncodingException::class)
    fun getDecoder(encode: String): String {
        val asBytes = Base64.decode(encode, Base64.DEFAULT)
        return String(asBytes, Charsets.UTF_8)
    }

    private val MAC_NAME = "HmacSHA1"
    private val ENCODING = "UTF-8"

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return 返回被加密后的字符串
     * @throws Exception
     */
    @Throws(Exception::class)
    fun HmacSHA1Encrypt(encryptText: String,
                        encryptKey: String): ByteArray {
        val data = encryptKey.toByteArray(charset(ENCODING))
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        val secretKey = SecretKeySpec(data, MAC_NAME)
        // 生成一个指定 Mac 算法 的 Mac 对象
        val mac = Mac.getInstance(MAC_NAME)
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey)
        val text = encryptText.toByteArray(charset(ENCODING))
        // 完成 Mac 操作
        //        StringBuilder sBuilder = bytesToHexString( digest );
        return mac.doFinal(text)
    }
}
