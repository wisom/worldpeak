package com.worldpeak.chnsmilead.oa.model

/**
 * 缺勤信息
 */
data class AbsenceInfoResponse(
        val studentLeaveCount: Int? = 0,
        val studentLeaveDetail: List<StudentLeaveDetail>? = null,
        val teacherLeaveCount: Int? = 0,
        val teacherLeaveDetail: List<TeacherLeaveDetail>? = null
)

data class StudentLeaveDetail(
        val className: String? = "",
        val leaveDate: String? = "",
        val schoolId: String? = "",
        val schoolName: String? = "",
        val userName: String? = ""
)

data class TeacherLeaveDetail(
        val leaveDate: String? = "",
        val orgName: String? = "",
        val schoolId: String? = "",
        val schoolName: String? = "",
        val userName: String? = ""
)