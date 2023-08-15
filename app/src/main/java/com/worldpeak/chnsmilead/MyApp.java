package com.worldpeak.chnsmilead;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.drake.tooltip.ToastConfig;
import com.drake.tooltip.interfaces.ToastGravityFactory;
import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.umeng.commonsdk.UMConfigure;
import com.worldpeak.chnsmilead.constant.Constants;
import com.worldpeak.chnsmilead.share.UmInitConfig;
import com.worldpeak.chnsmilead.util.ConfigurationManager;
import com.worldpeak.chnsmilead.util.SPUtils;

public class MyApp extends Application {

    private static MyApp mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;



        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsHeader(context);
            }
        });

        // 初始化
        ConfigurationManager.create(this);
        ToastConfig.initialize(this, new ToastGravityFactory());

        regToWx();

        initBugly();
        initGetui();
        initUmeng();

    }

    public static CrashReport.UserStrategy strategy;

    private void initBugly() {
        strategy = new CrashReport.UserStrategy(this);
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APPID, BuildConfig.DEBUG, strategy);
    }

    private void initGetui() {
        PushManager.getInstance().initialize(getContext());
    }

    private void initUmeng() {
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //友盟预初始化  appkey:59892f08310c9307b60023d0 channel
        UMConfigure.preInit(getApplicationContext(),Constants.UMENG_APPKEY,"Umeng");
        //您务必确保用户同意《隐私政策》之后，再初始化友盟+SDK。

//        if(SPUtils.getInstance().contains("uminit")).equals("")){
            //在用户阅读您的《隐私政策》并取得用户授权之后，才调用正式初始化函数UMConfigure.init()初始化统计SDK
            //友盟正式初始化
            UmInitConfig.UMinit(getApplicationContext());
            //QQ官方sdk授权
            Tencent.setIsPermissionGranted(true);
        }
//    }

    private static IWXAPI api = null;

    /**
     * 微信登录
     */
    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(Constants.WX_APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(Constants.WX_APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }

    public IWXAPI getWxApi() {
        return api;
    }

    public static MyApp getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance;
    }
}
