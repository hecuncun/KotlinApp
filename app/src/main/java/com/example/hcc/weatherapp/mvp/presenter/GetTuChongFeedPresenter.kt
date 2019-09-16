package com.example.hcc.weatherapp.mvp.presenter

import android.text.TextUtils
import com.example.hcc.weatherapp.data.TuChongFeedResponse
import com.example.hcc.weatherapp.http.ApiManager
import com.example.hcc.weatherapp.mvp.contract.GetTuChongFeedContract
import com.stx.xhb.core.mvp.BasePresenter

/**
 * Created by hecuncun on 2019/9/2
 */
class GetTuChongFeedPresenter(mvpView :GetTuChongFeedContract.View) :BasePresenter<TuChongFeedResponse,GetTuChongFeedContract.View>() ,GetTuChongFeedContract.Module{

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

    override fun getFeed(page: Int, type: String, posId: String) {
        val map =HashMap<String,String>()
        map["page"] = page.toString()
        map["type"] = type
        if (!TextUtils.isEmpty(posId)){
            map["post_id"] = posId
        }

        request(ApiManager.ApiFactory.createTuChongApi().getFeedApp(map),object :LoadTaskCallback<TuChongFeedResponse>{
            override fun onTaskLoaded(data: TuChongFeedResponse) {
               if (!data.feedList.isEmpty()){
                   getView()?.onResponse(data.feedList,data.more)
               }
            }

            override fun onDataNotAvailable(msg: String) {
              getView()?.showMsg(msg)
            }

        })
    }



}