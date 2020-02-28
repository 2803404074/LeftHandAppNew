package com.zhutian.lefthand.comment.activity

import android.content.Intent
import android.graphics.Paint
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.util.SPHelper
import com.zhutian.baselibrary.util.ToastUtil
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.vm.HighTypeVm
import com.zhutian.lefthand.comment.activity.vm.LoginVm
import com.zhutian.lefthand.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<LoginVm, ActivityLoginBinding>() {

    override fun createViewModel(): LoginVm {
        return ViewModelProvider(this).get(LoginVm::class.java)
    }

    override fun startWork() {
        finishFunction(mBinding.ivBack)
        //加下划线
        mBinding.tvRegister.paint.flags = Paint.UNDERLINE_TEXT_FLAG

        //注册
        mBinding.tvRegister.setOnClickListener {
            startActivityForResult(Intent(this,RegisterActivity::class.java),100)
        }
        //忘记密码
        mBinding.tvForgetPass.setOnClickListener {
            startActivityForResult(Intent(this,ResetPassActivity::class.java),100)
        }
        //登录
        mBinding.tvLogin.setOnClickListener {
            mViewModel.login(
                this@LoginActivity,
                mBinding.etPhoneInput.text.toString(),
                mBinding.etPwdInput.text.toString()
            )
        }

        mViewModel.isLogin.observe(this, Observer {
            if (it){
                ToastUtil.get().show(this,"登录成功")
                SPHelper.get().setPhone(mBinding.etPhoneInput.text.toString())
                finish()
            }
        })
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_login
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100 && resultCode == 100){
            val phone = data?.getStringExtra("phone")
            mBinding.etPhoneInput.setText(phone)
        }
    }

}
