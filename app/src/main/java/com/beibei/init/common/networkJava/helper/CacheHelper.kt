package com.joyou.smartcity.common.newNetwork.helper

import com.beibei.init.App
import okhttp3.Cache
import java.io.File

/**
 * Created by beibei on 2018/3/19.
 *
 * 缓存帮助类
 */


class CacheHelper private constructor() {
    private var mCache: Cache? = null

    init {
        cacheFile = File(App.context.cacheDir.absolutePath, "mycache")
        if (!cacheFile!!.exists()) {
            cacheFile!!.mkdir()
        }
    }

    //返回缓存对象
    val cache: Cache
        get() {
            if (mCache == null)
                mCache = Cache(cacheFile!!, maxSize)
            return mCache!!
        }

    companion object {
        //设置缓存目录
        private var cacheFile: File? = null
        private val maxSize = (8 * 1024 * 1024).toLong()
        private var helper: CacheHelper? = null

        val instance: CacheHelper
            get() {
                if (helper == null) {
                    synchronized(CacheHelper::class.java) {
                        if (helper == null) {
                            helper = CacheHelper()
                        }
                    }
                }
                return helper!!
            }
    }
}
