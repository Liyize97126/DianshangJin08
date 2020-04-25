package com.bawei.dianshangjin08.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit工具类
 */
public class RetrofitUtil {
    //定义
    private Retrofit retrofit;
    //单例
    private static final RetrofitUtil RETROFIT_UTIL = new RetrofitUtil();
    private RetrofitUtil() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //定义OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        //初始化Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://mobile.bwstudent.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
    public static RetrofitUtil getRetrofitUtil() {
        return RETROFIT_UTIL;
    }
    //请求
    public <T> T create(final Class<T> service){
        return retrofit.create(service);
    }
}
