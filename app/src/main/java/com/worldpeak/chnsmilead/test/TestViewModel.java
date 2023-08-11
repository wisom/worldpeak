package com.worldpeak.chnsmilead.test;

import android.text.TextUtils;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.base.manager.AccountManager;
import com.worldpeak.chnsmilead.main.model.User;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;

public class TestViewModel extends BaseViewModel {

    public void testLogin(String username,String pwd) {
        CommonRequest.getInstance().testLogin(username,pwd, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                } else {
                    if (!TextUtils.isEmpty(data.getMsg())) {
                        showToast(data.getMsg());
                    }
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {

            }
        });
    }
}
