package com.worldpeak.chnsmilead.oa.activity

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityStudentStatsBinding
import com.worldpeak.chnsmilead.oa.viewmodel.StudentStatsViewModel
import com.worldpeak.chnsmilead.util.*


/**
 * 学生统计
 */
class StudentStatsActivity : BaseVmVBActivity<StudentStatsViewModel, ActivityStudentStatsBinding>() {

    companion object {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_student_stats
    }

    override fun initView() {
        super.initView()
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    @SuppressLint("SetTextI18n")
    override fun loadData() {
        mViewModel.getStudentInfo()
        mViewModel.result.observe(this, Observer {
            mBinding.tvClassCount.text = it.classCount
            mBinding.tvGradeCount.text = (it.gradeCount ?: 0).toString()
            mBinding.totalCount.text = (it.studentCount?.studentTotal ?: 0).toString()
            mBinding.manCount.text = (it.studentCount?.studentManTotal ?: 0).toString()
            mBinding.womanCount.text = (it.studentCount?.studentWomanTotal ?: 0).toString()
        })
    }

}