package com.wjx.android.weather.module.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.weather.R

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/6 17:03
 */
class HourAdapter(layout: Int, listData: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(
        layout, listData
    ) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.let { holder ->
            item?.let {
                holder.setText(R.id.time, item)
                    .setText(R.id.temperature, "20")
            }
        }
    }

}