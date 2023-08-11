package com.worldpeak.chnsmilead.util;

import android.util.Log;

import com.tencent.bugly.crashreport.BuglyLog;

public class LogUtil {

    private static final boolean LOGABLE = true;

    public static void v(String tag, String msg) {
        if (!LOGABLE){
            return;
        }
        BuglyLog.v(tag, msg);
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (!LOGABLE){
            return;
        }
        BuglyLog.d(tag, msg);
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (!LOGABLE){
            return;
        }
        BuglyLog.i(tag, msg);
        Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!LOGABLE){
            return;
        }
        BuglyLog.e(tag, msg);
        Log.e(tag, msg);
    }
}
