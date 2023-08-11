package com.worldpeak.chnsmilead.home.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.home.model.AreaDynamicDetailReponse;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;

public class AreaDynamicDetailViewModel extends BaseViewModel {

    private String TAG = "AreaDynamicDetailViewModel==";
    public MutableLiveData<AreaDynamicDetailReponse> result = new MutableLiveData<>();

    public void getAreaDynamicDetail(String id) {
        CommonRequest.getInstance().getAreaDynamicDetail(id, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    AreaDynamicDetailReponse response = (AreaDynamicDetailReponse) data.getResult(AreaDynamicDetailReponse.class);
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
