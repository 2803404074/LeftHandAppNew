package com.zhutian.baselibrary.util

import androidx.core.view.get
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener
import com.zhutian.baselibrary.model.BannerMo

class BannerUtil {



    companion object{
        fun startBanner(bannerMo: MutableList<BannerMo>, banner: Banner, listener: OnBannerListener){
            var imgList = mutableListOf<String>()
            var titleList = mutableListOf<String>()
            for (index in 0 until bannerMo.size){
                imgList.add(bannerMo[index].url!!)
                titleList.add(bannerMo[index].title!!)
            }
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

            //设置图片加载器，图片加载器在下方
            banner.setImageLoader(GlideImageLoader());

            //设置图片网址或地址的集合
            banner.setImages(imgList);

            //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
            banner.setBannerAnimation(Transformer.Accordion);

            //设置轮播图的标题集合
            banner.setBannerTitles(titleList);

            //设置轮播间隔时间
            banner.setDelayTime(5000);

            //设置是否为自动轮播，默认是“是”。
            banner.isAutoPlay(true);

            //设置指示器的位置，小点点，左中右。
            banner.setIndicatorGravity(BannerConfig.CENTER)

            //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
            banner.setOnBannerListener(listener)

            //必须最后调用的方法，启动轮播图。
            banner.start();
        }
    }
}