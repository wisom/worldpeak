package com.worldpeak.chnsmilead.base.manager;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.worldpeak.chnsmilead.main.model.UserAgent;

public class DeviceManager {

    private static DeviceManager instance;

    private DeviceManager() {
    }

    public static DeviceManager getInstance() {
        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }

    public UserAgent getUserAgent() {
        UserAgent userAgent = new UserAgent();
        userAgent.appType = "Android";
        userAgent.appVersion = AppUtils.getAppVersionName();
        userAgent.appCode = AppUtils.getAppVersionCode();
        userAgent.model = DeviceUtils.getManufacturer() + "_" + DeviceUtils.getModel();
        return userAgent;
    }

}
