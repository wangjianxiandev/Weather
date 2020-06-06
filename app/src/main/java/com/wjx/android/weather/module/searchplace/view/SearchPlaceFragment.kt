package com.wjx.android.weather.module.searchplace.view

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.databinding.SearchPlaceFragmentBinding
import com.wjx.android.weather.module.searchplace.adapter.SearchPlaceAdapter
import com.wjx.android.weather.module.searchplace.viewmodel.SearchPlaceViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.search_place_fragment.*

class SearchPlaceFragment :
    BaseLifeCycleFragment<SearchPlaceViewModel, SearchPlaceFragmentBinding>() {

    private lateinit var mAdapter: SearchPlaceAdapter

    override fun getLayoutId() = R.layout.search_place_fragment

    private val mPlaceList = arrayListOf(
        "a", "c", "b", "f"
    )

    override fun initView() {
        super.initView()
        initAdapter()
        initHeaderView()
    }

    override fun initData() {
        super.initData()
        setPlaceList(mPlaceList)
    }

    private fun initAdapter() {
        mAdapter = SearchPlaceAdapter(R.layout.search_result_item, null)
        place_recycle.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        place_recycle.visibility = View.VISIBLE
        place_recycle.adapter = mAdapter
    }

    private fun initHeaderView() {
        search_bar.apply {
            detail_title.text = "搜索城市"
            detail_start.visibility = View.VISIBLE
            detail_end.visibility = View.GONE
            detail_end.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.searchPlaceFragment)
            }
            detail_start.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.choosePlaceFragment)
            }
        }
    }

    private fun setPlaceList(placeList: List<String>) {
        if (placeList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }
        mAdapter.addData(placeList)
        mAdapter.loadMoreComplete()
    }
}