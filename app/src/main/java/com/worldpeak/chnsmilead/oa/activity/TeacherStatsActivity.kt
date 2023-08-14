package com.worldpeak.chnsmilead.oa.activity

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityTeacherStatsBinding
import com.worldpeak.chnsmilead.oa.model.SchoolUserCount
import com.worldpeak.chnsmilead.oa.model.TeacherEducationCount
import com.worldpeak.chnsmilead.oa.viewmodel.TeacherStatsViewModel
import com.worldpeak.chnsmilead.util.*
import java.text.DecimalFormat
import kotlin.math.roundToInt


/**
 * 教师统计
 */
class TeacherStatsActivity :
    BaseVmVBActivity<TeacherStatsViewModel, ActivityTeacherStatsBinding>() {
    companion object {
        private const val TAG = "TeacherStatsActivity"
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
            mBinding.tvWomanCount.text =
                (it.teacherSexCount?.firstOrNull { it.sexName == "女" }?.sexCount
                    ?: 0).toString()
            mBinding.tvManCount.text =
                (it.teacherSexCount?.firstOrNull { it.sexName == "男" }?.sexCount
                    ?: 0).toString()


            mBinding.tvDoctorCount.text =
                (it.teacherEducationCount?.firstOrNull { it.educationName == "博士" }?.educationCount
                    ?: 0).toString()
            mBinding.tvMasterCount.text =
                (it.teacherEducationCount?.firstOrNull { it.educationName == "硕士" }?.educationCount
                    ?: 0).toString()
            mBinding.tvUndergradCount.text =
                (it.teacherEducationCount?.firstOrNull { it.educationName == "本科" }?.educationCount
                    ?: 0).toString()
            mBinding.tvJuniorCount.text =
                (it.teacherEducationCount?.firstOrNull { it.educationName == "专科" }?.educationCount
                    ?: 0).toString()
            mBinding.tvOtherCount.text =
                (it.teacherEducationCount?.firstOrNull { it.educationName == "其他" }?.educationCount
                    ?: 0).toString()


            mBinding.tvSpecialTeacherCount.text = (it.regularSenior ?: 0).toString()
            mBinding.tvHighTeacherCount.text = (it.senior ?: 0).toString()
            mBinding.tvOneTeacherCount.text = (it.oneLevel ?: 0).toString()
            mBinding.tvTwoTeacherCount.text = (it.twoLevel ?: 0).toString()
            mBinding.tvThirdTeacherCount.text = (it.threeLevel ?: 0).toString()

            it.teacherEducationCount?.let { teachers ->
                initEducationCountChart(teachers, TeacherEducationCountChartUiParams())
            }
            it.schoolUserCount?.let { teachers ->
                initSchoolCountChart(teachers, TeacherSchoolCountChartUiParams())
            }
        })
    }

    private fun initEducationCountChart(
        teacherList: List<TeacherEducationCount>,
        uiParams: TeacherEducationCountChartUiParams
    ) {
        val pieEntities = mutableListOf<PieEntry>()
        val colors = mutableListOf<Int>()
        teacherList.forEach { teacher ->
            val entity =
                PieEntry(teacher.educationCount?.toFloat() ?: 0F, teacher.educationName ?: "其他")
            pieEntities.add(entity)
            colors.add(uiParams.getEducationColor(teacher.educationName))
        }
        val set = PieDataSet(pieEntities, "老师")
        set.colors = colors
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
            description.isEnabled = false
            isRotationEnabled = false
            isHighlightPerTapEnabled = false
            legend.isEnabled = false
            setUsePercentValues(true)
            setTransparentCircleAlpha(0)
            holeRadius = uiParams.hollowCircleRadius
            setDrawEntryLabels(false)
            centerText = teacherList.sumOf { it.educationCount ?: 0 }.let {
                val span = SpannableString(
                    "总人数\n${DecimalFormat("#,###").format(it)}"
                )
                span.setSpan(
                    AbsoluteSizeSpan(uiParams.centerTextTitleSizeDp, true),
                    0,
                    3,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                span.setSpan(
                    ForegroundColorSpan(uiParams.centerTextTitleColor),
                    0,
                    3,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                span.setSpan(
                    AbsoluteSizeSpan(uiParams.centerTextContentSizeDp, true),
                    3,
                    span.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                span.setSpan(
                    ForegroundColorSpan(uiParams.centerTextContentColor), 3,
                    span.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                span.setSpan(
                    StyleSpan(Typeface.BOLD), 3,
                    span.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                span
            }
            postInvalidate()
        }
    }

    private fun initSchoolCountChart(
        teacherList: List<SchoolUserCount>,
        uiParams: TeacherSchoolCountChartUiParams
    ) {
        val xAxis = mBinding.barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelCount = teacherList.size
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.yOffset = uiParams.xAxisMargin
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return teacherList.getOrNull(value.toInt())?.schoolName ?: "其他"
            }
        }
        xAxis.textColor = uiParams.xAxisTextColor

        val yAxis = mBinding.barChart.axisLeft
        mBinding.barChart.axisRight.isEnabled = false
        yAxis.labelCount = uiParams.yAxisLabelCount
        yAxis.axisMinimum = 0F
        yAxis.setDrawAxisLine(false)
        yAxis.enableGridDashedLine(uiParams.gridDashedLineWidth, uiParams.gridDashedLineWidth, 1F)
        yAxis.gridColor = uiParams.gridLineColor
        yAxis.xOffset = uiParams.yAxisMargin
        yAxis.textColor = uiParams.yAxisTextColor

        val entities = mutableListOf<BarEntry>()
        teacherList.forEachIndexed { index, schoolCount ->
            val count = schoolCount.schoolTeacherCount?.toFloat() ?: 0F
            entities.add(BarEntry(index.toFloat(), count))
        }
        val barSet = BarDataSet(entities, "各校老师人数")
        barSet.setGradientColor(uiParams.barColors.first, uiParams.barColors.second)
        val data = BarData(barSet)
        data.barWidth = uiParams.barWidthScale
        data.setDrawValues(false)
        mBinding.barChart.apply {
            setData(data)
            renderer = TeacherSchoolCountBarChartRender(uiParams, this, animator, viewPortHandler)
            isHighlightPerTapEnabled = false
            isHighlightPerDragEnabled = false
            description.isEnabled = false
            legend.isEnabled = false
            postInvalidate()
        }
    }
}