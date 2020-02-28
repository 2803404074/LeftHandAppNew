package com.zhutian.lefthand.my.activity.vm

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zhutian.baselibrary.basehttp.BaseObserver
import com.zhutian.baselibrary.basehttp.RequestUtil
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.projectmodel.MessListMo
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NoticeDetailsVm : BaseViewModel() {

    var liveMo = MutableLiveData<MessListMo>()
    fun loadData(mId:String){
        RequestUtil.noticeDetails(true,mId,object :BaseObserver<MessListMo>(){
            @SuppressLint("SimpleDateFormat")
            override fun onSuccess(result: MessListMo?) {
                result?.let {
                    liveMo.value = it
                }?:let {
                    val defaultMo = MessListMo()
                    defaultMo.title = "左手App"
                    defaultMo.body = "文章已删除"
                    defaultMo.updated_at = SimpleDateFormat("yyyy-MM-dd").format(Date())
                    liveMo.value =defaultMo
                }
            }

            override fun onFailure(e: Throwable?, message: String?) {
                val defaultMo = MessListMo()
                defaultMo.title = "左手App"
                defaultMo.body = "错误信息$message"
                defaultMo.updated_at = SimpleDateFormat("yyyy-MM-dd").format(Date())
                liveMo.value =defaultMo
            }
        })
    }
}