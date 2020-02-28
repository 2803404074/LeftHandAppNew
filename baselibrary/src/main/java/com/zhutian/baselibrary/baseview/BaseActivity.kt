package com.zhutian.baselibrary.baseview

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zhutian.baselibrary.util.AppManager
import com.zhutian.baselibrary.util.StatusBarUtil

abstract class BaseActivity<VM:BaseViewModel,DB : ViewDataBinding> : AnimationActivity() {


    protected lateinit var mContext: Activity

    //处理逻辑业务
    protected abstract fun startWork()

    //创建viewmodel
    protected abstract fun createViewModel():VM


    //获取当前activity布局文件,并初始化我们的binding
    protected abstract fun getContenViewId():Int

    //activity的binding对象
    protected lateinit var mBinding:DB

    protected lateinit var mViewModel:VM

    protected var isLoad = false//是否已经加载过这个activity


    protected var isFull = true//是否需要沉浸式
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        if (isFull){
            //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
            StatusBarUtil.setRootViewFitsSystemWindows(this, false)
            //设置状态栏透明
            StatusBarUtil.setTranslucentStatus(this)
            if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
                StatusBarUtil.setStatusBarColor(this, 0x55000000)
            }
        }

        if (!isLoad) {
            //初始化binding
            mBinding = DataBindingUtil.setContentView(this,getContenViewId())

            //给binding加上感知生命周期，AppCompatActivity就是lifeOwner
            mBinding.lifecycleOwner = this

            mViewModel = createViewModel()

            //业务处理
            startWork()
            isLoad = true
        }

    }

    override fun onStart() {
        super.onStart()
        AppManager().getAppManager().addActivity(this)
    }

    //设置非沉浸式activity
    fun isFull(full:Boolean){
        this.isFull = full
    }

    fun getSelfContext():Activity{
        return mContext
    }

    fun finishFunction(view:View){
        view.setOnClickListener {
            finish()
        }
    }

}