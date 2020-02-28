package com.zhutian.baselibrary.model

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * 多类型item
 */
open class MoreType(override val itemType: Int) : MultiItemEntity {
    var layoutId:Int = 0
    constructor(layoutId:Int,itemType: Int) : this(itemType) {
        this.layoutId = layoutId
    }
}