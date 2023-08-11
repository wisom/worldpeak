package com.worldpeak.chnsmilead.oa.activity.file

import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.constant.Constants
import com.worldpeak.chnsmilead.databinding.ActivityFileDeliveryListBinding
import com.worldpeak.chnsmilead.oa.adapter.file.FileDeliveryListViewBinder
import com.worldpeak.chnsmilead.oa.adapter.notice.NoticeDeliveryListViewBinder
import com.worldpeak.chnsmilead.oa.model.FileDeliveryItem
import com.worldpeak.chnsmilead.oa.viewmodel.file.FileDeliveryListViewModel
import com.worldpeak.chnsmilead.util.StatusBarUtil

/**
 * 文件下发列表
 */
class FileDeliveryListActivity : BaseVmVBActivity<FileDeliveryListViewModel, ActivityFileDeliveryListBinding>() {

    private var mCurPage: Int = 1
    private val mDataList: MutableList<FileDeliveryItem> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(FileDeliveryItem::class.java, FileDeliveryListViewBinder(this@FileDeliveryListActivity) {
            val intent = Intent(this@FileDeliveryListActivity, FileDeliveryDetailActivity::class.java)
            intent.putExtra(FileDeliveryDetailActivity.EXTRA_INFO, it)
            startActivity(intent)
        })
        items = mDataList
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_file_delivery_list
    }

    override fun initView() {
        super.initView()
        val menu = LayoutInflater.from(this).inflate(R.layout.layout_add, null, false)
        menu.setOnClickListener {
            startActivity(Intent(this@FileDeliveryListActivity, FileDeliveryAddActivity::class.java))
        }
        mBinding.titleBar.addRightView(menu)
        mBinding.rlClear.setOnClickListener {
            mBinding.etSearch.setText("")
        }
        mBinding.tvSearch.setOnClickListener {

        }
        mBinding.refreshLayout.setOnRefreshListener {
            loadNet(false)
        }
        mBinding.refreshLayout.setOnLoadMoreListener {
            loadNet(true)
        }
        mBinding.rvCommon.layoutManager = LinearLayoutManager(this)
        mBinding.rvCommon.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
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
        // @param paramType 1:我发起的，2：全部，3：全部已发起的
        // @param grade 重要程度（1普通、2重要、3紧急）
        mViewModel.getList("2", "", mCurPage)
    }
}