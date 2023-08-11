package com.worldpeak.chnsmilead.oa.model


data class AllProcessListResponse(val rows: List<AllProcessItem>? = null, val pageNo: Int? = 0, val pageSize: Int? = 0, val rainbow: List<Int>? = null, val totalPage: Int? = 0, val totalRows: Int? = 0)

data class AllProcessItem(
        val id: String? = "",
        val formId: String? = "",
        val docId: String? = "",
        val title: String? = "",
        val content: String? = "",
        val orgName: String? = "",
        val dDate: String? = "",
        val remark: String? = "",
        val status: Int? = 0,//状态（0未发出、1批阅中、2已备案、3已拒绝）
        val reviewStatus: Int? = 0,//状态（1待批/待读、2已批/已读、3拒批）
        val cname: String? = "",
):java.io.Serializable