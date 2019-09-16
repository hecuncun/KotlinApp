package com.example.hcc.weatherapp.mvp.presenter

import com.example.hcc.weatherapp.data.SplashImageResponse
import com.example.hcc.weatherapp.http.ApiManager
import com.example.hcc.weatherapp.mvp.contract.GetWelcomeImgContract
import com.stx.xhb.core.mvp.BasePresenter

/**
 * Created by hecuncun on 2019/8/29
 */
class GetWelcomeImgPresenter(mvpView :GetWelcomeImgContract.View):BasePresenter<SplashImageResponse,GetWelcomeImgContract.View>(),GetWelcomeImgContract.Model {
    init {
        attachView(mvpView)
    }

    override fun getSplashImg(resolution: String, width: Int, height: Int) {
        request(ApiManager.ApiFactory.createTuChongApi().getSplashImg(resolution,width,height),object :LoadTaskCallback<SplashImageResponse>{
            override fun onTaskLoaded(data: SplashImageResponse) {
                if (data.result.equals("SUCCESS")){
                    getView()?.getSplashImgSuccess(data)
                }else{
                    getView()?.getSplashImgFailed(data.result)
                }
            }

            override fun onDataNotAvailable(msg: String) {
               getView()?.getSplashImgFailed(msg)
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