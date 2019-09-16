package com.example.hcc.weatherapp.ui.adapter

import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.config.GlideApp
import com.example.hcc.weatherapp.data.Feed
import com.stx.xhb.core.utils.ScreenUtil

/**
 * Created by hecuncun on 2019/9/6
 */
class TuChongWallPagerAdapter(resLayoutId :Int):BaseQuickAdapter<Feed,BaseViewHolder>(resLayoutId) {
    override fun convert(holder: BaseViewHolder?, feedListBean: Feed?) {
        val feedListBeanEntry = feedListBean?.entry
        if ("video"==feedListBeanEntry?.type){
            return
        }
        val imageView =  holder?.getView(R.id.iv_img) as ImageView

        val layoutParams = LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(imageView.context)/3,ScreenUtil.getScreenHeight(imageView.context) / 5 * 2)

        layoutParams.setMargins(2,2,2,2)
        imageView.layoutParams= layoutParams
        val images = feedListBeanEntry?.images
        if (images==null || images.isEmpty()){
            return
        }
        val imagesBean = images[0]
        val url = "https://photo.tuchong.com/" + imagesBean.user_id + "/f/" + imagesBean.img_id + ".jpg"

        GlideApp.with(mContext)
                .load(url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView)
                .getSize { width, height ->
                    if (!holder.itemView.isShown){
                        holder.itemView.visibility = View.VISIBLE//加载出来后显示出来
                    }
                }
        holder.itemView.tag=url
        ViewCompat.setTransitionName(imageView,url)

        val imageList = ArrayList<String>()
        for(i in images.indices ){
            val imageData = images[i]
            val url = "https://photo.tuchong.com/" + imageData.user_id + "/f/" + imageData.img_id + ".jpg"
            imageList.add(url)
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            if(mOnImageItemClickListener!=null){
                mOnImageItemClickListener?.setOnImageClick(imageView,imageList)
            }
        })

    }

    private var mOnImageItemClickListener: OnImageItemClickListener? = null

    fun setOnImageItemClickListener(onImageItemClickListener: OnImageItemClickListener) {
        mOnImageItemClickListener = onImageItemClickListener
    }

    interface OnImageItemClickListener {
        fun setOnImageClick(view: View, imageList: java.util.ArrayList<String>)
    }


}