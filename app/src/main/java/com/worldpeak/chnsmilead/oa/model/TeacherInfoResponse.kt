package com.worldpeak.chnsmilead.oa.model

/**
 * 教师信息
 */
data class TeacherInfoResponse(
    val oneLevel: Int?=0,
    val otherTitle: Int?=0,
    val regularSenior: Int?=0,
    val schoolUserCount: List<SchoolUserCount>?=null,
    val senior: Int?=0,
    val `super`: Int? = 0,
    val teacherEducationCount: List<TeacherEducationCount>?=null,
    val teacherSexCount: List<TeacherSexCount>?=null,
    val threeLevel: Int?=0,
    val twoLevel: Int?=0
)

data class SchoolUserCount(
    val schoolName: String?="",
    val schoolTeacherCount: Int?=0
)

data class TeacherEducationCount(
    val educationCount: Int?=0,
    val educationName: String?=""
)

data class TeacherSexCount(
    val sexCount: Int?=0,
    val sexName: String?=""
)