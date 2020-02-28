package com.zhutian.lefthand.my.activity.vm

import android.app.Application
import androidx.fragment.app.Fragment
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.lefthand.my.fragment.MessFragment
import com.zhutian.lefthand.my.fragment.NoticeFragment
import com.zhutian.lefthand.my.fragment.vm.NoticeVm
import java.util.ArrayList

class MessVm : BaseViewModel() {

    fun createFragments(index: Int):List<Fragment>{
        val fragments = ArrayList<Fragment>()
        for (i in 0 until index) {
            if (i == 0){
                fragments.add(MessFragment.create(0))
            }else{
                fragments.add(NoticeFragment.create(0))
            }
        }
        return fragments
    }
}