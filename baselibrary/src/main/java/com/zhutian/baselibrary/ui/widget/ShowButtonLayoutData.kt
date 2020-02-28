package com.zhutian.baselibrary.ui.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.zhutian.baselibrary.R

open class ShowButtonLayoutData<T>(
    private val context: Context,
    private val layout: ShowButtonLayout,
    private val data: MutableList<T>?,
    private val mListener: MyClickListener?
) {

    private var layoutView = -1

    //自定义接口，用于回调按钮点击事件到Activity
    interface MyClickListener {
        fun clickListener(v: View, lot: Double, lat: Double)
    }

    fun updata() {
        this.data!!.clear()
        layout.removeAllViews()
    }

    fun setView(layout: Int) {
        this.layoutView = layout
    }

    fun setData() {
        if (null == data) return
        val views = arrayOfNulls<TextView>(data.size)
        //热门数据源
        for (i in data.indices) {
            val view = LayoutInflater.from(context).inflate(
                if (layoutView == -1) R.layout.text_view else layoutView,
                layout,
                false
            ) as TextView
            if (data[i] is String) {
                view.text = data[i] as String
                view.tag = data[i]
            }

            views[i] = view
            view.setOnClickListener { v ->
                //String tag = (String) v.getTag();
                if (data[i] is String) {
                    mListener?.clickListener(v, 0.0, 0.0)
                }
            }
            layout.addView(view)

        }
    }
}
