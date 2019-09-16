package com.example.hcc.weatherapp.ui.fragment

import android.net.Uri
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.data.Item
import com.example.hcc.weatherapp.data.VideoResponse
import com.example.hcc.weatherapp.mvp.contract.GetVideoContract
import com.example.hcc.weatherapp.mvp.presenter.GetVideoPresenter
import com.example.hcc.weatherapp.ui.adapter.VideoRvAdapter
import com.stx.xhb.core.base.BaseFragment

/**
 * Created by hecuncun on 2019/8/30
 */
class VideoFragment : BaseFragment(), GetVideoContract.View {

    private var mRvVideo: RecyclerView? = null
    private var mRefreshLayout: SwipeRefreshLayout? = null
    private var list: MutableList<Item>? = null
    private var getVideoPresenter: GetVideoPresenter? = null
    private var mRvAdapter: VideoRvAdapter? = null
    private var isRefresh: Boolean = false
    private var nextPublishTime = ""

    override fun getLayoutResource(): Int {
        return R.layout.fragment_video
    }

    override fun initView() {
        mRvVideo = getView(R.id.rv_video)
        mRefreshLayout = getView(R.id.refresh_layout)
        mRvVideo?.layoutManager = LinearLayoutManager(mContext)
        mRefreshLayout?.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary)

    }

    override fun initData() {
        list = ArrayList()
        getVideoPresenter = GetVideoPresenter(this)
        setRvAdapter()
    }

    private fun setRvAdapter() {
        mRvAdapter = VideoRvAdapter(list)
        mRvVideo?.adapter = mRvAdapter
        mRvAdapter?.openLoadAnimation()
        mRvAdapter?.setOnLoadMoreListener({
            onLoadMore()
        }, mRvVideo)

    }

    private fun onLoadMore() {
        isRefresh = false
        getVideoPresenter?.getVideoInfo(nextPublishTime, 2)

    }

    override fun setListener() {
        mRefreshLayout?.setOnRefreshListener({ onRefreshData() })

    }

    private fun onRefreshData() {
        isRefresh = true
        mRefreshLayout?.isRefreshing = true
        getVideoPresenter?.getVideoInfo("", 2)
    }

    override fun onResponse(response: VideoResponse) {
        val issueList = response.issueList
        if (issueList.isEmpty()) {
            return
        }
        //刷新需要清除数据
        if (isRefresh) {
            list?.clear()
            mRefreshLayout?.isRefreshing = false
        } else {
            mRvAdapter?.loadMoreComplete()
        }

        for (i in issueList.indices) {
            list?.addAll(issueList[i].itemList)
        }

        val nextUrl = response.nextPageUrl
        nextPublishTime = Uri.parse(nextUrl).getQueryParameter("date")
        mRvAdapter?.notifyDataSetChanged()

    }


    override fun onVisible() {
        super.onVisible()
        onRefreshData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRefreshLayout?.isRefreshing=false
    }

    override fun onDestroy() {
        super.onDestroy()
        getVideoPresenter?.destory()
    }

    override fun showLoading() {
        if (isRefresh) {
            mRefreshLayout?.isRefreshing = true
        }
    }

    override fun hideLoading() {
        if (isRefresh) {
            mRefreshLayout?.isRefreshing = false
        }

        mRvAdapter?.loadMoreFail()
    }

    override fun showMsg(msg: String) {
        if (isRefresh) {
            mRefreshLayout?.setRefreshing(false)
        } else {
            mRvAdapter?.loadMoreComplete()
        }
        showToast(msg)
    }

}