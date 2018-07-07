package com.example.a13494.myapplication.util;

import com.example.a13494.myapplication.api.FileUploadService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRetrofit {

    public static final String SERVER_URL = "http://120.79.222.215:8000/";
    private Retrofit retrofit;
    public static FileUploadService fileUploadService;
    private static OkHttpClient okHttpClient ;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static FileUploadService getFileUploadService()
    {
        if(fileUploadService==null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://120.79.222.215:8000/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            fileUploadService = retrofit.create(FileUploadService.class);
        }
        return fileUploadService;
    }

    private HttpRetrofit()
    {
        okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
    }

    //在访问HttpRetrofit时创建单例
    private static class SingletonHolder{
        private static final HttpRetrofit INSTANCE = new HttpRetrofit();
    }

    //获取单例
    public static HttpRetrofit getInstance()
    {
            return SingletonHolder.INSTANCE;
    }

}
