package com.worldpeak.chnsmilead.home.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.home.model.AreaDynamicReponse;
import com.worldpeak.chnsmilead.home.model.DynamicItem;
import com.worldpeak.chnsmilead.home.model.SchoolStyleListResponse;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;

import java.util.List;

public class HomeViewModel extends BaseViewModel {

    private String TAG = "HomeViewModel==";
    public MutableLiveData<AreaDynamicReponse> areaDynamicInfo = new MutableLiveData<>();
    public MutableLiveData<List<DynamicItem>> areaPicInfo = new MutableLiveData<>();

    public void getAreaDynamicInfo() {
        CommonRequest.getInstance().getAreaDynamicInfo(new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    AreaDynamicReponse response = (AreaDynamicReponse) data.getResult(AreaDynamicReponse.class);
                    areaDynamicInfo.postValue(response);
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

    public void getSchoolStyleList(int pageNo) {
        CommonRequest.getInstance().getSchoolStyleList(pageNo,new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    SchoolStyleListResponse response = (SchoolStyleListResponse) data.getResult(SchoolStyleListResponse.class);
                    areaPicInfo.postValue(response.getRows());
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
