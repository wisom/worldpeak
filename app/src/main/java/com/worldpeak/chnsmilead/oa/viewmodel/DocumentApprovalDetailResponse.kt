package com.worldpeak.chnsmilead.oa.viewmodel

data class DocumentApprovalDetailResponse(
        val cname: String? = "",
        val content: String? = "",//内容
        val dDate: String? = "",
        val ddate: String? = "",
        val docId: String? = "",//文件编号
        val documentApproveInfoList: List<DocumentApproveInfo>? = null,
        val documentEnclosureInfoList: List<DocumentEnclosureInfo>? = null,
        val formId: String? = "",//表单编号
        val id: String? = "",
        val orgName: String? = "",//部门名称
        val remark: String? = "",//备注
        val status: Int? = 0,//状态（0未发出、1批阅中、2已备案、3已拒绝）
        val title: String? = ""//标题
)

data class DocumentApproveInfo(
        val approveDate: String? = "",//审批/阅读时间
        val approveId: String? = "",//审批/通知人id
        val approveName: String? = "",//审批/通知人名称
        val approveRemark: String? = "",//批阅意见
        val floor: Int? = 0,//批阅层级
        val formId: String? = "",//表单编号
        val id: String? = "",
        val kinds: String? = "",//1批阅 2通知
        val orgId: String? = "",//部门id
        val orgName: String? = "",
        val process: String? = "",
        val schoolId: String? = "",
        val schoolName: String? = "",
        val sort: Int? = 0,
        val status: Int//状态（0等待、1待批/待读、2已批/已读、3拒批）
)

data class DocumentEnclosureInfo(
        val fileOriginName: String? = "",
        val fileSuffix: String? = "",
        val fileUrl: String? = "",
        val formId: String? = "",
        val id: String? = ""
)