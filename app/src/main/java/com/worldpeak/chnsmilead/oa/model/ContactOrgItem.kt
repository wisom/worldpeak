package com.worldpeak.chnsmilead.oa.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactOrgItem(
        val avatarImg: String? = "",
        val children: List<SchoolItem>,
        val id: String? = "",
        val name: String? = "",
        val schoolId: String? = "",
        val schoolType: Int? = 0
) : Parcelable

@Parcelize
data class SchoolItem(
        val avatarImg: String? = "",
        val children: List<Department>? = null,
        val id: String? = "",
        val name: String? = "",
        val schoolId: String? = "",
        val schoolType: Int? = 0
) : Parcelable

@Parcelize
data class Department(
        val avatarImg: String? = "",
        val children: List<Person>? = null,
        val id: String? = "",
        val name: String? = "",
        val schoolId: String? = "",
        val schoolType: Int? = 0
) : Parcelable

@Parcelize
data class Person(
        val avatarImg: String? = "",
        val id: String? = "",
        val name: String? = "",
        val schoolId: String? = "",
        val schoolType: Int? = 0,
        var department: String? = "",
        var isSelect: Boolean? = false
) : Parcelable