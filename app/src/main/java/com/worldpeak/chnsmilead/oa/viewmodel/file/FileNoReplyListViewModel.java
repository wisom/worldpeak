package com.worldpeak.chnsmilead.oa.viewmodel.file;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.oa.model.AllProcessItem;
import com.worldpeak.chnsmilead.oa.model.AllProcessListResponse;
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryReplyItem;

import java.util.List;

public class FileNoReplyListViewModel extends BaseViewModel {

    private String TAG = "FileNoReplyListViewModel==";
    public MutableLiveData<List<NoticeDeliveryReplyItem>> dataList = new MutableLiveData<>();

    public void getReplyList(String formId,int status) {
        CommonRequest.getInstance().getFileReplyList(formId,status, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    List<NoticeDeliveryReplyItem> response = data.getResultList(NoticeDeliveryReplyItem.class);
                    dataList.postValue(response);
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

    public void remindFileNotConfirm(String id) {
        CommonRequest.getInstance().remindFileNotConfirm(id, new RetrofitListener<BaseResponse>() {
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
