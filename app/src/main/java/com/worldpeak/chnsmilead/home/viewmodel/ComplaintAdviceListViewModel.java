package com.worldpeak.chnsmilead.home.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.home.model.AreaDynamicReponse;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceItem;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceListResponse;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;

import java.util.List;

public class ComplaintAdviceListViewModel extends BaseViewModel {

    private String TAG = "ComplaintAdviceListViewModel==";
    public MutableLiveData<List<ComplaintAdviceItem>> dataList = new MutableLiveData<>();
    public MutableLiveData<AreaDynamicReponse> bannerInfo = new MutableLiveData<>();

    public void getComplaintAdviceList(String type,String pageNo) {
        CommonRequest.getInstance().getComplaintAdviceList(type,pageNo, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    ComplaintAdviceListResponse response = (ComplaintAdviceListResponse) data.getResult(ComplaintAdviceListResponse.class);
                    dataList.postValue(response.getRows());
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
