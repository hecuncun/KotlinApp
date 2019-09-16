package com.example.hcc.weatherapp.mvp.contract

import com.example.hcc.weatherapp.data.Feed
import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel

/**
 * Created by hecuncun on 2019/9/6
 *
 *
 */
interface GetTuChongWallPagerContract {
    interface Model:IModel{
        fun getWallPager (page :Int)
    }

    interface View :IBaseView{
        fun onResponse(feedList :List<Feed>, isMore :Boolean)
    }
}