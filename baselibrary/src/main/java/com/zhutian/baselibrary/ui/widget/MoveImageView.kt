package com.zhutian.baselibrary.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import androidx.appcompat.widget.AppCompatImageView
import com.zhutian.baselibrary.util.ScreenUtils
import me.jessyan.autosize.utils.ScreenUtils.getStatusBarHeight
import kotlin.math.abs


//可移动的view
class MoveImageView : AppCompatImageView {
    private var width: Int ?= 0 //  测量宽度 FreeView的宽度
    private var height: Int ?= 0 // 测量高度 FreeView的高度
    private var maxWidth: Int ?= 0 // 最大宽度 window 的宽度
    private var maxHeight: Int ?= 0 // 最大高度 window 的高度
    private var mContext: Context? = null
    private var downX: Float = 0.toFloat() //点击时的x坐标
    private var downY: Float = 0.toFloat()  // 点击时的y坐标
    //是否拖动标识
    private var isDrag = false

    constructor(context: Context):super(context,null)
    constructor(context: Context,attrs:AttributeSet):super(context,attrs){
        this.mContext = context
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获取屏宽高 和 可是适用范围 （我的需求是可在屏幕内拖动 不超出范围 也不需要隐藏）
        width = measuredWidth
        height = measuredHeight

        //获取最大宽度
        maxWidth = ScreenUtils.getMaxWidth(context)
        // 获取最大高度，此时减去状态栏高度 注意如果有状态栏 要减去状态栏 如下行 得到的是可活动的高度
        maxHeight = ScreenUtils.getMaxHeight(context) - getStatusBarHeight()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        event?.let {
            if (this.isEnabled) {
                when (event.action) {
                    ACTION_DOWN // 点击动作处理 每次点击时将拖动状态改为 false 并且记录下点击时的坐标 downX downY
                    -> {
                        isDrag = false
                        downX = event.x // 点击触屏时的x坐标 用于离开屏幕时的x坐标作计算
                        downY = event.y // 点击触屏时的y坐标 用于离开屏幕时的y坐标作计算
                    }
                    ACTION_MOVE // 滑动动作处理 记录离开屏幕时的 moveX  moveY 用于计算距离 和 判断滑动事件和点击事件 并作出响应
                    -> {
                        val moveX = event.x - downX
                        val moveY = event.y - downY
                        var l: Int
                        var r: Int
                        var t: Int
                        var b: Int // 上下左右四点移动后的偏移量
                        //计算偏移量 设置偏移量 = 3 时 为判断点击事件和滑动事件的峰值
                        if (abs(moveX) > 3 || abs(moveY) > 3) { // 偏移量的绝对值大于 3 为 滑动时间 并根据偏移量计算四点移动后的位置
                            l = (left + moveX).toInt()
                            r = l + width!!.toInt()
                            t = (top + moveY).toInt()
                            b = t + height!!.toInt()
                            //不划出边界判断,最大值为边界值
                            // 如果你的需求是可以划出边界 此时你要计算可以划出边界的偏移量 最大不能超过自身宽度或者是高度  如果超过自身的宽度和高度 view 划出边界后 就无法再拖动到界面内了 注意
                            if (l < 0) { // left 小于 0 就是滑出边界 赋值为 0 ; right 右边的坐标就是自身宽度 如果可以划出边界 left right top bottom 最小值的绝对值 不能大于自身的宽高
                                l = 0
                                r = l + width!!.toInt()
                            } else if (r > maxWidth!!.toInt()) { // 判断 right 并赋值
                                r = maxWidth!!.toInt()
                                l = r - width!!.toInt()
                            }
                            if (t < 0) { // top
                                t = 0
                                b = t + height!!.toInt()
                            } else if (b > maxHeight!!.toInt()) { // bottom
                                b = maxHeight!!.toInt()
                                t = b - height!!.toInt()
                            }
                            this.layout(l, t, r, b) // 重置view在layout 中位置
                            isDrag = true  // 重置 拖动为 true
                        } else {
                            isDrag = false // 小于峰值3时 为点击事件
                        }
                    }
                    ACTION_UP // 不处理
                    -> isPressed = false
                    ACTION_CANCEL // 不处理
                    -> isPressed = false
                }
                return true
            }
        }
        return false
    }
}