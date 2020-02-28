package com.zhutian.baselibrary.exeption

import android.content.Context
import android.util.Log

import java.io.File
import java.util.Date
import android.os.Build
import android.content.pm.PackageManager
import android.os.Looper
import android.os.SystemClock
import android.widget.Toast
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat


class MyCrashHandler : Thread.UncaughtExceptionHandler {

    var TAG = "MyCrash"
    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null


    private var mContext: Context? = null

    // 用来存储设备信息和异常信息
    private val infos = HashMap<String, String>()

    // 用于格式化日期,作为日志文件名的一部分
    private val formatter = SimpleDateFormat("yyyy-MM-dd")

    /** 保证只有一个CrashHandler实例  */
    constructor()

    /** 获取CrashHandler实例 ,单例模式  */
    companion object{
        private val instance = MyCrashHandler()
        fun getInstance(): MyCrashHandler {
            return instance
        }
    }


    /**
     * 初始化
     */
    fun init(context: Context) {
        mContext = context
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            SystemClock.sleep(3000)
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * @param ex
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null)
            return false
        try {
            // 使用Toast来显示异常信息
            object : Thread() {
                override fun run() {
                    Looper.prepare()
                    Toast.makeText(
                        mContext, "很抱歉,程序出现异常,即将重启.",
                        Toast.LENGTH_LONG
                    ).show()
                    Looper.loop()
                }
            }.start()
            // 收集设备参数信息
            collectDeviceInfo(mContext!!)
            // 保存日志文件
            saveCrashInfoFile(ex)
            SystemClock.sleep(3000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    /**
     * 收集设备参数信息
     */
    fun collectDeviceInfo(ctx: Context) {
        try {
            val pm = ctx.packageManager
            val pi = pm.getPackageInfo(
                ctx.packageName,
                PackageManager.GET_ACTIVITIES
            )
            if (pi != null) {
                val versionName = pi.versionName + ""
                val versionCode = pi.versionCode.toString() + ""
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info", e)
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field.get(null).toString()
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }

        }
    }

    /**
     * 保存错误信息到文件中
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    @Throws(Exception::class)
    private fun saveCrashInfoFile(ex: Throwable): String? {
        val sb = StringBuffer()
        try {
            val sDateFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
            )
            val date = sDateFormat.format(java.util.Date())
            sb.append("\r\n" + date + "\n")
            for ((key, value) in infos) {
                sb.append("$key=$value\n")
            }

            val writer = StringWriter()
            val printWriter = PrintWriter(writer)
            ex.printStackTrace(printWriter)
            var cause: Throwable? = ex.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            printWriter.flush()
            printWriter.close()
            val result = writer.toString()
            sb.append(result)

            return writeFile(sb.toString())
        } catch (e: Exception) {
            Log.e(TAG, "an error occured while writing file...", e)
            sb.append("an error occured while writing file...\r\n")
            writeFile(sb.toString())
        }

        return null
    }

    //将错误日志记录
    @Throws(Exception::class)
    private fun writeFile(sb: String): String {
        val time = formatter.format(Date())
        val fileName = "crash-$time.log"
        if (existSDCard()) {
            val path = getGlobalpath()
            val dir = File(path)
            if (!dir.exists())
                dir.mkdirs()

            val fos = FileOutputStream(path + fileName, true)
            fos.write(sb.toByteArray())
            fos.flush()
            fos.close()
        }
        return fileName
    }

    //是否存在sd卡
    private fun existSDCard(): Boolean {
        return android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
    }


    //获取缓存路径
    private fun getGlobalpath(): String {
        Log.e("缓存文件的路径", mContext!!.externalCacheDir!!.toString() + "")
        return mContext!!.externalCacheDir!!.toString()
    }

}
