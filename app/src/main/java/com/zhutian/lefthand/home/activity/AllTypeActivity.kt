package com.zhutian.lefthand.comment.activity

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.vm.AllTypeVm
import com.zhutian.lefthand.databinding.ActivityAllTypeBinding
import com.zhutian.lefthand.main.vm.MainModel

class AllTypeActivity : BaseActivity<AllTypeVm, ActivityAllTypeBinding>() {

    override fun createViewModel(): AllTypeVm {
        return ViewModelProvider(this).get(AllTypeVm::class.java)
    }
    override fun getContenViewId(): Int {
        return R.layout.activity_all_type
    }

    override fun startWork() {

        finishFunction(findViewById(R.id.ivBack))

        mViewModel.initAdapter(this)
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter
        mViewModel.loadData()

    }
}
