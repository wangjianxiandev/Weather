package com.wjx.android.weather.module.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wjx.android.weather.R
import com.wjx.android.weather.common.util.DateUtil
import com.wjx.android.weather.common.util.getSky
import com.wjx.android.weather.model.Daily

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/6 17:03
 */
class HomeDailyAdapter(layout: Int, listData: MutableList<Daily.DailyData>?) :
    BaseQuickAdapter<Daily.DailyData, BaseViewHolder>(
        layout, listData
    ) {

    override fun convert(holder: BaseViewHolder, item: Daily.DailyData) {
        holder?.let { holder ->
            item?.let {
                holder.setText(R.id.date, DateUtil.getTodayInWeek(item.date))
                    .setImageResource(R.id.weather, getSky(
                        item.value
                    ).icon)
                    .setText(R.id.temperature, "${item.min.toInt()}℃~ ${item.max.toInt()}℃")
            }
        }
    }
}