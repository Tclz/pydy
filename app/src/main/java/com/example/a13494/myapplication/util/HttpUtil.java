package com.example.a13494.myapplication.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;


import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    //服务器地址
    private  final String host="http://120.79.222.215:8000";
    //POST操作
    private  final String user_login ="/user/login"; //用户登录
    private  final String img_url="/file/";     //搜图
    private  final String user_register = " "; //用户注册


    private OkHttpClient client;
    private static HttpUtil httpUtil;

    public static HttpUtil getInstance()
    {
        if(httpUtil!=null) return httpUtil;
        else {
            httpUtil = new HttpUtil();
            return httpUtil;
        }
    }
    private HttpUtil()
    {
        client=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
    }


    public void sendLoginMsg(String username,String password,Callback callback)
    {
        RequestBody body = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        requestIncludeRequestBody(user_login,body,callback);
    }

    public void sendRegisterMsg(String username,String password,String code,Callback callback)
    {
        RequestBody body = new FormBody.Builder()
                .add("username",username)
                .add("password",username)
                .add("code",code)
                .build();
        requestIncludeRequestBody(user_register,body,callback);
    }

    public void uploadImage(File file,okhttp3.Callback callback){

        //File file = new File(Environment.getExternalStorageDirectory()+imageName);
        Log.d("UPLoad_File",file.getName());
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("imgUrl",file.getName(),
                        RequestBody.create(MediaType.parse("image/png"),file));

        RequestBody requestBody = builder.build();
        requestIncludeRequestBody(img_url,requestBody,callback);
    }

    //GET 操作
    public void requestImgFromUrl(String requestImgLink,Callback callback)
    {
          Request request = new Request.Builder()
                  .url(requestImgLink)
                  .build();
          client.newCall(request).enqueue(callback);
    }


    //POST请求封装方法
    private void requestIncludeRequestBody(String action,RequestBody body,Callback callback)
    {
        Request request = new Request.Builder()
                .url(host+action)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
