package com.hnkeystone.rxandroid.library.down;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class DownloadUtils {

    private static final String TAG = "DownloadUtils";

    private static final int DEFAULT_TIMEOUT = 15;

    private Retrofit retrofit;

    private DownloadListener listener;

    private String baseUrl;

    private String downloadUrl;

    public DownloadUtils(String baseUrl, DownloadListener listener) {

        this.baseUrl = baseUrl;
        this.listener = listener;

        DownloadInterceptor mInterceptor = new DownloadInterceptor(listener);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 开始下载
     *
     * @param url
     * @param filePath
     * @param subscriber
     */
    public void download(@NonNull String url, final String filePath, Observer subscriber) {

        listener.onStartDownload();

        // subscribeOn()改变调用它之前代码的线程
        // observeOn()改变调用它之后代码的线程
        retrofit.create(DownloadService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {

                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }

                })
                .observeOn(Schedulers.computation()) // 用于计算任务
                .map(new Function<InputStream, File>() {
                    @Override
                    public File apply(InputStream inputStream) throws Exception {
                        File file = new File(filePath);
                        if (file.exists()) {
                            file.delete();
                        }
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                            byte[] b = new byte[1024];
                            int len;
                            while ((len = inputStream.read(b)) != -1) {
                                fos.write(b, 0, len);
                            }
                            inputStream.close();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            listener.onFail("FileNotFoundException");
                        } catch (IOException e) {
                            listener.onFail("IOException");
                        }

                        return file;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
//                .subscribe((Observer<? super InputStream>) subscriber);

    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param filePath
     */
    private void writeFile(InputStream inputString, String filePath) {

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();

        } catch (FileNotFoundException e) {
            listener.onFail("FileNotFoundException");
        } catch (IOException e) {
            listener.onFail("IOException");
        }

    }
}