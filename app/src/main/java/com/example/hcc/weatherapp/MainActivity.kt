package com.example.hcc.weatherapp

import android.app.Fragment
import android.content.Intent
import android.os.Build
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.transition.Slide
import android.view.Gravity
import com.example.hcc.weatherapp.config.Config
import com.example.hcc.weatherapp.ui.activity.AboutActivity
import com.example.hcc.weatherapp.ui.fragment.TuChongFeedFragment
import com.example.hcc.weatherapp.ui.fragment.VideoFragment
import com.example.hcc.weatherapp.ui.fragment.WallPaperFragment
import com.example.hcc.weatherapp.ui.fragment.ZhiHuFragment
import com.stx.xhb.core.base.BaseActivity
import java.util.*

class MainActivity : BaseActivity() {
    var toolbar: Toolbar? = null
    var ctlMain: CoordinatorLayout? = null
    var navView: NavigationView? = null
    var drawerLayout: DrawerLayout? = null
    var mTitles: ArrayList<Int>? = null
    var mFragments: ArrayList<Fragment>? = null
    var mCurrentFragment: Fragment? = null
    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        toolbar = findViewById<Toolbar>(R.id.toolBar)
        ctlMain = findViewById<CoordinatorLayout>(R.id.ctl_main)
        navView = findViewById<NavigationView>(R.id.nav_view)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        setSupportActionBar(toolbar)
        ctlMain!!.fitsSystemWindows = false //不自适应屏幕
        toolbar?.let {
            setToolBar(it, true, false, drawerLayout)
        }
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()
        initMenu()
    }

    private fun initMenu() {
        //初始化目录
        val savedChannelList = ArrayList<Config.Channel>()
        mTitles = ArrayList()
        mFragments = ArrayList()
        val menu = navView!!.menu
        menu.clear()
        Collections.addAll<Config.Channel>(savedChannelList, *Config.Channel.values())

        for (i in savedChannelList.indices) {
            val menuItem = menu.add(0, i, 0, savedChannelList[i].title)
            mTitles!!.add(savedChannelList[i].title)
            menuItem.setIcon(savedChannelList[i].icon)
            menuItem.isCheckable = true
            addFragment(savedChannelList[i].name)
            if (i == 0) {
                menuItem.isCheckable = true
            }
        }
        navView!!.inflateMenu(R.menu.activity_main_drawer)
        switchFragment(mFragments!!.get(0), getString(mTitles!!.get(0)))


    }

    private fun switchFragment(fragment: Fragment, title: String) {
        val slideTransition: Slide
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slideTransition = Slide(Gravity.LEFT)
            slideTransition.duration = 700
            fragment.enterTransition = slideTransition
            fragment.exitTransition = slideTransition
        }

        if (mCurrentFragment == null || !(mCurrentFragment!!::class.java.name).equals(fragment::class.java.name)) {
            fragmentManager.beginTransaction().replace(R.id.replace, fragment).commit()
            mCurrentFragment = fragment
            val actionBar = supportActionBar!!
            actionBar.setTitle(title)
        }

    }

    private fun addFragment(name: String) {
        when (name) {
            Config.IMAGE -> mFragments!!.add(TuChongFeedFragment())
            Config.WALLPAPER -> mFragments!!.add(WallPaperFragment())
            Config.ZHIHU -> mFragments!!.add(ZhiHuFragment())
            Config.VIDEO -> mFragments!!.add(VideoFragment())
        }
    }

    override fun initData() {
    }

    override fun setListener() {
        navView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            val id = it.getItemId()
            if (id < mFragments!!.size) {
                switchFragment(mFragments!!.get(id), getString(mTitles!!.get(id)))
            }
            when (id) {
                R.id.nav_about -> startActivity(Intent(this, AboutActivity::class.java))
            }
            val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
            true
        })
    }


}





