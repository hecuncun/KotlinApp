package com.example.hcc.weatherapp.mvp.contract

import com.example.hcc.weatherapp.data.SplashImageResponse
import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel

/**
 * Created by hecuncun on 2019/8/29
 */
interface GetWelcomeImgContract {
    interface View :IBaseView{
        fun getSplashImgSuccess(data :SplashImageResponse)
        fun getSplashImgFailed(msg:String)
    }

    interface Model:IModel{
        fun getSplashImg(resolution :String,width:Int,height:Int)
    }
}