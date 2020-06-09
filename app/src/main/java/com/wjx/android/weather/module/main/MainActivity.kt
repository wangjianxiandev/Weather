package com.wjx.android.weather.module.main

import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleActivity
import com.wjx.android.weather.databinding.ActivityMainBinding

class MainActivity : BaseLifeCycleActivity<AppViewModel, ActivityMainBinding>() {
    override fun getLayoutId() = R.layout.activity_main
}