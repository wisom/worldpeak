package com.worldpeak.chnsmilead.bbs.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.bbs.model.BBSItem;
import com.worldpeak.chnsmilead.bbs.model.BBSListResponse;
import com.worldpeak.chnsmilead.home.model.AreaDynamicReponse;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;

import java.util.List;

public class BBSViewModel extends BaseViewModel {

    private String TAG = "BBSViewModel==";
    public MutableLiveData<List<BBSItem>> bbsList = new MutableLiveData<>();
    public MutableLiveData<AreaDynamicReponse> bannerInfo = new MutableLiveData<>();

    public void getBBsList(int pageNo) {
        CommonRequest.getInstance().getBBsList(pageNo, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    BBSListResponse response = (BBSListResponse) data.getResult(BBSListResponse.class);
                    bbsList.postValue(response.getRows());
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

    public void getBannerList() {
        CommonRequest.getInstance().getBannerList( new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    AreaDynamicReponse response = (AreaDynamicReponse) data.getResult(AreaDynamicReponse.class);
                    bannerInfo.postValue(response);
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
