package com.tencent.qcloud.tuikit.tuichat.bean;

import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatConstants;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;

import java.io.Serializable;

public class CustomHelloMessage implements Serializable {
    public static final int CUSTOM_HELLO_ACTION_ID = 3;

    public String businessID = TUIChatConstants.BUSINESS_ID_CUSTOM_HELLO;
    public String text = "欢迎来到中国微校"; //TUIChatService.getAppContext().getString(R.string.welcome_tip);
    public String link = "https://www.baidu.com/";

    public int version = TUIChatConstants.JSON_VERSION_UNKNOWN;
}
