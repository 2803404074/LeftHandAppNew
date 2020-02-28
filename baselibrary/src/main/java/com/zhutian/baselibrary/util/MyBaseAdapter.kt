package com.zhutian.baselibrary.util

import com.chad.library.adapter.base.BaseQuickAdapter

abstract class MyBaseAdapter<T>(mData:MutableList<T>,layoutId:Int) : BaseQuickAdapter<T, MyViewHolder>(layoutId,mData) {
    abstract fun myConvert(helper: MyViewHolder, item: T)

    override fun convert(helper: MyViewHolder, item: T?) {
        myConvert(helper,item!!)
    }
}
