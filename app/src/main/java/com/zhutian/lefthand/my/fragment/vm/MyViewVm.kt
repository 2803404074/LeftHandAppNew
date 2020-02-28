package com.zhutian.lefthand.my.fragment.vm

import android.app.Activity
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.zhutian.baselibrary.basehttp.BaseObserver
import com.zhutian.baselibrary.basehttp.RequestUtil
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.projectmodel.TouristMo
import com.zhutian.baselibrary.util.*
import com.zhutian.lefthand.R
import org.json.JSONObject

class MyViewVm : BaseViewModel() {

    var isLogOut = MutableLiveData<Boolean>()

    fun logOut(activity: Activity) {


        RequestUtil.userLogOut(false, object : BaseObserver<Any>() {
            override fun onSuccess(result: Any?) {
                DialogUtil.getInstance(activity).dismiss()
                //退出成功后，清理信息。然后游客注册获取token
                SPHelper.get().setPhone("")
                SPHelper.get().setSalt("")
                SPHelper.get().setToken("")
                touristRegister(activity)
                isLogOut.value = true
            }
            override fun onFailure(e: Throwable?, message: String?) {
                DialogUtil.getInstance(activity).dismiss()
                ToastUtil.get().show(activity,message!!)
            }
        })
    }

    private fun touristRegister(activity: Activity) {
        val map = HashMap<String,Any>()
        map["device"] = DeviceUtil.getDeviceId(mContent)//设备名
        map["platform"] = "leftHandApp"//平台
        map["system"] = "android"//系统
        map["version"] = DeviceUtil.getVersionName(mContent)//版本

        RequestUtil.touristRegister(false,false,map,object:BaseObserver<Any>(){
            override fun onSuccess(result: Any?) {
                DialogUtil.getInstance(activity).dismiss()
                //LinkedTreeMap
                val mo = Gson().fromJson<TouristMo>(result.toString(), TouristMo::class.java)
                mo?.let {
                    //保存token
                    it.token?.let { it1 ->
                        SPHelper.get().setToken(it1)
                    }
                    //保存加密盐值
                    it.salt?.let {it2 ->
                        SPHelper.get().setSalt(it2)
                    }
                }
            }
            override fun onFailure(e: Throwable?, message: String?) {
                ToastUtil.get().show(activity,"游客登录失败")
            }
        })
    }

    fun qDao(){
        RequestUtil.sign(true,object :BaseObserver<Any>(){
            override fun onSuccess(result: Any?) {
                ToastUtil.get().show(mContent,"签到成功")
            }

            override fun onFailure(e: Throwable?, message: String?) {
                ToastUtil.get().show(mContent,message!!)
            }
        })
    }
}
