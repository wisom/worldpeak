package com.worldpeak.chnsmilead.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class ComplaintAdviceListResponse(val rows: List<ComplaintAdviceItem>? = null) : BaseListResponse()

open class BaseListResponse(val pageNo: Int? = 0, val pageSize: Int? = 0, val rainbow: List<Int>? = null, val totalPage: Int? = 0, val totalRows: Int? = 0)

@Parcelize
data class ComplaintAdviceItem(val complaintProposePeople: String? = "",//投诉建议人
                               val contactInformation: String? = "",//投诉建议人联系方式
                               val content: String? = "",//投诉建议内容
                               val id: String? = "",
                               val replyType: Int? = 0,//回复状态（0：未回复，1：已回复）
                               val targetSchoolName: String? = "",
                               val complaintProposeName: String? = "",
                               val createTime: String? = "",
                               val title: String? = "",//投诉建议标题
                               val type: Int? = 0//业务类型（1：建议，2：投诉)
) : Parcelable