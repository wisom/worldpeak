package com.worldpeak.chnsmilead.oa.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.oa.model.AllProcessItem;
import com.worldpeak.chnsmilead.oa.model.AllProcessListResponse;

import java.util.List;

public class DocumentApprovalDetailViewModel extends BaseViewModel {

    private String TAG = "DocumentApprovalDetailViewModel==";
    public MutableLiveData<DocumentApprovalDetailResponse> result = new MutableLiveData<>();

    public void getDetail(String formId) {
        CommonRequest.getInstance().getDocumentApprovalDetail(formId, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    DocumentApprovalDetailResponse response = (DocumentApprovalDetailResponse) data.getResult(DocumentApprovalDetailResponse.class);
                    result.postValue(response);
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

    public void approval(String formId, String approveRemark, int status) {
        CommonRequest.getInstance().documentApproval(formId, approveRemark, status, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    DocumentApprovalDetailResponse response = (DocumentApprovalDetailResponse) data.getResult(DocumentApprovalDetailResponse.class);
                    result.postValue(response);
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
