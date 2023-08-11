package com.worldpeak.chnsmilead.mine.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.login.model.WxUserInfo;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;

import java.util.List;

public class WxUnbindViewModel extends BaseViewModel {

    private String TAG = "WxUnbindViewModel==";
    public MutableLiveData<List<WxUserInfo>> userList = new MutableLiveData();

    public void getWxUnbindList(String account) {
        CommonRequest.getInstance().getWxUnbindList(account, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    List wxUserList = (new Gson()).fromJson((String) data.getResult(String.class), new TypeToken<List<WxUserInfo>>() {
                    }.getType());
                    userList.postValue(wxUserList);
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

    public void wxUnbind(String account,String openId,int type) {
        CommonRequest.getInstance().wxunbinding(account,openId,type, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                        showToast("解绑成功");
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
