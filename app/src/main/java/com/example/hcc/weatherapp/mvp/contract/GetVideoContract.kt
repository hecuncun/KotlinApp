package com.example.hcc.weatherapp.mvp.contract

import com.example.hcc.weatherapp.data.VideoResponse
import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel

/**
 * Created by hecuncun on 2019/9/9
 */
interface GetVideoContract {
    interface Model :IModel{
        fun getVideoInfo(date:String,num:Int)
    }

    interface View :IBaseView{
        fun onResponse(response:VideoResponse)
    }

}