package com.worldpeak.chnsmilead.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class SchoolStyleListResponse(val rows: List<DynamicItem>? = null) : BaseListResponse()

@Parcelize
data class DynamicItem(
        val id: String? = "",
        val title: String? = "",//标题
        val author: String? = "",
        val topImg: String? = "",
        val intro: String? = "",
        val publishTime: String? = "",
        val pageUrl: String? = "",
): Parcelable
