package com.zhutian.lefthand.home.fragment

import android.os.Bundle
import android.view.View.GONE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zhutian.baselibrary.baseview.BaseFragment
import com.zhutian.baselibrary.ui.custom.DividerGridItemDecoration
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.FragmentTypeBinding
import com.zhutian.lefthand.home.vm.HomeFragmentVm
import com.zhutian.lefthand.home.vm.TypeViewModel

class TypeFragment:BaseFragment<TypeViewModel, FragmentTypeBinding>() {

    override fun createViewModel(): TypeViewModel {
        return ViewModelProvider(this).get(TypeViewModel::class.java)
    }

    private var mFragmentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentIndex = arguments!!.getInt("index")
    }

    override fun initLayout(): Int {
        return R.layout.fragment_type
    }

    override fun startWork() {

        mViewModel.initMoreAdapter(getSelfContext(),layoutInflater)
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter

        mViewModel.isLoad.observe(this, Observer {

        })
        mViewModel.loadData()
    }
    companion object {
        fun create(index: Int): TypeFragment {
            val pagerFragment = TypeFragment()
            val bundle = Bundle()
            bundle.putInt("index", index)
            pagerFragment.arguments = bundle
            return pagerFragment
        }
    }
}