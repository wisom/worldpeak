package com.worldpeak.chnsmilead.home.activity

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityComplaintAdviceDetailBinding
import com.worldpeak.chnsmilead.home.adapter.ComplaintAdviceDetailReplyViewBinder
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceItem
import com.worldpeak.chnsmilead.home.model.ComplaintProposeReplyResult
import com.worldpeak.chnsmilead.home.viewmodel.ComplaintAdviceDetailViewModel
import com.worldpeak.chnsmilead.util.StatusBarUtil

/**
 * 投诉建议详情
 */
class ComplaintAdviceDetailActivity : BaseVmVBActivity<ComplaintAdviceDetailViewModel, ActivityComplaintAdviceDetailBinding>() {

    private val mItem by lazy {
        intent.getParcelableExtra<ComplaintAdviceItem>(EXTRA_ITEM)
    }
    private val mReplyList: MutableList<ComplaintProposeReplyResult> = mutableListOf()
    private val mReplyAdapter = MultiTypeAdapter().apply {
        register(ComplaintProposeReplyResult::class.java, ComplaintAdviceDetailReplyViewBinder(this@ComplaintAdviceDetailActivity) {
//            val intent = Intent(this@ComplaintAdviceDetailActivity, ComplaintAdviceDetailActivity::class.java)
//            intent.putExtra(EXTRA_ITEM, it)
//            startActivity(intent)
        })
        items = mReplyList
    }

    companion object {
        const val EXTRA_ITEM = "EXTRA_ITEM"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_complaint_advice_detail
    }

    override fun initView() {
        super.initView()
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun loadData() {
        mViewModel.getComplaintAdviceDetail(mItem?.id)
        mViewModel.response.observe(this) {
            mBinding.tvSubTitle.text = it.title
            mBinding.tvContent.text = it.content
            mBinding.tvGroup.text = it.targetSchoolName
            if (!it.complaintProposeReplyResultList.isNullOrEmpty()) {
                mReplyList.clear()
                mReplyList.addAll(it.complaintProposeReplyResultList)
                mBinding.rvComplaintAdviceDetail.layoutManager = LinearLayoutManager(this)
                mBinding.rvComplaintAdviceDetail.adapter = mReplyAdapter
            }
        }
    }
}