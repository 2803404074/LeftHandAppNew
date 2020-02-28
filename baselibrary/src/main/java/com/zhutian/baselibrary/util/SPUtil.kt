package com.zhutian.baselibrary.util

import android.content.Context
import android.text.TextUtils
import android.content.SharedPreferences
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.zhutian.baselibrary.app.MyApplication
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class SPUtil{
    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

   init {
        val fileName = "db_user"
        sharedPreferences = MyApplication.instance.getSharedPreferences(
            fileName,
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
    }

    companion object {
        private var spUtil : SPUtil? = null
            get() {
                if (field == null){
                    field = SPUtil()
                }
                return field
            }
        @Synchronized fun get():SPUtil{
            return spUtil!!
        }
    }

    /**
     * 存储
     */
    @Synchronized
    fun put(key: String, `object`: Any) {
        if (`object` is String) {
            editor.putString(key, `object`)
        } else if (`object` is Int) {
            editor.putInt(key, `object`)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, `object`)
        } else if (`object` is Float) {
            editor.putFloat(key, `object`)
        } else if (`object` is Long) {
            editor.putLong(key, `object`)
        } else {
            editor.putString(key, `object`.toString())
        }
        editor.apply()//后台提交
    }

    /**
     * 获取保存的数据
     */
    @Synchronized
    fun getkey(key: String, defaultObject: Any): Any? {
        return if (defaultObject is String) {
            sharedPreferences.getString(key, defaultObject)
        } else if (defaultObject is Int) {
            sharedPreferences.getInt(key, defaultObject)
        } else if (defaultObject is Boolean) {
            sharedPreferences.getBoolean(key, defaultObject)
        } else if (defaultObject is Float) {
            sharedPreferences.getFloat(key, defaultObject)
        } else if (defaultObject is Long) {
            sharedPreferences.getLong(key, defaultObject)
        } else {
            sharedPreferences.getString(key, null)
        }
    }

    /**
     * 移除某个key值已经对应的值
     */
    fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        editor.clear()
        editor.apply()
    }

    /**
     * 查询某个key是否存在
     */
    fun contain(key: String): Boolean? {
        return sharedPreferences.contains(key)
    }

    /**
     * 返回所有的键值对
     */
    fun getAll(): Map<String, *> {
        return sharedPreferences.all
    }

    /**
     * 保存对象
     */
    fun <T> putObj(tag: String, data: T?) {
        if (null == data)
            return
        val gson = Gson()
        val strJson = gson.toJson(data)
        editor.putString(tag, strJson)
        editor.commit()
    }

    /**
     * 获取对象
     */
    fun <T> getObj(tag: String, cls: Class<T>): T? {
        var data: T? = null
        val strJson = sharedPreferences.getString(tag, null) ?: return null
        try {
            val gson = Gson()
            val jsonElement = JsonParser().parse(strJson)
            data = gson.fromJson(jsonElement, cls)
        } catch (e: Exception) {

        }

        return data
    }

    /**
     * 获取用户信息
     */
//    fun getUser(): UserMess? {
//        val str = getkey("userStr", "") as String?
//        if (StringUtil.(str)) {
//            return null
//        }
//        val gson = Gson()
//        return gson.fromJson(str, UserMess::class.java)
//    }

    fun getToken(): String? {
        return getkey("token", "") as String?
    }
    fun setToken(token:String){
        put("token",token)
    }


    fun getSalt(): String? {
        return getkey("salt", "") as String?
    }
    fun setSalt(salt:String){
        put("salt",salt)
    }


    fun getPhone(): String? {
        return getkey("phone", "") as String?
    }
    fun setPhone(phone:String){
        put("phone",phone)
    }



//    fun putUser(str: String) {
//        val gson = Gson()
//        val userMess = gson.fromJson(str, UserMess::class.java)
//        put("userStr", str)
//        put("token", userMess.getToken())
//    }

//    fun putUser(userMess: UserMess) {
//        val gson = Gson()
//        val str = gson.toJson(userMess)
//        put("userStr", str)
//    }

    fun removeUser() {
        remove("token")
    }

    /**
     * 存储对象----序列化 --私密数据
     */
    fun putObjectByInput(key: String, obj: Any?): String {
        if (obj == null) {//判断对象是否为空
            return ""
        }
        try {
            val baos = ByteArrayOutputStream()
            var oos: ObjectOutputStream? = null
            oos = ObjectOutputStream(baos)
            oos.writeObject(obj)
            // 将对象放到OutputStream中
            // 将对象转换成byte数组，并将其进行base64编码
            val objectStr = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
            baos.close()
            oos.close()
            put(key, objectStr)
            return objectStr
        } catch (t: Throwable) {

        }

        return ""
    }

    /**
     * 获取对象---序列化---私密数据
     */
    fun getObjectByInput(key: String): Any? {
        val wordBase64 = sharedPreferences.getString(key, "")
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) {
            return null
        }
        try {
            val objBytes = Base64.decode(wordBase64!!.toByteArray(), Base64.DEFAULT)
            val bais = ByteArrayInputStream(objBytes)
            val ois = ObjectInputStream(bais)
            // 将byte数组转换成product对象
            val obj = ois.readObject()
            bais.close()
            ois.close()
            return obj
        } catch (t: Throwable) {

        }

        return null
    }
}