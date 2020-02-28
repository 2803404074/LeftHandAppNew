package com.zhutian.lefthand.main.activity

import android.webkit.WebView
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import com.just.library.AgentWeb
import com.just.library.ChromeClientCallbackManager
import com.zhutian.baselibrary.baseview.BaseActivity
import com.zhutian.lefthand.R
import com.zhutian.lefthand.databinding.ActivityHtmlBinding
import com.zhutian.lefthand.main.vm.HtmlVM


class HtmlActivity : BaseActivity<HtmlVM, ActivityHtmlBinding>(),
    ChromeClientCallbackManager.ReceivedTitleCallback {
    override fun onReceivedTitle(view: WebView?, title: String?) {

    }


    var mAgentWeb : AgentWeb?=null
    override fun createViewModel(): HtmlVM {
        return ViewModelProvider(this).get(HtmlVM::class.java)
    }

    override fun startWork() {
        val linkUrl = intent.getStringExtra("linkUrl")
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.mTop,LinearLayout.LayoutParams(-1,-1))
            .useDefaultIndicator()
            .defaultProgressBarColor()
            .setReceivedTitleCallback(this)
            .createAgentWeb()
            .ready()
            .go(linkUrl)
    }


    override fun getContenViewId(): Int {
        return R.layout.activity_html
    }





}
