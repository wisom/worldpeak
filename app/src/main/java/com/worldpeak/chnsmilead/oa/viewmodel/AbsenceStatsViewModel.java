package com.worldpeak.chnsmilead.oa.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.oa.model.AbsenceInfoResponse;
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryDetailResponse;

import java.util.List;

public class AbsenceStatsViewModel extends BaseViewModel {

    private String TAG = "AbsenceStatsViewModel==";
    public MutableLiveData<AbsenceInfoResponse> result = new MutableLiveData<>();

    /**
     * 缺勤信息
     *
     * @param type 1:日，2：月
     * @param day  查询日期
     */
    public void getAbsenceInfo(int type, String day) {
        CommonRequest.getInstance().getAbsenceInfo(type, day, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    AbsenceInfoResponse response = (AbsenceInfoResponse) data.getResult(AbsenceInfoResponse.class);
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
