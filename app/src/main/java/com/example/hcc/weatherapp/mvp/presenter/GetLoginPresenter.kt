package com.example.hcc.weatherapp.mvp.presenter

import com.example.hcc.weatherapp.data.LoginResponse
import com.example.hcc.weatherapp.http.ApiManager
import com.example.hcc.weatherapp.mvp.contract.GetLoginContract
import com.stx.xhb.core.mvp.BasePresenter

/**
 * Created by hecuncun on 2019/9/16
 */
class GetLoginPresenter(mvpView:GetLoginContract.View) :BasePresenter<LoginResponse,GetLoginContract.View>(),GetLoginContract.Module {
    init {
        attachView(mvpView)
    }
    override fun getLoginToken(loginName: String, password: String) {
        request(ApiManager.ApiFactory.createTestApi().loginCall(loginName, password),object :LoadTaskCallback<LoginResponse>{
            override fun onTaskLoaded(data: LoginResponse) {
                if (data.code==0){
                    getView()?.onResponse(data.data.token)
                }else{
                    getView()?.onResponse(data.message)
                }

            }

            override fun onDataNotAvailable(msg: String) {
                getView()?.onResponse(msg)
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