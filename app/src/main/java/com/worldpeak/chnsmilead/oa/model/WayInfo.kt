package com.worldpeak.chnsmilead.oa.model

data class WayInfo(
        val type: Int? = 1,//批阅要求（1仅阅读、2需回复）
        val content: String? = ""//批阅要求（1仅阅读、2需回复）
)