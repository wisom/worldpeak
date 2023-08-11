package com.worldpeak.chnsmilead.oa.activity.notice

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import com.blankj.utilcode.util.ToastUtils
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityNoticeDeliveryAddBinding
import com.worldpeak.chnsmilead.mine.dialog.ListSelectConfirmDialog
import com.worldpeak.chnsmilead.oa.activity.contact.OaContactActivity
import com.worldpeak.chnsmilead.oa.activity.contact.OaContactOrgUnitUserActivity
import com.worldpeak.chnsmilead.oa.dialog.DocumentApprovalDialog
import com.worldpeak.chnsmilead.oa.model.WayInfo
import com.worldpeak.chnsmilead.oa.viewmodel.notice.NoticeDeliveryAddViewModel
import com.worldpeak.chnsmilead.util.*

/**
 * 新建通知下发
 */
class NoticeDeliveryAddActivity : BaseVmVBActivity<NoticeDeliveryAddViewModel, ActivityNoticeDeliveryAddBinding>() {

    companion object {
    }

    private var mNoticeList: MutableList<String> = mutableListOf()//通知的userId
    private var mGrade = 1//（1普通、2重要、3紧急）

    private var currWay: WayInfo? = null
    private val wayList = mutableListOf<WayInfo>()
    private var wayDialog: ListSelectConfirmDialog? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_notice_delivery_add
    }

    override fun initView() {
        super.initView()
        mBinding.tvNormal.isActivated = true
        mBinding.tvNormal.setPreventDoubleClickListener {
            mBinding.tvNormal.isActivated = true
            mBinding.tvImportance.isActivated = false
            mBinding.tvUrgency.isActivated = false
            mGrade = 1
        }
        mBinding.tvImportance.setPreventDoubleClickListener {
            mBinding.tvImportance.isActivated = true
            mBinding.tvNormal.isActivated = false
            mBinding.tvUrgency.isActivated = false
            mGrade = 2
        }
        mBinding.tvUrgency.setPreventDoubleClickListener {
            mBinding.tvUrgency.isActivated = true
            mBinding.tvImportance.isActivated = false
            mBinding.tvNormal.isActivated = false
            mGrade = 3
        }

        wayList.apply {
            add(WayInfo(1, "仅阅读"))
            add(WayInfo(2, "需回复"))
        }
        mBinding.llSelectWay.setPreventDoubleClickListener {
            val curIndex = wayList.indexOfFirst { it.content == mBinding.tvWay.text.toString() }
            val list = wayList.filter {
                !it.content.isNullOrEmpty()
            }.map {
                it.content ?: ""
            }
            wayDialog = ListSelectConfirmDialog(this@NoticeDeliveryAddActivity, list, if (curIndex == -1) 0 else curIndex, false) { index ->
                currWay = wayList.getOrNull(index)
                mBinding.tvWay.text = currWay?.content ?: ""
            }
            wayDialog?.show()
        }
        mBinding.llSelectPerson.setPreventDoubleClickListener {
            OaContactActivity.startActivity(this, true, "通知详情")
        }
        mBinding.tvSend.setPreventDoubleClickListener {
            startSumbit(1)
        }

        initMenu()
    }

    /**
     * @param status 状态（0：未发送、1：已发送，2：已撤回）
     */
    private fun startSumbit(status: Int) {
        val name = mBinding.etName.text.toString()
        val desc = mBinding.etDetail.text.toString()
        val remark = mBinding.etRemark.text.toString()
        if (name.isNullOrEmpty()) {
            ToastUtils.showShort("名称不可为空")
            return
        }
        if (desc.isNullOrEmpty()) {
            ToastUtils.showShort("通知详情不可为空")
            return
        }
        if (remark.isNullOrEmpty()) {
            ToastUtils.showShort("备注不可为空")
            return
        }
        if (currWay == null) {
            ToastUtils.showShort("确认方式不可为空")
            return
        }
        if (name.isNullOrEmpty()) {
            ToastUtils.showShort("名称不可为空")
            return
        }
        /**
         * 添加信息下发（发出）
         * @param title
         * @param content
         * @param grade 重要程度（1普通、2重要、3紧急）
         * @param process 批阅要求（1仅阅读、2需回复）
         * @param remark
         * @param id
         * @param formId
         * @param status 状态（0：未发送、1：已发送，2：已撤回）
         * @param infoDownNoticeList 通知学校用户userId集合
         * @param infoAccessoryList 附件集合
         * @param retrofitListener
         */
        mViewModel.addNoticeDelivery(
                name,
                desc,
                mGrade,
                1,
                remark,
                -1L,
                "",
                status,
                mNoticeList,
                null
        )
    }

    private fun initMenu() {
        val menu = LayoutInflater.from(this).inflate(R.layout.layout_draft, null, false)
        menu.setOnClickListener {
            startSumbit(0)
        }
        mBinding.titleBar.addRightView(menu)
        mBinding.titleBar.setBackClick {
            SelectPersonUtil.clearList()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    @SuppressLint("SetTextI18n")
    override fun loadData() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OaContactOrgUnitUserActivity.EXTRA_REQ_CODE && resultCode == RESULT_OK) {
            mNoticeList.clear()
            SelectPersonUtil.getUserList().map { it.id }.let { list ->
                list.forEach { id ->
                    if (id != null) {
                        mNoticeList.add(id)
                    }
                }
            }
            if (SelectPersonUtil.getUserList().isNullOrEmpty()) {
                mBinding.tvNoticeName.text = "选择发送人"
            } else {
                mBinding.tvNoticeName.text = SelectPersonUtil.getUserList().map { it.name }.joinToString("、")
            }
        }
    }
}