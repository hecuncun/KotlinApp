package com.example.hcc.weatherapp.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.text.TextUtils
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.example.hcc.weatherapp.MainActivity
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.config.GlideApp
import com.example.hcc.weatherapp.config.SpConstants
import com.example.hcc.weatherapp.data.SplashImageResponse
import com.example.hcc.weatherapp.mvp.contract.GetWelcomeImgContract
import com.example.hcc.weatherapp.mvp.presenter.GetWelcomeImgPresenter
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.core.utils.GsonUtil
import com.stx.xhb.core.utils.SPUtils

/**
 * Created by hecuncun on 2019/8/29
 */
class SplashActivity : BaseActivity(), GetWelcomeImgContract.View {

    private var alphaAnimIn: ObjectAnimator? = null
    private val ANIMATOR_DURATION = 2000L
    private var mSplashImgPresenter:GetWelcomeImgPresenter ?= null

    override fun getLayoutResource(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        //设置全屏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //隐藏虚拟按键并且全屏
        hideBottomUIMenu()

        val ivSplash = findViewById<ImageView>(R.id.iv_splash)
        val splashView = findViewById<FrameLayout>(R.id.splash_view)

        //欢迎页从本地/网络加载
        if (TextUtils.isEmpty(getWelcomeImage())) {
            GlideApp.with(this).load(R.drawable.splash_bg).dontAnimate().into(ivSplash)
        } else {
            GlideApp.with(this).load(getWelcomeImage()).dontAnimate().into(ivSplash)
        }

        alphaAnimIn = ObjectAnimator.ofFloat(splashView, "alpha", 0f, 1f)
        alphaAnimIn?.setDuration(ANIMATOR_DURATION)
        alphaAnimIn?.start()
        alphaAnimIn?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                jumpToMain()
            }
        })

    }

    private fun jumpToMain() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
        overridePendingTransition(R.anim.splash_fade_in, R.anim.splash_fade_out)
    }

    override fun initData() {
        mSplashImgPresenter = GetWelcomeImgPresenter(this)
        val widthPixels = resources.displayMetrics.widthPixels
        val heightPixels = resources.displayMetrics.heightPixels
        mSplashImgPresenter?.getSplashImg(widthPixels.toString()+"*"+heightPixels.toString(),widthPixels,heightPixels)
    }

    override fun setListener() {
    }

    override fun getSplashImgSuccess(data: SplashImageResponse) {
        SPUtils.putString(this, SpConstants.SP_WELCOME_IMAGE, GsonUtil.newGson().toJson(data, SplashImageResponse::class.java))
    }

    override fun getSplashImgFailed(msg: String) {
        showMsg(msg)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        alphaAnimIn?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        alphaAnimIn?.cancel()
        alphaAnimIn?.removeAllListeners()
        alphaAnimIn = null
    }

    fun getWelcomeImage(): String? {
        val data = SPUtils.getString(this, SpConstants.SP_WELCOME_IMAGE)
        if (!TextUtils.isEmpty(data)) {
            val imgResponse = GsonUtil.newGson().fromJson(data, SplashImageResponse::class.java)
            if (imgResponse.app.isNotEmpty() && !TextUtils.isEmpty(imgResponse.app.get(0).title)) {
                return imgResponse.app.get(0).image_url
            }
        }
        return ""
    }
}