package com.wjx.android.weather.common.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2


/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/19 19:02
 */
class CustomViewPager : ViewPager {
    constructor(mContext: Context) : super(mContext)

    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs)

    override fun canScroll(
        v: View?,
        checkV: Boolean,
        dx: Int,
        x: Int,
        y: Int
    ): Boolean {
        return if (v is HorizontalScrollView) {
            true
        } else super.canScroll(v, checkV, dx, x, y)
    }
}