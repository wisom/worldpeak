package com.worldpeak.chnsmilead.oa.activity

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityTeacherStatsBinding
import com.worldpeak.chnsmilead.oa.viewmodel.TeacherStatsViewModel
import com.worldpeak.chnsmilead.util.*


/**
 * 教师统计
 */
class TeacherStatsActivity : BaseVmVBActivity<TeacherStatsViewModel, ActivityTeacherStatsBinding>() {

    companion object {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_teacher_stats
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
        mViewModel.getTeacherInfo()
        mViewModel.result.observe(this, Observer {
            mBinding.tvWomanCount.text = (it.teacherSexCount?.firstOrNull { it.sexName == "女" }?.sexCount
                    ?: 0).toString()
            mBinding.tvManCount.text = (it.teacherSexCount?.firstOrNull { it.sexName == "男" }?.sexCount
                    ?: 0).toString()


            mBinding.tvDoctorCount.text = (it.teacherEducationCount?.firstOrNull { it.educationName == "博士" }?.educationCount
                    ?: 0).toString()
            mBinding.tvMasterCount.text = (it.teacherEducationCount?.firstOrNull { it.educationName == "硕士" }?.educationCount
                    ?: 0).toString()
            mBinding.tvUndergradCount.text = (it.teacherEducationCount?.firstOrNull { it.educationName == "本科" }?.educationCount
                    ?: 0).toString()
            mBinding.tvJuniorCount.text = (it.teacherEducationCount?.firstOrNull { it.educationName == "专科" }?.educationCount
                    ?: 0).toString()
            mBinding.tvOtherCount.text = (it.teacherEducationCount?.firstOrNull { it.educationName == "其他" }?.educationCount
                    ?: 0).toString()


            mBinding.tvSpecialTeacherCount.text = (it.regularSenior ?: 0).toString()
            mBinding.tvHighTeacherCount.text = (it.senior ?: 0).toString()
            mBinding.tvOneTeacherCount.text = (it.oneLevel ?: 0).toString()
            mBinding.tvTwoTeacherCount.text = (it.twoLevel ?: 0).toString()
            mBinding.tvThirdTeacherCount.text = (it.threeLevel ?: 0).toString()
        })
    }

}