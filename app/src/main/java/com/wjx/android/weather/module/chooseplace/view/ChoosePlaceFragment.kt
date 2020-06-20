package com.wjx.android.weather.module.chooseplace.view

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleFragment
import com.wjx.android.weather.common.getActivityMessageViewModel
import com.wjx.android.weather.databinding.FragmentListBinding
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.module.chooseplace.viewmodel.ChoosePlaceViewModel
import com.wjx.android.weather.module.chooseplace.adapter.ChoosePlaceAdapter
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.fragment_list.*

class ChoosePlaceFragment : BaseLifeCycleFragment<ChoosePlaceViewModel, FragmentListBinding>() {

    private lateinit var mAdapter: ChoosePlaceAdapter

    private lateinit var mHeaderView: View

    override fun getLayoutId() = R.layout.fragment_list


    override fun initView() {
        super.initView()
        initRefresh()
        initAdapter()
        initHeaderView()
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        mSrlRefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.bluebackground
            )
        )
        mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { initData() }
    }

    override fun initData() {
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
        }
        mViewModel.queryAllChoosePlace()
    }

    override fun initDataObserver() {
        mViewModel.mChoosePlaceData.observe(this, Observer { response ->
            response?.let {
                setPlaceList(response)
            }
        })
        getActivityMessageViewModel().addChoosePlace.observe(this, Observer {
            it?.let {
                mViewModel.queryAllChoosePlace()
                mAdapter.notifyDataSetChanged()
            }
        })
        showSuccess()
    }

    private fun initHeaderView() {
        mHeaderView = View.inflate(requireActivity(), R.layout.custom_bar, null)
        mHeaderView.apply {
            detail_title.text = "添加的城市"
            detail_start.visibility = View.VISIBLE
            detail_end.visibility = View.VISIBLE
            detail_end.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.searchPlaceFragment)
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
            mAdapter.getViewByPosition(position + 1, R.id.location_delete)?.visibility =
                View.VISIBLE
            mAdapter.getViewByPosition(position + 1, R.id.location_card)
                ?.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.grey_80
                    )
                )
            mAdapter.getViewByPosition(position + 1, R.id.location_delete)?.setOnClickListener {
                val place = mAdapter.getItem(position)
                place?.let {
                    MaterialDialog(requireContext()).show {
                        title(R.string.title)
                        message(R.string.delete_city)
                        cornerRadius(8.0f)
                        negativeButton(R.string.cancel) {
                            mAdapter.getViewByPosition(
                                position + 1,
                                R.id.location_delete
                            )?.visibility =
                                View.GONE
                            mAdapter.getViewByPosition(position + 1, R.id.location_card)
                                ?.setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.bluebackground
                                    )
                                )
                        }
                        positiveButton(R.string.delete) {
                            mViewModel.deletePlace(place.name)
                            mViewModel.deleteChoosePlace(place)
                            mAdapter.removeAt(position)
                        }
                    }
                }
                true
            }
            true
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun setPlaceList(addedPlaceList: MutableList<ChoosePlaceData>) {
        mAdapter.setNewInstance(addedPlaceList)
    }
}