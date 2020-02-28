package com.zhutian.lefthand.my.activity.vm

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.zhutian.baselibrary.baseview.BaseViewModel
import com.zhutian.lefthand.my.fragment.HistoryFragment
import java.util.*

class HistoryVm : BaseViewModel() {

    val fragments = ArrayList<HistoryFragment>()
    fun createFragments(index: Int):List<HistoryFragment>{

        for (i in 0 until index) {
            fragments.add(HistoryFragment.create(i))
        }
        return fragments
    }
}