package com.hnkeystone.rxandroid.library.http;

import com.hnkeystone.rxandroid.library.model.Result;
import com.hnkeystone.rxandroid.library.model.Wether;

import java.util.List;
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
    //   public static final String BASE_URL = "https://rcapi.zhengzhou.gov.cn/";
//    public static final String BASE_URL = "http://192.168.1.51:8080/";
//    public static final String BASE_URL = "http://192.168.1.100:8000/";
    public static final String BASE_URL = "http://apicloud.mob.com/";


    /**
     * @param user
     * @return
     */
    @GET("food/list/")
    Observable<Model> listRepos(@Path("user") String user, @Path("id") String id);

    @Multipart
    @POST("wisdomZZInter/login/uploadApk.do")
    Observable<ResponseBody> upload(@Part MultipartBody.Part file);

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
    Observable<Model<Model2>> opinionMessage(@QueryMap Map<String, String> map);

    @GET("/file/app/智汇郑州服务/app-release.apk")
    Call<ResponseBody> down();

    @GET("v1/weather/query")
    Observable<Wether<List<Result>>> weather(@QueryMap Map<String, String> map);
}
