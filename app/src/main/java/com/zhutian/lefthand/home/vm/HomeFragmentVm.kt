package com.zhutian.lefthand.home.vm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.tencent.liteav.demo.player.superplayer.SuperPlayerActivity
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import com.zhutian.baselibrary.basehttp.BaseObserver
import com.zhutian.baselibrary.basehttp.MyObserver
import com.zhutian.baselibrary.basehttp.RequestUtil
import com.zhutian.baselibrary.basehttp.RetrofitManager
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.model.BannerMo
import com.zhutian.baselibrary.projectmodel.BaseListMo
import com.zhutian.baselibrary.projectmodel.MessListMo
import com.zhutian.baselibrary.util.BannerUtil
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R

class HomeFragmentVm : BaseViewModel(){

    lateinit var mNewsAdapter:MyBaseAdapter<String>
    var mNewsData = mutableListOf<String>()

    lateinit var mLikesAdapter:MyBaseAdapter<String>
    var mLikesData = mutableListOf<String>()

    lateinit var mAllAdapter:MyBaseAdapter<MutableList<String>>
    var mAllData = mutableListOf<MutableList<String>>()

    fun initAdapter(context: Context){
        mNewsAdapter = object :MyBaseAdapter<String>(mNewsData, R.layout.item_news){
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrlCircular(R.id.ivContent,item)
            }
        }
        mNewsAdapter.setOnItemClickListener {
                adapter, view, position ->
            context.startActivity(Intent(context, SuperPlayerActivity::class.java))
        }


        mLikesAdapter = object :MyBaseAdapter<String>(mLikesData, R.layout.item_special_about){
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrlCircular(R.id.ivContent,item)
            }
        }
        mLikesAdapter.setOnItemClickListener {
                adapter, view, position ->
            context.startActivity(Intent(context, SuperPlayerActivity::class.java))
        }

        mAllAdapter = object :MyBaseAdapter<MutableList<String>>(mAllData, R.layout.item_type){
            override fun myConvert(helper: MyViewHolder, item: MutableList<String>) {
                val mRecyclerView = helper.getView<RecyclerView>(R.id.mRecyclerView)
                mRecyclerView.isNestedScrollingEnabled = false
                val mThisAdapter = object :MyBaseAdapter<String>(item,R.layout.item_special_about){
                    override fun myConvert(helper: MyViewHolder, item: String) {
                        helper.setImageByUrlCircular(R.id.ivContent,item)
                    }
                }
                mRecyclerView.adapter = mThisAdapter
            }
        }
        mAllAdapter.setOnItemClickListener {
                adapter, view, position ->
            context.startActivity(Intent(context, SuperPlayerActivity::class.java))
        }
    }

    /**
     * 加载轮播图
     */
    fun loadBanner(banner:Banner){
        val bannerMo = mutableListOf<BannerMo>()
        bannerMo.add(BannerMo("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843191708&di=097c8304a7d6eaf9c4e082352211045e&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-12-18%2F5a372590323d6.jpg",""))
        bannerMo.add(BannerMo("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843191809&di=cb98710e24e6000763ad94aad92ecd0f&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fc%2F5488fd5156ea2.jpg",""))
        bannerMo.add(BannerMo("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843191809&di=728d9f3231f6651756e2aaada68ca4e5&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fe%2F59a3cd353ad9e.jpg",""))
        BannerUtil.startBanner(bannerMo,banner, OnBannerListener {
                position ->
        })
    }

    /**
     * 加载最新影片
     */
    fun loadNews(){
        mNewsData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843734667&di=d01bf868f661ec1f65114fa71e1d5e98&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1212%2F10%2Fc1%2F16491670_1355126816487.jpg")
        mNewsData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843767168&di=c5c5c780589768e8ec9c5196794a0259&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201311%2F14%2F234553f16wj1ejnebtt514.jpg")
        mNewsData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843783648&di=24df2327365d1619d7e09878cfd8a866&imgtype=0&src=http%3A%2F%2Fc12.eoemarket.net%2Fapp0%2F615%2F615292%2Fscreen%2F3102102.jpg")
        mNewsData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843800947&di=08bb887a4326b7824a01267c90ecc95d&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F64880c5be2594f860b7f02cacc082a22fa2e1d86.jpg")
        mNewsAdapter.setNewData(mNewsData)

        RequestUtil.homeNews(object :BaseObserver<BaseListMo<Any>>(){
            override fun onSuccess(result: BaseListMo<Any>?) {

            }

            override fun onFailure(e: Throwable?, message: String?) {
            }
        })
    }


    /**
     * 加载 猜你喜欢 /换一换
     */
    fun loadLikes(){
        mLikesData.clear()
        mLikesData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843191809&di=c03560de18eb3f6bea35229e276c89ef&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fd%2F569452b47bfe8.jpg")
        mLikesData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843191807&di=36399c09dbc65344d5e5db81b1b7da98&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fe%2F5684f83c6a643.jpg")
        mLikesData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843518839&di=1ca88d49d0a1067519e29b7c5f185126&imgtype=0&src=http%3A%2F%2Fp0.ssl.qhimgs4.com%2Ft016d3330275be1ca31.jpg%3Fsize%3D1080x713")
        mLikesData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581843608681&di=4b2b99ffcaa480c1d3e3e475de86f5ba&imgtype=0&src=http%3A%2F%2Fimg008.hc360.cn%2Fm1%2FM06%2FC7%2FAD%2FwKhQcFQp2A-EJEIeAAAAADKgo5A811.jpg")
        mLikesAdapter.replaceData(mLikesData)
        RequestUtil.guessLike(object :BaseObserver<BaseListMo<Any>>(){

            override fun onSuccess(result: BaseListMo<Any>?) {
            }

            override fun onFailure(e: Throwable?, message: String?) {
            }

        })
    }

    var allLive = MutableLiveData<Boolean>()
    /**
     * 加载影片列表
     */
    fun loadMovie(){
        val mFooter = LayoutInflater.from(mContent).inflate(R.layout.list_footer_layout , null)
        mAllAdapter.addFooterView(mFooter)

        RequestUtil.homeMovies(object :BaseObserver<BaseListMo<Any>>(){
            override fun onSuccess(result: BaseListMo<Any>?) {
            }

            override fun onFailure(e: Throwable?, message: String?) {
            }
        })

        Thread{
            for (index in 0..6){
                val list = mutableListOf<String>()
                for (j in 0..5){
                    list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582360204087&di=b5f3b583d40e128c96da0ff732422352&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F7Po3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2Fd31b0ef41bd5ad6e7af4791586cb39dbb6fd3c3b.jpg")
                }
                mAllData.add(list)
            }
            allLive.postValue(true)
        }.start()
    }

}
