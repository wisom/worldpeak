package com.worldpeak.chnsmilead.login.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WxUserInfo(
        var openid: String? = "",
        var nickname: String? = "",
        var sex: Int? = 0,
        var province: String? = "",
        var city: String? = "",
        var country: String? = "",
        var headimgurl: String? = "",
        var privilege: List<String>? = null,
        var unionid: String? = "",
        var account: String? = "" //绑定的账号，手机号
) : Parcelable