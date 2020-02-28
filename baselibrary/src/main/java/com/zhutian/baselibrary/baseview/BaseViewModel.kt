package com.zhutian.baselibrary.baseview

import android.content.Context
import androidx.lifecycle.ViewModel
import com.zhutian.baselibrary.app.MyApplication

abstract class BaseViewModel: ViewModel(){

    protected var page = 1
    protected var limit = 10

    protected var mContent:Context = MyApplication.instance
}