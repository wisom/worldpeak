package com.worldpeak.chnsmilead.main.viewmodel;

import static com.worldpeak.chnsmilead.util.ThreadUtil.runOnUiThread;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.interfaces.TUICallback;
import com.worldpeak.chnsmilead.MyApp;
import com.worldpeak.chnsmilead.R;
import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.base.manager.AccountManager;
import com.worldpeak.chnsmilead.constant.Constants;
import com.worldpeak.chnsmilead.login.model.WxAccessInfo;
import com.worldpeak.chnsmilead.login.model.WxUserInfo;
import com.worldpeak.chnsmilead.main.model.App;
import com.worldpeak.chnsmilead.main.model.Region;
import com.worldpeak.chnsmilead.main.model.User;
import com.worldpeak.chnsmilead.main.model.UserInfo;
import com.worldpeak.chnsmilead.net.BaseRequest;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.util.ConfigurationManager;
import com.worldpeak.chnsmilead.util.GenerateTestUserSig;
import com.worldpeak.chnsmilead.util.LogUtil;
//import com.worldpeak.chnsmilead.util.tui.TUIUtils;
import com.worldpeak.chnsmilead.util.SPUtils;
import com.worldpeak.chnsmilead.util.Utils;
import com.youth.banner.util.LogUtils;

import java.util.List;

import kotlin.Triple;

public class LoginViewModel extends BaseViewModel {

    private String TAG = "LoginViewModel==";
    public MutableLiveData<Boolean> _getInfoSuccess = new MutableLiveData<>();
    public MutableLiveData<Triple<String, String, Integer>> unBindInfo = new MutableLiveData<>();
    public SPUtils spUtils = new SPUtils(MyApp.getContext(), "worldpeak");

    /**
     * @param userName
     * @param pwd
     * @param userIdentity
     * @param remindPwd    是否记住密码
     */
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


    private void loadUser(String token) {
        CommonRequest.getInstance().getLoginUser(token, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    UserInfo user = (UserInfo) data.getResult(UserInfo.class);
                    AccountManager.onLogin(user);
                    MyApp.strategy.setDeviceID(user.getAccount());
//                    _getInfoSuccess.postValue(true);
                    startIM(user);
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

    private void startIM(UserInfo user) {
        Log.e("tag", "user = " + new Gson().toJson(user));
        String userSig = GenerateTestUserSig.genTestUserSig(user.getId());
        Log.e("Tag", "userSig: " + user.getImUserSign() + "| " + userSig + " | userId = " + user.getId());
        TUILogin.login(MyApp.getInstance().getApplicationContext(), Utils.timAppId, user.getId(), user.getImUserSign(), new TUICallback() {
            @Override
            public void onSuccess() {
                ConfigurationManager.instance().setBoolean(Constants.PREF_KEY_IM_ERROR_STATUS, false);
                _getInfoSuccess.postValue(true);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ConfigurationManager.instance().setBoolean(Constants.PREF_KEY_IM_ERROR_STATUS, true);
                        ToastUtils.showShort(errorMessage);
//                        startMainUI();
//                        finish();
                    }
                });
                Log.e("Tag", "imLogin errorCode = " + errorCode + ", errorInfo = " + errorMessage);
            }
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
        });
    }

    /**
     * 获取access_token
     *
     * @param code 微信授权后返回的code，用于获取access_token
     */
    public void getWxAccessToken(String code) {
        CommonRequest.getInstance().getAccessToken(code, 0, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    WxAccessInfo wxAccessInfo = (WxAccessInfo) data.getResult(WxAccessInfo.class);
                    spUtils.putString(Constants.PREF_KEY_WX_ACCESSTOKEN, wxAccessInfo.accessToken);
                    spUtils.putString(Constants.PREF_KEY_WX_OPENID, wxAccessInfo.openid);
                    spUtils.putString(Constants.PREF_KEY_WX_REFRESHTOKEN, wxAccessInfo.refreshToken);
                    getWxUserInfo(wxAccessInfo.accessToken, wxAccessInfo.openid, 5);
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

    /**
     * 获取微信用户信息
     *
     * @param accessToken
     */
    private void getWxUserInfo(String accessToken, String openId, int userIdentity) {
        CommonRequest.getInstance().getWxUserInfo(accessToken, openId, 0, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    WxUserInfo wxUserInfo = (WxUserInfo) data.getResult(WxUserInfo.class);
                    if (wxUserInfo != null && !TextUtils.isEmpty(wxUserInfo.getAccount())) {
                        wxLoginReq(accessToken, openId, wxUserInfo.getAccount(), "", userIdentity);
                    } else {
                        unBindInfo.postValue(new Triple(accessToken, wxUserInfo.getOpenid(), userIdentity));
                    }
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

    private void wxLoginReq(String accessToken, String openId, String account,
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


    /**
     * 刷新access_token
     *
     * @param refreshToken
     */
    private void refreshWxAccessToken(String refreshToken, int userIdentity) {
        CommonRequest.getInstance().refreshToken(refreshToken, 0, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    WxAccessInfo wxAccessInfo = (WxAccessInfo) data.getResult(WxAccessInfo.class);
                    spUtils.putString(Constants.PREF_KEY_WX_ACCESSTOKEN, wxAccessInfo.accessToken);
                    spUtils.putString(Constants.PREF_KEY_WX_OPENID, wxAccessInfo.openid);
                    spUtils.putString(Constants.PREF_KEY_WX_REFRESHTOKEN, wxAccessInfo.refreshToken);
                    getWxUserInfo(wxAccessInfo.accessToken, wxAccessInfo.openid, userIdentity);
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

    /**
     * 检查微信登录token是否过期
     *
     * @param accessToken
     * @param openId
     */
    public void verifyToken(String accessToken, String openId, int userIdentity) {
        CommonRequest.getInstance().verifyAccessToken(accessToken, openId, 0, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    Boolean isSuccess = (Boolean) data.getResult(Boolean.class);
                    if (isSuccess) {
                        getWxUserInfo(accessToken, openId, userIdentity);
                    } else {
                        showToast(data.getMsg());
                        String refreshToken = spUtils.getString(Constants.PREF_KEY_WX_REFRESHTOKEN);
                        refreshWxAccessToken(refreshToken, userIdentity);
                    }
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
