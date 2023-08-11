package com.worldpeak.chnsmilead.home.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.home.model.LogoItem;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;

import java.util.List;

public class MainViewModel extends BaseViewModel {

    private String TAG = "MainViewModel==";
    public MutableLiveData<List<LogoItem>> logoList = new MutableLiveData<>();

    public void getLogo() {
        CommonRequest.getInstance().getLogo(new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    List<LogoItem> response = data.getResultList(LogoItem.class);
                    logoList.postValue(response);
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
