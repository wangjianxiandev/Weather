package com.wjx.android.weather.module.searchplace

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wjx.android.weather.R

class SearchPlaceFragment : Fragment() {

    companion object {
        fun newInstance() = SearchPlaceFragment()
    }

    private lateinit var viewModel: SearchPlaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_place_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchPlaceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}