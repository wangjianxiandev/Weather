package com.wjx.android.weather.module.chooseplace.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.weather.R

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 17:25
 */
class ChoosePlaceAdapter(layout: Int, listData: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(
        layout, listData
    ) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.let { holder ->
            item?.let {
                holder.setText(R.id.location_name, item)
            }
        }
    }

}