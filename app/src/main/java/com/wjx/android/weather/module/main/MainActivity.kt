package com.wjx.android.weather.module.main

import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleActivity
import kotlinx.android.synthetic.main.home_fragment.*

class MainActivity : BaseLifeCycleActivity<MainViewModel>() {
    override fun getLayoutId() = R.layout.activity_main

    override fun initView() {
        super.initView()
    }
}