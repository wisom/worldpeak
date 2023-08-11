package com.worldpeak.chnsmilead.net;

public interface RetrofitListener<T> {

    public void onSuccess(T data);
    public void onError(T data,String description);
}
