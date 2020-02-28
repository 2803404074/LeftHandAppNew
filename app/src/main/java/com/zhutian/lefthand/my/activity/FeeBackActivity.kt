package com.zhutian.lefthand.my.activity

import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityFeebackBinding
import com.zhutian.lefthand.my.activity.vm.ExtensionVm
import com.zhutian.lefthand.my.activity.vm.FeeBackVm

class FeeBackActivity : BaseActivity<FeeBackVm, ActivityFeebackBinding>() {
    override fun createViewModel(): FeeBackVm {
        return ViewModelProvider(this).get(FeeBackVm::class.java)
    }

    override fun startWork() {
        finishFunction(mBinding.ivBack)
        mViewModel.initAdapter()
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter


        mBinding.tvCommit.setOnClickListener {
            mViewModel.feedBack(this,mBinding.etContent.text.toString())
        }

    }


    override fun getContenViewId(): Int {
        return R.layout.activity_feeback
    }


}
