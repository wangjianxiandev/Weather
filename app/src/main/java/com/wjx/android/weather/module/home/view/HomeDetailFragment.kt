package com.wjx.android.weather.module.home.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.common.Constant
import com.wjx.android.weather.common.custom.HourlyWeatherItem
import com.wjx.android.weather.common.custom.WeatherView
import com.wjx.android.weather.common.util.getAirLevel
import com.wjx.android.weather.common.util.getSky
import com.wjx.android.weather.common.util.getWindOri
import com.wjx.android.weather.common.util.getWindSpeed
import com.wjx.android.weather.databinding.HomeFragmentBinding
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Daily
import com.wjx.android.weather.model.HourlyWeather
import com.wjx.android.weather.model.RealTime
import com.wjx.android.weather.module.home.adapter.HomeDailyAdapter
import com.wjx.android.weather.module.home.viewmodel.HomeDetailViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.home_detail_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.layout_container.*
import kotlinx.android.synthetic.main.layout_current_place_detail.*
import kotlinx.android.synthetic.main.layout_flipper_detail.*
import kotlinx.android.synthetic.main.life_index.*


class HomeDetailFragment : BaseLifeCycleFragment<HomeDetailViewModel, HomeFragmentBinding>() {

    private lateinit var mAdapterHome: HomeDailyAdapter

    private val mLng: String by lazy { arguments?.getString(Constant.LNG_KEY) ?: "" }

    private val mLat: String by lazy { arguments?.getString(Constant.LAT_KEY) ?: "" }

    private val mPlaceName: String by lazy { arguments?.getString(Constant.PLACE_NAME) ?: "" }

    private val mPrimaryKey: Int by lazy { arguments?.getInt(Constant.PLACE_PRIMARY_KEY) ?: 0 }
    var list = ArrayList<HourlyWeather>()

    companion object {
        fun newInstance(placeName: String, lng: String, lat: String, placeKey: Int): Fragment {
            val bundle = Bundle()
            bundle.putString(Constant.LNG_KEY, lng)
            bundle.putString(Constant.LAT_KEY, lat)
            bundle.putString(Constant.PLACE_NAME, placeName)
            bundle.putInt(Constant.PLACE_PRIMARY_KEY, placeKey)
            val fragment = HomeDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.home_detail_fragment

    override fun initView() {
        super.initView()
        initRefresh()
        setHasOptionsMenu(true)
        initAdapter()
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        home_container.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.material_blue
            )
        )
        home_container.setColorSchemeColors(Color.WHITE)
        home_container.setOnRefreshListener { initData() }
    }

    override fun initData() {
        if (home_container.isRefreshing) {
            home_container.isRefreshing = false
            list.clear()
        }
        showSuccess()
        mViewModel.loadRealtimeWeather(mLng, mLat)
        mViewModel.loadDailyWeather(mLng, mLat)
        mViewModel.loadHourlyWeather(mLng, mLat)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mRealTimeData.observe(this, Observer { response ->
            response?.let {
                initCurrentData(it.result.realtime)
                Log.d("WJXKey", mPrimaryKey.toString())
                mViewModel.updateChoosePlace(
                    it.result.realtime.temperature.toInt(),
                    it.result.realtime.skycon,
                    mPlaceName
                )
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
                for (i in 0 until it.result.hourly.temperature.size) {
                    list.add(
                        HourlyWeather(
                            it.result.hourly.temperature[i].value.toInt(),
                            it.result.hourly.skycon[i],
                            getSky(it.result.hourly.skycon[i].value).info,
                            it.result.hourly.temperature[i].datetime.substring(11, 16),
                            getSky(it.result.hourly.skycon[i].value).icon,
                            getWindOri(it.result.hourly.wind[i].direction).ori,
                            getWindSpeed(it.result.hourly.wind[i].speed).speed,
                            getAirLevel(it.result.hourly.air_quality.aqi[i].value.chn).airLevel
                        )
                    )
                }
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
    private fun initCurrentData(realtime: RealTime.Realtime) {
        temp_text_view.text = "${realtime.temperature.toInt()} ℃"
        description_text_view.text = getSky(
            realtime.skycon
        ).info
        animation_view.setImageResource(
            getSky(
                realtime.skycon
            ).icon
        )
        humidity_text_view.text = "湿度: ${(realtime.humidity * 100).toInt()} %"
        wind_text_view.text = "风力: ${getWindSpeed(realtime.wind.speed).speed}"
        visible_text_view.text = "能见度: ${realtime.visibility} m"
        index_text_view.text = "空气质量: ${getAirLevel(realtime.air_quality.aqi.chn).airLevel}"
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

    private fun initHourlyView(list: ArrayList<HourlyWeather>) {
        weather_view.setList(list)
        weather_view.setLineWidth(6f)
        try {
            weather_view.setColumnNumber(5)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        weather_view.setOnWeatherItemClickListener(object : WeatherView.OnWeatherItemClickListener {
            override fun onItemClick(
                itemView: HourlyWeatherItem?,
                position: Int,
                weatherModel: HourlyWeather?
            ) {
                Toast.makeText(requireContext(), position.toString() + "", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        weather_view.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                weather_view.requestDisallowInterceptTouchEvent(true)
                return false
            }
        })

    }
}