package com.example.hcc.weatherapp.data

/**
 * Created by hecuncun on 2019/8/29
 */
data class SplashImageResponse(
        val app :List<App>,
        val app_timing :List<Any>,
        val result:String
)

data class App(
        val author_name :String,
        val id :String,
        val image_url:String,
        val title :String,
        val tuchong_url:String
)