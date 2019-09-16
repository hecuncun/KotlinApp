package com.example.hcc.weatherapp.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.config.GlideApp
import com.github.chrisbanes.photoview.OnViewTapListener
import com.github.chrisbanes.photoview.PhotoView
import com.stx.xhb.core.utils.LoggerHelper

/**
 * Created by hecuncun on 2019/9/4
 */
class PhotoViewAdapter  (context: Context, imageList: ArrayList<String>) : PagerAdapter() {

    private val context: Context
    private val imageList: ArrayList<String>

    init {
        this.context = context
        this.imageList = imageList
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val convertView = View.inflate(context, R.layout.item_photoview, null)
        val photoView = convertView.findViewById<PhotoView>(R.id.photo_view)
        val progressBar = convertView.findViewById<ProgressBar>(R.id.progress_view)
        photoView.scaleType = ImageView.ScaleType.FIT_CENTER
        val url = imageList[position]
        LoggerHelper.d("图片的地址==$url")
        GlideApp.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(photoView)

        photoView.setOnViewTapListener(OnViewTapListener() { view, x, y ->
          if (onImageLayoutListener!=null){
              onImageLayoutListener!!.setOnImageOnclick()
          }
        })

        photoView.setOnLongClickListener(View.OnLongClickListener {
            if (onImageLayoutListener!=null){
                onImageLayoutListener!!.setLongClick()
            }
            true
        })

        container.addView(convertView)//添加到ViewPager

        return convertView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private var onImageLayoutListener: OnImageLayoutListener? = null


    fun setOnImageLayoutListener(onImageLayoutListener: OnImageLayoutListener) {
        this.onImageLayoutListener = onImageLayoutListener
    }

    interface OnImageLayoutListener {
        fun setOnImageOnclick()
        fun setLongClick()
    }
}