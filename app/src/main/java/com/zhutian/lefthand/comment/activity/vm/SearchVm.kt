package com.zhutian.lefthand.comment.activity.vm

import android.app.Application
import com.zhutian.baselibrary.baseview.BaseViewModel

class SearchVm : BaseViewModel() {

    var mHistoryData = mutableListOf<String>()

    var mHotsData = mutableListOf<String>()


    fun loadData(){
        //获取历史数据
        for(index in 0 until 7){
            mHistoryData.add("历史$index")
        }

        //获取热门数据
        for(index in 0 until 12){
            mHotsData.add("热门$index")
        }

    }
}