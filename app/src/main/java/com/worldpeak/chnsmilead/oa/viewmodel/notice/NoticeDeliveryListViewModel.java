package com.worldpeak.chnsmilead.oa.viewmodel.notice;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryItem;
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryListResponse;

import java.util.List;

public class NoticeDeliveryListViewModel extends BaseViewModel {

    private String TAG = "NoticeDeliveryListViewModel==";
    public MutableLiveData<List<NoticeDeliveryItem>> dataList = new MutableLiveData<>();

    public void getList(String paramType,String grade,int pageNo) {
        CommonRequest.getInstance().getNoticeDeliveryList(paramType,grade,pageNo, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    NoticeDeliveryListResponse response = (NoticeDeliveryListResponse) data.getResult(NoticeDeliveryListResponse.class);
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
}
