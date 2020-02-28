package com.zhutian.lefthand.my.activity

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.util.GlideImageLoader
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.vm.SearchVm
import com.zhutian.lefthand.databinding.ActivityExtensionBinding

import com.zhutian.lefthand.my.activity.vm.ExtensionVm

class ExtensionActivity : BaseActivity<ExtensionVm, ActivityExtensionBinding>() {

    override fun createViewModel(): ExtensionVm {
        return ViewModelProvider(this).get(ExtensionVm::class.java)
    }

    override fun startWork() {
        finishFunction(mBinding.ivBack)
        GlideImageLoader().displayImage(this,"https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPng89251edaa08e313484e42384871dac6d103740f7016e1c761f0f3914657bea81",mBinding.mBgView)
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_extension
    }

}
