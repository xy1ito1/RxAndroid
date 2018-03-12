package com.hnkeystone.rxandroid.library.upload;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/3/12.                        
 *********************************************/

public class UploadUtils {

    public static void upload(String baseUrl, File file, ProgressListener uploadCallback) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(25, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.51:8000/")
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //通过该行代码将RequestBody转换成特定的FileRequestBody
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(body, uploadCallback);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), progressRequestBody);

        retrofit.create(UploadService.class).upload(baseUrl, part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadCallback);
    }

}