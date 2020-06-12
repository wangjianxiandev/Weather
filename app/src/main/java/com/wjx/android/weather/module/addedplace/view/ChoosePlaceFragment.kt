package com.wjx.android.weather.module.addedplace.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.common.Constant
import com.wjx.android.weather.common.util.SPreference
import com.wjx.android.weather.databinding.FragmentListBinding
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.module.addedplace.viewmodel.ChoosePlaceViewModel
import com.wjx.android.weather.module.addedplace.adapter.ChoosePlaceAdapter
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.fragment_list.*

class ChoosePlaceFragment : BaseLifeCycleFragment<ChoosePlaceViewModel, FragmentListBinding>() {

    private lateinit var mAdapter: ChoosePlaceAdapter

    private lateinit var mHeaderView: View

    private var mPosition: Int by SPreference(Constant.POSITION, 0)

    override fun initDataObserver() {
        mViewModel.mPlaceData.observe(this, Observer { response ->
            response?.let {
                setPlaceList(response)
            }
        })
    }

    override fun getLayoutId() = R.layout.fragment_list


    override fun initView() {
        super.initView()
        initAdapter()
        initHeaderView()
    }

    override fun initData() {
        super.initData()
        mViewModel.queryAllPlace()
    }

    private fun initHeaderView() {
        mHeaderView = View.inflate(requireActivity(), R.layout.custom_bar, null)
        mHeaderView.apply {
            detail_title.text = "添加的城市"
            detail_start.visibility = View.VISIBLE
            detail_end.visibility = View.VISIBLE
            detail_end.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_choosePlaceFragment_to_searchPlaceFragment)
            }
            detail_start.setOnClickListener {
                Navigation.findNavController(it).navigateUp()
            }
        }
        mAdapter.addHeaderView(mHeaderView)
    }

    private fun initAdapter() {
        mAdapter = ChoosePlaceAdapter(R.layout.place_item, null)
        mRvArticle?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRvArticle.adapter = mAdapter
        mAdapter.setOnItemLongClickListener { adapter, view, position ->
            val place = mAdapter.getItem(position)
            place?.let {
                mViewModel.deletePlace(place)
                mAdapter.notifyDataSetChanged()
            }
            true
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            appViewModel.changeCurrentPlace(mAdapter.getItem(position))
            mPosition = position
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun setPlaceList(placeList: MutableList<Place>) {
        mAdapter.setNewInstance(placeList)
    }
}