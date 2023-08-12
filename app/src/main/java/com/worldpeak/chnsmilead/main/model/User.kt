package com.worldpeak.chnsmilead.main.model

import java.io.Serializable

class User : Serializable {
    var id //会员号
            : String? = null
    var imUserSign //im 的标识
            : String? = null
    var account: String? = null
    var nickName: String? = null
    var name: String? = null
    var avatar: String? = null
    var birthday: String? = null
    var sex: String? = null
    var email: String? = null
    var tel: String? = null
    var defaultIdentity // 1: 家长 2: 教师
            = 0
    var schoolId: String? = null
    var lastChildId // studentId
            : String? = null
    var baseUrl // 基本URL
            : String? = null
    var orgName // 局领导
            : String? = null
}