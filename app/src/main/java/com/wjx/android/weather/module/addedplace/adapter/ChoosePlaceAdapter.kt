package com.wjx.android.weather.module.addedplace.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wjx.android.weather.R
import com.wjx.android.weather.common.setAdapterAnimion
import com.wjx.android.weather.model.Place

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 17:25
 */
class ChoosePlaceAdapter(layout: Int, listData: MutableList<Place>?) :
    BaseQuickAdapter<Place, BaseViewHolder>(
        layout, listData
    ) {

    init {
        setAdapterAnimion(4)
    }

    override fun convert(holder: BaseViewHolder, item: Place) {
        holder?.let { holder ->
            item?.let {
                holder.setText(R.id.location_name, item.name)
            }
        }
    }

}