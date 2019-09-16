package com.example.hcc.weatherapp.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.os.Environment
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.ui.adapter.PhotoViewAdapter
import com.stx.xhb.core.base.BaseActivity
import com.stx.xhb.core.utils.RxImage
import com.stx.xhb.core.utils.ShareUtils
import rx.android.schedulers.AndroidSchedulers
import java.io.File

/**
 * Created by hecuncun on 2019/9/4
 */
class PhotoViewActivity : BaseActivity() {

    private var photoViewPager: ViewPager? = null
    private var mTvIndicator: TextView? = null
    private var toolbar: Toolbar? = null
    private var imagesList: ArrayList<String>? = null
    private var mPos: Int = 0
    val PERMISS_REQUEST_CODE = 0x001
    private var saveImgUrl = ""

    companion object {
        val TRANSIT_PIC = "transit_img"
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_photo_view
    }

    override fun initView() {
        photoViewPager = findViewById(R.id.photo_viewpager)
        mTvIndicator = findViewById(R.id.tv_indicator)
        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//设置返回键
        ViewCompat.setTransitionName(photoViewPager, PhotoViewActivity.TRANSIT_PIC)
    }

    override fun initData() {
        imagesList = intent.getStringArrayListExtra("images")
        mPos = intent.getIntExtra("pos", 0)
        mTvIndicator?.text = ((mPos + 1).toString() + "/" + imagesList?.size)
        setAdapter()
    }

    private fun setAdapter() {
        val adapter = imagesList?.let { PhotoViewAdapter(this, it) }
        photoViewPager?.adapter = adapter
        photoViewPager?.currentItem = mPos

        adapter?.setOnImageLayoutListener(object : PhotoViewAdapter.OnImageLayoutListener {
            override fun setOnImageOnclick() {
                onBackPressed()
            }

            //长按保存图片
            override fun setLongClick() {
                AlertDialog.Builder(this@PhotoViewActivity)
                        .setMessage("保存到手机")
                        .setNegativeButton(android.R.string.cancel) { dialog, which -> dialog.dismiss() }
                        .setPositiveButton(android.R.string.ok) { dialog, which ->
                            if (checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))) {
                                saveImage()
                            } else {
                                requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISS_REQUEST_CODE)
                            }

                            dialog.dismiss()
                        }
                        .show()
            }
        })
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PERMISS_REQUEST_CODE == requestCode) {
            if (checkPermissions(permissions)) {
                saveImage()
            } else {
                showTipsDialog()
            }
        }
    }

    /**
     * 保存图片
     */
    private fun saveImage() {

        val subscribe = RxImage.saveImageAndGetPathObservable(this, saveImgUrl)
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe({
                    val appDir = File(Environment.getExternalStorageDirectory(), "AALife")
                    val msg = String.format(getString(R.string.picture_has_save_to),
                            appDir.absolutePath)
                    showToast(msg)
                }, {
                    showToast(getString(R.string.string_img_save_failed))
                })
        addSubscription(subscribe)


    }

    override fun setListener() {
        photoViewPager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                saveImgUrl = photoViewPager?.currentItem?.let { imagesList?.get(it) }!!
                mTvIndicator?.text = ((photoViewPager?.getCurrentItem()!! + 1).toString() + "/" + imagesList?.size)
            }
        })
        toolbar?.setNavigationOnClickListener({ onBackPressed() })
    }

    //toolbar 设置菜单栏

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_more, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                if (checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))) {
                    saveImage()
                } else {
                    requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISS_REQUEST_CODE)
                }
                return true
            }

            R.id.setting_pic -> {
                if (checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_WALLPAPER))) {
                    setWallpaper()
                } else {
                    requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_WALLPAPER),PERMISS_REQUEST_CODE)
                }
                return true
            }
           R.id.menu_share ->{
               if (checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE))){
               val subcribe =RxImage.saveImageAndGetPathObservable(this,saveImgUrl)
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe({uri->
                                ShareUtils.shareImage(this@PhotoViewActivity,uri,"分享图片至...")
                           },{
                            throwable->showToast(throwable.message.toString())
                           })

                   addSubscription(subcribe)
               }else{
                   requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),PERMISS_REQUEST_CODE)
               }

               return true
           }

            else -> return super.onOptionsItemSelected(item)

        }

    }

    private fun setWallpaper() {
     val subscribe =  RxImage.setWallPaper(this,saveImgUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                },{
                    showToast("壁纸设置失败")
                })

        addSubscription(subscribe)
    }


}