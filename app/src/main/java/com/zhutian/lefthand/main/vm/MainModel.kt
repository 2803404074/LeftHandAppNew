package com.zhutian.lefthand.main.vm

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.zhutian.baselibrary.basehttp.BaseObserver
import com.zhutian.baselibrary.basehttp.RequestUtil
import com.zhutian.baselibrary.basehttp.RetrofitApiService
import com.zhutian.baselibrary.basehttp.RetrofitManager
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.model.DemoMo
import com.zhutian.lefthand.R
import com.zhutian.lefthand.my.fragment.MyFragment
import com.zhutian.lefthand.main.ui.NavHelper
import com.zhutian.lefthand.home.fragment.HomeFragment
import com.zhutian.lefthand.specialcolumn.fragment.SpecialFragment
import com.zhutian.lefthand.channel.fragment.ChannelFragment
import com.zhutian.lefthand.comment.service.HideService
import io.reactivex.Observable

class MainModel :BaseViewModel(){

    lateinit var mNavHelper:NavHelper<String>

    var user = MutableLiveData<DemoMo>()

    fun getUser(){

    }

    fun getNavFragment(fraLayoutId:Int,manager: FragmentManager){
        mNavHelper = NavHelper<String>(mContent, fraLayoutId, manager,null)
        mNavHelper.add(R.id.navigation_home, NavHelper.Tab(HomeFragment::class.java, "aaa"))
        mNavHelper.add(R.id.navigation_dashboard, NavHelper.Tab(ChannelFragment::class.java, "aaa"))
        mNavHelper.add(R.id.navigation_notifications, NavHelper.Tab(SpecialFragment::class.java, "aaa"))
        mNavHelper.add(R.id.navigation, NavHelper.Tab(MyFragment::class.java, "aaa"))
    }

    fun startHideService(context:Context){
        context.startService(Intent(context, HideService::class.java))
    }

    fun stopHideService(context:Context){
        context.stopService(Intent(context, HideService::class.java))
    }
}