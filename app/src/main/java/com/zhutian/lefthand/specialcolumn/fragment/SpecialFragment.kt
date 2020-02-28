package com.zhutian.lefthand.specialcolumn.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseFragment
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.FragmentSpecialBinding
import com.zhutian.lefthand.my.fragment.vm.NoticeVm
import com.zhutian.lefthand.specialcolumn.activity.SpecialDetailsActivity
import com.zhutian.lefthand.specialcolumn.fragment.vm.SpecialVm


class SpecialFragment : BaseFragment<SpecialVm, FragmentSpecialBinding>() {

    override fun createViewModel(): SpecialVm {
        return ViewModelProvider(this).get(SpecialVm::class.java)
    }

    override fun initLayout(): Int {
        return R.layout.fragment_special
    }

    override fun startWork() {


        mViewModel.initAdapter(getSelfContext())
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter

        mBinding.mRecyclerHots.isNestedScrollingEnabled = false
        mBinding.mRecyclerHots.adapter = mViewModel.mAdapterHots

        mBinding. mRecyclerLady.adapter = mViewModel.mAdapterLady


        mBinding. mRecyclerSeries.isNestedScrollingEnabled = false
        mBinding. mRecyclerSeries.adapter = mViewModel.mAdapterSeries


        mViewModel.loadData()
        mViewModel.loadHotsData()

        mViewModel.loadLadyData()

        mViewModel.loadSeriesData()


        mViewModel.loadBanner(mBinding.mBanner)

    }

}
