package com.zhutian.lefthand.specialcolumn.activity

import android.graphics.Rect
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.util.GlideImageLoader
import com.zhutian.baselibrary.util.ScreenUtils
import com.zhutian.baselibrary.util.TextColorUtil
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityRecommendBinding
import com.zhutian.lefthand.specialcolumn.activity.vm.HotsLadyVm

import com.zhutian.lefthand.specialcolumn.activity.vm.RecommendVm

/**
 * 专栏-精选推荐
 */
class RecommendActivity : BaseActivity<RecommendVm, ActivityRecommendBinding>() {

    var newsCheck = true//最新状态
    var hotsCheck = false//最热状态
    lateinit var animation:TranslateAnimation

    override fun createViewModel(): RecommendVm {
        return ViewModelProvider(this).get(RecommendVm::class.java)
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_recommend
    }

    override fun startWork() {
        GlideImageLoader().displayImage(this,"https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPngd77532bd9134eecb7421a2fc52a21e35eafaf450bd139df44e6c06bc1ed76c86",mBinding.ivTop)
        mViewModel.initAdapter(this)
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter
        mViewModel.loadData()

        //最新最热切换
        mBinding.tvNews.setOnClickListener {
            if (!newsCheck){
                selectFunc(true)
            }
        }
        mBinding.tvHots.setOnClickListener {
            if (!hotsCheck){
                selectFunc(false)
            }
        }
    }


    private fun selectFunc(default:Boolean){
        if (default){
            TextColorUtil.setTextColor(this,mBinding.tvHots,R.color.white)
            TextColorUtil.setTextColor(this,mBinding.tvNews,R.color.textTitleOld)
            animation = TranslateAnimation(ScreenUtils.dp2px(this,45f),0f,0f,0f)
            hotsCheck = false
        }else{
            TextColorUtil.setTextColor(this,mBinding.tvNews,R.color.white)
            TextColorUtil.setTextColor(this,mBinding.tvHots,R.color.textTitleOld)
            animation = TranslateAnimation(0f,ScreenUtils.dp2px(this,45f),0f,0f)
            newsCheck = false
        }
        animation.fillAfter = true
        animation.duration = 250
        mBinding.viewBg.startAnimation(animation)
    }



}
