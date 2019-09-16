package com.example.hcc.weatherapp.ui.adapter.provider

import android.view.View
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
class VideoItemProvider : BaseItemProvider<Item, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.list_home_item_video
    }

    override fun viewType(): Int {
        return VideoRvAdapter.VIDEO
    }

    override fun convert(holder: BaseViewHolder?, data: Item?, position: Int) {
        //得到数据
        val feed = data?.data?.cover?.feed
        val title = data?.data?.title
        val duration = data?.data?.duration
        var category = data?.data?.category
        category = "#$category / "

        val last = duration?.rem(60)  //除60 取余

        var stringLast :String= ""
        if (last!=null){
            if (last<9){
                stringLast = "0$last"
            }else{
                stringLast =last.toString()+""
            }
        }

        var durationString:String= ""
        val minit = duration?.div(60)  //除60  取商
        if (minit!=null){
            if (minit<10){
                durationString = "0$minit"
            }else{
                durationString=""+minit
            }
        }

        val stringTime =durationString + "' "+stringLast+ '"'.toString()

        val view = holder?.getView(R.id.iv) as ImageView

        GlideApp.with(view.context).load(feed).diskCacheStrategy(DiskCacheStrategy.ALL).transition(DrawableTransitionOptions.withCrossFade()).into(view)
        holder.setText(R.id.tv_title,title)
        holder.setText(R.id.tv_time,(category+stringTime))

    }

    override fun onClick(holder: BaseViewHolder?, data: Item?, position: Int) {
        data?.let {
            mOnItemClickListener?.onItemClick(holder?.getView(R.id.iv) as ImageView,it)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, data: Item)
    }

    private var mOnItemClickListener :OnItemClickListener ?= null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        mOnItemClickListener = onItemClickListener
    }
}