package com.worldpeak.chnsmilead.home.model

data class ComplaintAdviceBusinessType(
        val content: String? = "",//建议，投诉
        val businessType: Int? = 0//业务类型（1：建议，2：投诉）
)