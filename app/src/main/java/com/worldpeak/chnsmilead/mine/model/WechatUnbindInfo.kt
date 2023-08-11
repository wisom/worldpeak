package com.worldpeak.chnsmilead.mine.model


data class WechatUnbindInfo(
        val city: String? = "",
        val country: String? = "",
        val headimgurl: String? = "",
        val nickname: String? = "",
        val openid: String? = "",
        val privilege: List<String>? = null,
        val province: String? = "",
        val sex: Int? = 0,
        val unionid: String? = ""
):java.io.Serializable