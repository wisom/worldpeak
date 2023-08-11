package com.worldpeak.chnsmilead.main.model

data class UserInfo(
        val account: String? = "",
        val accountNonExpired: Boolean? = false,
        val accountNonLocked: Boolean? = false,
        val adminType: Int? = 0,
        val apps: List<App>? = null,
        val authorities: List<Authority>? = null,
        val avatar: String? = "",
        val birthday: String? = "",
        val createTime: String? = "",
        val credentialsNonExpired: Boolean? = false,
        val dataScopes: List<String>? = null,
        val defaultIdentity: Int? = 0,
        val email: String? = "",
        val enabled: Boolean? = false,
        val getuiCid: String? = "",
        val gradeClassScopes: List<Any>? = null,
        val hasPlatform: Int? = 0,
        val hasTencentIm: Int? = 0,
        val hasVip: Int? = 0,
        val id: String? = "",
        val imUserSign: String? = "",
        val innerUserId: String? = "",
        val lastLoginAddress: String? = "",
        val lastLoginBrowser: String? = "",
        val lastLoginIp: String? = "",
        val lastLoginOs: String? = "",
        val lastLoginTime: String? = "",
        val loginEmpInfo: LoginEmpInfo? = null,
        val menus: List<Menu>? = null,
        val name: String? = "",
        val permissions: List<String>? = null,
        val phone: String? = "",
        val platformAdminType: Int? = 0,
        val roles: List<Role>? = null,
        val sex: Int? = 0,
        val totalNumber: Int? = 0,
        val usedNumber: Int? = 0,
        val username: String? = ""
) {
    constructor() : this("", false, false, 0, null,
            null, "", "", "", false,
            null, 0, "", false, "", null,
            0, 0, 0, "", "", "", "",
            "", "", "", "", null, null,
            "", null, "", 0, null, 0, 0, 0, ""
    )
}

data class App(
        val active: Boolean? = false,
        val code: String? = "",
        val name: String? = ""
)

data class Authority(
        val authority: String? = ""
)

data class LoginEmpInfo(
        val jobNum: String? = "",
        val orgId: String? = "",
        val orgName: String? = ""
)

data class Menu(
        val component: String? = "",
        val hidden: Boolean? = false,
        val id: String? = "",
        val meta: Meta,
        val name: String? = "",
        val path: String? = "",
        val pid: String? = "",
        val redirect: String? = ""
)

data class Role(
        val code: String? = "",
        val id: String? = "",
        val name: String? = ""
)

data class Meta(
        val hideChildren: Boolean? = false,
        val icon: String? = "",
        val show: Boolean? = false,
        val title: String? = ""
)