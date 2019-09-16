package com.example.hcc.weatherapp.ui.adapter

import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.config.GlideApp
import com.example.hcc.weatherapp.data.FeedListBean
import com.stx.xhb.core.widget.RatioImageView

/**
 * Created by hecuncun on 2019/9/2
 */
class TuChongFeedAdapter(layoutResId: Int) : BaseQuickAdapter<FeedListBean, BaseViewHolder>(layoutResId) {

    private var mOnImageItemClickListener :OnImageItemClickListener?=null
    override fun convert(holder: BaseViewHolder?, feedListBean: FeedListBean?) {
        val imageView = holder?.getView(R.id.iv_img) as RatioImageView
        imageView.setOriginalSize(50, 50)
        val limit = 48
        val text = if (feedListBean?.title?.length!! > limit)
            feedListBean.title.substring(0, limit) + "..."
        else
            feedListBean.title
        val textView = holder.getView(R.id.tv_title) as TextView
        textView.text = text

        val images = feedListBean.images
        if (images.isEmpty()) return

        val imagesBean = images[0]

        val url = "https://photo.tuchong.com/" + imagesBean.user_id + "/f/" + imagesBean.img_id + ".jpg"

        GlideApp.with(mContext)
                .load(url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView)

        holder.itemView.tag = url

        ViewCompat.setTransitionName(imageView,url)

        val imageList =ArrayList<String>()

        for (i in images.indices){
            val  imageData = images[i]
            val  url = "https://photo.tuchong.com/" + imageData.user_id + "/f/" + imageData.img_id + ".jpg"
            imageList.add(url)
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            if (mOnImageItemClickListener!=null){
                mOnImageItemClickListener?.setOnImageClick(imageView,imageList)
            }
        })

    }

    fun setOnImageItemClickListener(onImageItemClickListener: OnImageItemClickListener){
        mOnImageItemClickListener= onImageItemClickListener
    }

    interface OnImageItemClickListener{
        fun setOnImageClick(view :View,imageList :ArrayList<String>)
    }
}