package com.zhutian.lefthand.main.activity

import android.Manifest
import android.Manifest.permission.*
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.zhutian.baselibrary.basehttp.BaseObserver
import com.zhutian.baselibrary.basehttp.RequestUtil
import com.zhutian.baselibrary.projectmodel.TouristMo
import com.zhutian.baselibrary.util.DeviceUtil
import com.zhutian.baselibrary.util.SPHelper
import com.zhutian.baselibrary.util.StringUtils
import com.zhutian.baselibrary.util.ToastUtil
import com.zhutian.lefthand.R
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.view.View.VISIBLE
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fadai.particlesmasher.ParticleSmasher
import com.fadai.particlesmasher.SmashAnimator
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONObject

/**
 * 动画框架
 *  val smasher = ParticleSmasher(this@WellComActivity)
smasher.with(mView)
.setDuration(2000)
.setHorizontalMultiple(10F)
.setVerticalMultiple(10F)
.start()
 */
class WellComActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wellcom_t)
        checkPer()
    }

    private fun touristRegister() {
        val map = HashMap<String, Any>()
        map["device"] = DeviceUtil.getDeviceId(this)//设备名
        map["platform"] = "leftHandApp"//平台
        map["system"] = "android"//系统
        map["version"] = DeviceUtil.getVersionName(this)//版本

        RequestUtil.touristRegister(false, false, map, object : BaseObserver<Any>() {
            override fun onSuccess(result: Any?) {
                //LinkedTreeMap
                val mo = Gson().fromJson<TouristMo>(result.toString(), TouristMo::class.java)
                mo?.let {
                    //保存token
                    it.token?.let { it1 ->
                        SPHelper.get().setToken(it1)
                    }
                    //保存加密盐值
                    it.salt?.let { it2 ->
                        SPHelper.get().setSalt(it2)
                    }

                    ToastUtil.get().show(this@WellComActivity, "成功：${mo.token}")
                }
                //为不为空都跳到主页面
                startDrop()
            }

            override fun onFailure(e: Throwable?, message: String?) {
                ToastUtil.get().show(this@WellComActivity, "通知：$message")
                startDrop()
            }
        })
    }


    private val PERMISSIONS_STORAGE = arrayOf<String>(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE,READ_PHONE_STATE)
    private fun checkPer() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                !== PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
                !== PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE)
                !== PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 100)
            }else{
                judge()
            }
        }else{
            judge()
        }
    }

    fun judge() {
        //是否有token，如果有，直接去首页，如果没有，注册游客
        if (StringUtils.isEntity(SPHelper.get().getToken())) {
            startDrop()
        } else {
            //注册游客
            touristRegister()
        }
    }

    var liveDrop = MutableLiveData<Boolean>()
    fun startDrop() {
        liveDrop.observe(this, Observer {
            //startActivity(Intent(this@WellComActivity, AdvertActivity::class.java))

            val mIntent = Intent(this@WellComActivity,MainActivity::class.java)
            mIntent.putExtra("hasAdvertisement",true)
            mIntent.putExtra("cover","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582694926967&di=71a75f6f54d4d827595593f37d352147&imgtype=0&src=http%3A%2F%2Fimage13.m1905.cn%2Fuploadfile%2F2013%2F0422%2F20130422102549664.jpg")
            mIntent.putExtra("linkUrl","http://zuo8.live/")
            startActivity(mIntent)
            overridePendingTransition(R.anim.activity_fade_out, R.anim.activity_fade_in)
            finish()
        })

        val mView = findViewById<TextView>(R.id.tvTitle)
        mView.visibility = VISIBLE
        val alph = AlphaAnimation(0.0f, 1.0f)
        alph.duration = 500
        alph.fillAfter = true
        alph.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationRepeat(p0: Animation?) {
            }
            override fun onAnimationEnd(p0: Animation?) {
                liveDrop.value = true
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
        mView.startAnimation(alph)

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val a = grantResults.isNotEmpty()
        val b = grantResults[2]
        val c = PackageManager.PERMISSION_GRANTED
        if (requestCode == 100) {
            if (a && b == c) {
                judge()
            } else {
                //弹出框让用户去设置
            }
        }
    }
}
