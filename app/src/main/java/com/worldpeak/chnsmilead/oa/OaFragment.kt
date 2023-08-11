package com.worldpeak.chnsmilead.oa

import android.content.Intent
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.databinding.FragmentOaBinding
import com.worldpeak.chnsmilead.oa.activity.*
import com.worldpeak.chnsmilead.oa.activity.file.FileDeliveryListActivity
import com.worldpeak.chnsmilead.oa.activity.notice.NoticeDeliveryListActivity
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

class OaFragment : BaseVmVBFragment<BaseViewModel, FragmentOaBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_oa
    }

    override fun initView() {
        super.initView()
        mBinding.clUpgrade.clUpgradeNotify.setPreventDoubleClickListener {
            startActivity(Intent(activity, NoticeDeliveryListActivity::class.java))
        }
        mBinding.clUpgrade.clUpgradeFile.setPreventDoubleClickListener {
            startActivity(Intent(activity, FileDeliveryListActivity::class.java))
        }
        mBinding.clDocument.clDocumentApprove.setPreventDoubleClickListener {
            activity?.let { it1 -> DocumentApprovalListActivity.startActivity(it1,0) }
        }
        mBinding.clArea.clAreaTeacher.setPreventDoubleClickListener {
            startActivity(Intent(activity, TeacherStatsActivity::class.java))
        }
        mBinding.clArea.clAreaStudent.setPreventDoubleClickListener {
            startActivity(Intent(activity, StudentStatsActivity::class.java))
        }
        mBinding.clArea.clAreaAbsence.setPreventDoubleClickListener {
            startActivity(Intent(activity, AbsenceStatsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(activity)
    }

    override fun loadData() {
    }

}