package com.zhutian.lefthand.my.fragment.vm

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.zhutian.baselibrary.basehttp.BaseObserver
import com.zhutian.baselibrary.basehttp.RequestUtil
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.projectmodel.BaseListMo
import com.zhutian.baselibrary.projectmodel.MessListMo
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.baselibrary.util.ToastUtil
import com.zhutian.lefthand.R
import com.zhutian.lefthand.my.activity.NoticeDetailsActivity

class NoticeVm : BaseViewModel() {

    lateinit var mAdapter:MyBaseAdapter<MessListMo>
    var mData = mutableListOf<MessListMo>()

    fun initAdapter(context:Context){
        mAdapter = object:MyBaseAdapter<MessListMo>(mData, R.layout.item_notice){
            override fun myConvert(helper: MyViewHolder, item: MessListMo) {
                helper.setText(R.id.tvTitle,item.title)
                helper.setText(R.id.tvBody,item.body)
                helper.setText(R.id.tvData,item.updated_at)
            }
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(mContent,NoticeDetailsActivity::class.java)
            intent.putExtra("mId",mAdapter.data[position].id)
            context.startActivity(intent)
        }
    }

    fun loadData(){
        RequestUtil.noticeList(true,page,limit,object : BaseObserver<BaseListMo<MessListMo>>(){
            override fun onSuccess(result: BaseListMo<MessListMo>?) {
                result?.let {
                    it.items?.let {it2->
                        mAdapter.setNewData(it2)
                    }?:let {
                        ToastUtil.get().show(mContent,"暂无系统消息")
                    }
                }
            }

            override fun onFailure(e: Throwable?, message: String?) {
                ToastUtil.get().show(mContent,message!!)
            }

        })
    }
}
