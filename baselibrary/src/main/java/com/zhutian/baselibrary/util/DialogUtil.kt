package com.zhutian.baselibrary.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import com.zhutian.baselibrary.R

class DialogUtil(private var mContext: Context) {

    private var dialog: AlertDialog? = null

    private fun setContext(mContext: Context){
        this.mContext = mContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var dialogUtil: DialogUtil? = null
        fun getInstance(mContext: Context): DialogUtil {
            dialogUtil?.let {
                it.setContext(mContext)
                return it
            }?:let {
                dialogUtil = DialogUtil(mContext)
                return dialogUtil as DialogUtil
            }
        }
    }

    fun show(layout: Int, isCancel: Boolean,isDimAmount:Boolean, convers: Conver) {
        val view = LayoutInflater.from(mContext).inflate(layout, null, false)
        convers.setView(view)
        dialog = AlertDialog.Builder(mContext, R.style.TransparentDialog).setView(view).create()
        dialog?.let {
            it.setCancelable(isCancel)
            it.setCanceledOnTouchOutside(isCancel)
            it.setOnDismissListener { dismiss() }
            it.show()

            val params = WindowManager.LayoutParams()
            params.width = ScreenUtils.getScreenWidth(mContext) / 4 * 3
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT
            if (isDimAmount){
                params.dimAmount = 0.0f
            }
            it.window?.attributes = params
            it.window?.setWindowAnimations(R.style.dialog_animation)
        }


    }

     fun dismiss() {
        dialog?.dismiss()
    }

     fun destroy(){
        dialog?.dismiss()
        dialogUtil?.let {
            dialogUtil = null
        }
    }
}
