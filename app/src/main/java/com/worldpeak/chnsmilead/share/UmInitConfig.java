package com.worldpeak.chnsmilead.share;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.worldpeak.chnsmilead.constant.Constants;

/**
 * Create by ggyy on 13
 */
public class UmInitConfig {

    public static void  UMinit(Context context){
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(context, Constants.UMENG_APPKEY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        String fileProvider = context.getPackageName()+".fileprovider";
        PlatformConfig.setFileProvider(fileProvider);

        // 微信设置
        PlatformConfig.setWeixin(Constants.WX_APP_ID, Constants.WX_APP_SECRET);
        PlatformConfig.setWXFileProvider(fileProvider);
        // QQ设置
        PlatformConfig.setQQZone(Constants.QQ_APP_ID, Constants.QQ_APP_KEY);
        PlatformConfig.setQQFileProvider(fileProvider);

    }
}
