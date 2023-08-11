package com.worldpeak.chnsmilead.net;


import com.worldpeak.chnsmilead.constant.Constants;
import com.worldpeak.chnsmilead.util.ConfigurationManager;

import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRequest {
    private static ICommonApi commonApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static Retrofit retrofit;

    public static ICommonApi getLoginApi() {
        commonApi = getLoginRetrofit().create(ICommonApi.class);
        return commonApi;
    }

    public static ICommonApi getCommonApi() {
        commonApi = getRetrofit().create(ICommonApi.class);
        return commonApi;
    }

    @NonNull
    private static Retrofit getLoginRetrofit() {
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.SERVER_URL_ORIGIN)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        return retrofit;
    }

    @NonNull
    private static Retrofit getRetrofit() {
        String baseUrl = ConfigurationManager.instance().getString(Constants.PREF_KEY_URL);
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        return retrofit;
    }

//    public static void setBaseUrl(String baseUrl) {
//        if (commonApi == null) {
//            retrofit = new Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(gsonConverterFactory)
//                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
//                    .build();
//        }
//    }

    @NonNull
    protected static Callback<BaseResponse> getCallback(final RetrofitListener<BaseResponse> retrofitListener) {
        return new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onSuccess(response.body());
                } else {
                    retrofitListener.onError(response.body(), response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        };
    }


    @NonNull
    protected static Callback<BaseResponse> getCallback2(final RetrofitListener<BaseResponse> retrofitListener) {
        return new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onSuccess(response.body());
                } else {
                    retrofitListener.onError(response.body(), response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        };
    }
}
