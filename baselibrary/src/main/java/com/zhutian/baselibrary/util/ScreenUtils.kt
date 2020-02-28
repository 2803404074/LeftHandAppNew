package com.zhutian.baselibrary.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.WindowManager



class ScreenUtils {



    companion object {
        /**
         * 获取屏幕高度(px)
         */
        fun getScreenHeight(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }

        /**
         * 获取屏幕宽度(px)
         */
        fun getScreenWidth(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }

        fun dp2px(context: Context, dp: Float): Float {
            val scale = context.resources.displayMetrics.density
            return dp * scale + 0.5f
        }

        fun sp2px(context: Context, sp: Float): Float {
            val scale = context.resources.displayMetrics.scaledDensity
            return sp * scale
        }

        fun isInputShow(activity: Activity): Boolean {
            //获取当屏幕内容的高度
            val screenHeight = activity.window.decorView.height
            //获取View可见区域的bottom
            val rect = Rect()
            //DecorView即为activity的顶级view
            activity.window.decorView.getWindowVisibleDisplayFrame(rect)
            //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
            //选取screenHeight*2/3进行判断
            return screenHeight * 2 / 3 > rect.bottom
        }


        //获取最大宽度
        fun getMaxWidth(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

        // 获取最大高度
        fun getMaxHeight(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }



    }
}
