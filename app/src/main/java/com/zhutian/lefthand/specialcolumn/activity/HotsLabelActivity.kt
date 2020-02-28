package com.zhutian.lefthand.specialcolumn.activity

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.util.GlideImageLoader
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityHotsLabelBinding
import com.zhutian.lefthand.specialcolumn.activity.vm.AdultVm

import com.zhutian.lefthand.specialcolumn.activity.vm.HotsLabelVm

/**
 * 热门标签详情
 */
class HotsLabelActivity : BaseActivity<HotsLabelVm, ActivityHotsLabelBinding>() {

    override fun createViewModel(): HotsLabelVm {
        return ViewModelProvider(this).get(HotsLabelVm::class.java)
    }

    override fun startWork() {

        GlideImageLoader().displayImage(this,"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3589907345,1908375408&fm=26&gp=0.jpg",mBinding.mIvTop)

        mViewModel.initAdapter(this)
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter
        mViewModel.loadData()
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_hots_label
    }

}
