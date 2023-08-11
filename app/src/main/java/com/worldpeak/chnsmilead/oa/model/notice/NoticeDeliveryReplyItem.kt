package com.worldpeak.chnsmilead.oa.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NoticeDeliveryReplyItem(
        val dowmNoticeAccessoryList: List<DowmNoticeAccessory>? = null,
        val id: String? = "",
        val noticeUserId: String? = "",
        val noticeUserName: String? = "",
        val orgId: String? = "",
        val orgName: String? = "",
        val readDate: String? = "",
        val replyDate: String? = "",
        val replyRemark: String? = "",
        val schoolId: String? = "",
        val schoolName: String? = "",
        val sort: Int? = 0,
        val status: Int? = 0//状态（0等待、1：待读、2：已读、3：已回复）
):Parcelable

@Parcelize
data class DowmNoticeAccessory(
        val fileOriginName: String? = "",
        val fileSizeInfo: String? = "",
        val fileSuffix: String? = "",
        val fileUrl: String? = "",
        val id: String? = ""
):Parcelable