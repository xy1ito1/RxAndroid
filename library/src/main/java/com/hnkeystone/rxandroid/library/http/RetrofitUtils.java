package com.hnkeystone.rxandroid.library.http;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/2/26.                        
 *********************************************/

public class RetrofitUtils {

    private Application context;

    private Retrofit retrofit;

    private OkHttpClient okHttpClient;

    private Map<String, String> headers;

    private boolean isLog = true;

    /**
     * ScalarsConverterFactory
     * GsonConverterFactory
     */
    private Converter.Factory factory = GsonConverterFactory.create();

    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 20;

    private static final int NETWORK_NO = 0;

    private Handler requestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NETWORK_NO:
                    Toast.makeText(context, "暂时无网络连接!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private RetrofitUtils() {

    }

    public void init() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setLevel(isLog ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        if (NetworkUtils.isConnected()) {
                            return chain.proceed(chain.request());
                        } else {
                            requestHandler.sendEmptyMessage(NETWORK_NO);
                        }
                        return null;
                    }
                })
                //添加请求头
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder();
                        if (headers != null)
                            //获取手机UA信息
                            requestBuilder.header("User-Agent", getUserAgent());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                //拦截日志
                .addInterceptor(loggingInterceptor)
                //失败重连
                .retryOnConnectionFailure(false)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                //time out
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)//20s写入超时
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        if (retrofit == null) {
            synchronized (RetrofitUtils.class) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(APIService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(factory)
                        .client(okHttpClient)
                        .build();
            }
        }
    }

    public static RetrofitUtils getInstance() {
        return HTTPHolder.holder;
    }

    private static class HTTPHolder {
        private static RetrofitUtils holder = new RetrofitUtils();
    }

    public RetrofitUtils isDebug(boolean isLog) {
        this.isLog = isLog;
        return this;
    }

    public RetrofitUtils setConverterFactory(Converter.Factory factory) {
        this.factory = factory;
        return this;
    }

    public <T> T getService(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    public RetrofitUtils addHeaders(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * User-Agent: Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; Redmi Note 3 Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36
     */
    private String getUserAgent() {
        String userAgent = "";
        if (TextUtils.isEmpty(userAgent)) {
            String webUserAgent = null;
            try {
                Class<?> sysResCls = Class.forName("com.android.internal.R$string");
                Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
                Integer resId = (Integer) webUserAgentField.get(null);
                webUserAgent = getContext().getString(resId);
            } catch (Exception e) {
                // We have nothing to do
            }
            if (TextUtils.isEmpty(webUserAgent)) {
                webUserAgent = "okhttp-Retrofit/android";
            }

            Locale locale = Locale.getDefault();
            StringBuffer buffer = new StringBuffer();
            // Add version
            final String version = Build.VERSION.RELEASE;
            if (version.length() > 0) {
                buffer.append(version);
            } else {
                // default to "1.0"
                buffer.append("1.0");
            }
            buffer.append("; ");
            final String language = locale.getLanguage();
            if (language != null) {
                buffer.append(language.toLowerCase(locale));
                final String country = locale.getCountry();
                if (!TextUtils.isEmpty(country)) {
                    buffer.append("-");
                    buffer.append(country.toLowerCase(locale));
                }
            } else {
                // default to "en"
                buffer.append("en");
            }
            // add the model for the release build
            if ("REL".equals(Build.VERSION.CODENAME)) {
                final String model = Build.MODEL;
                if (model.length() > 0) {
                    buffer.append("; ");
                    buffer.append(model);
                }
            }
            final String id = Build.ID;
            if (id.length() > 0) {
                buffer.append(" Build/");
                buffer.append(id);
            }
            userAgent = String.format(webUserAgent, buffer, "Mobile ");
            return userAgent;
        }
        return userAgent;
    }


    /**
     * 必须在全局Application先调用，获取context上下文，否则缓存无法使用
     */
    public RetrofitUtils setContext(Application app) {
        context = app;
        return this;
    }

    /**
     * 获取全局上下文
     */
    public Context getContext() {
        if (context == null) {
            throw new NullPointerException("please call RetrofitUtils.getInstance().setContext() first in application!");
        }
        return context;
    }
}
