package com.zhutian.lefthand.specialcolumn.activity

import androidx.lifecycle.ViewModelProvider
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.baselibrary.baseview.BaseActivityBackup
import com.zhutian.baselibrary.util.GlideImageLoader
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivitySpecialDetailsBinding
import com.zhutian.lefthand.specialcolumn.activity.vm.RecommendVm

import com.zhutian.lefthand.specialcolumn.activity.vm.SpecialDetailsVm

/**
 * 影片系列
 */
class SpecialDetailsActivity : BaseActivity<SpecialDetailsVm, ActivitySpecialDetailsBinding>() {

    override fun createViewModel(): SpecialDetailsVm {
        return ViewModelProvider(this).get(SpecialDetailsVm::class.java)
    }

    override fun startWork() {
        val url = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=av%E9%AB%98%E6%B8%85&step_word=&hs=2&pn=52&spn=0&di=38060&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2249652611%2C1473145190&os=1042377476%2C4179424046&simid=3417742688%2C116404943&adpicid=0&lpn=0&ln=311&fr=&fmq=1582859435223_R&fm=result&ic=&s=undefined&hd=&latest=&copyright=&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%3Dpixel_huitu%2C0%2C0%2C294%2C40%2Fsign%3D884a98dd9a2397ddc274904430fad7db%2Fe61190ef76c6a7ef2369091cf6faaf51f3de66b9.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B8yhj_z%26e3BvgAzdH3Fqe51%25El%25la%25Ad%25El%25lD%25lB%25Ec%25Ac%25lmweAzdH3F&gsm=35&rpstart=0&rpnum=0&islist=&querylist=&force=undefined"
        GlideImageLoader().displayImage(this,url,mBinding.ivTop)
        mViewModel.initAdapter()
        mBinding.mRecyclerView.adapter = mViewModel.mAdapter
        mViewModel.loadData()
    }

    override fun getContenViewId(): Int {
        return R.layout.activity_special_details
    }

}
