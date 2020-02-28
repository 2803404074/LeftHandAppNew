package com.zhutian.lefthand.my.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityNoticeDetailsBinding
import com.zhutian.lefthand.databinding.ActivityXnbDetailsBinding
import com.zhutian.lefthand.my.activity.vm.NoticeDetailsVm
import com.zhutian.lefthand.my.activity.vm.XnbDetailsVm
import com.zhutian.lefthand.my.activity.vm.XnbVm

class NoticeDetailsActivity : BaseActivity<NoticeDetailsVm, ActivityNoticeDetailsBinding>() {

    override fun createViewModel(): NoticeDetailsVm {
        return ViewModelProvider(this).get(NoticeDetailsVm::class.java)
    }

    override fun startWork() {
        finishFunction(mBinding.ivBack)

        val mId = intent.getStringExtra("mId")
        mViewModel.loadData(mId)
        mViewModel.liveMo.observe(this, Observer {
            mBinding.tvTitle.text = it.title
            mBinding.tvDate.text = it.updated_at
            mBinding.tvBody.text = it.body
        })

    }
    override fun getContenViewId(): Int {
        return R.layout.activity_notice_details
    }

}
