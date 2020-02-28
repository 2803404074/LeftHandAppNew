package com.zhutian.lefthand.home.fragment

import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseFragment
import com.zhutian.baselibrary.ui.widget.HomeScrollView
import com.zhutian.baselibrary.util.BottomDialogUtil
import com.zhutian.baselibrary.util.Conver
import com.zhutian.baselibrary.util.GlideImageLoader
import com.zhutian.lefthand.R
import com.zhutian.lefthand.comment.activity.AllTypeActivity
import com.zhutian.lefthand.comment.activity.SearchActivity
import com.zhutian.lefthand.databinding.FragmentHomeBinding
import com.zhutian.lefthand.home.vm.HomeFragmentVm
import com.zhutian.lefthand.my.activity.HistoryActivity


class HomeFragment : BaseFragment<HomeFragmentVm, FragmentHomeBinding>() {

    override fun createViewModel(): HomeFragmentVm {
        return ViewModelProvider(this).get(HomeFragmentVm::class.java)
    }

    override fun initLayout(): Int {
        return R.layout.fragment_home
    }

    override fun startWork() {

        //下拉
        mBinding.srl.setColorSchemeResources(R.color.text0,R.color.home_top,R.color.main01)
        mBinding.srl.setProgressViewEndTarget(true,500)
        mBinding.srl.setProgressViewOffset(true, 100, 600)
        mBinding.srl.setOnRefreshListener {
            Handler().postDelayed({
                mBinding.srl.isRefreshing = false
            },2000)
        }

        //滑动监听，设置滑动透明
        initScroll()

        //初始化页面适配器
        mViewModel.initAdapter(getSelfContext())

        //设置适配器
        mBinding.mRecyclerNews.adapter = mViewModel.mNewsAdapter

        mBinding.mRecyclerLikes.isNestedScrollingEnabled = false
        mBinding.mRecyclerLikes.adapter = mViewModel.mLikesAdapter

        mBinding.mRecyclerAll.isNestedScrollingEnabled = false
        mBinding.mRecyclerAll.adapter = mViewModel.mAllAdapter


        //点击换一换监听
        mBinding.tvChange.setOnClickListener {
            val anima = AnimationUtils.loadAnimation(getSelfContext(), R.anim.rote_360)
            anima.fillAfter = true
            mBinding.ivFresh.startAnimation(anima)
            mViewModel.loadLikes()
        }

        //加载轮播图
        mViewModel.loadBanner(mBinding.mBanner)

        //加载最新影片列表
        mViewModel.loadNews()

        //加载猜你喜欢
        mViewModel.loadLikes()

        //加载其他影片列表
        mViewModel.loadMovie()
        mViewModel.allLive.observe(this, Observer {
            mViewModel.mAllAdapter.setNewData(mViewModel.mAllData)
        })

        //跳转页面监听
        drop2ActivityListener()

    }


    private fun initScroll() {
        mBinding.llTop.alpha = 0F
        mBinding.nsv.setFadingView(mBinding.llTop)
        mBinding.nsv.setFadingHeightView(mBinding.mBanner)
    }

    private fun drop2ActivityListener() {
        mBinding.mIncludeSearch.llSearch.setOnClickListener {
            startFadeActivity(SearchActivity::class.java)
        }

        mBinding.ivAllType.setOnClickListener {
            startActivity(Intent(context, AllTypeActivity::class.java))
        }

        mBinding.ivTime.setOnClickListener {
            startActivity(Intent(context, HistoryActivity::class.java))
        }
    }

}
