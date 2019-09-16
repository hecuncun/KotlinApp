package com.example.hcc.weatherapp.mvp.presenter

import android.text.TextUtils
import com.example.hcc.weatherapp.data.VideoResponse
import com.example.hcc.weatherapp.http.ApiManager
import com.example.hcc.weatherapp.mvp.contract.GetVideoContract
import com.stx.xhb.core.mvp.BasePresenter

/**
 * Created by hecuncun on 2019/9/9
 */
class GetVideoPresenter(mvpView :GetVideoContract.View) :BasePresenter<VideoResponse,GetVideoContract.View>(),GetVideoContract.Model {
    override fun getVideoInfo(date: String, num: Int) {
        val map =HashMap<String,String>()
        map["num"] = "2"
        if (!TextUtils.isEmpty(date)){
            map["date"]="date"
        }

        request(ApiManager.ApiFactory.createVideoApi().getVideoList(map),object :LoadTaskCallback<VideoResponse>{
            override fun onTaskLoaded(data: VideoResponse) {
                getView()?.onResponse(data)
            }

            override fun onDataNotAvailable(msg: String) {
               getView()?.showMsg(msg)
            }

        })
    }

    init {
        attachView(mvpView)
    }



    override fun onCreate() {
    }

    override fun start() {
    }

    override fun destory() {
        detachView()
    }
}