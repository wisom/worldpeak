package com.worldpeak.chnsmilead.oa.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryDetailResponse;
import com.worldpeak.chnsmilead.oa.model.TeacherInfoResponse;

import java.util.List;

public class TeacherStatsViewModel extends BaseViewModel {

    private String TAG = "TeacherStatsViewModel==";
    public MutableLiveData<TeacherInfoResponse> result = new MutableLiveData<>();

    public void getTeacherInfo() {
        CommonRequest.getInstance().getTeacherInfo( new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    TeacherInfoResponse response = (TeacherInfoResponse) data.getResult(TeacherInfoResponse.class);
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
