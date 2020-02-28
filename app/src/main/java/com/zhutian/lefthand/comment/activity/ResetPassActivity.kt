package com.zhutian.lefthand.comment.activity

import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.util.Conver
import com.zhutian.baselibrary.util.DialogUtil
import com.zhutian.baselibrary.util.ToastUtil
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.vm.RegisterVm
import com.zhutian.lefthand.comment.activity.vm.ResetPassVm
import com.zhutian.lefthand.databinding.ActivityResetpassBinding
import kotlinx.android.synthetic.main.activity_register.*

class ResetPassActivity : BaseActivity<ResetPassVm, ActivityResetpassBinding>() {

    override fun createViewModel(): ResetPassVm {
        return ViewModelProvider(this).get(ResetPassVm::class.java)
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

        //确定
        mBinding.tvRegister.setOnClickListener {
            mViewModel.resetPas(
                this,
                mBinding.etPhoneInput.text.toString(),
                mBinding.etCodeInput.text.toString(),
                mBinding.etPwdInput.text.toString())
        }

        //观察注册时留空的信息
        mViewModel.resetPassStatus.observe(this, Observer {
            if (it){
                DialogUtil.getInstance(this).show(R.layout.dialog_base,false,false,object : Conver {
                    override fun setView(view: View) {
                        view.findViewById<TextView>(R.id.tvTips).text = "修改成功,前往登录"
                        view.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
                            DialogUtil.getInstance(this@ResetPassActivity ).dismiss()
                        }
                        view.findViewById<TextView>(R.id.tvConfirm).setOnClickListener {
                            setResult(100, Intent().putExtra("phone",mBinding.etPhoneInput.text.toString()))
                            finish()
                            DialogUtil.getInstance(this@ResetPassActivity ).dismiss()
                        }
                    }
                })

            }else{
                ToastUtil.get().show(this,"网络连接失败,请重新操作")
            }
        })

    }

    override fun getContenViewId(): Int {
        return R.layout.activity_resetpass
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
