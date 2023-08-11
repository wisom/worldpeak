package com.worldpeak.chnsmilead.oa.model.file

import com.worldpeak.chnsmilead.home.model.CommonAccessory
import com.worldpeak.chnsmilead.oa.model.ConfirmResult

data class FileDeliveryDetailResponse(
        val confirmResult: ConfirmResult? = null,
        val content: String? = "",
        val createName: String? = "",
        val createTime: String? = "",
        val createUser: String? = "",//发起人
        val formId: String? = "",
        val grade: Int? = 0,//重要程度（1普通、2重要、3紧急）
        val id: String? = "",
        val papersDowmAccessoryList: List<CommonAccessory>? = null,
        val process: Int? = 0,
        val remark: String? = "",
        val status: Int? = 0,//状态（0：未发送、1：已发送、2:已撤销、3：已删除）
        val title: String? = ""
)
