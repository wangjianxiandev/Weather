package com.wjx.android.weather.module.home.view

import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.common.getEventViewModel
import com.wjx.android.weather.common.init
import com.wjx.android.weather.common.util.CommonUtil
import com.wjx.android.weather.databinding.HomeFragmentBinding
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.module.home.viewmodel.HomeViewModel
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*


class HomeFragment : BaseLifeCycleFragment<HomeViewModel, HomeFragmentBinding>() {
    override fun getLayoutId(): Int = R.layout.home_fragment

    private val mPlaceNameList = arrayListOf<String>()

    override fun initView() {
        super.initView()
        initToolbar()
        setHasOptionsMenu(true)
    }

    private fun initToolbar() {
        home_bar.title = ""
        (requireActivity() as AppCompatActivity).setSupportActionBar(home_bar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_city -> {
                Navigation.findNavController(home_bar)
                    .navigate(R.id.action_homeFragment_to_choosePlaceFragment)
            }
            R.id.action_more -> {
                Navigation.findNavController(home_bar).navigate(R.id.action_homeFragment_to_aboutFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
        mViewModel.queryAllPlace()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mPlaceData.observe(this, Observer { response ->
            response?.let {
                if (response.size == 0) {
                    Navigation.findNavController(home_normal_view)
                        .navigate(R.id.choosePlaceFragment)
                }
                initHomeDetailFragment(it)
                showSuccess()
            }
        })

        requireActivity().getEventViewModel().addPlace.observe(this, Observer {
            it?.let {
                mViewModel.queryAllPlace()
            }
        })

        requireActivity().getEventViewModel().changeCurrentPlace.observe(this, Observer {
            it?.let {
                home_viewpager.setCurrentItem(appViewModel.mCurrentPlace.value!!, true)
            }
        })
    }

    private fun initHomeDetailFragment(dataList: MutableList<Place>) {
        val tabs = arrayListOf<String>()
        val fragments = arrayListOf<Fragment>()
        for (data in dataList) {
            tabs.add(data.name)
            fragments.add(
                HomeDetailFragment.newInstance(
                    data.name,
                    data.location.lng,
                    data.location.lat,
                    data.primaryKey
                )
            )
        }
        mPlaceNameList.clear()
        mPlaceNameList.addAll(tabs)
        if (mPlaceNameList.isNotEmpty()) {
            home_bar.home_title.text = mPlaceNameList[0]
        }
        home_viewpager.offscreenPageLimit = mPlaceNameList.size
        home_viewpager.init(childFragmentManager, fragments)
        //设置监听
        home_viewpager.addOnPageChangeListener(TitlePageChangeListener())

        indicator_view
            .setSliderColor(
                CommonUtil.getColor(requireContext(), R.color.grey_10),
                CommonUtil.getColor(requireContext(), R.color.material_blue)
            )
            .setSliderWidth(resources.getDimension(R.dimen.safe_padding))
            .setSliderHeight(resources.getDimension(R.dimen.safe_padding))
            .setSlideMode(IndicatorSlideMode.WORM)
            .setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setupWithViewPager(home_viewpager)
        indicator_view.visibility = View.INVISIBLE
    }


    private inner class TitlePageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            indicator_view.visibility = View.INVISIBLE
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            changeTitle(position)
            indicator_view.visibility = View.VISIBLE
        }
    }

    private fun changeTitle(position: Int) {
        home_bar.home_title.text = mPlaceNameList[position]
    }
}