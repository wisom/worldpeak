package com.worldpeak.chnsmilead.constant;

import android.text.TextUtils;

import com.worldpeak.chnsmilead.util.ConfigurationManager;

public class Constants {

    // url
//    public static final String SERVER_URL_ORIGIN = "https://www.csmiledu.com/"; // 获取域名
//    public static final String SERVER_URL_ORIGIN = "http://121.199.18.201/"; // 获取域名
//    public static final String SERVER_URL_ORIGIN = "http://47.98.251.192/"; // 获取域名
    public static String SERVER_URL_ORIGIN = "http://yun3.csmiledu.com/"; // 获取域名
    //    public static String SERVER_URL_ORIGIN = "https://www.wanandroid.com/"; // 获取域名
//    public static String SERVER_URL_ORIGIN = "http://47.98.251.192/"; // 获取域名
    public static final String SERVER_URL_ORIGIN_2 = "http://121.199.18.201/"; // 二期接口域名
    public static final String SERVER_URL_GET_PLATFORM = SERVER_URL_ORIGIN + "platformRegionUser/default/list"; // 获取域名
    //    public static String SERVER_URL_BASE_HOST = TextUtils.isEmpty(ConfigurationManager.instance().getString(Constants.PREF_KEY_URL)) ? "https://www.csmiledu.com/" : ConfigurationManager.instance().getString(Constants.PREF_KEY_URL); // online
//    public static String SERVER_URL_BASE_HOST = TextUtils.isEmpty(ConfigurationManager.instance().getString(Constants.PREF_KEY_URL)) ? "http://121.199.18.201/" : ConfigurationManager.instance().getString(Constants.PREF_KEY_URL); // online
//    public static String SERVER_URL_BASE_HOST = TextUtils.isEmpty(ConfigurationManager.instance().getString(Constants.PREF_KEY_URL)) ? "http://47.98.251.192/" : ConfigurationManager.instance().getString(Constants.PREF_KEY_URL); // online
    public static String SERVER_URL_BASE_HOST = TextUtils.isEmpty(ConfigurationManager.instance().getString(Constants.PREF_KEY_URL)) ? "http://yun3.csmiledu.com/" : ConfigurationManager.instance().getString(Constants.PREF_KEY_URL); // online
    public static final String SERVER_URL_USERS_LOGIN = "app-api/mobileLogin";
    public static final String SERVER_URL_BINDCID = "app-api/app/user/push/bindCidAndAlias";
    public static final String SERVER_URL_USERS_GET = "app-api/getLoginUser";
    public static final String SERVER_URL_TEACHER_CONTACT = "app-api/app/school/student/class/teacherContact";
    public static final String SERVER_URL_UNREADNUM_CONTACT = "app-api/app/user/tab/unReadNum";

    public static final String PERSONAL_POLICY = SERVER_URL_ORIGIN + "app-api/app/school/page/platform/WX_USER_PRIVACY";
    public static final String AGREEMENT = SERVER_URL_ORIGIN + "app-api/app/school/page/platform/WX_USER_AGREEMENT";
    public static final String THIRD_SDK_INSTRUCTIONS = SERVER_URL_ORIGIN + "app-api/app/school/page/platform/WX_APP_SDK_EXPLAIN";

    // preferences
    public static final String PREF_KEY_STORAGE_PATH = "worldpeak.prefs.storage.path";
    public static final String PREF_KEY_USER = "worldpeak.prefs.user";
    public static final String PREF_KEY_URL = "worldpeak.prefs.url";
    public static final String PREF_KEY_IM_ERROR_STATUS = "worldpeak.prefs.im.error.status";
    public static final String PREF_KEY_SESSION = "worldpeak.prefs.user.session";
    public static final String PREF_KEY_USER_ACCOUNT = "worldpeak.prefs.user.account";
    public static final String PREF_KEY_USER_ID = "worldpeak.prefs.user.id";
    public static final String PREF_KEY_PROTOCOL_STATUS = "worldpeak.prefs.protocol.status";
    public static final String PREF_KEY_FIRST_ENTER = "worldpeak.prefs.first.enter";
    public static final String PREF_KEY_GETUI_CID = "worldpeak.prefs.getui.cid";

    // fragment type
    public static final String FRAGMENT_TYPE_KEY = "fragment_key";

    // router 页面
    public static final String SP_BASE_LOGIN = "/sp/login"; // 登录页面
    public static final String SP_BASE_FORGET = "/sp/forget"; // 忘记密码页面
    public static final String SP_BASE_MAIN = "/sp/main"; // 首页
    public static final String SP_WEBVIEW = "/sp/webview"; // webview
    public static final String SP_WEBVIEW2 = "/sp/webview2"; // webview
    public static final String SP_MEDIA = "/sp/media"; // 媒体查看器
    public static final String SP_PICTURE = "/sp/picture"; // 图片查看器
    public static final String SP_VIDEO = "/sp/video"; // 视频查看器
    public static final String SP_ATTACHMENT = "/sp/attachment"; // 附件查看器
    public static final String SP_CHAT = "/sp/chat"; // 聊天页面

    // jsbridge
    public static final String JSBRIDGE_TEST = "submitFromWeb"; // 测试bridge
    public static final String JSBRIDGE_OPEN_MEDIA = "openMedia"; // 打开多媒体(视频，图片, 参数:url)
    public static final String JSBRIDGE_OPEN_NATIVE = "openNative"; // 打开Native页面(参数url)

    // type
    public static final byte FILE_TYPE_AUDIO = 0x00;
    public static final byte FILE_TYPE_PICTURES = 0x01;
    public static final byte FILE_TYPE_VIDEOS = 0x02;
    public static final byte FILE_TYPE_DOCUMENTS = 0x03;
    public static final byte FILE_TYPE_APPLICATIONS = 0x04;
    public static final byte FILE_TYPE_RINGTONES = 0x05;

    // IM APPID
    public static final int SDKAPPID = 1400627286;

    public static final int DEFAULT_LOAD_PAGESIZE = 10;//默认一页加载个数

    public static final String KEY_REMEMBER_PWD = "KEY_REMEMBER_PWD";//是否记住密码
    public static final String KEY_LAST_PWD = "KEY_LAST_PWD";//最近一次的密码
    public static final String KEY_LAST_ACCOUNT = "KEY_LAST_ACCOUNT";//最近一次的账号
    public static final String KEY_FIRST_SHOW_SPLASH = "KEY_FIRST_SHOW_SPLASH";//第一次打开


    public static final String TECENT_IM_APP_ID = "1400816731";//腾讯im id
    public static final String TECENT_IM_APP_SECRET = "91507c490f4c0b0997af6e7906f73730a0319d5bb72c74fc2758f46387e79ea4";//腾讯im秘钥

    public static final String WX_APP_ID = "wxb38d2c0f12bd1994";
    public static final String WX_APP_SECRET = "c4099a923f93e0ebc9cd034a609b781b";
    //微信accessToken
    public static final String PREF_KEY_WX_ACCESSTOKEN = "worldpeak.prefs.wechat.accesstoken";
    public static final String PREF_KEY_WX_OPENID = "worldpeak.prefs.wechat.openid";
    public static final String PREF_KEY_WX_REFRESHTOKEN = "worldpeak.prefs.wechat.refreshtoken";

    public static final String BUGLY_APPID="0994bc577b";//bugly的appid
    public static final String BUGLY_APPKEY="75193ca5-f4ed-47c8-a314-ed6693911724";//bugly的appkey

    public static final String UMENG_APPKEY="64d9c336ab920c276a2e4b41";//umeng的appkey

    public static final String QQ_APP_ID ="102060909";//qq的app id
    public static final String QQ_APP_KEY="xrY2KOFtJVUFm5A1";//qq的appkey



}

