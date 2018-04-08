package com.dashen.init.common.constant

/**
 * Created by anbeibei on 2018/4/8.
 */

object Constant {
    /**=============================服务器加密============================== */

    val HOST = "http://vcas.newtranx.com:8088/vcas/v1/"//ip

    var MOBILE = ""//用户id
    var TOKEN = ""
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

    /**=============================三方平台key============================== */
    val BUGOUT_APPKEY = "28ade283a41de5e9fee28467a1d4e606"
    var UMENG_KEY = "5aaf6fa8f43e48346500018e"

    /**
     * ==========================公共标识==(0-99)============================
     */
    /**
     * 请求权限返回码
     */
    val REQUEST_PERMISSION1 = 0
    /**
     * 请求权限返回码
     */
    val REQUEST_PERMISSION2 = 1
    /**
     * 请求定位权限
     */
    val REQUEST_PERMISSION_LOCATION = 2
    /**
     * 请求定位权限
     */
    val REQUEST_PERMISSION_LOCATION_REPAIR = 3
    /**
     * 请求定位权限
     */
    val REQUEST_PERMISSION_LOCATION_PROTECT = 4


    val AUTHORITY = "com.dashen.demeter.FileProvider"
    /**
     * userid存储标识
     */
    val KEY_MOBILE = "mobile"
    /**
     * token存储标识
     */
    val KEY_TOKEN = "token"
    /**
     * 头像ID存储标识
     */
    val KEY_PHOTO = "photo"
    /**
     * 身份种类存储标识
     */
    val KEY_U_TYPE = "utype"

    /**
     * 身份种类存储标识
     */
    val IS_INFO_COMPLETE = "isPerfectInformation"
    /**
     * 第一次打开应用标示
     */
    val FIRST_OPEN = "first_open"
    /**
     * 版本更新标识  是否有新版本  0没有  1  有   默认0
     */
    var VERSION_FLAG = "0"
    /**
     * 版本更新地址
     */
    var UPDATE_ADDRESS = ""
    /**
     * 版本更新新的版本号
     */
    var VERSION_NAME = ""

    /**
     * 是否开启极光推送震动
     */
    var IS_OPEN_VIBRATE = "is_open_vibrate"

    val shareTitle = "躺着能赚钱？新译众包"
    val shareContent = "以后你的零花钱我们包了"

    /**==================界面数据传递标识==(100-199)================================= */

    /** =============================聊天界面(200---250)============================== */
    /**
     * 发送文本消息
     */
    val CHAT_SEND_TEXT = 200
    /**
     * 用户类型  用户发送
     */
    val CHAT_CLIENT = 201
    /**
     * 服务类型  机器返回
     */
    val CHAT_SERVICE = 202
    /**
     * 系统消息 语言设置记录
     */
    val CHAT_LANGUAGE_SET = 203
    /**
     * 系统消息 间隔时间记录
     */
    val CHAT_TIME_TOO_LONG = 210
    /**
     * 自动播放翻译通知
     */
    val AUTO_PLAY_VOICE = 204

    val STOP_AUDIO = 205
    /**
     * rxbus 发送录音识别结果
     */
    val RECORD_RESULT = 206
    /**
     * rxbus 清除缓存 刷新列表
     */
    val CLEAR_DATA = 207
    /**
     * rxbus 翻译页面 语音听写 开启状态(成功、失败)及听写识别状态(成功、失败) 到主页面通知
     */
    val LISTENING_FAILED = 208
    /**
     * rxbus 主页翻译 长按 无网络
     */
    val NO_NETWORK = 209
    /**
     * rxbus 主页 区别于语言方向的按钮 识别出结果通知
     */
    val MAIN_OTHER_RECOGNIZE = 211

    val LOGIN_TYPE_FORGET = 212//登录界面---忘记密码
    val LOGIN_TYPE_REGISTER = 213//登录界面---注册新用
    val REFRESH_TASK_CENTER = 214//采集审核上传成功后 刷新任务中心
    val GET_TASK_REFRESH = 215//领取任务 刷新任务中心、我的任务,当前库列表 通知
    val REFRESH_TASK_DETAIL_MINE = 216//任务中的一句提交成功后，刷新任务详情页面
    val REFRESH_TASK_DETAIL_MINE1 = 217//录制完成功后，刷新任务详情页面
    val VERNACULAR_VERIFY = 218//采集审核后，刷新任务中心的任务列表
    val REFRESH_TASK_LIB_LIST = 219//一个任务的全部句子提交后刷新任务库

    /** =============================我的界面(251---300)============================== */
    val MINE_MODIFY_AVATAR = 252//修改头像----成功
    val REQUEST_NAME = 253   //跳转编辑姓名页面
    val REQUEST_LANGUAGE1 = 254   //跳转语言列表页面
    val REQUEST_LANGUAGE2 = 255   //跳转语言列表页面
    val REQUEST_LANGUAGE3 = 256   //跳转语言列表页面
    val REQUEST_EDUCATION = 257   //跳转学历列表页面
    val REQUEST_AREA = 258        //跳转省份列表页面
    val REQUEST_CITY = 259        //跳转城市列表页面
    val MINE_MODIFY_INFO = 260    //修改个人信息成功

}