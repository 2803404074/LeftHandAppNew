package com.zhutian.baselibrary.ui.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.zhutian.baselibrary.R
import com.zhutian.baselibrary.util.GlideImageLoader

class ViewPagerAdapter(private val mList: List<String>, private val mContext: Context) :
    PagerAdapter() {
    private var defaultImg = R.drawable.img_load//默认图片
    private var mRoundCorners = -1
    private var mImagesListener: OnClickImagesListener? = null

    /**
     * 默认
     * @param defaultImg
     */
    fun setDefaultImg(defaultImg: Int) {
        this.defaultImg = defaultImg
    }

    /**
     * 设置圆角
     * @param mRoundCorners
     */
    fun setmRoundCorners(mRoundCorners: Int) {
        this.mRoundCorners = mRoundCorners
    }

    /**
     * 点击回调
     */
    interface OnClickImagesListener {
        fun onImagesClick(position: Int)
    }

    fun setOnClickImagesListener(listener: OnClickImagesListener) {
        mImagesListener = listener

    }

    override fun getCount(): Int {
        return 500000
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.banner_img_layout, container, false)
        val imageView = view.findViewById<View>(R.id.img) as ImageView

        val index = position % mList.size
        loadImage(mList[index], imageView)
        //OnClick
        imageView.setOnClickListener { mImagesListener?.onImagesClick(index) }

        container.addView(view)
        return view
    }

    /**
     * 加载图片
     */
    private fun loadImage(url: String, imageview: ImageView) {
        GlideImageLoader().displayImage(mContext,url,imageview)

    }
}
