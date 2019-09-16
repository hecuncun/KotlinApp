package com.example.hcc.weatherapp.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.example.hcc.weatherapp.data.Item
import com.example.hcc.weatherapp.ui.adapter.provider.BannerItemProvider
import com.example.hcc.weatherapp.ui.adapter.provider.TextItemProvider
import com.example.hcc.weatherapp.ui.adapter.provider.VideoItemProvider

/**
 * Created by hecuncun on 2019/9/9
 */
class VideoRvAdapter(date: MutableList<Item>?) : MultipleItemRvAdapter<Item, BaseViewHolder>(date) {

    init {
        finishInitialize()
    }

    companion object {
        val VIDEO = 1
        val TEXT = 2
        val BANNER = 3
    }

    override fun registerItemProvider() {
        mProviderDelegate.registerProvider(TextItemProvider())
        val videoItemProvider = VideoItemProvider()
        mProviderDelegate.registerProvider(videoItemProvider)
        mProviderDelegate.registerProvider(BannerItemProvider())
        videoItemProvider.setOnItemClickListener(object : VideoItemProvider.OnItemClickListener{
            override fun onItemClick(view: View, data: Item) {
                //点击进入视频页
                Toast.makeText(view.context,"sss",Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun getViewType(item: Item?): Int {
       return if ("video" == item?.type){
           VIDEO
       }else if (item?.type?.startsWith("banner")!! && TextUtils.isEmpty(item.data.actionUrl)){
           BANNER
       }else{
           TEXT
       }
    }


}