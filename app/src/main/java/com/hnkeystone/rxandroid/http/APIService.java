package com.hnkeystone.rxandroid.http;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/*********************************************
 ****    Copyright (C)  2018
 ****    河南坚磐电子科技有限公司
 ****    All Rights Reserved
 ****    Author:HC
 ****    Schema:
 ****    2018/2/26.
 *********************************************/
public interface APIService {

    public static final String BASE_URL = "https://rcapi.zhengzhou.gov.cn/";

    /**
     * @param user
     * @return
     */
    @GET("food/list/")
    Observable<Model> listRepos(@Path("user") String user, @Path("id") String id);

    @Multipart
    @POST("organization/uploadAvator.do")
    Call<Model> upload(@Part MultipartBody.Part file);

    /**
     * 适用于多文件
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("organization/uploadAvator.do")
    Call<Model> upload(@PartMap Map<String, RequestBody> params);

    @POST("inter/talents/opinionMessage.do")
    Observable<ResponseBody> opinionMessage(@QueryMap Map<String, String> map);
}
