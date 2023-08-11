package com.worldpeak.chnsmilead.home.model

data class LogoItem(
        val configName: String,
        val configVal: String,
        val createTime: String,
        val createUser: String,
        val id: String,
        val schoolId: String,
        val status: Int,
        val type: String,
        val updateTime: String,
        val updateUser: String
)

data class LogoConfig(
        val topImg: String? = "",
        val topImgId: String? = "",
        val schoolName: String? = "",
)