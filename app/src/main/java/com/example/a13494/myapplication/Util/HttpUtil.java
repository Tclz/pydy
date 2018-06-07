package com.example.a13494.myapplication.Util;

import android.os.Environment;
import java.io.File;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public static final String sign_url="";
    public static final String img_url="";

    public static void sendOkHttpRequest(String username,String password,String code,okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .add("code",code)
                .build();
        Request request = new Request.Builder()
                .url(sign_url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void uploadImage(String imageName,okhttp3.Callback callback){

        OkHttpClient client = new OkHttpClient();
        File file = new File(Environment.getExternalStorageDirectory()+imageName);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img",imageName+".jpg",
                        RequestBody.create(MediaType.parse("image/png"),file));

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(img_url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);

    }
}
