package com.zhutian.lefthand.my.activity

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.util.Conver
import com.zhutian.baselibrary.util.DialogUtil
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityLoveBinding
import com.zhutian.lefthand.my.activity.vm.HistoryVm
import com.zhutian.lefthand.my.activity.vm.LoveVm

class LoveActivity : BaseActivity<LoveVm, ActivityLoveBinding>() {


    override fun createViewModel(): LoveVm {
        return ViewModelProvider(this).get(LoveVm::class.java)
    }


    var isEdit = false
    var isAllSelect = true


    override fun startWork() {
        finishFunction(mBinding.ivBack)
        mViewModel.initAdapter(this)
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter
        mViewModel.loadData()

        mBinding.tvEdit.setOnClickListener {
            if (isEdit){
                mViewModel.showEdit(GONE)
                mBinding.tvEdit.text = "编辑"
                mBinding.llBottom.visibility = GONE
            }else{
                mViewModel.showEdit(VISIBLE)
                mBinding.tvEdit.text = "完成"
                mBinding.llBottom.visibility = VISIBLE
            }
            isEdit = !isEdit
        }

        mBinding.tvAllSelect.setOnClickListener {
            mViewModel.allSelect(isAllSelect)
            isAllSelect = !isAllSelect
        }

        mBinding.tvDelete.setOnClickListener {
            DialogUtil.getInstance(this).show(R.layout.dialog_base,true,false,object :Conver{
                override fun setView(view: View) {
                    view.findViewById<TextView>(R.id.tvTips).text = "确定删除选中的视频？"
                    view.findViewById<TextView>(R.id.tvConfirm).setOnClickListener {
                        mViewModel.delete(!isAllSelect)
                        DialogUtil.getInstance(getSelfContext()).dismiss()
                    }
                    view.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
                        DialogUtil.getInstance(getSelfContext()).dismiss()
                    }
                }
            })

        }

    }
    override fun getContenViewId(): Int {
        return R.layout.activity_love
    }

}
