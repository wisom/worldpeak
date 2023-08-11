package com.worldpeak.chnsmilead.net

import com.google.gson.Gson
import com.google.gson.JsonParser

class BaseResponse<T> {
    var code //0:成功
            = 0
    var msg: String? = null
    var data: Any? = null
    fun getResult(classOfT: Class<T>?): T {
        return mGson.fromJson(mGson.toJson(data), classOfT)
    }

    fun <T> getResultList(cls: Class<T>?): List<T> {
        val list: MutableList<T> = ArrayList()
        try {
            val gson = Gson()
            val arry = JsonParser().parse(mGson.toJson(data)).asJsonArray
            for (jsonElement in arry) {
                list.add(gson.fromJson(jsonElement, cls))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    val isSuccessful: Boolean
        get() = code == 200

    companion object {
        private val mGson = Gson()
    }
}