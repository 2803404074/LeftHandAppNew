package com.zhutian.baselibrary.ui.anim

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class ViewSizeChangeAnimo() : Animation(),Animation.AnimationListener {

    var callBack:AnimoCallBack?=null

    override fun onAnimationRepeat(p0: Animation?) {
    }

    override fun onAnimationEnd(p0: Animation?) {
        callBack?.end()
    }

    override fun onAnimationStart(p0: Animation?) {

    }

    interface AnimoCallBack{
        fun end()
    }



    public fun setCetCallBack(a: AnimoCallBack) {
        this.callBack = a
    }

    var initialHeight: Int = 0
    var targetHeight: Int = 0
    var initialWidth: Int = 0
    var targetWidth: Int = 0
    var view: View? = null


    init {
        setAnimationListener(this)
    }

    constructor(view: View) : this() {
        this.view = view;
        this.targetHeight = targetHeight;
        this.targetWidth = targetWidth;
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        view?.layoutParams!!.height =
            initialHeight + ((targetHeight - initialHeight) * interpolatedTime) as Int
        view?.layoutParams!!.width =
            initialWidth + ((targetWidth - initialWidth) * interpolatedTime).toInt()
        view?.requestLayout()
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        this.initialHeight = height
        this.initialWidth = width
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean {
        return true
    }


}