package com.example.hcc.weatherapp.mvp.contract

import com.stx.xhb.core.mvp.IBaseView
import com.stx.xhb.core.mvp.IModel

/**
 * Created by hecuncun on 2019/9/16
 */
interface GetLoginContract{
    interface Module :IModel{
        fun getLoginToken(loginName:String,password:String)
    }

    interface View :IBaseView{
        fun onResponse(token: String)
    }
}