package com.zhutian.baselibrary.util

import android.content.Context
import android.widget.Toast


class ToastUtil {

    companion object {
        private var toastUtil : ToastUtil? = null
            get() {
                if (field == null){
                    field = ToastUtil()
                }
                return field
            }
        @Synchronized fun get(): ToastUtil {
            return toastUtil!!
        }
    }

    fun show(context: Context,mess:String){
        Toast.makeText(context,mess,Toast.LENGTH_SHORT).show()
    }

}