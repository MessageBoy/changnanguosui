package com.outsource.changnanguoshui.application;

import android.app.Application;

import com.outsource.changnanguoshui.utlis.LoggerInterceptor;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApplication extends Application
{
    private static MyApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG", true))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);

    }

    public static MyApplication getInstance()
    {
        if (null == instance)
        {
            instance = new MyApplication();
        }
        return instance;
    }
}