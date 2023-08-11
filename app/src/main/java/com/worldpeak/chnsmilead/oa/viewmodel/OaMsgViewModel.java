package com.worldpeak.chnsmilead.oa.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.oa.model.ContactOrgItem;

import java.util.List;

public class OaMsgViewModel extends BaseViewModel {

    private String TAG = "OaMsgViewModel==";
    public MutableLiveData<List<ContactOrgItem>> contactList = new MutableLiveData<>();

    public void getContactList() {
        CommonRequest.getInstance().getContactList(new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    List<ContactOrgItem> list = data.getResultList(ContactOrgItem.class);
                    contactList.postValue(list);
                } else {
                    if (!TextUtils.isEmpty(data.getMsg())) {
                        showToast(data.getMsg());
                    }
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description);
                }
            }
        });
    }

    /**
     * 撤销
     *
     * @param formId
     */
    public void rebackNoticeDelivery(String formId) {
        CommonRequest.getInstance().rebackNoticeDelivery(formId, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (!TextUtils.isEmpty(data.getMsg())) {
                    showToast(data.getMsg());
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description);
                }
            }
        });
    }

    public void deleteNoticeDelivery(List<String> ids) {
        CommonRequest.getInstance().deleteNoticeDelivery(ids, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (!TextUtils.isEmpty(data.getMsg())) {
                    showToast(data.getMsg());
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description);
                }
            }
        });
    }

    public void remindNotConfirm(String formId) {
        if (TextUtils.isEmpty(formId)) return;
        CommonRequest.getInstance().remindNoticeNotConfirm(formId, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    showToast("提醒成功");
                } else {
                    if (!TextUtils.isEmpty(data.getMsg())) {
                        showToast(data.getMsg());
                    }
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description);
                }
            }
        });
    }
}
