package com.worldpeak.chnsmilead.home.model

data class AreaDynamicDetailReponse(
        val attachInfoList: List<AttachInfo>?=null,
        val author: String? = "",
        val content: String? = "",
        val createName: String? = "",
        val createTime: String? = "",
        val id: String? = "",
        val publishTime: String? = "",
        val status: Int? = 0,
        val title: String? = "",
        val topImg: String? = ""
)

data class AttachInfo(
        val attachId: String? = "",
        val attachSizeInfo: String? = "",
        val attachSuffix: String? = "",
        val attachUrl: String? = "",
        val origionName: String? = ""
)