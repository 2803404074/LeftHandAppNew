package com.zhutian.lefthand.channel.vm

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R

class ChannelVm : BaseViewModel() {

    lateinit var mAdapter:MyBaseAdapter<MutableList<String>>
    var mData = mutableListOf<MutableList<String>>()
    var isLoad = MutableLiveData<Boolean>()


    fun initAllAdapter(){
        mAdapter = object :MyBaseAdapter<MutableList<String>>(mData,R.layout.item_channel){
            override fun myConvert(helper: MyViewHolder, item: MutableList<String>) {
                var re = helper.getView<RecyclerView>(R.id.mRecyclerView)
                var thisAdapter = object :MyBaseAdapter<String>(item,R.layout.item_channel_sun){
                    override fun myConvert(helper: MyViewHolder, item: String) {

                    }
                }
                re.adapter = thisAdapter
            }

        }
    }

    fun loadData(){
        for (index in 0 until 20){
            val list = mutableListOf<String>()
            for (j in 0 until 6){
                list.add("小分类")
            }
            mData.add(list)
        }

        mAdapter.setNewData(mData)
    }
}
