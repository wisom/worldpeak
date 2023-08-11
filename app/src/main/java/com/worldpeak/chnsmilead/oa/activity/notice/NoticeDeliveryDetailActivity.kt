package com.worldpeak.chnsmilead.oa.activity.notice

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityNoticeDeliveryDetailBinding
import com.worldpeak.chnsmilead.home.dialog.CommonConfirmDialog
import com.worldpeak.chnsmilead.home.model.CommonAccessory
import com.worldpeak.chnsmilead.mine.dialog.ListSelectDialog
import com.worldpeak.chnsmilead.mine.model.ListItemInfo
import com.worldpeak.chnsmilead.oa.adapter.DeliveryDetailFileLongViewBinder
import com.worldpeak.chnsmilead.oa.adapter.DeliverySchoolViewBinder
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryDetailResponse
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryItem
import com.worldpeak.chnsmilead.oa.model.SchoolNotifyInfo
import com.worldpeak.chnsmilead.oa.viewmodel.notice.NoticeDeliveryDetailViewModel
import com.worldpeak.chnsmilead.util.*

/**
 * 通知下发详情
 */
class NoticeDeliveryDetailActivity : BaseVmVBActivity<NoticeDeliveryDetailViewModel, ActivityNoticeDeliveryDetailBinding>() {

    private val mFileList: MutableList<CommonAccessory> = mutableListOf()
    private val mFileAdapter = MultiTypeAdapter().apply {
        register(CommonAccessory::class.java, DeliveryDetailFileLongViewBinder(this@NoticeDeliveryDetailActivity) {
            WebUtil.openBrowser(it.fileUrl)
        })
        items = mFileList
    }

    private val mDataList: MutableList<SchoolNotifyInfo> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(SchoolNotifyInfo::class.java, DeliverySchoolViewBinder(this@NoticeDeliveryDetailActivity) {
            val intent = Intent(this@NoticeDeliveryDetailActivity, NoticeDeliveryReplyListActivity::class.java)
            intent.putExtra(NoticeDeliveryReplyListActivity.EXTRA_FORMID, mInfo?.formId)
            startActivity(intent)
        })
        items = mDataList
    }

    private val mInfo by lazy {
        intent.getParcelableExtra(EXTRA_INFO) as? NoticeDeliveryItem
    }

    companion object {
        const val EXTRA_INFO = "EXTRA_INFO"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_notice_delivery_detail
    }

    override fun initView() {
        super.initView()
        //（0：未发送、1：已发送、3：已删除）
        when (mInfo?.status) {
            0 -> {
                mBinding.titleBar.showRightIcon()
                mBinding.titleBar.setRightIconClick {
                    val list = mutableListOf<ListItemInfo>().apply {
                        add(ListItemInfo(content = "发出", color = "#4EBBF5", click = View.OnClickListener {
                            mViewModel.addNoticeDelivery(
                                    mResponse?.title,
                                    mResponse?.content,
                                    mResponse?.grade?:0,
                                    mResponse?.process?:0,
                                    mResponse?.remark,
                                    (mResponse?.id?:"0").toLong(),
                                    mResponse?.formId,
                                    1,
                                    mResponse?.confirmResult?.detailResultList?.map { it.schoolId },
                                    mResponse?.infoDowmAccessoryList?: emptyList<CommonAccessory>()
                            )
                        }))
                        add(ListItemInfo(content = "删除", click = View.OnClickListener {
                            mViewModel.deleteNoticeDelivery(mutableListOf<String?>().apply { add(mInfo?.formId) })
                        }))
                    }
                    ListSelectDialog(this@NoticeDeliveryDetailActivity, list, true).show()
                }
            }
            1 -> {
                mBinding.titleBar.showRightIcon()
                mBinding.titleBar.setRightIconClick {
                    val list = mutableListOf<ListItemInfo>().apply {
                        add(ListItemInfo(content = "撤回通知", click = View.OnClickListener {
                            mViewModel.rebackNoticeDelivery(mInfo?.formId)
                        }))
                    }
                    ListSelectDialog(this@NoticeDeliveryDetailActivity, list, true).show()
                }
            }
        }
        mBinding.tvRemind.setPreventDoubleClickListener {
            CommonConfirmDialog(this@NoticeDeliveryDetailActivity, "将再次发送此通知，提醒未确认老师", "取消", "发送", {}, {
                mViewModel.remindNotConfirm(mResponse?.formId)
            }).show()
        }
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    private var mResponse: NoticeDeliveryDetailResponse? = null

    @SuppressLint("SetTextI18n")
    override fun loadData() {
        mViewModel.getDetail(mInfo?.formId)
        mViewModel.result.observe(this, Observer { response ->
            mResponse = response
            //0：未发送、1：已发送、2:已撤销、3：已删除）
            when (response.status) {
                0 -> {
                    mBinding.ivStatus.show()
                    mBinding.ivStatus.setImageResource(R.drawable.ic_draft_nolaunch)
                }
                2 -> {
                    mBinding.ivStatus.show()
                    mBinding.ivStatus.setImageResource(R.drawable.ic_reback)
                }
            }
            mBinding.tvName.text = "${response.createName}发送："
            mBinding.tvClass.text = response.confirmResult?.detailResultList?.map { it.schoolName }?.joinToString("、")
            mBinding.tvTag.text = getTagText(response.grade)
            mBinding.tvTag.setBackgroundResource(getTagBg(response.grade))
            mBinding.tvSubTitle.text = response.title
            mBinding.tvTime.text = "发布时间：" + response.createTime
            mBinding.tvContent.text = response.content
            if (!response.remark.isNullOrEmpty()) {
                mBinding.clRemark.show()
                mBinding.tvRemark.text = response.remark
            }
            if (response.infoDowmAccessoryList.isNullOrEmpty()) {
                mBinding.rvAttach.hide()
                mBinding.tvLookAll.hide()
            } else {
                mBinding.rvAttach.show()
                mBinding.tvLookAll.show()
                mFileList.clear()
                response.infoDowmAccessoryList.getOrNull(0)?.let { it1 -> mFileList.add(it1) }
                mBinding.rvAttach.layoutManager = LinearLayoutManager(this)
                mBinding.rvAttach.adapter = mFileAdapter
                mBinding.tvLookAll.setPreventDoubleClickListener {
                    mBinding.tvLookAll.hide()
                    mFileList.clear()
                    mFileList.addAll(response.infoDowmAccessoryList)
                    mFileAdapter.notifyDataSetChanged()
                }
            }
            mBinding.tvUnLook.text = (response.confirmResult?.notConfirmCount
                    ?: 0).toString() + "名老师未查看"
            mBinding.tvRate.text = if (response.confirmResult?.noticeCount.isNullOrEmpty() || response.confirmResult?.noticeCount.equals("0")) {
                "查看率  0%"
            } else {
                "查看率  ${100f - (response.confirmResult?.notConfirmCount ?: 0).toFloat() / (response.confirmResult?.noticeCount ?: "0").toFloat()}%"
            }

            if (!response.confirmResult?.detailResultList.isNullOrEmpty()) {
                mDataList.clear()
                response.confirmResult?.detailResultList?.let { it1 -> mDataList.addAll(it1) }
                mBinding.rvGroup.layoutManager = LinearLayoutManager(this)
                mBinding.rvGroup.adapter = mAdapter
            }
        })
    }

    /**
     * grade:重要程度（1普通、2重要、3紧急）
     */
    private fun getTagText(grade: Int?): String {
        return when (grade) {
            2 -> {
                "重要"
            }
            3 -> {
                "紧急"
            }
            else -> {
                "普通"
            }
        }
    }

    /**
     * grade:重要程度（1普通、2重要、3紧急）
     */
    private fun getTagBg(grade: Int?): Int {
        return when (grade) {
            2 -> {
                R.drawable.shape_bg_orange_radius_8dp_lt_other
            }
            3 -> {
                R.drawable.shape_bg_red_radius_8dp_lt_other
            }
            else -> {
                R.drawable.shape_bg_blue_radius_8dp_lt_other
            }
        }
    }
}