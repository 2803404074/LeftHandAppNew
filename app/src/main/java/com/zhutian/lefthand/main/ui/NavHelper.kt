package com.zhutian.lefthand.main.ui

import android.content.Context
import android.util.SparseArray

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class NavHelper<T>(
    private val mContext: Context,
    private val containerId: Int,
    private val fragmentManager: FragmentManager,
    private val mListener: OnTabChangeListener<T>?
) {
    private val tabs = SparseArray<Tab<T>>()

    /**
     * 获取当前Tab
     *
     * @return
     */
    var currentTab: Tab<T>? = null
        private set

    /**
     * 添加tab
     *
     * @param menuId
     * @param tab
     * @return
     */
    fun add(menuId: Int, tab: Tab<T>): NavHelper<T> {
        tabs.put(menuId, tab)
        return this
    }

    /**
     * 执行点击菜单的操作
     *
     * @param menuId
     * @return
     */
    fun performClickMenu(menuId: Int): Boolean {
        val tab = tabs.get(menuId)
        if (tab != null) {
            doSelect(tab)
            return true
        }

        return false
    }

    /**
     * 进行tab的选择操作
     * @param tab
     */
    private fun doSelect(tab: Tab<T>) {
        var oldTab: Tab<T>? = null
        if (currentTab != null) {
            oldTab = currentTab
            if (oldTab === tab) {
                //如果当前tab点击的tab，不做任何操作或者刷新
                notifyReselect(tab)
                return
            }
        }
        currentTab = tab
        doTabChanged(currentTab, oldTab)
    }

    /**
     * Tab切换方法
     * @param newTab
     * @param oldTab
     */
    private fun doTabChanged(newTab: Tab<T>?, oldTab: Tab<T>?) {
        val ft = fragmentManager.beginTransaction()

        if (oldTab != null) {
            if (oldTab.fragment != null) {
                //从界面移除，但在Fragment的缓存中
                ft.detach(oldTab.fragment!!)
            }
        }

        if (newTab != null) {
            if (newTab.fragment == null) {
                //首次新建并缓存
                val fragment = Fragment.instantiate(mContext, newTab.clx.name, null)
                newTab.fragment = fragment
                ft.add(containerId, fragment, newTab.clx.name)
            } else {
                ft.attach(newTab.fragment!!)
            }
        }
        ft.commit()
        notifySelect(newTab, oldTab)
    }

    /**
     * 选择通知回调
     * @param newTab
     * @param oldTab
     */
    private fun notifySelect(newTab: Tab<T>?, oldTab: Tab<T>?) {
        mListener?.onTabChange(newTab, oldTab)

    }

    private fun notifyReselect(tab: Tab<T>) {
        //TODO 刷新
    }

    class Tab<T>(internal var clx: Class<*>, var extra: T) {
        //内部缓存对应的Fragment,internal 修饰统一模块下可见
        internal var fragment: Fragment? = null
    }

    /**
     * 事件处理回调接口
     *
     * @param <T>
    </T> */
    interface OnTabChangeListener<T> {
        fun onTabChange(newTab: Tab<T>?, oldTab: Tab<T>?)
    }
}
