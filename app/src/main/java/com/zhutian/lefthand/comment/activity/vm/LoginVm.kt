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

class LoginVm: BaseViewModel() {

    var isLogin = MutableLiveData<Boolean>()

    fun login(activity: Activity, phone:String, pwd:String){

        if(!StringUtils.isEntity(phone)){
            ToastUtil.get().show(activity,"请输入手机号码")
            return
        }
        if(!StringUtils.isEntity(pwd)){
            ToastUtil.get().show(activity,"请输入密码")
            return
        }

        val map = HashMap<String, Any>()
        map["phone"] = phone
        map["password"] = pwd
        map["device"] = DeviceUtil.getDeviceId(mContent)
        map["platform"] = DeviceUtil.mPlatform
        map["system"] = DeviceUtil.buildVersion
        map["version"] = DeviceUtil.getVersionName(mContent)

        RequestUtil.userLogin(false,map,object :BaseObserver<Any>(){
            override fun onSuccess(result: Any?) {
                val mo = Gson().fromJson<TouristMo>(result.toString(),TouristMo::class.java)
                mo?.let {
                    //保存token
                    it.token?.let { it1 ->
                        SPHelper.get().setToken(it1)
                        isLogin.value = true
                    }
                    //保存加密盐值
                    it.salt?.let {it2 ->
                        SPHelper.get().setSalt(it2)
                    }
                }?:let {
                    isLogin.value = false
                    ToastUtil.get().show(activity,result.toString())
                }
                DialogUtil.getInstance(activity).dismiss()
            }

            override fun onFailure(e: Throwable?, message: String?) {
                ToastUtil.get().show(activity,message!!)
                DialogUtil.getInstance(activity).dismiss()
                isLogin.value = false
            }
        })
    }
}