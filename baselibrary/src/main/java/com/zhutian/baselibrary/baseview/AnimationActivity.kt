package com.zhutian.baselibrary.baseview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.zhutian.baselibrary.R

abstract class AnimationActivity : AppCompatActivity() {

    var isFade = false

    fun startFadeActivity(mClass:Class<*>){
        startActivity(Intent(this,mClass))
        // 淡出淡入动画效果
        overridePendingTransition(R.anim.activity_fade_out, R.anim.activity_fade_in)
    }

    fun fade(){
        isFade = true
    }

    override fun finish() {
        super.finish();
        //淡出淡入动画效果
        if (isFade){
            overridePendingTransition(R.anim.activity_fade_out, R.anim.activity_fade_in)
        }
    }
}