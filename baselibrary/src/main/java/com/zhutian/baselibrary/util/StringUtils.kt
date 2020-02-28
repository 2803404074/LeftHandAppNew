package com.zhutian.baselibrary.util

class StringUtils {
    companion object{
        fun isEntity(str:String?):Boolean{
            if (str.equals(""))return false
            if (str.equals("null"))return false
            str?.let {
                return true
            }
            return false
        }
    }
}