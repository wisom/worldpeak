package com.worldpeak.chnsmilead.home.model

data class ComplaintAdviceDetailResponse(
        val complaintProposeAccessoryList: List<ComplaintProposeAccessory>? = null,
        val complaintProposeName: String? = "",
        val complaintProposePeople: String? = "",
        val complaintProposeReplyResultList: List<ComplaintProposeReplyResult>? = null,
        val contactInformation: String? = "",
        val content: String? = "",
        val createTime: String? = "",
        val id: String? = "",
        val replyType: Int? = 0,
        val targetSchoolId: String? = "",
        val targetSchoolName: String? = "",
        val title: String? = "",
        val type: Int? = 0
)

data class ComplaintProposeAccessory(
        val createTime: String? = "",
        val createUser: String? = "",
        val fileOriginName: String? = "",
        val fileSizeInfo: String? = "",
        val fileSuffix: String? = "",
        val fileUrl: String? = "",
        val id: String? = ""
)

data class ComplaintProposeReplyResult(
        val createTime: String? = "",
        val replyContent: String? = "",
        val schoolId: String? = "",
        val schoolName: String? = "",
        val schoolType: Int? = 0,
        val targetType: Int? = 0//0=转发 1-受理
)