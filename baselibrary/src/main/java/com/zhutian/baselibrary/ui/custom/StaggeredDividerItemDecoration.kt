package com.zhutian.baselibrary.ui.custom

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredDividerItemDecoration
/**
 * @param interval item的间距
 * @param spanCount 列数
 */
    (private val context: Context, private val interval: Float, private val spanCount: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        // 获取item在span中的下标
        val spanIndex = params.spanIndex
        val interval = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.interval, context.resources.displayMetrics
        ).toInt()
        // 中间间隔

        /**
         * 这个判断适用于瀑布流只有两列的情况，如果有多列，那么再增加spanIndex % spanCount == 的判断并做处理就好了
         * 此处的left和right都为interval / 2的原因是为了让左边item和右边item同宽
         */
        if (spanIndex % spanCount == 0) {
            outRect.right = interval / 2
        } else {
            outRect.left = interval / 2
        }

        // 下方间隔
        outRect.bottom = interval
    }
}
