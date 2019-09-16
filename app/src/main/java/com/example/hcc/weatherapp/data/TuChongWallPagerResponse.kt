package com.example.hcc.weatherapp.data

/**
 * Created by hecuncun on 2019/9/6
 */
data class TuChongWallPagerResponse(
        val feedList: List<Feed>,
        val tos_name: String,
        val more: Boolean,
        val result: String)


data class Feed(
   val type:String,
   val entry :Entry
)

data class Entry(
        val vid: String,
        val video_id: String,
        val title: String,
        val content: String,
        val type: String,
        val author: Author,
        val cover: String,
        val raw_cover: String,
        val favorites: Int,
        val views: Int,
        val video_width: String,
        val video_height: String,
        val created: String,
        val share_url: String,
        val share_cover: String,
        val duration: String,
        val is_recommend: Boolean,
        val comments: String,
        val passed_time: String,
        val is_ultra: Boolean,
        val category: List<String>,
        val collected: Boolean,
        val collect_num: Int,
        val gif_cover: String,
        val images:List<ImageBeans>
)
data class Author(
        val site_id: Int,
        val type: String,
        val name: String,
        val domain: String,
        val description: String,
        val followers: Int,
        val url: String,
        val icon: String,
        val verified: Boolean,
        val verified_type: Int,
        val verified_reason: String,
        val verifications: Int,
        val verification_list: List<Any>
)

data class ImageBeans(
        val img_id: Int,
        val user_id: Int,
        val title: String,
        val excerpt: String,
        val width: Int,
        val height: Int,
        val description: String
)