package com.example.hcc.weatherapp.ui.fragment

import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.data.Feed
import com.example.hcc.weatherapp.mvp.contract.GetTuChongWallPagerContract
import com.example.hcc.weatherapp.mvp.presenter.GetTuChongWallPaperPresenter
import com.example.hcc.weatherapp.ui.adapter.TuChongWallPagerAdapter
import com.stx.xhb.core.base.BaseFragment
import com.stx.xhb.core.utils.ScreenUtil
import com.stx.xhb.core.widget.DividerDecoration
import java.util.*

/**
 * 壁纸页
 * Created by hecuncun on 2019/8/30
 */
class WallPaperFragment : BaseFragment(), GetTuChongWallPagerContract.View, SwipeRefreshLayout.OnRefreshListener {


    private var mRvTuChongWallPaper: RecyclerView? = null
    private var mRefreshLayout: SwipeRefreshLayout? = null
    private var mTuChongWallPaperAdapter: TuChongWallPagerAdapter? = null
    private var getTuChongWallPaperPresenter: GetTuChongWallPaperPresenter? = null
    private var page = 1
    override fun getLayoutResource(): Int {
        return R.layout.fragment_wall_paper
    }

    override fun initView() {
        mRvTuChongWallPaper = getView(R.id.rv_wall_paper)
        mRefreshLayout = getView(R.id.refresh_layout)
        val gridLayoutManager = GridLayoutManager(activity, 3)
        mRvTuChongWallPaper?.layoutManager = gridLayoutManager
        mRvTuChongWallPaper?.addItemDecoration(DividerDecoration(ContextCompat.getColor(activity, R.color.colorWhite), ScreenUtil.dp2px(activity, 1)))
        mTuChongWallPaperAdapter = TuChongWallPagerAdapter(R.layout.item_wall_paper)
        mTuChongWallPaperAdapter?.openLoadAnimation()
        mRvTuChongWallPaper?.adapter = mTuChongWallPaperAdapter

    }

    override fun initData() {
        getTuChongWallPaperPresenter = GetTuChongWallPaperPresenter(this)
    }

    override fun setListener() {
        mTuChongWallPaperAdapter?.setOnImageItemClickListener(object : TuChongWallPagerAdapter.OnImageItemClickListener {
            override fun setOnImageClick(view: View, imageList: ArrayList<String>) {
//                val intent = Intent(mContext, WallPagerActivity::class.java)
//                intent.putStringArrayListExtra("image", imageList)
//                intent.putExtra("pos", 0)
//                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        activity, view, WallPagerActivity.TRANSIT_PIC)
//                try {
//                    ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
//                } catch (e: IllegalArgumentException) {
//                    e.printStackTrace()
//                    startActivity(intent)
//                }
            }

        })

        mRefreshLayout?.setOnRefreshListener(this)
        mTuChongWallPaperAdapter?.setOnLoadMoreListener({
            page++
            getTuChongWallPaperPresenter?.getWallPager(page)

        }, mRvTuChongWallPaper)

    }

    override fun onResponse(feedList: List<Feed>, isMore: Boolean) {
        onLoadCompleted()
        //设置数据
        mTuChongWallPaperAdapter?.setEnableLoadMore(isMore)
        for (i in feedList.indices) {
            val feedListBean = feedList[i]
            if ("post" == feedListBean.type) {
                mTuChongWallPaperAdapter?.addData(feedListBean)
            }
        }

    }

    private fun onLoadCompleted() {
        if (page == 1) {
            mTuChongWallPaperAdapter?.setNewData(null)
            mRefreshLayout?.isRefreshing = false
        } else {
            mTuChongWallPaperAdapter?.loadMoreComplete()
        }
    }

    override fun onRefresh() {
        page = 1
        getTuChongWallPaperPresenter?.getWallPager(page)
    }

    override fun onVisible() {
        super.onVisible()
        mRefreshLayout?.isRefreshing = true
        onRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        getTuChongWallPaperPresenter?.destory()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMsg(msg: String) {
        onLoadCompleted()
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }
}