package com.zhutian.baselibrary.util

import android.animation.TypeEvaluator

class KickBackAnimator : TypeEvaluator<Float> {

    private val s = 1.70158f
    internal var mDuration = 0f

    fun setDuration(duration: Float) {
        mDuration = duration
    }

    override fun evaluate(fraction: Float, startValue: Float, endValue: Float): Float? {
        val t = mDuration * fraction
        val c = endValue - startValue
        val d = mDuration
        return calculate(t, c, startValue, d)!!
    }

    fun calculate(t: Float, c: Float, b: Float, d: Float): Float? {
        var t = t
        return b * ((t / d - 1) * t * ((s + 1) * t + s) + 1) + c
    }
}
