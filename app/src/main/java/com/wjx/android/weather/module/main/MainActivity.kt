package com.wjx.android.weather.module.main

import android.os.Bundle
import androidx.navigation.Navigation
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleActivity
import com.wjx.android.weather.common.checkUpdate
import com.wjx.android.weather.databinding.ActivityMainBinding

class MainActivity : BaseLifeCycleActivity<AppViewModel, ActivityMainBinding>() {
    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUpdate(this, false)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_main).navigateUp()
    }
}