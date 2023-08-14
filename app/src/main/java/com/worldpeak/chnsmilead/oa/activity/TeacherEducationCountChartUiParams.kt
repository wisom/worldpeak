package com.worldpeak.chnsmilead.oa.activity

import android.graphics.Color

class TeacherEducationCountChartUiParams {

    val valueTextColor = Color.WHITE
    val valueTextSize = 15F
    val hollowCircleRadius = 60F
    val centerTextTitleSizeDp = 40
    val centerTextTitleColor = Color.parseColor("#8F97A2")
    val centerTextContentSizeDp = 60
    val centerTextContentColor = Color.BLACK

    fun getEducationColor(educationName: String?): Int {
        return when (educationName) {
            "博士" -> Color.parseColor("#0098FA")
            "硕士" -> Color.parseColor("#0CD9B5")
            "本科" -> Color.parseColor("#3B72AD")
            "专科" -> Color.parseColor("#FDCC00")
            else -> Color.parseColor("#3B72AD")
        }
    }

}