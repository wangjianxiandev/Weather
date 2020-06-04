package com.wjx.android.weather.module.home

import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment

class HomeFragment : BaseLifeCycleFragment<HomeViewModel>() {

    override fun initDataObserver() {
    }

    override fun getLayoutId() = R.layout.home_fragment
}