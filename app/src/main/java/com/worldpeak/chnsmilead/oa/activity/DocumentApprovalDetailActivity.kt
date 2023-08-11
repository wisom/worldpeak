package com.worldpeak.chnsmilead.oa.activity

import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityDocumentApprovalDetailBinding
import com.worldpeak.chnsmilead.databinding.ActivityDocumentApprovalListBinding
import com.worldpeak.chnsmilead.home.adapter.ComplaintAdviceListViewBinder
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceItem
import com.worldpeak.chnsmilead.oa.adapter.DocumentApprovalDetailViewBinder
import com.worldpeak.chnsmilead.oa.dialog.DocumentApprovalDialog
import com.worldpeak.chnsmilead.oa.model.AllProcessItem
import com.worldpeak.chnsmilead.oa.viewmodel.DocumentApprovalDetailViewModel
import com.worldpeak.chnsmilead.oa.viewmodel.DocumentApproveInfo
import com.worldpeak.chnsmilead.util.StatusBarUtil

/**
 * 公文审批详情
 */
class DocumentApprovalDetailActivity : BaseVmVBActivity<DocumentApprovalDetailViewModel, ActivityDocumentApprovalDetailBinding>() {

    private val mDataList: MutableList<DocumentApproveInfo> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(DocumentApproveInfo::class.java, DocumentApprovalDetailViewBinder(this@DocumentApprovalDetailActivity))
        items = mDataList
    }

    private val mInfo by lazy {
        intent.getSerializableExtra(EXTRA_INFO) as? AllProcessItem
    }

    companion object {
        const val EXTRA_INFO = "EXTRA_INFO"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_document_approval_detail
    }

    override fun initView() {
        super.initView()
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun loadData() {
        mViewModel.getDetail(mInfo?.formId)
        mViewModel.result.observe(this, Observer {
            //0未发出、1批阅中、2已备案、3已拒绝）
            if (it?.status == 1) {
                initMenu()
            }
            mBinding.ivStatus.setImageResource(getStatusRes(it?.status))
            mBinding.tvStatus.text = getStatusText(it?.status)
            mBinding.tvSubTitle.text = it?.title
            mBinding.tvFileNo.text = "文件编号：" + it?.docId
            mBinding.tvForm.text = "表单编号：" + it?.formId
            mBinding.tvTime.text = "申请时间：" + it?.dDate
            mBinding.tvPerson.text = "发起人：" + it?.cname
            mBinding.tvContent.text = it?.content
            mBinding.tvRemark.text = it?.remark
            mBinding.rvProcess.layoutManager = LinearLayoutManager(this@DocumentApprovalDetailActivity, RecyclerView.VERTICAL, false)
            mDataList.clear()
            it?.documentApproveInfoList?.let { it1 -> mDataList.addAll(it1) }
            mBinding.rvProcess.adapter = mAdapter
            mBinding.rvProcess.isNestedScrollingEnabled = false
        })
    }

    private fun initMenu() {
        //2已批/已读、3拒批
        val menu = LayoutInflater.from(this).inflate(R.layout.layout_approval, null, false)
        menu.setOnClickListener {
            DocumentApprovalDialog(this, leftCallback = {
                mViewModel.approval(mInfo?.formId, it, 3)
            }, rightCallback = {
                mViewModel.approval(mInfo?.formId, it, 2)
            }).show()
        }
        mBinding.titleBar.addRightView(menu)
    }

    /**
     * 0未发出、1批阅中、2已备案、3已拒绝
     */
    private fun getStatusRes(status: Int?): Int {
        return when (status) {
            2 -> {
                R.drawable.ic_approval_pass
            }
            3 -> {
                R.drawable.ic_approval_nopass
            }
            4 -> {
                R.drawable.ic_approval_reback
            }
            else -> {
                R.drawable.ic_approval_ing
            }
        }
    }

    private fun getStatusText(status: Int?): String {
        return when (status) {
            0 -> {
                "未发出"
            }
            2 -> {
                "已备案"
            }
            3 -> {
                "已拒绝"
            }
            else -> {
                "批阅中"
            }
        }
    }
}