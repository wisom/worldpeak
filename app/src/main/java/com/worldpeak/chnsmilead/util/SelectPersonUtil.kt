package com.worldpeak.chnsmilead.util

import com.worldpeak.chnsmilead.oa.model.Person


object SelectPersonUtil {

    private val mUserList: MutableList<Person> = mutableListOf()

    fun addUser(user: Person) {
        if (mUserList.none { it.id == user.id }) {
            mUserList.add(user);
        }
    }

    fun removeUser(user: Person) {
        val index = mUserList.indexOfFirst { it.id == user.id }
        if (index != -1) {
            mUserList.removeAt(index)
        }
    }

    @JvmStatic
    fun clearList() {
        mUserList.clear()
    }

    fun getUserList(): MutableList<Person> {
        return mUserList;
    }

    fun getMaxCount(): Int {
        return 500
    }
}