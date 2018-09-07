package com.dashen.init.common.constant

import com.dashen.init.App

/**
 * Created by anbeibei on 2018/4/8.
 */

object Constant {
    /**=============================服务器加密============================== */

    const val HOST = "http://111.207.84.82:8088/"//测试域名
    var MOBILE = ""//用户id
    var TOKEN = "201804072213289532d872b5e096b40858e3e9b96bea01bb8"
    var PHOTO = ""//用户昵称
    var UTYPE = ""//用户头像url

    /**
     * 验签partner,key
     */
    val PARTNER = "1000001"
    /**
     * 验签key
     */
    val PARTNER_KEY = "18fa0501640c40eaab7a9221bf90313d"//正式key

    val FILE_NAME = App.context.packageName//包名存储
    /**=============================三方平台key============================== */
    val BUGOUT_APPKEY = "28ade283a41de5e9fee28467a1d4e606"
    var UMENG_KEY = "5aaf6fa8f43e48346500018e"

    /**
     * ==========================公共标识==(0-99)============================
     */
    /**
     * 请求权限返回码
     */
   const val REQUEST_PERMISSION1 = 0

    /**==================界面数据传递标识==(100-199)================================= */

    /** =============================聊天界面(200---250)============================== */

    /** =============================我的界面(251---300)============================== */
    val MINE_MODIFY_INFO = 260    //修改个人信息成功

}