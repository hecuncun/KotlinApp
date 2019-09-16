package com.example.hcc.weatherapp.ui.adapter.provider

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.config.GlideApp
import com.example.hcc.weatherapp.data.Item
import com.example.hcc.weatherapp.ui.adapter.VideoRvAdapter

/**
 * Created by hecuncun on 2019/9/10
 */
class BannerItemProvider : BaseItemProvider<Item, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.list_home_item_banner
    }

    override fun viewType(): Int {
        return VideoRvAdapter.BANNER
    }

    override fun convert(holder: BaseViewHolder?, data: Item?, position: Int) {
        val imageView = holder?.getView(R.id.iv_banner) as ImageView
        GlideApp.with(imageView.context)
                .load(data?.data?.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }
}