package com.beibei.init.common.utils

import android.app.Activity
import android.content.Context
import com.dashen.utils.LogUtils
import java.util.*

/**
 * 管理activity
 * Created by wuhuijie on 2016/6/27.
 */

class ActivityManagerUtils private constructor() {

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (!mActivityStack!!.contains(activity)) {
            mActivityStack!!.add(activity)
        }
    }

    /**
     * 获取栈顶Activity
     */
    val topActivity: Activity
        get() {
            val activity = mActivityStack!!.lastElement()
            return activity
        }

    /**
     * 结束栈顶Activity
     */
    fun killTopActivity() {
        val activity = mActivityStack!!.lastElement()
        killActivity(activity)
    }

    /**
     * 结束指定的Activity  结束某个activity时当前界面不能用 finish()
     */
    fun killActivity(activity: Activity?) {
        var activity = activity
        if (activity != null && mActivityStack!!.contains(activity)) {
            mActivityStack!!.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 移除栈内空间
     */
    fun removeActivity(activity: Activity?) {
        var activity = activity
        if (activity != null && mActivityStack!!.contains(activity)) {
            mActivityStack!!.remove(activity)
            activity = null
        }
    }

    /**
     * 结束指定类名的Activity  结束某个activity时当前界面不能用 finish()
     */
    fun killActivity(cls: Class<*>) {
        val li = mActivityStack!!.iterator()
        while (li.hasNext()) {
            var activity: Activity? = li.next()
            if (activity != null && activity.javaClass == cls) {
                li.remove()
                activity.finish()
                activity = null
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun killAllActivity() {
        val li = mActivityStack!!.iterator()
        while (li.hasNext()) {
            var activity: Activity? = li.next()
            if (activity != null) {
                li.remove()
                activity.finish()
                activity = null
            }
        }
        mActivityStack!!.clear()
    }

    /**
     * 结束除指定Activity的其他所有Activity
     */
    fun killOtherActivity(cls: Class<*>) {
        val li = mActivityStack!!.iterator()
        LogUtils.d("-----------killOtherActivity----所有的-------" + mActivityStack!!.size + "///" + mActivityStack!!.toString())
        while (li.hasNext()) {
            var activity: Activity? = li.next()
            if (activity != null && activity.javaClass != cls) {
                li.remove()
                activity.finish()
                LogUtils.d("-----------killOtherActivity-----------" + activity.localClassName)
                activity = null
            }
        }
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        try {
            killAllActivity()
            val activityMgr = context
                    .getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            activityMgr.restartPackage(context.packageName)
//            System.exit(0)
        } catch (e: Exception) {

        }

    }

    companion object {
        private var mActivityStack: Stack<Activity>? = null
        private var mAppManager: ActivityManagerUtils? = null

        /**
         * 单一实例
         */
        val instance: ActivityManagerUtils
            get() {
                if (mAppManager == null) {
                    mAppManager = ActivityManagerUtils()
                }
                if (mActivityStack == null) {
                    mActivityStack = Stack<Activity>()
                }
                return mAppManager!!
            }
    }
}
