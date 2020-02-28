package com.zhutian.baselibrary.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.telephony.TelephonyManager

import androidx.core.app.ActivityCompat

import java.io.File
import android.content.Context.TELEPHONY_SERVICE
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService



object DeviceUtil {

    //用户平台
    val mPlatform:String
    get() = "Android"


    /**
     * SD卡判断
     *
     * @return
     */
    val isSDCardAvailable: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    /**
     * 获取手机品牌
     *
     * @return
     */
    val phoneBrand: String
        get() = android.os.Build.BRAND

    /**
     * 获取手机型号
     *
     * @return
     */
    val phoneModel: String
        get() = android.os.Build.MODEL

    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return
     */
    val buildLevel: Int
        get() = android.os.Build.VERSION.SDK_INT

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return
     */
    val buildVersion: String
        get() = android.os.Build.VERSION.RELEASE

    /**
     * 获取设备宽度（px）
     *
     * @param context
     * @return
     */
    fun deviceWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获取设备高度（px）
     *
     * @param context
     * @return
     */
    fun deviceHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 返回版本名字
     * 对应build.gradle中的versionName
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            val packageManager = context.packageManager
            val packInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionName = packInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return versionName
    }

    /**
     * 返回版本号
     * 对应build.gradle中的versionCode
     *
     * @param context
     * @return
     */
    fun getVersionCode(context: Context): String {
        var versionCode = ""
        try {
            val packageManager = context.packageManager
            val packInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionCode = packInfo.versionCode.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return versionCode
    }

    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context
     * @return
     */
    fun getDeviceId(context: Context): String {
        val tm = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //没有权限则返回""
            return ""
        } else {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return tm.imei
            } else {
                val method = tm.javaClass.getMethod("getImei")
                return method.invoke(tm) as String
            }
        }
    }

    /**
     * 创建App文件夹
     *
     * @param appName
     * @param application
     * @param folderName
     * @return
     */
    @JvmOverloads
    fun createAPPFolder(
        appName: String,
        application: Application,
        folderName: String? = null
    ): String {
        var root: File? = Environment.getExternalStorageDirectory()
        var folder: File
        /**
         * 如果存在SD卡
         */
        if (DeviceUtil.isSDCardAvailable && root != null) {
            folder = File(root, appName)
            if (!folder.exists()) {
                folder.mkdirs()
            }
        } else {
            /**
             * 不存在SD卡，就放到缓存文件夹内
             */
            root = application.cacheDir
            folder = File(root, appName)
            if (!folder.exists()) {
                folder.mkdirs()
            }
        }
        if (folderName != null) {
            folder = File(folder, folderName)
            if (!folder.exists()) {
                folder.mkdirs()
            }
        }
        return folder.absolutePath
    }

    /**
     * 通过Uri找到File
     *
     * @param context
     * @param uri
     * @return
     */
    fun uri2File(context: Activity, uri: Uri): File {
        val file: File
        val project = arrayOf(MediaStore.Images.Media.DATA)
        val actualImageCursor = context.contentResolver.query(uri, project, null, null, null)
        if (actualImageCursor != null) {
            val actual_image_column_index = actualImageCursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            actualImageCursor.moveToFirst()
            val img_path = actualImageCursor
                .getString(actual_image_column_index)
            file = File(img_path)
        } else {
            file = File(uri.path!!)
        }
        actualImageCursor?.close()
        return file
    }


}
/**
 * 创建App文件夹
 *
 * @param appName
 * @param application
 * @return
 */

