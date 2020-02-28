package com.zhutian.baselibrary.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

class ShowButtonLayout
/**
 * 最终调用这个构造方法
 * @param context 上下文
 * @param attrs xml属性集合
 * @param defStyle Theme中定义的style
 */
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    ViewGroup(context, attrs, defStyle) {
    //记录每个View的位置
    private val mChildPos = ArrayList<ChildPos>()

    private inner class ChildPos(
        internal var left: Int,
        internal var top: Int,
        internal var right: Int,
        internal var bottom: Int
    )

    /**
     * 测量宽度和高度
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //获取流式布局的宽度和模式
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        //获取流式布局的高度和模式
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        //使用wrap_content的流式布局的最终宽度和高度
        var width = 0
        var height = 0
        //记录每一行的宽度和高度
        var lineWidth = 0
        var lineHeight = 0
        //得到内部元素的个数
        val count = childCount
        mChildPos.clear()
        for (i in 0 until count) {
            //获取对应索引的view
            val child = getChildAt(i)
            //测量子view的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val lp = child.layoutParams as ViewGroup.MarginLayoutParams
            //子view占据的宽度
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            //子view占据的高度
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            //换行
            if (lineWidth + childWidth > widthSize - paddingLeft - paddingRight) {
                //取最大的行宽为流式布局宽度
                width = Math.max(width, lineWidth)
                //叠加行高得到流式布局高度
                height += lineHeight
                //重置行宽度为第一个View的宽度
                lineWidth = childWidth
                //重置行高度为第一个View的高度
                lineHeight = childHeight
                //记录位置
                mChildPos.add(
                    ChildPos(
                        paddingLeft + lp.leftMargin,
                        paddingTop + height + lp.topMargin,
                        paddingLeft + childWidth - lp.rightMargin,
                        paddingTop + height + childHeight - lp.bottomMargin
                    )
                )
            } else {  //不换行
                //记录位置
                mChildPos.add(
                    ChildPos(
                        paddingLeft + lineWidth + lp.leftMargin,
                        paddingTop + height + lp.topMargin,
                        paddingLeft + lineWidth + childWidth - lp.rightMargin,
                        paddingTop + height + childHeight - lp.bottomMargin
                    )
                )
                //叠加子View宽度得到新行宽度
                lineWidth += childWidth
                //取当前行子View最大高度作为行高度
                lineHeight = Math.max(lineHeight, childHeight)
            }
            //最后一个控件
            if (i == count - 1) {
                width = Math.max(lineWidth, width)
                height += lineHeight
            }
        }

        setMeasuredDimension(
            if (widthMode == View.MeasureSpec.EXACTLY) widthSize else width + paddingLeft + paddingRight,
            if (heightMode == View.MeasureSpec.EXACTLY) heightSize else height + paddingTop + paddingBottom
        )
    }

    /**
     * 让ViewGroup能够支持margin属性
     */
    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }

    /**
     * 设置每个View的位置
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            val pos = mChildPos[i]
            //设置View的左边、上边、右边底边位置
            child.layout(pos.left, pos.top, pos.right, pos.bottom)
        }
    }
}
