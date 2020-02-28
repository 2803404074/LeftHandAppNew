package com.zhutian.lefthand.specialcolumn.activity.vm

import android.app.Application
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.model.TitleMo
import com.zhutian.baselibrary.model.TitleMoSun
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyMoreBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R

class AdultVm : BaseViewModel() {

    lateinit var mAdapter: MyBaseAdapter<TitleMo>

    var mData = mutableListOf<TitleMo>()

    fun initAdapter(recyclerView: RecyclerView){
        recyclerView.layoutManager
        mAdapter = object : MyBaseAdapter<TitleMo>(mData,R.layout.item_adult_title){
            override fun myConvert(helper: MyViewHolder, item: TitleMo) {
                helper.setText(R.id.tvYear,item.title)
                val rec=helper.getView<RecyclerView>(R.id.mRecyclerView)
                rec.isNestedScrollingEnabled = false
                val thisAdapter = object : MyBaseAdapter<TitleMoSun>(item.sunList!!,R.layout.item_adult){
                    override fun myConvert(helper: MyViewHolder, item: TitleMoSun) {
                        helper.setText(R.id.tvName,item.name)
                        helper.setImageByUrl(R.id.ivCover,item.head)
                    }
                }
                rec.adapter = thisAdapter
            }
        }
    }

    fun loadData(){
        mData.clear()
        for (index in 0..10){
            var titleMo = TitleMo()
            titleMo.title = "201$index"+"年"

            var list = mutableListOf<TitleMoSun>()
            for (j in 0 until 5){
                var sunMo = TitleMoSun()
                sunMo.head = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1846428644,780344053&fm=26&gp=0.jpg"
                sunMo.name="波多野结结$j"
                list.add(sunMo)
            }
            titleMo.sunList = list
            mData.add(titleMo)
        }
        mAdapter.setNewData(mData)
    }
}