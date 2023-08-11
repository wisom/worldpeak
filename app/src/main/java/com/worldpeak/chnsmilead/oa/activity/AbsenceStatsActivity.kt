package com.worldpeak.chnsmilead.oa.activity

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityAbsenceStatsBinding
import com.worldpeak.chnsmilead.oa.adapter.DocumentApprovalDetailViewBinder
import com.worldpeak.chnsmilead.oa.adapter.StudentAbsenceViewBinder
import com.worldpeak.chnsmilead.oa.adapter.TeacherAbsenceViewBinder
import com.worldpeak.chnsmilead.oa.model.StudentLeaveDetail
import com.worldpeak.chnsmilead.oa.model.TeacherLeaveDetail
import com.worldpeak.chnsmilead.oa.viewmodel.AbsenceStatsViewModel
import com.worldpeak.chnsmilead.oa.viewmodel.DocumentApproveInfo
import com.worldpeak.chnsmilead.util.*


/**
 * 缺勤统计
 */
class AbsenceStatsActivity : BaseVmVBActivity<AbsenceStatsViewModel, ActivityAbsenceStatsBinding>() {

    private val mStudentList: MutableList<StudentLeaveDetail> = mutableListOf()
    private val mStudentAdapter = MultiTypeAdapter().apply {
        register(StudentLeaveDetail::class.java, StudentAbsenceViewBinder(this@AbsenceStatsActivity))
        items = mStudentList
    }

    private val mTeacherList: MutableList<TeacherLeaveDetail> = mutableListOf()
    private val mTeacherAdapter = MultiTypeAdapter().apply {
        register(TeacherLeaveDetail::class.java, TeacherAbsenceViewBinder(this@AbsenceStatsActivity))
        items = mTeacherList
    }

    private val TYPE_DAY = 1//日
    private val TYPE_MONTH = 2//月
    private var mCurType = TYPE_DAY
    private lateinit var mCurDay: String

    companion object {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_absence_stats
    }

    override fun initView() {
        super.initView()

        mBinding.tvDay.isActivated = true
        mBinding.tvMonth.isActivated = false
        mBinding.tvDay.setPreventDoubleClickListener {
            mBinding.tvDay.isActivated = true
            mBinding.tvMonth.isActivated = false
            mCurType = TYPE_DAY
            mViewModel.getAbsenceInfo(mCurType, mCurDay)
        }
        mBinding.tvMonth.setPreventDoubleClickListener {
            mBinding.tvDay.isActivated = false
            mBinding.tvMonth.isActivated = true
            mCurType = TYPE_MONTH
            mViewModel.getAbsenceInfo(mCurType, mCurDay)
        }

        mCurDay = DateUtil.getyyMMddHHmmss(System.currentTimeMillis())
        mBinding.tvDate.text = mCurDay
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    @SuppressLint("SetTextI18n")
    override fun loadData() {
        mViewModel.getAbsenceInfo(mCurType, mCurDay)
        mViewModel.result.observe(this, Observer {
            mBinding.tvStudentAbsenceCount.text = (it.studentLeaveCount ?: 0).toString()
            mBinding.tvTeacherAbsenceCount.text = (it.teacherLeaveCount ?: 0).toString()

            mStudentList.clear()
            it.studentLeaveDetail?.let { it1 -> mStudentList.addAll(it1) }
            mBinding.rvStudentAbsence.layoutManager = LinearLayoutManager(this@AbsenceStatsActivity)
            mBinding.rvStudentAbsence.adapter = mStudentAdapter

            mTeacherList.clear()
            it.teacherLeaveDetail?.let { it1 -> mTeacherList.addAll(it1) }
            mBinding.rvTeacherAbsence.layoutManager = LinearLayoutManager(this@AbsenceStatsActivity)
            mBinding.rvTeacherAbsence.adapter = mTeacherAdapter
        })
    }

}