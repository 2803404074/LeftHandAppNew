package com.zhutian.baselibrary.app

import android.app.Application
import android.graphics.Bitmap
import androidx.multidex.MultiDex
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.youngfeng.snake.Snake
import com.zhutian.baselibrary.exeption.MyCrashHandler


abstract class MyApplication : Application() {

    companion object{
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()

        initTinker()

        Snake.init(this);

        MyCrashHandler.getInstance().init(this)


        instance = this

        MultiDex.install(this)
        //对ImagePipelineConfig进行一些配置
        val config = ImagePipelineConfig.newBuilder(applicationContext)

            .setDownsampleEnabled(true)// 对图片进行自动缩放

            .setResizeAndRotateEnabledForNetwork(true) // 对网络图片进行resize处理，减少内存消耗

        .setBitmapsConfig(Bitmap.Config.RGB_565) //图片设置RGB_565，减小内存开销  fresco默认情况下是RGB_8888
        .build()


        Fresco.initialize(this,config)
    }

    abstract fun initTinker()


}