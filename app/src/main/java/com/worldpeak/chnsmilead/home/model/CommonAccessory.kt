package com.worldpeak.chnsmilead.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 通用附件详情
 */
@Parcelize
data class CommonAccessory(
    val createTime: String?="",
    val createUser: String?="",
    val fileBucket: String?="",
    val fileLocation: Int?=0,
    val fileObjectName: String?="",
    val fileOriginName: String?="",
    val fileSizeInfo: String?="",
    val fileSizeKb: String?="",
    val fileSuffix: String?="",
    val fileUrl: String?="",
    val id: String?=""
) : Parcelable