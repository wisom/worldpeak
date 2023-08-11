package com.worldpeak.chnsmilead.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.worldpeak.chnsmilead.MyApp;

public class WebUtil {

    private static String TAG = "WebUtil==";

    /**
     * 调用第三方浏览器打开
     *
     * @param context
     * @param url     要浏览的资源地址
     */
    public static void openBrowser(String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(MyApp.getInstance().getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(MyApp.getInstance().getPackageManager());
            // 打印Log   ComponentName到底是什么
            Log.d(TAG, "componentName = " + componentName.getClassName());
            Intent newIntent = Intent.createChooser(intent, "请选择浏览器");
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApp.getInstance().startActivity(newIntent);
        } else {
            Toast.makeText(MyApp.getInstance().getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }
}
