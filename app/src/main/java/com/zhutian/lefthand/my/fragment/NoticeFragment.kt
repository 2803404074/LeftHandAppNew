package com.zhutian.lefthand.my.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhutian.baselibrary.baseview.BaseFragment
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.LayoutRecyBinding
import com.zhutian.lefthand.my.fragment.vm.NoticeVm


class NoticeFragment : BaseFragment<NoticeVm, LayoutRecyBinding>() {

    override fun createViewModel(): NoticeVm {
        return ViewModelProvider(this).get(NoticeVm::class.java)
    }

    override fun initLayout(): Int {
        return R.layout.layout_recy
    }

    override fun startWork() {
        mBinding.recyDemo.layoutManager = LinearLayoutManager(getSelfContext())
        mViewModel.initAdapter(getSelfContext())
        mBinding.recyDemo.adapter = mViewModel.mAdapter

        mViewModel.loadData()
    }

    companion object {
        fun create(index: Int): NoticeFragment {
            val pagerFragment = NoticeFragment()
            val bundle = Bundle()
            bundle.putInt("index", index)
            pagerFragment.arguments = bundle
            return pagerFragment
        }
    }
}
