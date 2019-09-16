package com.example.hcc.weatherapp.ui.fragment

import android.view.View
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import com.example.hcc.weatherapp.R
import com.example.hcc.weatherapp.mvp.contract.GetLoginContract
import com.example.hcc.weatherapp.mvp.presenter.GetLoginPresenter
import com.stx.xhb.core.base.BaseFragment
import com.stx.xhb.core.utils.LoggerHelper

/**
 * Created by hecuncun on 2019/8/30
 */
class ZhiHuFragment : BaseFragment(),GetLoginContract.View {
    private var mLoginPresenter:GetLoginPresenter?=null

    private var mRvContact: ExpandableListView? = null

    private var groups = ArrayList<Map<String, String>>()

    private var childs = ArrayList<List<Map<String, String>>>()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_zhi_hu
    }

    override fun initView() {
        mRvContact = getView(R.id.qq_list)

    }

    override fun initData() {
        mLoginPresenter = GetLoginPresenter(this)
        mLoginPresenter?.getLoginToken("lisi","admin")


        //新建两个1级条目
        val title_1 = HashMap<String, String>()
        val title_2 = HashMap<String, String>()
        val title_3 = HashMap<String, String>()

        title_1["group"] = "A组"
        title_2["group"] = "B组"
        title_3["group"] = "C组"
        groups.add(title_1)
        groups.add(title_2)
        groups.add(title_3)
        //创建二级条目1
        val title_1_content_1 = HashMap<String, String>()
        val title_1_content_2 = HashMap<String, String>()
        val title_1_content_3 = HashMap<String, String>()
        title_1_content_1["child"] = "工人"
        title_1_content_2["child"] = "学生"
        title_1_content_3["child"] = "老师"

        val childs_1 = ArrayList<Map<String, String>>()

        childs_1.add(title_1_content_1)
        childs_1.add(title_1_content_2)
        childs_1.add(title_1_content_3)

        // 内容二
        val title_2_content_1 = HashMap<String, String>()
        val title_2_content_2 = HashMap<String, String>()
        val title_2_content_3 = HashMap<String, String>()

        title_2_content_1["child"] = "猩猩"
        title_2_content_2["child"] = "老虎"
        title_2_content_3["child"] = "狮子"

        val childs_2 = ArrayList<Map<String, String>>()

        childs_2.add(title_2_content_1)
        childs_2.add(title_2_content_2)
        childs_2.add(title_2_content_3)

        // 内容3
        val title_3_content_1 = HashMap<String, String>()
        val title_3_content_2 = HashMap<String, String>()
        val title_3_content_3 = HashMap<String, String>()
        val title_3_content_4 = HashMap<String, String>()
        val title_3_content_5 = HashMap<String, String>()
        val title_3_content_6 = HashMap<String, String>()
        val childs_3 = ArrayList<Map<String,String>>()
        title_3_content_1["child"] = "桃子"
        title_3_content_2["child"] = "李子"
        title_3_content_3["child"] = "西瓜子"
        title_3_content_4["child"] = "火龙玫瑰"
        title_3_content_5["child"] = "猕猴桃"
        title_3_content_6["child"] = "香蕉"
        childs_3.add(title_3_content_1)
        childs_3.add(title_3_content_2)
        childs_3.add(title_3_content_3)
        childs_3.add(title_3_content_4)
        childs_3.add(title_3_content_5)
        childs_3.add(title_3_content_6)

        childs.add(childs_1)
        childs.add(childs_2)
        childs.add(childs_3)

        setAdapter()


    }

    private fun setAdapter() {
        // 1.上下文
        // 2.一级集合
        // 3.一级样式文件
        // 4. 一级条目键值
        // 5.一级显示控件名
        // 6. 二级集合
        // 7. 二级样式
        // 8.二级条目键值
        // 9.二级显示控件名
        val simpleExpandableListAdapter = SimpleExpandableListAdapter(mContext, groups, R.layout.text_group, arrayOf("group"), intArrayOf(R.id.text_group),
                childs, R.layout.childs, arrayOf("child"), intArrayOf(R.id.text_child))

        mRvContact?.setAdapter(simpleExpandableListAdapter)
        mRvContact?.setOnChildClickListener(object :ExpandableListView.OnChildClickListener{
            override fun onChildClick(p0: ExpandableListView?, p1: View?, groupPos: Int, childPos: Int, p4: Long): Boolean {
                showToast("groupPos = $groupPos,childPos=$childPos")
                return true
            }

        })


    }

    override fun setListener() {
    }

    override fun onResponse(token: String) {
        LoggerHelper.d("token==$token")
        showToast("Token=$token")
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun onDestroy() {
        mLoginPresenter?.detachView()
        super.onDestroy()
    }
}