package com.zhutian.baselibrary.util

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.zhutian.baselibrary.model.MoreType


/**
 * 多类型的列表
 * 泛型必须继承MultiItemEntity
 * 参数一是数据源
 * 参数二是一个存放类型和视图的列表
 */
abstract class MyMoreBaseAdapter<T : MoreType>(e:MutableList<T>): BaseMultiItemQuickAdapter<T, MyViewHolder>(e) {

    init {
        for (index in 0..e.size){
            if (index != e.size && index>=0){
                addItemType(e[index].itemType,e[index].layoutId)
            }
        }
    }

    /**
     * 实现在该方法
     * 根据type装载相应的数据
     */
    abstract fun myConvert(helper: MyViewHolder, item: T,type:Int)

    override fun convert(helper: MyViewHolder, item: T?) {
        myConvert(helper,item!!,item.itemType)
    }
}
