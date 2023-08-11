package com.worldpeak.chnsmilead.oa.model

import android.os.Parcelable
import com.worldpeak.chnsmilead.home.model.CommonAccessory
import kotlinx.parcelize.Parcelize


data class FileDeliveryListResponse(val rows: List<FileDeliveryItem>? = null, val pageNo: Int? = 0, val pageSize: Int? = 0, val rainbow: List<Int>? = null, val totalPage: Int? = 0, val totalRows: Int? = 0)

@Parcelize
data class FileDeliveryItem(
        val formId: String? = "",
        val title: String? = "",
        val content: String? = "",
        val status: Int? = 0,//状态（0：未发送、1：已发送、3：已删除）
        val grade: Int? = 0,//重要程度（1普通、2重要、3紧急）
        val createName: String? = "",//发起人
        val createTime: String? = "",//创建时间
        val papersDowmAccessoryList: List<CommonAccessory>? = null,//附件集合
) : Parcelable