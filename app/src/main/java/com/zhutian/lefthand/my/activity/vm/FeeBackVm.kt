package com.zhutian.lefthand.my.activity.vm

import android.app.Application
import android.content.Context
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.MutableLiveData
import com.rey.material.widget.CheckBox
import com.zhutian.baselibrary.basehttp.BaseObserver
import com.zhutian.baselibrary.basehttp.RequestUtil
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.projectmodel.FeedBackMo
import com.zhutian.baselibrary.util.*
import com.zhutian.lefthand.R

class FeeBackVm : BaseViewModel() {

    lateinit var mAdapter : MyBaseAdapter<FeedBackMo>
    var mData = mutableListOf<FeedBackMo>()

    var feeBackLive = MutableLiveData<Boolean>()

    var checkStr = ""

    fun initAdapter(){

        for (index in 0..5){
            mData.add(FeedBackMo(false,"闪退闪退"))
        }
        mAdapter = object :MyBaseAdapter<FeedBackMo>(mData, R.layout.item_feeback){
            override fun myConvert(helper: MyViewHolder, item: FeedBackMo) {
                val check = helper.getView<AppCompatCheckBox>(R.id.cb)
                check.text = item.mTitle
                if (item.isCheck){
                    check.setBackgroundResource(R.drawable.shape_style_04)
                    TextColorUtil.setTextColor(mContent,check,R.color.white)
                }else{
                    check.setBackgroundResource(R.drawable.shape_style_04_w)
                    TextColorUtil.setTextColor(mContent,check,R.color.textTitle)
                }
            }
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            for (index in 0 until mData.size){
                mData[index].isCheck = false
            }
            mData[position].isCheck = !mData[position].isCheck
            if (mData[position].isCheck){
                checkStr = mData[position].mTitle!!
            }
            mAdapter.replaceData(mData)
        }
    }


    fun feedBack(context: Context,content:String){
        if (!StringUtils.isEntity(checkStr)){
            ToastUtil.get().show(mContent,"请选择问题类型")
            return
        }
        if (!StringUtils.isEntity(content)){
            ToastUtil.get().show(mContent,"请输入详情描述")
            return
        }
        RequestUtil.feedBack(true,checkStr,content,object : BaseObserver<ArrayList<Any>>(){
            override fun onSuccess(result: ArrayList<Any>?) {
                feeBackLive.value = true
            }
            override fun onFailure(e: Throwable?, message: String?) {
                ToastUtil.get().show(mContent,message!!)
            }
        })
    }
}