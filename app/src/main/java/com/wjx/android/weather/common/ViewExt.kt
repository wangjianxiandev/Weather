package com.wjx.android.weather.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/9 21:50
 */
//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int) {
    //等于0，关闭列表动画 否则开启
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}

fun ViewPager2.init(
    fragment: Fragment,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}