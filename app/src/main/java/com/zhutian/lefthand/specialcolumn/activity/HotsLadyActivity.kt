package com.zhutian.lefthand.specialcolumn.activity

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.util.GlideImageLoader
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityHotsLadyBinding
import com.zhutian.lefthand.specialcolumn.activity.vm.HotsLabelVm
import com.zhutian.lefthand.specialcolumn.activity.vm.HotsLadyVm

/**
 * 热门女友详情
 */
class HotsLadyActivity : BaseActivity<HotsLadyVm, ActivityHotsLadyBinding>() {

    override fun createViewModel(): HotsLadyVm {
        return ViewModelProvider(this).get(HotsLadyVm::class.java)
    }

    override fun startWork() {
        GlideImageLoader().displayImage(
            this,
            "https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPng61ff54d3793936da43f7ab312ff2c48cee6b2613e3c013775b39be355289dc78",
            mBinding.ivTop
        )

        GlideImageLoader().displayImage(
            this,
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581680667971&di=ea7ec2bdd70c0fcb5e236b343e975c51&imgtype=0&src=http%3A%2F%2Fimg02.jszdgyyq.com%2Ffanhao%2F1pondo%2F1pondo031018_656.jpg",
            mBinding.ivHead
        )
        mViewModel.initAdapter(this)
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter
        mViewModel.loadData()
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_hots_lady
    }

}
