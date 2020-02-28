package com.zhutian.lefthand.my.activity

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.model.TypeMo
import com.zhutian.baselibrary.ui.indicator.DynamicFragmentPagerAdapter
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityMessBinding
import com.zhutian.lefthand.my.activity.vm.LoveVm
import com.zhutian.lefthand.my.activity.vm.MessVm

class MessActivity : BaseActivity<MessVm, ActivityMessBinding>() {

    override fun createViewModel(): MessVm {
        return ViewModelProvider(this).get(MessVm::class.java)
    }

    override fun startWork() {
        finishFunction(mBinding.ivBack)
        setViewPager()
    }

    private fun setViewPager() {
        var list = mutableListOf<TypeMo>()
        list.add(TypeMo(0,"消息"))
        list.add(TypeMo(1,"通知"))
        mBinding.mViewPager.adapter =
            DynamicFragmentPagerAdapter(supportFragmentManager, mViewModel.createFragments(2), list)

        mBinding.mDpi.viewPager = mBinding.mViewPager

    }

    override fun getContenViewId(): Int {
        return R.layout.activity_mess
    }
}
