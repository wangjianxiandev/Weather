package com.wjx.android.weather.module.chooseplace.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wjx.android.weather.R
import com.wjx.android.weather.common.setAdapterAnimation
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
        setAdapterAnimation(2)
    }

    override fun convert(holder: BaseViewHolder, item: Place) {
        holder?.let { holder ->
            item?.let {
                holder.setText(R.id.location_name, item.name)
//                holder.setText(
//                    R.id.location_temperature,
//                    "${item.realTimeData.result.realtime.temperature.toInt()} ℃"
//                )
//                holder.setImageResource(
//                    R.id.location_img,
//                    getSky(item.realTimeData.result.realtime.skycon).icon
//                )
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        setAdapterAnimation(2)
    }
}