package com.wjx.android.weather.module.home.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.common.Constant
import com.wjx.android.weather.common.util.CommonUtil
import com.wjx.android.weather.common.util.SPreference
import com.wjx.android.weather.common.util.getSky
import com.wjx.android.weather.databinding.HomeFragmentBinding
import com.wjx.android.weather.model.DailyResponse
import com.wjx.android.weather.model.RealTimeResponse
import com.wjx.android.weather.module.home.adapter.DailyAdapter
import com.wjx.android.weather.module.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.layout_container.*
import kotlinx.android.synthetic.main.layout_current_place_detail.*
import kotlinx.android.synthetic.main.layout_flipper_detail.*
import kotlinx.android.synthetic.main.life_index.*
import me.zhouzhuo.zzweatherview.AirLevel
import me.zhouzhuo.zzweatherview.WeatherModel
import me.zhouzhuo.zzweatherview.ZzWeatherView


class HomeFragment : BaseLifeCycleFragment<HomeViewModel, HomeFragmentBinding>() {

    private lateinit var mAdapter: DailyAdapter

    private var mLng: String by SPreference(Constant.LNG_KEY, "")

    private var mLat: String by SPreference(Constant.LAT_KEY, "")

    private var mPlaceName: String by SPreference(Constant.PLACE_NAME, "")

    override fun initView() {
        super.initView()
        setHasOptionsMenu(true)
        initAdapter()
        initToolbar(mPlaceName)
        initHourlyView()
    }

    override fun initData() {
        super.initData()
        mViewModel.loadRealtimeWeather(mLng, mLat)
        mViewModel.loadDailyWeather(mLng, mLat)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        appViewModel.currentPlace.observe(this, Observer { it ->
            it?.let {
                mPlaceName = it.name
                mLng = it.location.lng
                mLat = it.location.lat
                initToolbar(it.name)
                mViewModel.loadRealtimeWeather(mLng, mLat)
                mViewModel.loadDailyWeather(mLng, mLat)
            }
        })

        mViewModel.mRealTimeData.observe(this, Observer { response ->
            response?.let {
                initCurrentData(it.result.realtime)
            }
        })

        mViewModel.mDailyData.observe(this, Observer { response ->
            response?.let {
                val dailyDataList = ArrayList<DailyResponse.DailyData>()
                for (i in 0 until it.result.daily.skycon.size) {
                    dailyDataList.add(
                        DailyResponse.DailyData(
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
    }

    override fun getLayoutId() = R.layout.home_fragment

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_city -> {
                Navigation.findNavController(home_container)
                    .navigate(R.id.action_homeFragment_to_choosePlaceFragment)
            }
            R.id.action_more -> {
                CommonUtil.showToast(requireContext(), getString(R.string.more))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar(title: String?) {
        home_bar.home_title.text = title
        home_bar.setTitle("")
        (requireActivity() as AppCompatActivity).setSupportActionBar(home_bar)
    }

    private fun initAdapter() {
        mAdapter = DailyAdapter(R.layout.daily_item, null)
        home_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        home_recycler.adapter = mAdapter
    }

    @SuppressLint("ResourceType")
    private fun initCurrentData(realtime: RealTimeResponse.Realtime) {
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

    private fun initHourlyView() {
        //填充天气数据

        //填充天气数据
        weather_view.setList(generateData())

        //画折线

        //画折线
        weather_view.setLineType(ZzWeatherView.LINE_TYPE_DISCOUNT)
        //画曲线(已修复不圆滑问题)
//        weather_view.setLineType(ZzWeatherView.LINE_TYPE_CURVE);

        //设置线宽
        //画曲线(已修复不圆滑问题)
//        weather_view.setLineType(ZzWeatherView.LINE_TYPE_CURVE);

        //设置线宽
        weather_view.setLineWidth(6f)

        //设置一屏幕显示几列(最少3列)

        //设置一屏幕显示几列(最少3列)
        try {
            weather_view.setColumnNumber(5)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //设置白天和晚上线条的颜色

        //设置白天和晚上线条的颜色
        weather_view.setDayAndNightLineColor(Color.BLUE, Color.RED)

        //点击某一列

        //点击某一列
        weather_view.setOnWeatherItemClickListener(ZzWeatherView.OnWeatherItemClickListener { itemView, position, weatherModel ->
            Toast.makeText(requireContext(), position.toString() + "", Toast.LENGTH_SHORT)
                .show()
        })

        weather_view.setLineType(ZzWeatherView.LINE_TYPE_CURVE)
    }

    private fun initDailyData(dailyData: MutableList<DailyResponse.DailyData>) {
        mAdapter.setNewInstance(dailyData)
    }

    private fun initDailyIndex(lifeIndex: DailyResponse.LifeIndex) {
        coldRiskText.text = lifeIndex.carWashing[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
    }

    private fun generateData(): List<WeatherModel>? {
        val list: MutableList<WeatherModel> = java.util.ArrayList()
        val model = WeatherModel()
        model.date = "12/07"
        model.week = "昨天"
        model.dayWeather = "大雪"
        model.dayTemp = 11
        model.nightTemp = 5
        model.nightWeather = "晴"
        model.windOrientation = "西南风"
        model.windLevel = "3级"
        model.airLevel = AirLevel.EXCELLENT
        list.add(model)
        val model1 = WeatherModel()
        model1.date = "12/08"
        model1.week = "今天"
        model1.dayWeather = "晴"
        model1.dayTemp = 8
        model1.nightTemp = 5
        model1.nightWeather = "晴"
        model1.windOrientation = "西南风"
        model1.windLevel = "3级"
        model1.airLevel = AirLevel.HIGH
        list.add(model1)
        val model2 = WeatherModel()
        model2.date = "12/09"
        model2.week = "明天"
        model2.dayWeather = "晴"
        model2.dayTemp = 9
        model2.nightTemp = 8
        model2.nightWeather = "晴"
        model2.windOrientation = "东南风"
        model2.windLevel = "3级"
        model2.airLevel = AirLevel.POISONOUS
        list.add(model2)
        val model3 = WeatherModel()
        model3.date = "12/10"
        model3.week = "周六"
        model3.dayWeather = "晴"
        model3.dayTemp = 12
        model3.nightTemp = 9
        model3.dayPic = R.drawable.w0
        model3.nightPic = R.drawable.w1
        model3.nightWeather = "晴"
        model3.windOrientation = "东北风"
        model3.windLevel = "3级"
        model3.airLevel = AirLevel.GOOD
        list.add(model3)
        val model4 = WeatherModel()
        model4.date = "12/11"
        model4.week = "周日"
        model4.dayWeather = "多云"
        model4.dayTemp = 13
        model4.nightTemp = 7
        model4.dayPic = R.drawable.w2
        model4.nightPic = R.drawable.w4
        model4.nightWeather = "多云"
        model4.windOrientation = "东北风"
        model4.windLevel = "3级"
        model4.airLevel = AirLevel.LIGHT
        list.add(model4)
        val model5 = WeatherModel()
        model5.date = "12/12"
        model5.week = "周一"
        model5.dayWeather = "多云"
        model5.dayTemp = 17
        model5.nightTemp = 8
        model5.dayPic = R.drawable.w3
        model5.nightPic = R.drawable.w4
        model5.nightWeather = "多云"
        model5.windOrientation = "西南风"
        model5.windLevel = "3级"
        model5.airLevel = AirLevel.LIGHT
        list.add(model5)
        val model6 = WeatherModel()
        model6.date = "12/13"
        model6.week = "周二"
        model6.dayWeather = "晴"
        model6.dayTemp = 13
        model6.nightTemp = 6
        model6.dayPic = R.drawable.w5
        model6.nightPic = R.drawable.w6
        model6.nightWeather = "晴"
        model6.windOrientation = "西南风"
        model6.windLevel = "3级"
        model6.airLevel = AirLevel.POISONOUS
        list.add(model6)
        val model7 = WeatherModel()
        model7.date = "12/14"
        model7.week = "周三"
        model7.dayWeather = "晴"
        model7.dayTemp = 19
        model7.nightTemp = 10
        model7.dayPic = R.drawable.w5
        model7.nightPic = R.drawable.w7
        model7.nightWeather = "晴"
        model7.windOrientation = "西南风"
        model7.windLevel = "3级"
        model7.airLevel = AirLevel.POISONOUS
        list.add(model7)
        val model8 = WeatherModel()
        model8.date = "12/15"
        model8.week = "周四"
        model8.dayWeather = "晴"
        model8.dayTemp = 22
        model8.nightTemp = 4
        model8.dayPic = R.drawable.w5
        model8.nightPic = R.drawable.w8
        model8.nightWeather = "晴"
        model8.windOrientation = "西南风"
        model8.windLevel = "3级"
        model8.airLevel = AirLevel.POISONOUS
        list.add(model8)
        return list
    }
}