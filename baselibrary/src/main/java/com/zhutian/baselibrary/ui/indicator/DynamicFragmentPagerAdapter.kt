package com.zhutian.baselibrary.ui.indicator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.zhutian.baselibrary.model.TypeMo

/**
 * @author KCrason
 * @date 2018/1/21
 */
class DynamicFragmentPagerAdapter(fm: FragmentManager, private var mFragments: List<Fragment>?,private var mTypeMod:List<TypeMo>) : FragmentPagerAdapter(fm) {

    fun update(fragments: List<Fragment>) {
        this.mFragments = fragments
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getItem(position: Int): Fragment {
        return mFragments!![position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position < mTypeMod.size) {
            mTypeMod[position].title
        } else "舞蹈"
    }

    override fun getCount(): Int {
        return if (mFragments == null) 0 else mFragments!!.size
    }
}
