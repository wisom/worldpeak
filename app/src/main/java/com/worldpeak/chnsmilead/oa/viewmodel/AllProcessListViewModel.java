package com.worldpeak.chnsmilead.oa.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceItem;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceListResponse;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.oa.model.AllProcessItem;
import com.worldpeak.chnsmilead.oa.model.AllProcessListResponse;

import java.util.List;

public class AllProcessListViewModel extends BaseViewModel {

    private String TAG = "AllProcessListViewModel==";
    public MutableLiveData<List<AllProcessItem>> dataList = new MutableLiveData<>();

    /**
     *
     * @param listType 2：我的审批，3：我的通知
     * @param queryStatus 审批状态（1待批/待读、2已批/已读)
     * @param pageNo
     */
    public void getList(String listType,String queryStatus,int pageNo) {
        CommonRequest.getInstance().getAllProcessList(listType,queryStatus,pageNo, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    AllProcessListResponse response = (AllProcessListResponse) data.getResult(AllProcessListResponse.class);
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
