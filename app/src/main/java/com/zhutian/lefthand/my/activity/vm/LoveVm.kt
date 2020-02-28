package com.zhutian.lefthand.my.activity.vm

import android.content.Context
import android.content.Intent
import android.view.View.GONE
import android.widget.CheckBox
import com.tencent.liteav.demo.player.superplayer.SuperPlayerActivity
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.model.LiveMo
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R
import java.util.concurrent.CopyOnWriteArrayList

class LoveVm : BaseViewModel() {

    var isShowEdit = GONE

    lateinit var mAdapter:MyBaseAdapter<LiveMo>
    var mData = mutableListOf<LiveMo>()

    fun initAdapter(context: Context){
        mAdapter = object :MyBaseAdapter<LiveMo>(mData, R.layout.item_special_about_tow_check){
            override fun myConvert(helper: MyViewHolder, item: LiveMo) {
                helper.getView<CheckBox>(R.id.mCheck).visibility = isShowEdit
                helper.getView<CheckBox>(R.id.mCheck).isChecked = item.isSelect
                helper.setImageByUrlCircular(R.id.ivContent,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582870280112&di=ce37a511c523c0def600d3d16ff1caf8&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20120417%2FImg340809213.jpg")
            }
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (isShowEdit == GONE){
                context.startActivity(Intent(context, SuperPlayerActivity::class.java))
            }else{
//                       if (mData[position].isSelect){
//                           mData[position].isSelect = false
//                       }else{
//                           mData[position].isSelect = true
//                       }
                mData[position].isSelect = !mData[position].isSelect
                mAdapter.replaceData(mData)//更新

            }
        }
    }

    fun loadData(){
        for (index in 0 until 20){
            mData.add(LiveMo())
        }
    }

    fun showEdit(isShow:Int){
        isShowEdit = isShow
        mAdapter.replaceData(mData)
    }


    /**
     * 全部选中和取消选中
     */
    fun allSelect(isSelect:Boolean){
        for (index in 0 until mData.size){
            mData[index].isSelect = isSelect
        }
        mAdapter.replaceData(mData)
    }

    /**
     * 删除
     */
    fun delete(isAllSelect:Boolean){
        if (isAllSelect){
            mData.clear()
        }else{
            val copyOnWriteArrayList = CopyOnWriteArrayList(mData)
            for (s in copyOnWriteArrayList) {
                if (s.isSelect) {
                    copyOnWriteArrayList.remove(s)
                }
            }
            mData = copyOnWriteArrayList
        }
        mAdapter.replaceData(mData)
    }
}