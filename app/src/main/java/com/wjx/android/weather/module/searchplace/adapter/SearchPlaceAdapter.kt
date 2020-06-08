package com.wjx.android.weather.module.searchplace.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.weather.R
import com.wjx.android.weather.module.chooseplace.model.Place
import kotlinx.android.synthetic.main.search_result_item.view.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/6 10:48
 */
class SearchPlaceAdapter(layout: Int, listData: MutableList<Place>?) :
    BaseQuickAdapter<Place, BaseViewHolder>(layout, listData) {
    override fun convert(helper: BaseViewHolder?, item: Place?) {
        helper?.let { holder ->
            item?.let {
                holder.setText(R.id.placeName,item.name)
                holder.setText(R.id.placeAddress, item.address)
            }
        }
    }

}