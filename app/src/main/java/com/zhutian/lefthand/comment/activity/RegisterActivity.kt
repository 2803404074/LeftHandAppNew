package com.zhutian.lefthand.comment.activity

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.vm.RegisterVm
import com.zhutian.lefthand.databinding.ActivityRegisterBinding
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.zhutian.baselibrary.util.Conver
import com.zhutian.baselibrary.util.DialogUtil
import com.zhutian.baselibrary.util.ToastUtil
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BaseActivity<RegisterVm, ActivityRegisterBinding>() {

    override fun createViewModel(): RegisterVm {
        return ViewModelProvider(this).get(RegisterVm::class.java)
    }

    override fun startWork() {
        finishFunction(mBinding.ivBack)
        mBinding.tvGetCode.setOnClickListener {
            downDate()
            //获取验证码
            mViewModel.getMess(mBinding.etPhoneInput.text.toString())
        }

        //观察短时是否发送成功
        mViewModel.isSendSuccess.observe(this, Observer {
            if (it == false){
                tvGetCode.isClickable = true
                tvGetCode.text = "重新获取"
            }
        })

        //注册
        mBinding.tvRegister.setOnClickListener {
            mViewModel.register(
                this,
                mBinding.etPhoneInput.text.toString(),
                mBinding.etCodeInput.text.toString(),
                mBinding.etPwdInput.text.toString())
        }


        //观察注册时留空的信息
        mViewModel.registerStatus.observe(this, Observer {
            if (it){
                DialogUtil.getInstance(this).show(R.layout.dialog_base,false,false,object :Conver{
                    override fun setView(view: View) {
                        view.findViewById<TextView>(R.id.tvTips).text = "注册成功,前往登录"
                        view.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
                            DialogUtil.getInstance(this@RegisterActivity ).dismiss()
                        }
                        view.findViewById<TextView>(R.id.tvConfirm).setOnClickListener {
                            setResult(100, Intent().putExtra("phone",mBinding.etPhoneInput.text.toString()))
                            finish()
                            DialogUtil.getInstance(this@RegisterActivity ).dismiss()
                        }
                    }
                })

            }else{
                ToastUtil.get().show(this,"网络连接失败,请重新操作")
            }
        })
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_register
    }

    /**
     * 倒计时开始
     */
    private fun downDate() {
        object : CountDownTimer((60 * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mBinding.tvGetCode.text = ((millisUntilFinished / 1000).toString() + "秒")
                mBinding.tvGetCode.isClickable = false
            }
            override fun onFinish() {
                tvGetCode.isClickable = true
                tvGetCode.text = "重新获取"
            }
        }.start()
    }

}
