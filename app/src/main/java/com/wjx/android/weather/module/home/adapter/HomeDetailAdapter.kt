package com.wjx.android.weather.module.home.adapter


import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/12 8:17
 */
class HomeDetailAdapter (
    fragmentManager: FragmentManager,
    val tabs: List<String>,
    val fragments: List<Fragment>
) : FragmentStatePagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = tabs.size

    override fun getPageTitle(position: Int): CharSequence? {
        return tabs[position]
    }
}