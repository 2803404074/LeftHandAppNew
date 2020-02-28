package com.zhutian.baselibrary.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zhutian.baselibrary.R
import com.youth.banner.loader.ImageLoader
import com.zhutian.baselibrary.app.MyApplication

class GlideImageLoader: ImageLoader {

    constructor(){
    }
    constructor(isCircular:Boolean){
        this.isCircular  = isCircular
    }
    var isCircular = false//是否加载圆角图片

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        imageView?.let {
            Glide.with(MyApplication.instance)
                .load(path)
                .placeholder(R.drawable.img_load)
                .error(R.drawable.no_banner)
                .centerCrop().into(it)
        }
    }
}