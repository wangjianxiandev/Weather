package com.wjx.android.weather.module.searchplace.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.common.util.KeyBoardUtil.hideKeyboard
import com.wjx.android.weather.databinding.SearchPlaceFragmentBinding
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.module.searchplace.adapter.SearchPlaceAdapter
import com.wjx.android.weather.module.searchplace.viewmodel.SearchPlaceViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.search_place_fragment.*

class SearchPlaceFragment :
    BaseLifeCycleFragment<SearchPlaceViewModel, SearchPlaceFragmentBinding>() {

    private lateinit var mAdapter: SearchPlaceAdapter

    private lateinit var mPlace: Place

    override fun getLayoutId() = R.layout.search_place_fragment

    override fun initView() {
        super.initView()
        showSuccess()
        initAdapter()
        initHeaderView()
    }

    private fun initAdapter() {
        mAdapter = SearchPlaceAdapter(R.layout.search_result_item, null)
        place_recycle.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        place_recycle.visibility = View.VISIBLE
        place_recycle.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val place = mAdapter.getItem(position)
            place?.let {
                mPlace = place
                mViewModel.loadRealtimeWeather(place.location.lng, place.location.lat)
                mViewModel.insertPlace(place)
                hideKeyboard()
            }
        }
    }

    private fun initHeaderView() {
        search_bar.apply {
            detail_title.text = "搜索城市"
            detail_start.visibility = View.VISIBLE
            detail_end.visibility = View.GONE
            detail_start.setOnClickListener {
                Navigation.findNavController(it).navigateUp()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearch()
    }

    private fun initSearch() {
        search_places_edit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                mViewModel.searchPlaces(content)
                place_recycle.visibility = View.VISIBLE
            } else {
                place_recycle.visibility = View.GONE
                mViewModel.mSearchPlacesData.value = null
                setPlaceList(arrayListOf())
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mSearchPlacesData.observe(this, Observer {
            it?.let {
                setPlaceList(it.places)
            }
        })

        mViewModel.mRealTimeData.observe(this, Observer {
            it?.let {
                mViewModel.insertChoosePlace(
                    ChoosePlaceData(
                        0, mPlace.name, it.result.realtime.temperature.toInt(),
                        it.result.realtime.skycon
                    )
                )
                Navigation.findNavController(search_place).navigateUp()
            }
        })
    }

    private fun setPlaceList(placeList: MutableList<Place>) {
        mAdapter.setNewInstance(placeList)
    }


}