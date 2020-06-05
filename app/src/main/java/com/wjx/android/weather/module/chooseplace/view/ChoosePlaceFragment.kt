package com.wjx.android.weather.module.chooseplace.view

import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.databinding.FragmentListBinding
import com.wjx.android.weather.module.chooseplace.viewmodel.ChoosePlaceViewModel
import com.wjx.android.weather.module.chooseplace.adapter.ChoosePlaceAdapter
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.fragment_list.*

class ChoosePlaceFragment : BaseLifeCycleFragment<ChoosePlaceViewModel, FragmentListBinding>() {

    private lateinit var mAdapter: ChoosePlaceAdapter

    private lateinit var mHeaderView: View

    private val mPlaceList = arrayListOf(
        "a", "c", "b", "f"
    )

    override fun initDataObserver() {
    }

    override fun getLayoutId() = R.layout.fragment_list

    override fun initData() {
        super.initData()
        setPlaceList(mPlaceList)
    }

    override fun initView() {
        super.initView()
        showSuccess()
        initAdapter()
        initHeaderView()
        initFab()
    }

    private fun initHeaderView() {
        mHeaderView = View.inflate(requireActivity(), R.layout.custom_bar, null)
        mHeaderView.apply {
            detail_title.text = "添加的城市"
            detail_start.visibility = View.VISIBLE
            detail_end.visibility = View.VISIBLE
            detail_end.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.homeFragment)
            }
            detail_start.setOnClickListener { }
        }
        mAdapter.addHeaderView(mHeaderView)
    }

    private fun initAdapter() {
        mAdapter = ChoosePlaceAdapter(R.layout.place_item, mPlaceList)
        mRvArticle?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRvArticle.adapter = mAdapter
        mAdapter.setEnableLoadMore(true)
    }

    private fun initFab() {
        var fad_add = view?.findViewById<View>(R.id.fab_add)
        fad_add?.visibility = View.VISIBLE
    }

    private fun setPlaceList(placeList: List<String>) {
        if (placeList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(placeList)
            mAdapter.loadMoreComplete()
            return
        }

        mAdapter.addData(placeList)
        mAdapter.loadMoreComplete()
    }
}