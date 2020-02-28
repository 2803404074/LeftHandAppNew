package com.zhutian.baselibrary.baseview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.zhutian.baselibrary.R
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    var clickDrop = MutableLiveData<Boolean>()

    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: DB
    private var isLoad: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!isLoad!!) {
            Log.e("startF","执行onCreateView")
            mBinding = DataBindingUtil.inflate(inflater, initLayout(), container, false)
            mBinding.lifecycleOwner = this
            mViewModel = createViewModel()
            startWork()
            isLoad = true
        }
        return mBinding.root
    }

    abstract fun initLayout(): Int

    abstract fun startWork()

    protected abstract fun createViewModel():VM

    fun getSelfContext():Context{
        return this@BaseFragment.requireContext()
    }

    fun startFadeActivity(cls: Class<*>){
        startActivity(Intent(this@BaseFragment.requireContext(),cls))
        // 淡出淡入动画效果
        activity?.overridePendingTransition(R.anim.activity_fade_out, R.anim.activity_fade_in)
    }



}