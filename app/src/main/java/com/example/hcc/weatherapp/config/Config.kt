package com.example.hcc.weatherapp.config

import com.example.hcc.weatherapp.R

/**
 * Created by hecuncun on 2019/8/30
 */
class Config {
    companion object {
        val WALLPAPER = "WALLPAPER"
        val IMAGE = "IMAGE"
        val ZHIHU = "ZHIHU"
        val VIDEO = "VIDEO"
    }

    enum class Channel constructor(var title: Int, var icon: Int) {
        IMAGE(R.string.fragment_image_title, R.drawable.ic_photo_black_24dp),
        WALLPAPER(R.string.fragment_wallpaper_title, R.drawable.icon_picture_black_24px),
        VIDEO(R.string.fragment_video_title, R.drawable.ic_video_black_24px),
        ZHIHU(R.string.fragment_zhihu_title, R.drawable.icon_zhihu_black_24px)
    }
}