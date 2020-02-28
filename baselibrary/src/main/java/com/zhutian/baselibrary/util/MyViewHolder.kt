package com.zhutian.baselibrary.util

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MyViewHolder(v:View) : BaseViewHolder(v) {
    fun setImageByUrl(viewId: Int, path: String?): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        GlideImageLoader().displayImage(null,path,view)
        return this
    }

    fun setImageByUrlCircular(viewId: Int, path: String?): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        GlideImageLoader().displayImage(null,path,view)
        return this
    }
}