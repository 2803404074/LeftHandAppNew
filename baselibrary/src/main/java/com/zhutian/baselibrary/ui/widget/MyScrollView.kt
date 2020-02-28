package com.zhutian.baselibrary.ui.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.core.widget.NestedScrollView
import android.view.animation.AnticipateInterpolator


class MyScrollView : NestedScrollView {

    private var mInnerView: View? = null// 孩子View

    private var mDownY: Float = 0.toFloat()// 点击时y坐标

    private val mRect = Rect()
    private var offset: Int = 0

    private var isCount = false// 是否开始计算

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    constructor(context: Context) : super(context)
    constructor(context: Context,attrs:AttributeSet) : super(context,attrs)
    constructor(context: Context,attrs:AttributeSet,defStyleAttr:Int) : super(context,attrs,defStyleAttr)


    override fun onFinishInflate() {
        super.onFinishInflate()
        //获取的就是 scrollview 的第一个子 View
        if (childCount > 0) {
            mInnerView = getChildAt(0)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)

        val lp = mInnerView!!.layoutParams as MarginLayoutParams
        //减去 margin 的值
        offset = mInnerView!!.measuredHeight - lp.topMargin - lp.bottomMargin - mHeight
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (mInnerView != null) {
            commOnTouchEvent(e)
        }
        return super.onTouchEvent(e)
    }

    private fun commOnTouchEvent(e: MotionEvent) {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val preY = mDownY// 按下时的y坐标
                val nowY = e.y// 时时y坐标
                var deltaY = (preY - nowY).toInt()// 滑动距离
                //排除出第一次移动计算无法得知y坐标
                if (!isCount) {
                    deltaY = 0
                }

                mDownY = nowY
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    if (mRect.isEmpty) {
                        // 保存正常的布局位置
                        mRect.set(
                            mInnerView!!.left, mInnerView!!.top,
                            mInnerView!!.right, mInnerView!!.bottom
                        )
                    }
                    // 移动布局
                    mInnerView!!.layout(
                        mInnerView!!.left, mInnerView!!.top - deltaY / 2,
                        mInnerView!!.right, mInnerView!!.bottom - deltaY / 2
                    )
                }
                isCount = true
            }
            MotionEvent.ACTION_UP -> if (isNeedAnimation()) {
                translateAnimator()
                isCount = false
            }
        }
    }

    private fun translateAnimator() {
        val animation = TranslateAnimation(0f, 0f, mInnerView!!.top.toFloat(), mRect.top.toFloat())
        animation.duration = 500
        animation.interpolator = AnticipateInterpolator(3f)
        mInnerView!!.startAnimation(animation)
        // 设置回到正常的布局位置
        mInnerView!!.layout(mRect.left, mRect.top, mRect.right, mRect.bottom)
        mRect.setEmpty()
    }

    // 是否需要开启动画
    fun isNeedAnimation(): Boolean {
        return !mRect.isEmpty
    }

    // 判断是否处于顶部或者底部
    fun isNeedMove(): Boolean {

//        return if (scrollY == 0 || scrollY >= offset) {
//            true
//        } else false

        // 0是顶部，offset是底部
        return scrollY == 0 || scrollY >= offset
    }
}