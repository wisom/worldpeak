package com.worldpeak.chnsmilead.oa.model

/**
 * 学生信息
 */
data class StudentInfoResponse(
    val classCount: String?="",//班级数
    val gradeCount: Int?=0,//年级数
    val studentCount: StudentCount?=null
)

data class StudentCount(
    val studentTotal: Int?=0,//学生总数
    val studentManTotal: Int?=0,//男学生总数
    val studentWomanTotal: Int?=0//女学生总数
)