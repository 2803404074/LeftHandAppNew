package com.zhutian.lefthand.comment.activity

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.model.TypeMo
import com.zhutian.baselibrary.ui.indicator.DynamicFragmentPagerAdapter
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.vm.AllTypeVm
import com.zhutian.lefthand.comment.activity.vm.HighTypeVm
import com.zhutian.lefthand.databinding.ActivityHighTypeBinding

class HighTypeActivity : BaseActivity<HighTypeVm, ActivityHighTypeBinding>() {

    override fun createViewModel(): HighTypeVm {
        return ViewModelProvider(this).get(HighTypeVm::class.java)
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_high_type
    }
    override fun startWork() {
        finishFunction(findViewById(R.id.ivBack))
        setViewPager()
    }
    private fun setViewPager() {
        val list = mutableListOf<TypeMo>()
        list.add(TypeMo(0,"三级最新"))
        list.add(TypeMo(1,"中国三级"))
        list.add(TypeMo(2,"日本三级"))
        list.add(TypeMo(3,"韩国三级"))
        list.add(TypeMo(4,"欧美三级"))
        list.add(TypeMo(5,"东南亚三级"))
        list.add(TypeMo(6,"非洲三级"))
        list.add(TypeMo(7,"澳大利亚三级"))
        mBinding.mViewPager.adapter =
            DynamicFragmentPagerAdapter(supportFragmentManager, mViewModel.createFragments(8), list)

        mBinding.mDpi.viewPager = mBinding.mViewPager

    }
}
