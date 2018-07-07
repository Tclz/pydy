package com.example.a13494.myapplication.util;

import android.os.Environment;

import com.example.a13494.myapplication.entity.ClothGoods;

import java.io.File;
//import java.util.Observable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;

public class HttpMethods {

    public static void retrofitFileUpload(String uploadUrl,File file) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("imgUrl",file.getName(),
                        RequestBody.create(MediaType.parse("image/png"),file));
        RequestBody requestBody = builder.build();

        Observable<ClothGoods> call = HttpRetrofit.getInstance().getFileUploadService().upload(uploadUrl,requestBody);
        // call.enqueue(new Callback<ResponseBody>()
        // { @Override
        // public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) { Log.v("Upload", "success"); } @Override public void onFailure(Call<ResponseBody> call, Throwable t) { Log.e("Upload error:", t.getMessage()); } });

    }

}
