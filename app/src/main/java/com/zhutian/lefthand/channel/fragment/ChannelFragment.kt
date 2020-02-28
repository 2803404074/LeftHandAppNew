package com.zhutian.lefthand.channel.fragment

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseFragment
import com.zhutian.lefthand.R
import com.zhutian.lefthand.channel.vm.ChannelVm
import com.zhutian.lefthand.databinding.FragmentChannelBinding


class ChannelFragment : BaseFragment<ChannelVm, FragmentChannelBinding>() {
    override fun createViewModel(): ChannelVm {
        return ViewModelProvider(this).get(ChannelVm::class.java)
    }

    override fun initLayout(): Int {
        return R.layout.fragment_channel
    }

    override fun startWork() {

        mViewModel.initAllAdapter()

        mBinding.mRecyclerView.adapter = mViewModel.mAdapter

        mViewModel.loadData()
    }
}
