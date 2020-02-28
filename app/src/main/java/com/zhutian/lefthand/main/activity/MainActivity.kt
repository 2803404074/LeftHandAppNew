package com.zhutian.lefthand.main.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityMainBinding
import com.zhutian.lefthand.main.vm.MainModel
import com.zhutian.baselibrary.util.AppManager
import android.widget.Toast
import android.view.KeyEvent.KEYCODE_BACK
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.tencent.liteav.demo.player.superplayer.SuperPlayerActivityTest
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.util.GlideImageLoader
import java.util.*


class MainActivity : BaseActivity<MainModel, ActivityMainBinding>() {

    var isClickBale = true

    override fun createViewModel(): MainModel {
        return ViewModelProvider(this).get(MainModel::class.java)
    }

    override fun startWork() {
        //先判断是否有广告
        judgeAdvertisement()

        mBinding.ivMain.setOnClickListener {
            startActivity(Intent(this,SuperPlayerActivityTest::class.java))
        }

        mBinding.navView.itemIconTintList = null
        mViewModel.getNavFragment(R.id.mFrameLayout, supportFragmentManager)
        mViewModel.mNavHelper.performClickMenu(R.id.navigation_home)
        mBinding.navView.setOnNavigationItemSelectedListener { item ->
            selectItem(item)
        }
    }

    fun selectItem(item: MenuItem): Boolean {
        if (isClickBale) {
            return mViewModel.mNavHelper.performClickMenu(item.itemId)
        }
        return false
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.startHideService(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.stopHideService(this)
    }


    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KEYCODE_BACK && event.action === KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                AppManager().getAppManager().AppExit(this)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    var mAdvertisementContainer: RelativeLayout? = null
    var tvOpen: TextView? = null
    var liveAction = MutableLiveData<Boolean>()
    private var timer = Timer()
    private var recLen = 5//跳过倒计时提示5秒
    var isClick = false

    private fun judgeAdvertisement() {
        val hasAdvertisement = intent.getBooleanExtra("hasAdvertisement", false)
        if (hasAdvertisement) {
            mAdvertisementContainer =
                LayoutInflater.from(this).inflate(
                    R.layout.activity_wellcom,
                    null
                ) as RelativeLayout?
            mAdvertisementContainer?.let {
                val mImageView = it.findViewById<ImageView>(R.id.ivTop)
                GlideImageLoader().displayImage(
                    getSelfContext(),
                    intent.getStringExtra("cover"),
                    mImageView
                )
                tvOpen = it.findViewById(R.id.tvOpen)
                tvOpen?.setOnClickListener {
                    isClick = true
                    task.cancel()
                    mBinding.container.removeView(mAdvertisementContainer)
                }

                mImageView.setOnClickListener {
                    isClick = true
                    task.cancel()
                    val mIntent = Intent(this,HtmlActivity::class.java)
                    mIntent.putExtra("linkUrl",intent.getStringExtra("linkUrl"))
                    startActivity(mIntent)
                    mBinding.container.removeView(mAdvertisementContainer)
                }

                timer.schedule(task, 1000, 1000)

                mBinding.container.addView(it)
            }

            liveAction.observe(this,androidx.lifecycle.Observer {
                if (it){
                    mBinding.container.removeView(mAdvertisementContainer)
                }
            })
        }
    }

    var task: TimerTask = object : TimerTask() {
        override fun run() {
            runOnUiThread {
                recLen--
                tvOpen?.text = "广告$recLen"
                if (recLen <= 0) {
                    timer.cancel()
                    if (!isClick) {
                        liveAction.postValue(true)
                    }
                }
            }
        }
    }
}
