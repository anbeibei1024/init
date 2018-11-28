package com.beibei.init

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.support.multidex.MultiDexApplication
import cn.testin.analysis.data.TestinDataApi
import cn.testin.analysis.data.TestinDataConfig
import com.beibei.init.common.constant.Constant
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.squareup.leakcanary.LeakCanary



/**
 * Created by anbeibei on 2018/4/8.
 */

class App : MultiDexApplication() {

    /**
     * 静态代码
     */
    companion object {
        val context: Context
            get() = application!!.applicationContext


        private var application: App? = null
        var sp: SharedPreferences? = null//sp存储
        var isLogin: Boolean = false//是否登录
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        sp = getSharedPreferences(Constant.FILE_NAME, Context.MODE_PRIVATE)

        initLeakCanary()
        initBugOut()
    }


    /**
     * 初始化内存泄漏检测工具
     */
    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    /**
     * 初始化BugOut
     */
    private fun initBugOut() {
        //设置启动参数
        val bugOutConfig = TestinDataConfig()
                .openShake(false)//设置是否打开摇一摇反馈bug功能
                .collectCrash(true)//设置是否收集app崩溃信息
                .collectANR(true)//设置是否收集ANR异常信息
                .collectLogCat(false)//设置是否收集logcat系统日志
                .collectUserSteps(true)//设置是否收集用户操作步骤
        //SDK初始化
        TestinDataApi.init(this, Constant.bugOutAK, bugOutConfig)
    }


    /**
     * 获取sp存储
     * @return
     */
    fun getSharedPreferences(): SharedPreferences = sp!!

    fun getLoginStatus(): Boolean {
        return isLogin
    }


    /**
     * 配置fresco相关
     *
     * @param applicationContext
     */
    private fun initFresco(applicationContext: Context) {
        val configBuilder = ImagePipelineConfig.newBuilder(applicationContext)
        //初始化MemoryCacheParams对象。从字面意思也能看出，这是内存缓存的Params
        val bitmapCacheParams = MemoryCacheParams(
                // 内存缓存最大值，这里用最大运行内存的三分之一
                Runtime.getRuntime().maxMemory().toInt() / 3,
                // 内存缓存单个文件最大值，这里就用Integer的最大值了。
                Integer.MAX_VALUE,
                // 可以释放的内存缓存的最大值，当内存缓存到了上面设置的最大值，最多可以释放多少内存
                Runtime.getRuntime().maxMemory().toInt() / 3,
                //可以被释放缓存的文件数
                Integer.MAX_VALUE,
                //Max cache entry size.
                Integer.MAX_VALUE)
        //初始化DiskCacheConfig对象
        val diskCacheConfig = DiskCacheConfig.newBuilder(applicationContext)
                //设置磁盘缓存路径
                .setBaseDirectoryPath(applicationContext.externalCacheDir)
                //设置磁盘缓存文件夹名
                .setBaseDirectoryName("imagecache")
                //设置磁盘缓存最大值
                .setMaxCacheSize((300 * ByteConstants.MB).toLong())
                .build()
        //getImagePipelineConfig设置属性
        configBuilder
                //设置内存缓存的Params
                .setBitmapMemoryCacheParamsSupplier { bitmapCacheParams }
                //设置磁盘缓存的配置
                .setMainDiskCacheConfig(diskCacheConfig)
                //设置图片压缩质量，不设置默认为ARGB_8888
                .setBitmapsConfig(Bitmap.Config.RGB_565).isDownsampleEnabled = true

        Fresco.initialize(this, configBuilder.build())
    }
}
