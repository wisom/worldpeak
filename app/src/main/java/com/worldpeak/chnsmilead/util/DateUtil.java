package com.worldpeak.chnsmilead.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    /**
     * 获取当前时间并格式化： 2017-4-16 12:43:37
     */
    public static String getCurTime() {
        return formatStandard(System.currentTimeMillis());
    }

    public static String formatStandard(long timemillis) {
        DateFormat date = DateFormat.getDateTimeInstance();
        String format = date.format(new Date(timemillis));
        return format;
    }

    public static String getMMddHHmmss(long timemillis){
        SimpleDateFormat sMMddHHmmss = new SimpleDateFormat("MMddHHmmss", Locale.CHINA);
        String format = sMMddHHmmss.format(new Date(timemillis));
        return format;
    }

    public static String getyyMMddHHmmss(long timemillis){
        SimpleDateFormat sMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String format = sMMddHHmmss.format(new Date(timemillis));
        return format;
    }

    public static String getMMdd(long timemillis){
        SimpleDateFormat sMMddHHmmss = new SimpleDateFormat("MM月dd日", Locale.CHINA);
        String format = sMMddHHmmss.format(new Date(timemillis));
        return format;
    }

    public static String getWeek(Date date){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static boolean isToday(long time) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);

        return (time >= today.getTimeInMillis()) && (time < tomorrow.getTimeInMillis());
    }
}
