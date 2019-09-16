package com.example.hcc.weatherapp.ui.fragment

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.data.FeedListBean
import com.example.hcc.weatherapp.mvp.contract.GetTuChongFeedContract
import com.example.hcc.weatherapp.mvp.presenter.GetTuChongFeedPresenter
import com.example.hcc.weatherapp.ui.activity.PhotoViewActivity
import com.example.hcc.weatherapp.ui.adapter.TuChongFeedAdapter
import com.stx.xhb.core.base.BaseFragment
import com.stx.xhb.core.widget.RecyclerViewNoBugStaggeredGridLayoutManger

/**
 * Created by hecuncun on 2019/8/30
 */
class TuChongFeedFragment : BaseFragment(), GetTuChongFeedContract.View, SwipeRefreshLayout.OnRefreshListener {


    private var mRvTuChong: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var mTuChongListAdapter: TuChongFeedAdapter? = null
    private var mTuChongFeedPresenter: GetTuChongFeedPresenter? = null

    private var page = 1
    private var posId = ""

    override fun getLayoutResource(): Int {
        return R.layout.fragment_tu_chong_feed
    }

    override fun initView() {
        mRvTuChong = getView(R.id.recly_view)
        mSwipeRefreshLayout = getView(R.id.refresh_layout)
        val layoutManger = RecyclerViewNoBugStaggeredGridLayoutManger(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManger.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        mRvTuChong?.itemAnimator = null
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary)
        mRvTuChong?.layoutManager = layoutManger
        mTuChongListAdapter = TuChongFeedAdapter(R.layout.list_item_tu_chong)
        mTuChongListAdapter?.openLoadAnimation()
        mRvTuChong?.adapter = mTuChongListAdapter
    }

    override fun initData() {
        mTuChongFeedPresenter = GetTuChongFeedPresenter(this)
    }

    override fun setListener() {
        mTuChongListAdapter?.setOnImageItemClickListener(object : TuChongFeedAdapter.OnImageItemClickListener {
            override fun setOnImageClick(view: View, imageList: ArrayList<String>) {
                val intent = Intent(mContext,PhotoViewActivity::class.java)
                intent.putStringArrayListExtra("images",imageList)
                intent.putExtra("pos",0)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,view,PhotoViewActivity.TRANSIT_PIC
                )
                try {
                    //转场动画
                    ActivityCompat.startActivity(activity,intent,optionsCompat.toBundle())
                }catch (e:IllegalArgumentException){
                    e.printStackTrace()
                    startActivity(intent)
                }

            }
        })

        mSwipeRefreshLayout?.setOnRefreshListener(this)

        mTuChongListAdapter?.setOnLoadMoreListener({
            page++
            mTuChongFeedPresenter?.getFeed(page, "loadmore", posId)
        }, mRvTuChong)

    }

    override fun onRefresh() {
        page = 1
        mTuChongFeedPresenter?.getFeed(page, "refresh", posId)
    }

    override fun onResponse(feedList: List<FeedListBean>, isMore: Boolean) {
        onLoadCompleted()
        mTuChongListAdapter?.setEnableLoadMore(isMore)
        posId = feedList[feedList.size - 1].post_id.toString()
        if (page == 1) {
            mTuChongListAdapter?.setNewData(feedList)
        } else {
            mTuChongListAdapter?.addData(feedList)
        }

    }

    private fun onLoadCompleted() {
        if (page == 1) {
            mSwipeRefreshLayout?.setRefreshing(false)
        } else {
            mTuChongListAdapter?.loadMoreComplete()
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMsg(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onVisible() {
        super.onVisible()
        mSwipeRefreshLayout?.setRefreshing(true)
        onRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        mTuChongFeedPresenter?.destory()
    }
}