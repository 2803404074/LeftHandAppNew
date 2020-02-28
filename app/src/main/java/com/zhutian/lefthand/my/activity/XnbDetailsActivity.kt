package com.zhutian.lefthand.my.activity

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityXnbDetailsBinding
import com.zhutian.lefthand.my.activity.vm.XnbDetailsVm
import com.zhutian.lefthand.my.activity.vm.XnbVm

class XnbDetailsActivity : BaseActivity<XnbDetailsVm, ActivityXnbDetailsBinding>() {

    override fun createViewModel(): XnbDetailsVm {
        return ViewModelProvider(this).get(XnbDetailsVm::class.java)
    }

    override fun startWork() {
        finishFunction(mBinding.ivBack)
        mViewModel.initAdapter()
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter
        mViewModel.loadData()
    }
    override fun getContenViewId(): Int {
        return R.layout.activity_xnb_details
    }

}
