package com.zhutian.lefthand.specialcolumn.fragment.vm

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.widget.TextView
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.model.BannerMo
import com.zhutian.baselibrary.util.BannerUtil
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.baselibrary.util.TextColorUtil
import com.zhutian.lefthand.R
import com.zhutian.lefthand.specialcolumn.activity.*


class SpecialVm : BaseViewModel() {

    lateinit var mAdapter:MyBaseAdapter<String>
    var mData = mutableListOf<String>()

    lateinit var mAdapterHots:MyBaseAdapter<String>
    var mDataHots = mutableListOf<String>()

    lateinit var mAdapterLady:MyBaseAdapter<String>
    var mDataLady = mutableListOf<String>()


    lateinit var mAdapterSeries:MyBaseAdapter<String>
    var mDataSeries = mutableListOf<String>()



    fun initAdapter(context: Context){
        mAdapter = object :MyBaseAdapter<String>(mData, R.layout.item_jxtj){
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrlCircular(R.id.mImage,item)
            }
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (position==0){
                context.startActivity(Intent(context, AdultActivity::class.java))
            }else{
                context.startActivity(Intent(context,RecommendActivity::class.java))
            }
        }



        mAdapterHots = object :MyBaseAdapter<String>(mDataHots, R.layout.item_hots_label){
            override fun myConvert(helper: MyViewHolder, item: String) {
                val tv = helper.getView<TextView>(R.id.tvShow)
                if (helper.layoutPosition %2==0){
                    tv.setBackgroundResource(R.drawable.shape_zi)
                    TextColorUtil.setTextColorStyle(tv,"#9653FE","#507FFF")
                }else{
                    tv.setBackgroundResource(R.drawable.shape_hui)
                    TextColorUtil.setTextColor(context,tv,R.color.textTitleSun)
                }
            }
        }
        mAdapterHots.setOnItemClickListener { adapter, view, position ->
            context.startActivity(Intent(context,HotsLabelActivity::class.java))
        }

        mAdapterLady = object :MyBaseAdapter<String>(mDataLady, R.layout.item_lady){
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrlCircular(R.id.mImage,item)
                helper.setImageByUrl(R.id.iv01,"https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPng87bab653e3b2efbb3e4a55ad8656f5c9a9709876c53cfd640b5f459c5de1cb45")
                helper.setImageByUrl(R.id.iv02,"https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPng8d4a6bf5bacfcc30781b39d90fe83d686f687bfdfd5c04692e5d42d7bedbaa77")
                helper.setImageByUrl(R.id.iv03,"https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPng9e9a3bc9fa30e25ffb60c372433a7a921128793fcfdcb3357da5dc6c2db3784a")
            }
        }
        mAdapterLady.setOnItemClickListener { adapter, view, position ->
            context.startActivity(Intent(context,HotsLadyActivity::class.java))
        }


        mAdapterSeries = object :MyBaseAdapter<String>(mDataSeries, R.layout.item_series){
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrlCircular(R.id.mImage,item)
                val tvText = helper.getView<TextView>(R.id.tvName)
                tvText.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线
                tvText.paint.isAntiAlias = true//抗锯齿
            }
        }
        mAdapterSeries.setOnItemClickListener { adapter, view, position ->
            context.startActivity(Intent(context,SpecialDetailsActivity::class.java))
        }

    }

    fun loadData(){
        mData.clear()
        for (index in 0..5){
            mData.add("https://lanhu.oss-cn-beijing.aliyuncs.com/SketchPng9344d21b4835cfd92f560787a40e1b41657ed070d7e6e5493751deeab4f3f7fd")
        }
        mAdapter.setNewData(mData)
    }


    fun loadHotsData(){
        mDataHots.clear()
        for (index in 0..5){
            mDataHots.add("标签名")
        }
        mAdapterHots.setNewData(mDataHots)
    }

    fun loadLadyData(){
        mDataLady.clear()
        for (index in 0..10){
            mDataLady.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581679687505&di=54eaf2d7cb38461da10dd846aa4bd6a6&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201201%2F12%2F100203fypyfv127l9vp94y.jpg")
        }
        mAdapterLady.setNewData(mDataLady)
    }

    fun loadSeriesData(){
        mDataSeries.clear()
        for (index in 0..10){
            mDataSeries.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581679994520&di=22d9bc7b99255fcb203b9db78e71f83d&imgtype=0&src=http%3A%2F%2Fimage001.qianmengyuan.net%2Ffanhao%2F1pondo%2F1pondo110211_207.jpg")
        }
        mAdapterSeries.setNewData(mDataSeries)
    }


    fun loadBanner(banner: Banner){
        val bannerMo = mutableListOf<BannerMo>()
        bannerMo.add(BannerMo("广告一","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581679868096&di=b7075df9e94934d4cbf67fd0809671a7&imgtype=0&src=http%3A%2F%2Fp19.qhimg.com%2Fbdr%2F__%2Fd%2F_open360%2Fmn0830%2F8.jpg","www.baidu.com"))
        bannerMo.add(BannerMo("广告二","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581679887277&di=95f0dda8efa6d78b70caad74711a5246&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-10-11%2F59dd7e2debd08.jpg","www.baidu.com"))
        bannerMo.add(BannerMo("广告三","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581679945199&di=97dc621bbf6f92a6e9441be8a428f1ec&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-10-18%2F59e6c4c9924a2.jpg","www.baidu.com"))
        BannerUtil.startBanner(bannerMo,banner, OnBannerListener {
                position ->
        })

    }
}
