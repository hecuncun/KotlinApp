package com.example.hcc.weatherapp.mvp.contract

import com.example.hcc.weatherapp.data.FeedListBean
import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel

/**
 * Created by hecuncun on 2019/9/2
 */
interface GetTuChongFeedContract {
    interface Module :IModel{
        fun getFeed(page :Int,type :String,posId:String)
    }

    interface View :IBaseView{
        fun onResponse(feedList :List<FeedListBean>,isMore :Boolean)
    }
}