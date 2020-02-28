package com.zhutian.lefthand.specialcolumn.activity.vm

import android.app.Application
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R

class SpecialDetailsVm : BaseViewModel() {

    lateinit var mAdapter:MyBaseAdapter<String>

    var mData = mutableListOf<String>()

    fun initAdapter(){
        mAdapter = object : MyBaseAdapter<String>(mData, R.layout.item_special_about){
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrlCircular(R.id.ivContent,item)
            }
        }
    }

    fun loadData(){
        for (index in 0..20){
            mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582869733179&di=6da460350fd315d33da105039bef133e&imgtype=0&src=http%3A%2F%2Fpic.feizl.com%2Fupload%2Fallimg%2F170614%2F1515293D7-11.jpg")
        }
        mAdapter.setNewData(mData)
    }
}