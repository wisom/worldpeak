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

public class DynamicListViewModel extends BaseViewModel {

    private String TAG = "SchoolStyleListViewModel==";
    public MutableLiveData<List<DynamicItem>> dataList = new MutableLiveData<>();

    /**
     * @param type   1:区域教育动态；2：区域学校风采
     * @param pageNo
     */
    public void getDataList(int type, int pageNo) {
        if (type == 2) {
            getSchoolStyle(pageNo);
        } else {
            getEducationDynamic();
        }
    }

    private void getEducationDynamic() {
        CommonRequest.getInstance().getEducationDynamicList(new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    AreaDynamicReponse response = (AreaDynamicReponse) data.getResult(AreaDynamicReponse.class);
                    dataList.postValue(response.getAdminArticleApps());
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

    private void getSchoolStyle(int pageNo) {
        CommonRequest.getInstance().getSchoolStyleList(pageNo, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    SchoolStyleListResponse response = (SchoolStyleListResponse) data.getResult(SchoolStyleListResponse.class);
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
