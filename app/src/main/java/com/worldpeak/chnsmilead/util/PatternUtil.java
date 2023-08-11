package com.worldpeak.chnsmilead.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

    public static boolean checkPwd(String pwd) {
        String s = "^(?=.*[a-z])(?=.*\\d){8,16}$";
        Pattern p = Pattern.compile(s);
        Matcher matcher = p.matcher(pwd);
        return matcher.matches();
    }
}
