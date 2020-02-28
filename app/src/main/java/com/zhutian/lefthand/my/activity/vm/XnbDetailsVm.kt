package com.zhutian.lefthand.my.activity.vm

import android.app.Application
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R

class XnbDetailsVm : BaseViewModel() {
    lateinit var mAdapter:MyBaseAdapter<String>
    var mData = mutableListOf<String>()

    fun initAdapter(){
        mAdapter = object :MyBaseAdapter<String>(mData, R.layout.item_xnb_details){
            override fun myConvert(helper: MyViewHolder, item: String) {
            }
        }
    }

    fun loadData(){
        for (index in 0 until 20){
            mData.add("数据")
        }
    }
}