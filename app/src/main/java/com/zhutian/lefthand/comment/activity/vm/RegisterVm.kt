package com.zhutian.lefthand.comment.activity.vm

import android.app.Activity
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.zhutian.baselibrary.basehttp.BaseObserver
import com.zhutian.baselibrary.basehttp.RequestUtil
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.projectmodel.TouristMo
import com.zhutian.baselibrary.util.*
import com.zhutian.lefthand.R

class RegisterVm : BaseViewModel() {

    var isSendSuccess = MutableLiveData<Boolean>()

    var registerStatus = MutableLiveData<Boolean>()

    fun getMess(phone: String) {
        if (!StringUtils.isEntity(phone)) {
            return
        }
        RequestUtil.sendCode(false, phone, object : BaseObserver<ArrayList<Any>>() {
            override fun onSuccess(result: ArrayList<Any>?) {
                ToastUtil.get().show(mContent, result as String)
                isSendSuccess.value = false
            }

            override fun onFailure(e: Throwable?, message: String?) {
                ToastUtil.get().show(mContent, message!!)
                isSendSuccess.value = true
            }
        })
    }

    fun register(activity:Activity,phone: String, code: String, pwd: String) {
        if (!StringUtils.isEntity(phone)) {
            ToastUtil.get().show(mContent, "手机号不能留空哦")
            return
        }
        if (!StringUtils.isEntity(code)) {
            ToastUtil.get().show(mContent, "验证码不能留空哦")
            return
        }

        if (!StringUtils.isEntity(pwd)) {
            ToastUtil.get().show(mContent, "密码不能留空哦")
            return
        }

        val map = HashMap<String, Any>()
        map["phone"] = phone
        map["code"] = code
        map["password"] = pwd
        map["device"] = DeviceUtil.getDeviceId(mContent)
        map["platform"] = DeviceUtil.mPlatform
        map["system"] = DeviceUtil.buildVersion
        map["version"] = DeviceUtil.getVersionName(mContent)

        RequestUtil.touristRegister(false, true, map, object : BaseObserver<Any>() {
            override fun onSuccess(result: Any?) {
                val mo = Gson().fromJson<TouristMo>(result.toString(),TouristMo::class.java)
                mo?.let {
                    //保存token
                    it.token?.let { it1 ->
                        SPHelper.get().setToken(it1)
                    }
                    //保存加密盐值
                    it.salt?.let {it2 ->
                        SPHelper.get().setSalt(it2)
                    }
                    registerStatus.value = true
                }?:let {
                    registerStatus.value = false
                }
                DialogUtil.getInstance(activity).dismiss()
            }

            override fun onFailure(e: Throwable?, message: String?) {
                DialogUtil.getInstance(activity).dismiss()
                registerStatus.value = false
            }
        })
    }
}