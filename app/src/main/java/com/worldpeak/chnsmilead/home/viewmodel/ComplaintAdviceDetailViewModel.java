package com.worldpeak.chnsmilead.home.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceDetailResponse;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;

public class ComplaintAdviceDetailViewModel extends BaseViewModel {

    private String TAG = "ComplaintAdviceDetailViewModel==";
    public MutableLiveData<ComplaintAdviceDetailResponse> response = new MutableLiveData<>();

    public void getComplaintAdviceDetail(String id) {
        CommonRequest.getInstance().getComplaintAdviceDetail(id, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    ComplaintAdviceDetailResponse result = (ComplaintAdviceDetailResponse) data.getResult(ComplaintAdviceDetailResponse.class);
                    response.postValue(result);
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
