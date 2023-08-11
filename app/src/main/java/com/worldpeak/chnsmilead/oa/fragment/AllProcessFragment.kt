package com.worldpeak.chnsmilead.oa.fragment

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.constant.Constants
import com.worldpeak.chnsmilead.databinding.FragmentAllProcessBinding
import com.worldpeak.chnsmilead.oa.activity.DocumentApprovalDetailActivity
import com.worldpeak.chnsmilead.oa.adapter.AllProcessListViewBinder
import com.worldpeak.chnsmilead.oa.model.AllProcessItem
import com.worldpeak.chnsmilead.oa.viewmodel.AllProcessListViewModel

/**
 * 全部流程Fragment
 * listType:2：我的审批，3：我的通知
 * queryStatus:审批人状态（1待批/待读、2已批/已读(包含拒批)）,传空时为查所有
 */
class AllProcessFragment(val listType:String,val queryStatus:String) : BaseVmVBFragment<AllProcessListViewModel, FragmentAllProcessBinding>() {

    private var mCurPage: Int = 1
    private val mDataList: MutableList<AllProcessItem> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(AllProcessItem::class.java, AllProcessListViewBinder(context) {
            val intent = Intent(activity, DocumentApprovalDetailActivity::class.java)
            intent.putExtra(DocumentApprovalDetailActivity.EXTRA_INFO, it)
            startActivity(intent)
        })
        items = mDataList
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_all_process
    }

    override fun initView() {
        super.initView()
        mBinding.refreshLayout.setOnRefreshListener {
            loadNet(false)
        }
        mBinding.refreshLayout.setOnLoadMoreListener {
            loadNet(true)
        }
        mBinding.rvCommon.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mBinding.rvCommon.adapter = mAdapter
    }

    override fun loadData() {
        loadNet(false)
        mViewModel.dataList.observe(this, Observer {
            mBinding.refreshLayout.finishRefresh()
            mBinding.refreshLayout.finishLoadMore()
            if (mCurPage == 1) {
                mDataList.clear()
            }
            if (it.size < Constants.DEFAULT_LOAD_PAGESIZE) {
                mBinding.refreshLayout.setNoMoreData(true)
            }
            mDataList.addAll(it)
            mAdapter.notifyDataSetChanged()
        })
    }

    private fun loadNet(isLoadMore: Boolean) {
        if (isLoadMore) {
            ++mCurPage
        } else {
            mCurPage = 1
        }
        mViewModel.getList(listType,queryStatus, mCurPage)
    }
}