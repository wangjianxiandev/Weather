package com.wjx.android.weather.module.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.common.Constant
import com.wjx.android.weather.common.util.getSky
import com.wjx.android.weather.databinding.HomeFragmentBinding
import com.wjx.android.weather.model.Daily
import com.wjx.android.weather.model.HourlyWeather
import com.wjx.android.weather.model.RealTimeData
import com.wjx.android.weather.module.home.adapter.HomeDailyAdapter
import com.wjx.android.weather.module.home.viewmodel.HomeDetailViewModel
import kotlinx.android.synthetic.main.layout_container.*
import kotlinx.android.synthetic.main.layout_current_place_detail.*
import kotlinx.android.synthetic.main.layout_flipper_detail.*
import kotlinx.android.synthetic.main.life_index.*


class HomeDetailFragment : BaseLifeCycleFragment<HomeDetailViewModel, HomeFragmentBinding>() {

    private lateinit var mAdapterHome: HomeDailyAdapter

    private val mLng: String by lazy { arguments?.getString(Constant.LNG_KEY) ?: "" }

    private val mLat: String by lazy { arguments?.getString(Constant.LAT_KEY) ?: "" }
    var list = ArrayList<HourlyWeather>()

    companion object {
        fun newInstance(placeName: String, lng: String, lat: String): Fragment {
            val bundle = Bundle()
            bundle.putString(Constant.LNG_KEY, lng)
            bundle.putString(Constant.LAT_KEY, lat)
            bundle.putString(Constant.PLACE_NAME, placeName)
            val fragment = HomeDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.home_detail_fragment

    override fun initView() {
        super.initView()
        setHasOptionsMenu(true)
        initAdapter()
    }

    override fun initData() {
        mViewModel.loadRealtimeWeather(mLng, mLat)
        mViewModel.loadDailyWeather(mLng, mLat)
        mViewModel.loadHourlyWeather(mLng, mLat)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mRealTimeDataData.observe(this, Observer { response ->
            response?.let {
                initCurrentData(it.result.realtime)
            }
        })

        mViewModel.mDailyData.observe(this, Observer { response ->
            response?.let {
                val dailyDataList = ArrayList<Daily.DailyData>()
                for (i in 0 until it.result.daily.skycon.size) {
                    dailyDataList.add(
                        Daily.DailyData(
                            it.result.daily.skycon[i].date,
                            it.result.daily.skycon[i].value,
                            it.result.daily.temperature[i].max,
                            it.result.daily.temperature[i].min
                        )
                    )
                }
                initDailyData(dailyDataList)
                initDailyIndex(it.result.daily.life_index)
            }
        })

        mViewModel.mHourlyData.observe(this, Observer { response ->
            response?.let {
                Log.d("wjxHour2", it.toString())
                for(i in 0 until it.result.hourly.temperature.size) {
                    list.add(HourlyWeather(
                        it.result.hourly.temperature[i].value,
                        "wjx",
                        "8:00",
                        getSky(it.result.hourly.skycon[i].value).icon,
                        it.result.hourly.wind[i].direction,
                        it.result.hourly.wind[i].speed
                    ))
                }
                Log.d("WjxHour", list.toString())
                initHourlyView(list)
            }
        })
    }

    private fun initAdapter() {
        mAdapterHome = HomeDailyAdapter(R.layout.daily_item, null)
        home_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        home_recycler.adapter = mAdapterHome
    }

    @SuppressLint("ResourceType")
    private fun initCurrentData(realtime: RealTimeData.Realtime) {
        temp_text_view.text = "${realtime.temperature.toInt()} ℃"
        description_text_view.text = getSky(
            realtime.skycon
        ).info
        animation_view.setImageResource(
            getSky(
                realtime.skycon
            ).icon
        )
        humidity_text_view.text = "湿度: ${realtime.humidity.toString()}"
        wind_text_view.text = "风力: ${realtime.wind.speed.toString()}"
        visible_text_view.text = "能见度: ${realtime.visibility.toString()}"
        index_text_view.text = "空气指数: ${realtime.air_quality.aqi.chn.toInt()}"
    }



    private fun initDailyData(dailyData: MutableList<Daily.DailyData>) {
        mAdapterHome.setNewInstance(dailyData)
    }

    private fun initDailyIndex(lifeIndex: Daily.LifeIndex) {
        coldRiskText.text = lifeIndex.carWashing[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
    }

    private fun initHourlyView(list : ArrayList<HourlyWeather>) {
        //填充天气数据
        weather_view.setList(list)

        //设置线宽
        weather_view.setLineWidth(6f)
        //设置一屏幕显示几列(最少3列)
        try {
            weather_view.setColumnNumber(5)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //设置线条的颜色
        weather_view.setLineColor(0xFFFFFF)
        //点击某一列
    }
}