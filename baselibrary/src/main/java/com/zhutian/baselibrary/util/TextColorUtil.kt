package com.zhutian.baselibrary.util

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class TextColorUtil {
    companion object{

        fun setTextColor(context: Context,textView: TextView,arg01:Int){
            textView.setTextColor(
                AppCompatResources.getColorStateList(
                    context,
                    arg01
                )
            )
        }

        fun setTextColorStyle(textView: TextView,arg01:String,arg02: String){
            val gradient = LinearGradient(0F,0F,textView.paint.textSize*textView.text.length,0F,Color.parseColor(arg01),Color.parseColor(arg02),
                Shader.TileMode.CLAMP)
            textView.paint.shader = gradient
            textView.invalidate()
        }

    }
}