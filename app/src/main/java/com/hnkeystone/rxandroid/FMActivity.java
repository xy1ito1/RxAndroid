package com.hnkeystone.rxandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hnkeystone.rxandroid.http.APIService;
import com.hnkeystone.rxandroid.http.RetrofitUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/2/23.                        
 *********************************************/

public class FMActivity extends AppCompatActivity implements FGinterfae {

    private final static String TAG = "rxAndroid";

    private TextView progress2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_activity);

        progress2 = findViewById(R.id.progress);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl1, new FL1Fragment());
        fragmentTransaction.add(R.id.fl2, new FL2Fragment());
        fragmentTransaction.commit();

        Observable.just(2, 4, 1, 3)
                .delaySubscription(5, TimeUnit.SECONDS)
                .toSortedList()
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) {
                        LogUtils.d("toSortedList onNext:" + integers);
                    }
                });

//        Observable.just(2, 4, 1, 3)
//                .delaySubscription(7, TimeUnit.SECONDS)  //延迟5s订阅
//                .toMultimap(new Function<Integer, String>() {
//                    //生成map的key
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        return integer % 2 == 0 ? "偶" : "奇";
//                    }
//                }, new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        return integer % 2 == 0 ? "偶" + integer : "奇" + integer;
//                    }
//                })
//                .subscribe(new Consumer<Map<String, Collection<String>>>() {
//                    @Override
//                    public void accept(Map<String, Collection<String>> stringCollectionMap) throws Exception {
//                        Collection<String> o = stringCollectionMap.get("偶");
//                        Collection<String> j = stringCollectionMap.get("奇");
//                        LogUtils.d("toMultimap onNext:" + o);
//                        LogUtils.d("toMultimap onNext:" + j);
//                    }
//                });

        progress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new TreeMap<>();
                params.put("ueseId", "1734");
                params.put("opinionId", "15");
                RetrofitUtils.getInstance().getService(APIService.class).opinionMessage(params)
                        .subscribeOn(Schedulers.io())
                        .doOnNext(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody s) throws Exception {
                                LogUtils.d("doOnNext---accept");
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                LogUtils.d("subscribe---onSubscribe");
                            }

                            @Override
                            public void onNext(ResponseBody s) {
                                try {
                                    LogUtils.d("subscribe---onNext" + s.string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                LogUtils.d("subscribe---onError");
                            }

                            @Override
                            public void onComplete() {
                                LogUtils.d("subscribe---onComplete");
                            }
                        });
            }
        });


//        RetrofitUtils.getInstance().getService(APIService.class).listRepos("张三", "111")//获取Observable对象
//                .subscribeOn(Schedulers.io())//请求在新的线程中执行
//                .doOnNext(new Consumer<Model>() {
//                    @Override
//                    public void accept(Model model) throws Exception {
//                        model.setStatusCode("65165156156156156");
//                    }
//                })
//                .flatMap(new Function<Model, ObservableSource<Model>>() {
//                    @Override
//                    public ObservableSource<Model> apply(Model model) throws Exception {
//
//                        return RetrofitUtils.getInstance().getService(APIService.class).listRepos("123", "123");
//                    }
//                })
//                .map(new Function<Model, Model>() {
//                    @Override
//                    public Model apply(Model model) throws Exception {
//                        Model r = new Model();
//                        r.setMessage(model.getMessage());
//                        return r;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Model>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(Model aBeanBaseEntity) {
//                        LogUtils.d(aBeanBaseEntity.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//        List<String> sdCardPaths = SDCardUtils.getSDCardPaths();
//        File file = new File(sdCardPaths.get(0) + File.separator + "123.jpg");
//
//        ProgressCallBack progressCallBack = new ProgressCallBack() {
//            @Override
//            public void onLoading(final long total, final long progress) {
////                LogUtils.d(total + "---" + progress);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progress2.setText(total / 1024 + "===" + progress);
//                    }
//                });
//            }
//        };
//
//        MultipartBody.Part singleFile = MultipartUtils.getSingleFile(file, progressCallBack);
//        RetrofitUtils.getInstance().getService(APIService.class).upload(singleFile).enqueue(new Callback<Model>() {
//            @Override
//            public void onResponse(Call<Model> call, Response<Model> response) {
//
//                Thread thread = Thread.currentThread();
//                Looper mainLooper = Looper.getMainLooper();
//
//                LogUtils.d(thread.getName() + "===" + mainLooper.toString());
//
//                Model body1 = response.body();
//                LogUtils.d(body1);
//            }
//
//            @Override
//            public void onFailure(Call<Model> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void onButtonClick() {

    }

}
