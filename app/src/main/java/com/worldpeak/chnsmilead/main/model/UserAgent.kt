package com.worldpeak.chnsmilead.main.model

import java.io.Serializable

class UserAgent : Serializable {
    @JvmField
    var appType // android ios
            : String? = null
    @JvmField
    var appVersion // 版本号
            : String? = null
    @JvmField
    var model // xiaomi_530
            : String? = null
    @JvmField
    var appCode // 版本code
            = 0
}