package com.zhutian.lefthand.my.fragment.vm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.tencent.liteav.demo.player.superplayer.SuperPlayerActivity
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R
import com.zhutian.lefthand.my.activity.vm.HistoryVm


class HistoryFgVm : BaseViewModel(){

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var vmModel : HistoryFgVm
    }

    lateinit var mAdapter: MyBaseAdapter<String>

    var mData = mutableListOf<String>()

    var isLoad = MutableLiveData<Boolean>()

    fun initAdapter(context: Context) {
        vmModel = this
        mAdapter = object : MyBaseAdapter<String>(mData, R.layout.item_special_about_tow) {
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrlCircular(R.id.ivContent,item)
            }
        }
        mAdapter.setOnItemClickListener { _, _, _ ->
            context.startActivity(Intent(context, SuperPlayerActivity::class.java))
        }
    }

    fun loadData() {
        for (index in 1..20) {
            mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581598377245&di=e4e203b63f7b3e49138eb34312fa2dd8&imgtype=0&src=http%3A%2F%2Fwenhua.youth.cn%2Fxwjj%2Fxw%2F201307%2FW020130709539807156520.jpg")
        }
        isLoad.value = true
        mAdapter.setNewData(mData)
    }
}
