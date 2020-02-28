package com.zhutian.baselibrary.util

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import com.rey.material.app.BottomSheetDialog
import com.zhutian.baselibrary.R

class BottomDialogUtil {
    private var dialog: BottomSheetDialog? = null

    private var onDismissCallBack: OnDismissCallBack? = null
    private var onShowCallBack: OnShowCallBack? = null

    interface OnDismissCallBack {
        fun onDismiss()
    }

    interface OnShowCallBack {
        fun onShow()
    }

    fun setOnShowCallBack(onShowCallBack: OnShowCallBack) {
        this.onShowCallBack = onShowCallBack
    }

    fun setOnDismissCallBack(onDismissCallBack: OnDismissCallBack) {
        this.onDismissCallBack = onDismissCallBack
    }

    fun show(mContext:Activity,layoutId: Int, h: Double, convers: Conver) {
        dialog = BottomSheetDialog(mContext, R.style.Animation_Design_BottomSheetDialog)
        val view = LayoutInflater.from(mContext).inflate(layoutId, null)
        convers.setView(view)
        val height = h.toInt()

        if (mContext.isFinishing) {
            return
        }

        if (h == 0.0) {
            dialog!!.contentView(view)
                .inDuration(500)
                .outDuration(500)
                .cancelable(true)
                .show()
        } else {
            dialog!!.contentView(view)
                .heightParam(height)
                .inDuration(500)
                .outDuration(500)
                .cancelable(true)
                .show()

        }
    }



    fun showNoHide(mContext:Activity,layoutId: Int, h: Double, convers: Conver) {
        dialog = BottomSheetDialog(mContext, R.style.Animation_Design_BottomSheetDialog)
        val view = LayoutInflater.from(mContext).inflate(layoutId, null)
        convers.setView(view)
        val height = h.toInt()

        if (mContext.isFinishing) {
            return
        }

        if (h == 0.0) {
            dialog!!.contentView(view)
                .inDuration(300)
                .outDuration(300)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .show()
        } else {
            dialog!!.contentView(view)
                .heightParam(height)
                .inDuration(300)
                .outDuration(300)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .show()

        }
    }




    fun dess() {
        dialog?.dismiss()
    }

    companion object {
        private var bottomDialogUtil : BottomDialogUtil? = null
            get() {
                if (field == null){
                    field = BottomDialogUtil()
                }
                return field
            }
        @Synchronized fun get():BottomDialogUtil{
            return bottomDialogUtil!!
        }
    }


}
