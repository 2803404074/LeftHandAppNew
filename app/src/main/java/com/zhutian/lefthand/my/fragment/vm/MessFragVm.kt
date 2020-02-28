package com.zhutian.lefthand.my.fragment.vm

import android.content.Context
import android.os.Handler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhutian.baselibrary.basehttp.BaseObserver
import com.zhutian.baselibrary.basehttp.BaseResponse
import com.zhutian.baselibrary.basehttp.RequestUtil
import com.zhutian.baselibrary.basehttp.RetrofitManager
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.projectmodel.BaseListMo
import com.zhutian.baselibrary.projectmodel.MessListMo
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.baselibrary.util.ToastUtil
import com.zhutian.lefthand.R

class MessFragVm : BaseViewModel() {

    lateinit var mAdapter:MyBaseAdapter<MessListMo>
    var mData = mutableListOf<MessListMo>()

    fun initAdapter(){
        mAdapter = object:MyBaseAdapter<MessListMo>(mData, R.layout.item_mess){
            override fun myConvert(helper: MyViewHolder, item: MessListMo) {
                helper.setText(R.id.tvMessType,item.title)
                helper.setText(R.id.tvContent,item.body)
                helper.setText(R.id.tvData,item.updated_at)
            }
        }
    }


    fun loadData(context:Context){

        Thread {
//            RequestUtil.messList(true,page,limit,object :BaseObserver<Any>(){
//                override fun onSuccess(result: Any?) {
//                    //转对象
//                    val mo = Gson().fromJson<BaseListMo>(result.toString(),BaseListMo::class.java)
//
//                    mData = Gson().fromJson<MutableList<MessListMo>>(mo.items,Array<MessListMo>::class.java).toMutableList()
//
//                    if (mData.size==0){
//                        val a = MessListMo()
//                        a.title = "服务没有消息，自己手动添加一个"
//                        a.body = "服务没有消息，自己手动添加一个"
//                        mData.add(a)
//                    }
//                    mAdapter.setNewData(mData)
//
//                }
//                override fun onFailure(e: Throwable?, message: String?) {
//                    ToastUtil.get().show(context,message!!)
//                }
//            })


            //RetrofitManager.get().setBaseUrl("http://192.168.1.7:8089/")
            RequestUtil.messList(true,page,limit,object :BaseObserver<BaseListMo<MessListMo>>(){
                override fun onSuccess(result: BaseListMo<MessListMo>?) {
                    result?.let {
                        it.items?.let {it2->
                            mAdapter.setNewData(it2)
                        }
                    }
                }

                override fun onFailure(e: Throwable?, message: String?) {

                }
            })

        }.start()

    }
}
