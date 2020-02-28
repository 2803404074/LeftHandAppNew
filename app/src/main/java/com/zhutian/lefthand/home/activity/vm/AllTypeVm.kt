package com.zhutian.lefthand.comment.activity.vm

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.baselibrary.util.TextColorUtil
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.AllTypeActivity
import com.zhutian.lefthand.comment.activity.HighTypeActivity
import com.zhutian.lefthand.specialcolumn.activity.HotsLadyActivity

class AllTypeVm : BaseViewModel() {

    lateinit var mAdapter:MyBaseAdapter<String>
    var mData = mutableListOf<String>()


    fun initAdapter(context: Context){
        mAdapter = object :MyBaseAdapter<String>(mData, R.layout.item_all_type){
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrl(R.id.ivContent,item)
                val mRecyclerView = helper.getView<RecyclerView>(R.id.mRecyclerView)
                mRecyclerView.isNestedScrollingEnabled = false
                val list = mutableListOf<String>()
                list.add("网红美女")
                list.add("精品女优")
                val thisAdapter = object :MyBaseAdapter<String>(list, R.layout.text_view_type){
                    override fun myConvert(helper: MyViewHolder, item: String) {
                        val txt = helper.getView<TextView>(R.id.mTxt)
                        txt.text = item
                        TextColorUtil.setTextColor(context,txt,R.color.white)
                    }
                }
                mRecyclerView.adapter = thisAdapter
            }
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            context.startActivity(Intent(context, HighTypeActivity::class.java))
        }
    }

    fun loadData(){
        for (index in 0 until 8){
            mData.add("https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPng82e273040089b345fd633080be8bc09e88120d2303c98125b789143167639cad")
        }
        mAdapter.setNewData(mData)
    }
}