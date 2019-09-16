package com.example.hcc.weatherapp.mvp.presenter

import com.example.hcc.weatherapp.data.TuChongWallPagerResponse
import com.example.hcc.weatherapp.http.ApiManager
import com.example.hcc.weatherapp.mvp.contract.GetTuChongWallPagerContract
import com.stx.xhb.core.mvp.BasePresenter

/**
 * Created by hecuncun on 2019/9/6
 */
class GetTuChongWallPaperPresenter(mvpView:GetTuChongWallPagerContract.View):BasePresenter<TuChongWallPagerResponse,GetTuChongWallPagerContract.View>(),GetTuChongWallPagerContract.Model {
    init {
        attachView(mvpView)
    }

    //获取后台数据
    override fun getWallPager(page: Int) {
       request(ApiManager.ApiFactory.createTuChongApi().getWallPaper(page),object :LoadTaskCallback<TuChongWallPagerResponse>{
           override fun onTaskLoaded(data: TuChongWallPagerResponse) {
               if (!data.feedList.isEmpty()){
                   getView()?.onResponse(data.feedList,data.more)
               }
           }

           override fun onDataNotAvailable(msg: String) {
               getView()?.showMsg(msg)
           }
       })
    }

    override fun onCreate() {
    }

    override fun start() {
    }

    override fun destory() {
       detachView()
    }
}