package com.zhutian.baselibrary.util

import android.app.ActivityManager
import android.app.Activity
import android.content.Context
import java.util.*


open class AppManager {

    companion object{
        private var instance: AppManager? = null
        private var activityStack: Stack<Activity>? = null
    }

    constructor(){

    }
    /**
     * 单一实例
     */
    fun getAppManager(): AppManager {
        if (instance == null) {
            instance = AppManager()
        }
        return instance as AppManager
    }

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack<Activity>()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity {

        return activityStack!![activityStack!!.size-2]
    }

    /**
     * 最后第二个压入的activity
     */
    fun currentTowActivity(activity: Activity): Class<*> {
        for (i in 0 until activityStack!!.size) {
            if (i == activityStack!!.size-2){
                return activityStack!![i].javaClass
            }
        }
        return activity.javaClass
    }


    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {

        for (i in 0 until activityStack!!.size-1) {
            if (activityStack!![i].javaClass == cls) {
                finishActivity(activityStack!![i])
            }
        }
        //        for (Activity activity : activityStack) {
        //            if (activity.getClass().equals(cls)) {
        //                finishActivity(activity);
        //            }
        //        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!!.get(i)) {
                activityStack!!.get(i).finish()
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * 放到栈底
     */
    fun addFirst(activity: Activity){
        activityStack!!.add(0,activity)
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        try {
            finishAllActivity()
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.restartPackage(context.getPackageName())
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}