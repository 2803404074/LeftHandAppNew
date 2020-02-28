package com.zhutian.lefthand.comment.activity.vm

import androidx.fragment.app.Fragment
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.lefthand.home.fragment.TypeFragment
import java.util.*

class HighTypeVm : BaseViewModel() {
    fun createFragments(index: Int):List<Fragment>{
        val fragments = ArrayList<Fragment>()
        for (i in 0 until index) {
            fragments.add(TypeFragment.create(i))
        }
        return fragments
    }
}