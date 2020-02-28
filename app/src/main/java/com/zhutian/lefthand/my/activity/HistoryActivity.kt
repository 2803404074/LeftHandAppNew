package com.zhutian.lefthand.my.activity

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.youngfeng.snake.annotations.EnableDragToClose
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.model.TypeMo
import com.zhutian.baselibrary.ui.indicator.DynamicFragmentPagerAdapter
import com.zhutian.baselibrary.util.Conver
import com.zhutian.baselibrary.util.DialogUtil
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityHistroyBinding
import com.zhutian.lefthand.my.activity.vm.HistoryVm

@EnableDragToClose
class HistoryActivity : BaseActivity<HistoryVm, ActivityHistroyBinding>() {


    override fun createViewModel(): HistoryVm {
        return ViewModelProvider(this).get(HistoryVm::class.java)
    }


    override fun startWork() {
        finishFunction(mBinding.ivBack)
        mBinding.tvClean.setOnClickListener {
            DialogUtil.getInstance(this).show(R.layout.dialog_base,true,false,object : Conver {
                override fun setView(view: View) {
                    view.findViewById<TextView>(R.id.tvTips).text = "确定清空内容吗？"
                    view.findViewById<TextView>(R.id.tvConfirm).setOnClickListener {
                        startActivity(Intent(getSelfContext(), HistoryActivity::class.java))
                        finish()
                        DialogUtil.getInstance(getSelfContext()).dismiss()
                    }
                    view.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
                        DialogUtil.getInstance(getSelfContext()).dismiss()
                    }
                }
            })
        }
        setViewPager()
    }

    private fun setViewPager() {
        val list = mutableListOf<TypeMo>()
        list.add(TypeMo(0,"今天"))
        list.add(TypeMo(1,"七天"))
        list.add(TypeMo(2,"更早"))
        mBinding.mViewPager.adapter =
            DynamicFragmentPagerAdapter(supportFragmentManager, mViewModel.createFragments(3), list)

        mBinding.mDpi.viewPager = mBinding.mViewPager


        mBinding.mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

            }

        })

    }

    override fun getContenViewId(): Int {
        return R.layout.activity_histroy
    }
}
