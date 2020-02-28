package com.zhutian.baselibrary.basehttp

import android.app.Activity
import android.content.Context
import android.view.View
import com.zhutian.baselibrary.R
import com.zhutian.baselibrary.util.Conver
import com.zhutian.baselibrary.util.DialogUtil
import io.reactivex.disposables.Disposable


abstract class MyObserver<T>(context: Activity) : BaseObserver<T>() {

    var mContext= context

    private fun setLoadingView(isLoading: Boolean) {
        if (isLoading){
            DialogUtil.getInstance(mContext).show(R.layout.dialog_loading,false,true,object : Conver{
                override fun setView(view: View) {
                }
            })
        }else{
            DialogUtil.getInstance(mContext).dismiss()
        }
    }

    override fun onSubscribe(d: Disposable) {
        setLoadingView(true)
    }

    override fun onError(e: Throwable) {
        setLoadingView(false)
        super.onError(e)
    }

    override fun onComplete() {
        setLoadingView(false)
        super.onComplete()
    }
}