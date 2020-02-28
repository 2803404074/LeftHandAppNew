package com.zhutian.lefthand.specialcolumn.activity.vm

import android.content.Context
import android.content.Intent
import com.tencent.liteav.demo.player.superplayer.SuperPlayerActivity
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R

class HotsLabelVm : BaseViewModel() {

    lateinit var mAdapter:MyBaseAdapter<String>

    var mData = mutableListOf<String>()

    fun initAdapter(context: Context){
        mAdapter = object : MyBaseAdapter<String>(mData, R.layout.item_special_about){
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
            mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582869827029&di=71bbbe6c33238b917283404e748190c0&imgtype=0&src=http%3A%2F%2Fimages.china.cn%2Fattachement%2Fjpg%2Fsite1000%2F20160308%2Fc03fd54abb76184888ad04.jpg")
        }
        mAdapter.setNewData(mData)
    }
}