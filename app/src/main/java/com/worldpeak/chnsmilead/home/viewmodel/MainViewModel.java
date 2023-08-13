package com.worldpeak.chnsmilead.home.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.constant.Constants;
import com.worldpeak.chnsmilead.home.model.LogoItem;
import com.worldpeak.chnsmilead.main.model.User;
import com.worldpeak.chnsmilead.main.model.UserInfo;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.util.ConfigurationManager;

import java.util.List;

public class MainViewModel extends BaseViewModel {

    public MutableLiveData<List<LogoItem>> logoList = new MutableLiveData<>();

    public MainViewModel() {
    }

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

    private void startIM() {
        String value = ConfigurationManager.instance().getString(Constants.PREF_KEY_USER);
        if (TextUtils.isEmpty(value)) {
            return;
        }
        UserInfo user = new Gson().fromJson(value, UserInfo.class);
        V2TIMManager.getInstance().login(user.getId(), user.getImUserSign(), new V2TIMCallback() {
            @Override
            public void onSuccess() {
                ConfigurationManager.instance().setBoolean(Constants.PREF_KEY_IM_ERROR_STATUS, false);
            }

            @Override
            public void onError(int i, String s) {
                ConfigurationManager.instance().setBoolean(Constants.PREF_KEY_IM_ERROR_STATUS, true);
                ToastUtils.showShort(s);
            }
        });
    }
}
