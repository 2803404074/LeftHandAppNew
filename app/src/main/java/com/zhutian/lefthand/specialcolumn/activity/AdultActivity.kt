package com.zhutian.lefthand.specialcolumn.activity

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.util.GlideImageLoader
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityAdultBinding
import com.zhutian.lefthand.my.activity.vm.XnbDetailsVm

import com.zhutian.lefthand.specialcolumn.activity.vm.AdultVm

/**
 * 成人奖 页面
 */
class AdultActivity : BaseActivity<AdultVm, ActivityAdultBinding>() {

    override fun createViewModel(): AdultVm {
        return ViewModelProvider(this).get(AdultVm::class.java)
    }

    override fun startWork() {
        GlideImageLoader().displayImage(this,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581575051930&di=5c685c88a414845286309a9834040367&imgtype=0&src=http%3A%2F%2Fimg1.cache.netease.com%2Fcatchpic%2F4%2F42%2F4280C94E136ACEA88F91E0FE13004F78.JPG",mBinding.ivTop)
        mViewModel.initAdapter(mBinding.mRecyclerView)
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter
        mViewModel.loadData()
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_adult
    }

}
