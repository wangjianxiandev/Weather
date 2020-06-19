package com.wjx.android.weather.module.chooseplace.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wjx.android.weather.R
import com.wjx.android.weather.common.setAdapterAnimation
import com.wjx.android.weather.common.util.CommonUtil
import com.wjx.android.weather.common.util.getSky
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Place
import kotlinx.android.synthetic.main.place_item.view.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 17:25
 */
class ChoosePlaceAdapter(layout: Int, listData: MutableList<ChoosePlaceData>?) :
    BaseQuickAdapter<ChoosePlaceData, BaseViewHolder>(
        layout, listData
    ) {

    init {
        setAdapterAnimation(2)
    }

    override fun convert(holder: BaseViewHolder, item: ChoosePlaceData) {
        holder?.let { holder ->
            item?.let {
                holder.itemView.location_card.setBackgroundColor(
                    if (CommonUtil.getNightString(item.skycon)) ContextCompat.getColor(
                        context,
                        R.color.colorPrimaryDarkNight
                    ) else ContextCompat.getColor(
                        context, R.color.material_blue
                    )
                )
                holder.setText(R.id.location_name, item.name)
                holder.setText(
                    R.id.location_temperature,
                    "${item.temperature} ℃"
                )
                holder.setImageResource(
                    R.id.location_img,
                    getSky(item.skycon).icon
                )
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        setAdapterAnimation(2)
    }
}