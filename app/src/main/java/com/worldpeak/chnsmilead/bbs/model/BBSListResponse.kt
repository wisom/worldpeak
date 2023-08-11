package com.worldpeak.chnsmilead.bbs.model

data class BBSListResponse(
        val pageNo: Int?=0,
        val pageSize: Int?=0,
        val rainbow: List<Int>?=null,
        val rows: List<BBSItem>?=null,
        val totalPage: Int?=0,
        val totalRows: Int?=0
)

data class BBSItem(
    val author: String?="",
    val id: String?="",
    val intro: String?="",
    val labelDate: String?="",
    val listDate: String?="",
    val pageUrl: String?="",
    val publishTime: String?="",
    val title: String?="",
    val topImg: String?=""
)