package com.zhutian.lefthand.specialcolumn.activity.vm

import android.content.Context
import android.content.Intent
import com.tencent.liteav.demo.player.superplayer.SuperPlayerActivity
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R

class RecommendVm : BaseViewModel() {

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
            mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582869964097&di=c121d4fa787c5b19d3bc5321e92ef475&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F0%2Fe6%2Ff119621393.jpg")
        }
        mAdapter.setNewData(mData)
    }
}