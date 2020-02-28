package com.zhutian.lefthand.my.fragment

import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fadai.particlesmasher.ParticleSmasher
import com.zhutian.baselibrary.baseview.BaseFragment
import com.zhutian.baselibrary.util.*
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.LoginActivity
import com.zhutian.lefthand.databinding.FragmentMyBinding
import com.zhutian.lefthand.my.activity.*
import com.zhutian.lefthand.my.fragment.vm.MyViewVm


class MyFragment : BaseFragment<MyViewVm, FragmentMyBinding>() {

    override fun createViewModel(): MyViewVm {
        return ViewModelProvider(this).get(MyViewVm::class.java)
    }

    override fun initLayout(): Int {
        return R.layout.fragment_my
    }

    override fun onResume() {
        super.onResume()
        val phone = SPHelper.get().getPhone()
        if (StringUtils.isEntity(phone)){
            mBinding.tvNickName.text = phone
            mBinding.tvNickName.isClickable = false
        }else{
            mBinding.tvNickName.text = "去登录"
        }
    }
    override fun startWork() {
        val path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582092764095&di=a43ffb1e0d5b7d82e7c53452b3f260a1&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201707%2F08%2F20170708154014_BczRG.jpeg"
        GlideImageLoader().displayImage(getSelfContext(),path,mBinding.ivContent01)

        mBinding.tvNickName.setOnClickListener {
            startActivity(Intent(getSelfContext(),LoginActivity::class.java))
        }

        mBinding.tvMyLove.setOnClickListener {
            startActivity(Intent(getSelfContext(),LoveActivity::class.java))
        }
        mBinding.tvMess.setOnClickListener {
            startActivity(Intent(getSelfContext(),MessActivity::class.java))
        }
        mBinding.rlXnb.setOnClickListener {
            startActivity(Intent(getSelfContext(),XnbActivity::class.java))
        }
        mBinding.rlExtension.setOnClickListener {
            startActivity(Intent(getSelfContext(),ExtensionActivity::class.java))
        }

        mBinding.tvFeeBack.setOnClickListener {
            startActivity(Intent(getSelfContext(),FeeBackActivity::class.java))
        }
        mBinding.tvHistory.setOnClickListener {
            startActivity(Intent(getSelfContext(),HistoryActivity::class.java))
        }

        mBinding.tvLogOut.setOnClickListener {
            DialogUtil.getInstance(activity!!).show(R.layout.dialog_base,true,false, object : Conver {
                override fun setView(view: View) {
                    view.findViewById<TextView>(R.id.tvConfirm).setOnClickListener {

                        DialogUtil.getInstance(activity!!).dismiss()

                        BottomDialogUtil.get().showNoHide(activity!!,R.layout.dialog_logout,0.0,object :Conver{
                            override fun setView(view: View) {
                                val img = view.findViewById<ImageView>(R.id.ivLogout)
                                GlideImageLoader().displayImage(activity,"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2577621724,3659343035&fm=26&gp=0.jpg",img)
                            }
                        })

                        activity?.let { it1 -> mViewModel.logOut(it1) }

                    }
                    view.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
                        DialogUtil.getInstance(activity!!).dismiss()
                    }
                }
            })
        }

        mViewModel.isLogOut.observe(this, Observer {
            if (it){
                Handler().postDelayed({
                    BottomDialogUtil.get().dess()
                },2000)

                mBinding.tvNickName.text = "去登录"
                mBinding.tvNickName.isClickable = true
            }
        })


        mBinding.tvQd.setOnClickListener {

            mViewModel.qDao()
            val smasher = ParticleSmasher(activity)
            smasher.with(mBinding.tvQd)
                .setDuration(2000)
                .setStartDelay(500)
                .setHorizontalMultiple(10F)
                .setVerticalMultiple(10F)
                .start()
        }
    }
}
