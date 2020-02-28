package com.zhutian.lefthand.specialcolumn.activity.vm

import android.content.Context
import android.content.Intent
import com.tencent.liteav.demo.player.superplayer.SuperPlayerActivity
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R

class HotsLadyVm : BaseViewModel() {

    lateinit var mAdapter:MyBaseAdapter<String>

    var mData = mutableListOf<String>()

    fun initAdapter(context: Context){
        mAdapter = object : MyBaseAdapter<String>(mData, R.layout.item_special_about_tow){
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrlCircular(R.id.ivContent,item)
            }
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            context.startActivity(Intent(context, SuperPlayerActivity::class.java))
        }

    }

    fun loadData(){
        for (index in 0..20){
            mData.add("https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPngce944e0dfa92fd9eb14e07d3287cbffc4b9241f87200d1a69611c607b1d0060d")
        }
        mAdapter.setNewData(mData)
    }
}