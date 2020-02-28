package com.zhutian.lefthand.my.activity

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityXnbBinding
import com.zhutian.lefthand.my.activity.vm.MessVm
import com.zhutian.lefthand.my.activity.vm.XnbVm

class XnbActivity : BaseActivity<XnbVm, ActivityXnbBinding>() {

    override fun createViewModel(): XnbVm {
        return ViewModelProvider(this).get(XnbVm::class.java)
    }

    override fun startWork() {
        finishFunction(mBinding.ivBack)
        mBinding.tvXnbDetails.setOnClickListener {
            startActivity(Intent(this,XnbDetailsActivity::class.java))
        }
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_xnb
    }

}
