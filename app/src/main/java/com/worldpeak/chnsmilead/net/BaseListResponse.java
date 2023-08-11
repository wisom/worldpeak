package com.worldpeak.chnsmilead.net;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class BaseListResponse<T> {
    private static Gson mGson = new Gson();
    int code;//0:成功
    String msg;
    Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public <T> List<T> getResult(Class<T[]> classOfT) {
        Gson gson = new Gson();
        T[] array = gson.fromJson((String)data, classOfT);
        return Arrays.asList(array);
    }

    public Object getData() {
        return data;
    }

    public boolean isSuccessful() {
        return code == 0;
    }
}
