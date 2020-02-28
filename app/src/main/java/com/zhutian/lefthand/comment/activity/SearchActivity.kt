package com.zhutian.lefthand.comment.activity

import android.graphics.Rect
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.ui.widget.ShowButtonLayoutData
import com.zhutian.baselibrary.util.ScreenUtils
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.vm.SearchVm
import com.zhutian.lefthand.databinding.ActivitySearchBinding


class SearchActivity : BaseActivity<SearchVm, ActivitySearchBinding>(){

    override fun createViewModel(): SearchVm {
        return ViewModelProvider(this).get(SearchVm::class.java)
    }

    lateinit var historyData:ShowButtonLayoutData<String>

    override fun getContenViewId(): Int {
        return R.layout.activity_search
    }

    private val mRect = Rect()

    private fun translateAnimator() {
        mBinding.llAnima.layout(mBinding.llAnima.left,ScreenUtils.getScreenHeight(mContext)/2,mBinding.llAnima.right,-ScreenUtils.getScreenHeight(mContext))

        mBinding.llAnima.visibility = VISIBLE
        val tranAnimation = TranslateAnimation(0f, 0f,  ScreenUtils.getScreenHeight(mContext).toFloat()/2,0F)
        tranAnimation.interpolator = DecelerateInterpolator()

        val alphaAnimation = AlphaAnimation(0.0f, 1.0f)
        val aniSet = AnimationSet(true)
        aniSet.fillAfter = true
        aniSet.duration = 500
        aniSet.addAnimation(tranAnimation)
        aniSet.addAnimation(alphaAnimation)
        mBinding.llAnima.startAnimation(aniSet)

        // 设置回到正常的布局位置
        mBinding.llAnima.layout(mRect.left, mRect.top, mRect.right, mRect.bottom)
        mRect.setEmpty()
    }


    override fun startWork() {

        translateAnimator()

        fade()

        finishFunction(findViewById(R.id.ivBack))

        mViewModel.loadData()
        setViewData()

        mBinding.ivDelete.setOnClickListener {
            //移除所有历史
            historyData.updata()
            mBinding.llHistory.visibility = GONE
            //隐藏相关控件
        }
    }


    private fun setViewData() {
        //历史数据
        historyData = ShowButtonLayoutData(this,mBinding.sblHistory,mViewModel.mHistoryData,object : ShowButtonLayoutData.MyClickListener{
            override fun clickListener(v: View, lot: Double, lat: Double) {

            }
        })
        historyData.setData()

        //热门数据
        ShowButtonLayoutData(this,mBinding.sblHot,mViewModel.mHotsData,object : ShowButtonLayoutData.MyClickListener{
            override fun clickListener(v: View, lot: Double, lat: Double) {

            }
        }).setData()
    }


    var finishLive = MutableLiveData<Boolean>()
    override fun finish() {
        finishLive.observe(this, Observer {
            if (it){
                super.finish()
            }
        })
        Handler().postDelayed({
            finishLive.value = true
        },200)

        Thread{
            val tranAnimation = TranslateAnimation(0f, 0f, 0F ,ScreenUtils.getScreenHeight(mContext).toFloat()/2)
            tranAnimation.interpolator = DecelerateInterpolator()
            val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
            val aniSet = AnimationSet(true)
            aniSet.fillAfter = true
            aniSet.duration = 400
            aniSet.addAnimation(tranAnimation)
            aniSet.addAnimation(alphaAnimation)
            runOnUiThread {
                mBinding.llAnima.startAnimation(aniSet)
            }
        }.start()



    }
}
