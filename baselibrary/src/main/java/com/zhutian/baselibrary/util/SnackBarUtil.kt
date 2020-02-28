package com.zhutian.baselibrary.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackBarUtil {
    companion object{
        fun show(view:View,mess:String){
            Snackbar.make(view, mess, 2000).show();
        }
    }
}