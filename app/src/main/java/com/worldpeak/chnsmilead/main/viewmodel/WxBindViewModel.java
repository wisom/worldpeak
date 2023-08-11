package com.worldpeak.chnsmilead.main.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.MyApp;
import com.worldpeak.chnsmilead.R;
import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.base.manager.AccountManager;
import com.worldpeak.chnsmilead.constant.Constants;
import com.worldpeak.chnsmilead.login.model.WxAccessInfo;
import com.worldpeak.chnsmilead.login.model.WxUserInfo;
import com.worldpeak.chnsmilead.main.model.Region;
import com.worldpeak.chnsmilead.main.model.UserInfo;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.util.ConfigurationManager;
import com.worldpeak.chnsmilead.util.LogUtil;
import com.worldpeak.chnsmilead.util.SPUtils;

import java.util.List;

public class WxBindViewModel extends BaseViewModel {

    private String TAG = "WxBindViewModel==";
    public MutableLiveData<Boolean> _getInfoSuccess = new MutableLiveData<>();
    public SPUtils spUtils = new SPUtils(MyApp.getContext(), "worldpeak");

    public void login(String userName, String pwd, String userIdentity, boolean remindPwd) {
        CommonRequest.getInstance().platformRegionUser(userName, userIdentity, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {
                if (result != null && result.isSuccessful()) {
                    spUtils.putBoolean(Constants.KEY_REMEMBER_PWD, remindPwd);
                    spUtils.putString(Constants.KEY_LAST_PWD, pwd);
                    spUtils.putString(Constants.KEY_LAST_ACCOUNT, userName);
                    List regions = result.getResultList(Region.class);
                    String url = null;
                    if (regions != null && regions.size() > 0) {
                        Region region = (Region) regions.get(0);
                        if (region.onlineState != null && region.onlineState.equals("2")) {
                            showToast(R.string.service_offline_error);
                            return;
                        }
                        if (!TextUtils.isEmpty(region.account)) {
                            ConfigurationManager.instance().setString(Constants.PREF_KEY_USER_ACCOUNT, region.account);
                        }
                        if (!TextUtils.isEmpty(region.studentId)) {
                            ConfigurationManager.instance().setString(Constants.PREF_KEY_USER_ID, region.studentId);
                        }


                        if (!TextUtils.isEmpty(region.hostUrl)) {
                            url = region.hostUrl;
                        }
                        if (url == null && !TextUtils.isEmpty(region.hostUrl1)) {
                            url = region.hostUrl1;
                        }
                    }

                    if (url == null) {
                        ConfigurationManager.instance().setString(Constants.PREF_KEY_URL, "");
                        url = Constants.SERVER_URL_ORIGIN;
                    }

                    ConfigurationManager.instance().setString(Constants.PREF_KEY_URL, url);
//                    Constants.SERVER_URL_ORIGIN = url;
                    LogUtil.d(TAG, "mobileLogin url=" + url + "  userName=" + userName + "  pwd=" + pwd + "  userIdentity=" + userIdentity);
                    CommonRequest.getInstance().mobileLogin(userName, pwd, userIdentity, new RetrofitListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse result) {
                            if (result != null && result.isSuccessful()) {
                                String token = (String) result.getData();
                                if (!TextUtils.isEmpty(token)) {
                                    LogUtil.d(TAG, "登录成功 $token");
                                    AccountManager.onAuth(token);
                                    loadUser(token);
                                } else {
                                    showToast(R.string.login_token_error);
                                }
                            } else {
                                if (!TextUtils.isEmpty(result.getMsg())) {
                                    showToast(result.getMsg());
                                }
                            }
                        }

                        @Override
                        public void onError(BaseResponse data, String description) {
                            LogUtil.d(TAG, "description=" + description);
                            if (!TextUtils.isEmpty(description))
                                showToast(description);
                        }
                    });
                } else {
                    if (!TextUtils.isEmpty(result.getMsg())) {
                        showToast(result.getMsg());
                    } else {
                        showToast("服务器异常");
                    }
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {
                if (!TextUtils.isEmpty(description))
                    showToast(description);
            }
        });
    }
    public void wxLoginReq(String accessToken, String openId, String account,
                            String password, int userIdentity) {
        CommonRequest.getInstance().wxLogin(accessToken, openId, account, password, userIdentity, 0, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    String token = (String) data.getResult(String.class);
                    if (!account.isEmpty()) {
                        spUtils.putString(Constants.KEY_LAST_ACCOUNT, account);
                    }
                    AccountManager.onAuth(token);
                    CommonRequest.getInstance().platformRegionUser(account, String.valueOf(userIdentity), new RetrofitListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse result) {
                            if (result != null && result.isSuccessful()) {
                                spUtils.putString(Constants.KEY_LAST_ACCOUNT, account);
                                List regions = result.getResultList(Region.class);
                                String url = null;
                                if (regions != null && regions.size() > 0) {
                                    Region region = (Region) regions.get(0);
                                    if (region.onlineState != null && region.onlineState.equals("2")) {
                                        showToast(R.string.service_offline_error);
                                        return;
                                    }
                                    if (!TextUtils.isEmpty(region.account)) {
                                        ConfigurationManager.instance().setString(Constants.PREF_KEY_USER_ACCOUNT, region.account);
                                    }
                                    if (!TextUtils.isEmpty(region.studentId)) {
                                        ConfigurationManager.instance().setString(Constants.PREF_KEY_USER_ID, region.studentId);
                                    }

                                    if (!TextUtils.isEmpty(region.hostUrl)) {
                                        url = region.hostUrl;
                                    }
                                    if (url == null && !TextUtils.isEmpty(region.hostUrl1)) {
                                        url = region.hostUrl1;
                                    }
                                }

                                if (url == null) {
                                    ConfigurationManager.instance().setString(Constants.PREF_KEY_URL, "");
                                    url = Constants.SERVER_URL_ORIGIN;
                                }

                                ConfigurationManager.instance().setString(Constants.PREF_KEY_URL, url);


                                // 请求用户
                                loadUser(token);

                            } else {
                                if (!TextUtils.isEmpty(result.getMsg())) {
                                    showToast(result.getMsg());
                                } else {
                                    showToast("服务器异常");
                                }
                            }
                        }

                        @Override
                        public void onError(BaseResponse data, String description) {
                            if (!TextUtils.isEmpty(description))
                                showToast(description);
                        }
                    });
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

    public void loadUser(String token) {
        CommonRequest.getInstance().getLoginUser(token, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    UserInfo user = (UserInfo) data.getResult(UserInfo.class);
                    AccountManager.onLogin(user);
                    _getInfoSuccess.postValue(true);
//                    startIM(user);
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

//    private void startIM(User user) {
////        String userSig = GenerateTestUserSig.genTestUserSig(user.id);
//        LogUtils.i("userSig: " + user.imUserSign);
//        TUIUtils.login(user.id, user.imUserSign, new V2TIMCallback() {
//            @Override
//            public void onError(final int code, final String desc) {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        ConfigurationManager.instance().setBoolean(Constants.PREF_KEY_IM_ERROR_STATUS, true);
////                        ToastUtils.showShort(R.string.failed_login_tip + ":" + desc);
////                        startMainUI();
////                        finish();
//                    }
//                });
//                LogUtils.e("imLogin errorCode = " + code + ", errorInfo = " + desc);
//            }
//
//            @Override
//            public void onSuccess() {
//                ConfigurationManager.instance().setBoolean(Constants.PREF_KEY_IM_ERROR_STATUS, false);
////                startMainUI();
////                finish();
//            }
//        });
//    }
}
