package com.zhutian.lefthand.my.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhutian.baselibrary.baseview.BaseFragment
import com.zhutian.baselibrary.util.ToastUtil
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.LayoutRecyBinding
import com.zhutian.lefthand.home.vm.TypeViewModel
import com.zhutian.lefthand.my.fragment.vm.HistoryFgVm

class HistoryFragment:BaseFragment<HistoryFgVm, LayoutRecyBinding>() {


    override fun createViewModel(): HistoryFgVm {
        return ViewModelProvider(this).get(HistoryFgVm::class.java)
    }

    private var mFragmentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentIndex = arguments!!.getInt("index")
    }

    override fun initLayout(): Int {
        return R.layout.layout_recy
    }

    override fun startWork() {
        mBinding.recyDemo.layoutManager = LinearLayoutManager(getSelfContext()) as RecyclerView.LayoutManager
        mViewModel.initAdapter(getSelfContext())
        mBinding.recyDemo.adapter = mViewModel.mAdapter
        mViewModel.loadData()
    }
    companion object {
        fun create(index: Int): HistoryFragment {
            val pagerFragment = HistoryFragment()
            val bundle = Bundle()
            bundle.putInt("index", index)
            pagerFragment.arguments = bundle
            return pagerFragment
        }
    }
}