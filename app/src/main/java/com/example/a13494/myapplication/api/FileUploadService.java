package com.example.a13494.myapplication.api;

import com.example.a13494.myapplication.entity.ClothGoods;

import okhttp3.MultipartBody;

//import okhttp3.RequestBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

public interface FileUploadService {

    @Multipart
    @POST()
    Observable<ClothGoods> upload(@Url() String url, @Part("") RequestBody Body);

}
