package com.zhutian.lefthand.home.vm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.ViewPager
import com.tencent.liteav.demo.player.superplayer.SuperPlayerActivity
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.baselibrary.ui.widget.ScrollOffsetTransformer
import com.zhutian.baselibrary.ui.widget.ViewPagerAdapter
import com.zhutian.baselibrary.util.GlideImageLoader
import com.zhutian.baselibrary.util.MyBaseAdapter
import com.zhutian.baselibrary.util.MyViewHolder
import com.zhutian.lefthand.R


class TypeViewModel : BaseViewModel(){

    lateinit var mAdapter: MyBaseAdapter<String>

    var mData = mutableListOf<String>()

    var isLoad = MutableLiveData<Boolean>()

    fun initMoreAdapter(context: Context,layoutInflater: LayoutInflater) {

        mAdapter = object : MyBaseAdapter<String>(mData, R.layout.item_special_about) {
            override fun myConvert(helper: MyViewHolder, item: String) {
                helper.setImageByUrlCircular(R.id.ivContent,item)
            }
        }
        val layout = layoutInflater.inflate(R.layout.banner3d,null)
        val mBanner = layout.findViewById<ViewPager>(R.id.mBanner)
        init3d(mBanner)

        layout.setOnClickListener {
            context.startActivity(Intent(context, SuperPlayerActivity::class.java))
        }
        val imgView = layout.findViewById<ImageView>(R.id.ivContent)
        GlideImageLoader().displayImage(context,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581600491199&di=8c3f078da6bca421720b40c43a2410f6&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2Fd%2F35%2F1c19515177.jpg",imgView)
        mAdapter.addHeaderView(layout)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            context.startActivity(Intent(context,SuperPlayerActivity::class.java))
        }
    }

    private fun init3d(mBanner: ViewPager) {
        val mImageList = mutableListOf<String>()
        val mTitle = mutableListOf<String>()
        mImageList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3316485073,630900464&fm=26&gp=0.jpg")
        mImageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582900952573&di=cb93011f9a5a069a9728b5cfe89c6b73&imgtype=0&src=http%3A%2F%2Fpic3.16pic.com%2F00%2F53%2F22%2F16pic_5322554_b.jpg")
        mImageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582900952571&di=cc196e94965216f7e7253a14f83973ba&imgtype=0&src=http%3A%2F%2Fpic30.nipic.com%2F20130615%2F11558895_154634588348_2.jpg")
        mBanner.adapter = ViewPagerAdapter(mImageList,mContent)
        mBanner.setPageTransformer(true,ScrollOffsetTransformer())
        mBanner.offscreenPageLimit = 2//设置预加载的数量，这里设置了2,会预加载中心item左边两个Item和右边两个Item
        mBanner.currentItem = 1
        mBanner.pageMargin = -20//设置两个Page之间的距离

    }

    fun loadData() {
        for (index in 1..20) {
            mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581598377245&di=e4e203b63f7b3e49138eb34312fa2dd8&imgtype=0&src=http%3A%2F%2Fwenhua.youth.cn%2Fxwjj%2Fxw%2F201307%2FW020130709539807156520.jpg")
        }
        isLoad.value = true
        mAdapter.setNewData(mData)
    }

}
