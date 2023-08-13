package com.worldpeak.chnsmilead.oa.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.tencent.imsdk.v2.V2TIMConversationResult
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMValueCallback
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.net.BaseResponse
import com.worldpeak.chnsmilead.net.CommonRequest
import com.worldpeak.chnsmilead.net.RetrofitListener
import com.worldpeak.chnsmilead.oa.model.ContactOrgItem


class OaMsgViewModel : BaseViewModel() {
    var contactList = MutableLiveData<List<ContactOrgItem>>()
    private fun getContactList() {
        CommonRequest.getInstance().getContactList(object : RetrofitListener<BaseResponse<*>> {
            override fun onSuccess(data: BaseResponse<*>?) {
                if (data?.isSuccessful!!) {
                    val list = data.getResultList(
                        ContactOrgItem::class.java
                    )
                    list.map { Log.e("tag", "item = ${Gson().toJson(it)}") }
                    contactList.postValue(list)
                } else {
                    showToast(data.msg ?: "请求失败")
                }
            }

            override fun onError(data: BaseResponse<*>, description: String) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description)
                }
            }
        })
    }

    /**
     * 撤销
     *
     * @param formId
     */
    fun rebackNoticeDelivery(formId: String?) {
        CommonRequest.getInstance()
            .rebackNoticeDelivery(formId, object : RetrofitListener<BaseResponse<*>> {
                override fun onSuccess(data: BaseResponse<*>?) {
                    if (!TextUtils.isEmpty(data?.msg)) {
                        showToast(data?.msg!!)
                    }
                }

                override fun onError(data: BaseResponse<*>, description: String) {
                    if (!TextUtils.isEmpty(description)) {
                        showToast(description)
                    }
                }
            })
    }

    fun deleteNoticeDelivery(ids: List<String?>?) {
        CommonRequest.getInstance()
            .deleteNoticeDelivery(ids, object : RetrofitListener<BaseResponse<*>> {
                override fun onSuccess(data: BaseResponse<*>?) {
                    if (!TextUtils.isEmpty(data?.msg)) {
                        showToast(data?.msg!!)
                    }
                }

                override fun onError(data: BaseResponse<*>, description: String) {
                    if (!TextUtils.isEmpty(description)) {
                        showToast(description)
                    }
                }
            })
    }

    fun remindNotConfirm(formId: String?) {
        if (TextUtils.isEmpty(formId)) return
        CommonRequest.getInstance()
            .remindNoticeNotConfirm(formId, object : RetrofitListener<BaseResponse<*>> {
                override fun onSuccess(data: BaseResponse<*>?) {
                    if (data != null && data.isSuccessful) {
                        showToast("提醒成功")
                    } else {
                        if (!TextUtils.isEmpty(data?.msg)) {
                            showToast(data?.msg!!)
                        }
                    }
                }

                override fun onError(data: BaseResponse<*>, description: String) {
                    if (!TextUtils.isEmpty(description)) {
                        showToast(description)
                    }
                }
            })
    }
}