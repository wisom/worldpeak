package com.worldpeak.chnsmilead.oa.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.constant.Constants
import com.worldpeak.chnsmilead.databinding.FragmentNoticeNoReplyBinding
import com.worldpeak.chnsmilead.home.dialog.CommonConfirmDialog
import com.worldpeak.chnsmilead.oa.adapter.notice.NoticeHasReplyViewBinder
import com.worldpeak.chnsmilead.oa.event.NoticeHasReplyCountEvent
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryReplyItem
import com.worldpeak.chnsmilead.oa.viewmodel.notice.NoticeNoReplyListViewModel
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener
import org.greenrobot.eventbus.EventBus

/**
 * 通知下发未回复Fragment
 */
class NoticeNoReplyFragment(val formId: String? = "") : BaseVmVBFragment<NoticeNoReplyListViewModel, FragmentNoticeNoReplyBinding>() {

    private var mCurPage: Int = 1
    private val mDataList: MutableList<NoticeDeliveryReplyItem> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(NoticeDeliveryReplyItem::class.java, NoticeHasReplyViewBinder(context) {
        })
        items = mDataList
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_notice_no_reply
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

        mBinding.tvRemind.setPreventDoubleClickListener {
            context?.let { it1 ->
                CommonConfirmDialog(it1, "将再次发送此通知，提醒未确认老师", "取消", "发送", {}, {
                    mDataList.filter { it.status != 3 }.forEach {
                        mViewModel.remindNoticeNotConfirm(it.orgId)
                    }
                }).show()
            }
        }
    }

    override fun loadData() {
        loadNet(false)
        mViewModel.dataList.observe(this, Observer {
            EventBus.getDefault().post(NoticeHasReplyCountEvent(it.size))
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
        mViewModel.getReplyList(formId,3)
    }
}