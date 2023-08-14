package com.worldpeak.chnsmilead.oa.activity

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityStudentStatsBinding
import com.worldpeak.chnsmilead.oa.model.StudentCount
import com.worldpeak.chnsmilead.oa.viewmodel.StudentStatsViewModel
import com.worldpeak.chnsmilead.util.*
import kotlin.math.roundToInt


/**
 * 学生统计
 */
class StudentStatsActivity :
    BaseVmVBActivity<StudentStatsViewModel, ActivityStudentStatsBinding>() {

    companion object {
        private const val TAG = "StudentStatsActivity"
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
            initChart(it.studentCount ?: StudentCount(), StudentChartUiParams())
        })
    }

    private fun initChart(studentCounts: StudentCount, uiParams: StudentChartUiParams) {
        val pieEntities = mutableListOf<PieEntry>()
        pieEntities.add(PieEntry(studentCounts.studentManTotal?.toFloat() ?: 0F, "男生"))
        pieEntities.add(PieEntry(studentCounts.studentWomanTotal?.toFloat() ?: 0F, "女生"))
        val set = PieDataSet(pieEntities, "学生")
        set.colors = listOf(uiParams.manColor, uiParams.womanColor)
        set.setValueTextColors(listOf(uiParams.valueTextColor))
        set.valueTextSize = uiParams.valueTextSize
        set.valueFormatter = object : PercentFormatter(mBinding.pieChart) {
            override fun getFormattedValue(value: Float): String {
                return "${value.roundToInt()}%"
            }
        }
        val data = PieData(set)
        mBinding.pieChart.apply {
            setData(data)
            renderer = StudentPieChartRender(uiParams, this, animator, viewPortHandler)
            description.isEnabled = false
            isRotationEnabled = false
            isHighlightPerTapEnabled = false
            legend.isEnabled = false
            setUsePercentValues(true)
            setTransparentCircleAlpha(0)
            holeRadius = uiParams.hollowCircleRadius
            setDrawEntryLabels(false)
            setDrawCenterText(false)
            postInvalidate()
        }
    }
}