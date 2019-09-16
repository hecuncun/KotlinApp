package com.example.hcc.weatherapp

import com.stx.xhb.core.base.BaseApplication
import com.stx.xhb.core.utils.LoggerHelper

/**
 * Created by hecuncun on 2019/8/29
 */
class MyApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        LoggerHelper.initLogger(true)
    }
}