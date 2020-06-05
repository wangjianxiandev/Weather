package com.wjx.android.weather.module.home

import androidx.appcompat.app.AppCompatActivity
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.layout_home_toolbar.*

class HomeFragment : BaseLifeCycleFragment<HomeViewModel, HomeFragmentBinding>() {
    override fun initView() {
        super.initView()
        (requireActivity() as AppCompatActivity).setSupportActionBar(home_toolbar)
    }


    override fun getLayoutId() = R.layout.home_fragment

}