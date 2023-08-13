package com.worldpeak.chnsmilead.oa.viewmodel

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.net.BaseResponse
import com.worldpeak.chnsmilead.net.CommonRequest
import com.worldpeak.chnsmilead.net.RetrofitListener
import com.worldpeak.chnsmilead.oa.model.ContactOrgItem

class OaContactViewModel : BaseViewModel() {
    private val TAG = "OaContactViewModel=="
    var contactList = MutableLiveData<List<ContactOrgItem>>()

    fun getContactList() {
        CommonRequest.getInstance().getContactList(object : RetrofitListener<BaseResponse<*>> {
            override fun onSuccess(data: BaseResponse<*>?) {
                if (data != null && data.isSuccessful) {
                    val list = data.getResultList(
                        ContactOrgItem::class.java
                    )
                    contactList.postValue(list)
                } else {
                    showToast(data?.msg ?: "请求错误")
                }
            }

            override fun onError(data: BaseResponse<*>, description: String) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description)
                }
            }
        })
    }

    fun goGroupList(view: View) {
        ToastUtils.showShort("响应了")
    }

    /**
     * 撤销
     *
     * @param formId
     */
    fun rebackNoticeDelivery(formId: String?) {
        CommonRequest.getInstance()
            .rebackNoticeDelivery(formId, object : RetrofitListener<BaseResponse<*>> {
                override fun onSuccess(data: BaseResponse<*>) {
                    if (!TextUtils.isEmpty(data.msg)) {
                        showToast(data.msg!!)
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
                override fun onSuccess(data: BaseResponse<*>) {
                    if (!TextUtils.isEmpty(data.msg)) {
                        showToast(data.msg!!)
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
                override fun onSuccess(data: BaseResponse<*>) {
                    if (data != null && data.isSuccessful) {
                        showToast("提醒成功")
                    } else {
                        if (!TextUtils.isEmpty(data.msg)) {
                            showToast(data.msg!!)
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