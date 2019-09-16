package com.example.hcc.weatherapp.ui.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.data.Item
import com.example.hcc.weatherapp.ui.adapter.VideoRvAdapter

/**
 * Created by hecuncun on 2019/9/10
 */
class TextItemProvider : BaseItemProvider<Item, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.list_home_text_item
    }

    override fun viewType(): Int {
        return VideoRvAdapter.TEXT
    }

    override fun convert(holder: BaseViewHolder?, data: Item?, position: Int) {
        if (data?.type?.startsWith("text")!!) {
            holder?.setGone(R.id.tv_home_text, true)
        } else {
            holder?.setGone(R.id.tv_home_text, false)
        }

        holder?.setText(R.id.tv_home_text, data.data.text)
    }
}