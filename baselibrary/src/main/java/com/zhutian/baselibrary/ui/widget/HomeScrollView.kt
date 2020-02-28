package com.zhutian.baselibrary.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView


class HomeScrollView : NestedScrollView {



    //渐变view
    private var fadingView: View? = null
    //滑动view的高度，如果这里fadingHeightView是一张图片，
    // 那么就是这张图片上滑至完全消失时action bar 完全显示，
    // 过程中透明度不断增加，直至完全显示
    private var fadingHeightView: View? = null
    private val oldY: Int = 0
    //滑动距离，默认设置滑动500 时完全显示，根据实际需求自己设置
    private var fadingHeight = 500

    fun setFadingView(view: View) {
        this.fadingView = view
    }

    fun setFadingHeightView(v: View) {
        this.fadingHeightView = v
    }

    private var call: CallBack? = null

    interface CallBack {
        fun loadMore()
    }

    fun setCallBack(call: CallBack) {
        this.call = call
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        fadingHeightView?.let {
            fadingHeight = it.measuredHeight
        }
    }
    constructor(context:Context) : super(context)
    constructor(context:Context,attrs: AttributeSet,defStyle:Int) : super(context,attrs,defStyle)
    constructor(context:Context,attrs: AttributeSet):super(context,attrs)

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)

        //        l,t  滑动后 xy位置，
//        oldl lodt 滑动前 xy 位置-----
        val fading = (if (y > fadingHeight) fadingHeight else if (y > 30) y else 0).toFloat()

        Log.e("aaaaa","fading=$fading")

        Log.e("aaaaa","fading/fadingHeight=${fading / fadingHeight}")
        updateActionBarAlpha(fading / fadingHeight)

        if (getChildAt(0).height <= scrollY + height) {   // 如果满足就是到底部了
            call?.loadMore()
        }
    }

    private fun updateActionBarAlpha(alph: Float) {
        try {
            setActionBarAlpha(alph)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class)
    fun setActionBarAlpha(mAlpha: Float) {
        fadingView?.let {
            Log.e("aaaaa","mAlpha=$mAlpha")
            it.alpha = mAlpha
        }?:let {
            throw Exception("fadingView is null...")
        }
    }
}

