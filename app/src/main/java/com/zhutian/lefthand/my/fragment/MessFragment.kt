package com.zhutian.lefthand.my.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhutian.baselibrary.baseview.BaseFragment
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.LayoutRecyBinding
import com.zhutian.lefthand.my.fragment.vm.HistoryFgVm
import com.zhutian.lefthand.my.fragment.vm.MessFragVm

/**
 * 消息通知列表
 */
class MessFragment : BaseFragment<MessFragVm, LayoutRecyBinding>() {

    override fun createViewModel(): MessFragVm {
        return ViewModelProvider(this).get(MessFragVm::class.java)
    }

    override fun initLayout(): Int {
        return R.layout.layout_recy
    }

    override fun startWork() {
        mBinding.recyDemo.layoutManager = LinearLayoutManager(getSelfContext())
        mViewModel.initAdapter()
        mBinding.recyDemo.adapter = mViewModel.mAdapter

        mViewModel.loadData(getSelfContext())
    }

    companion object {
        fun create(index: Int): MessFragment {
            val pagerFragment = MessFragment()
            val bundle = Bundle()
            bundle.putInt("index", index)
            pagerFragment.arguments = bundle
            return pagerFragment
        }
    }
}
