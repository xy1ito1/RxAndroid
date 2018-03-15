package com.hnkeystone.rxandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hnkeystone.rxandroid.library.http.APIService;
import com.hnkeystone.rxandroid.library.http.BaseObserver;
import com.hnkeystone.rxandroid.library.http.RetrofitUtils;
import com.hnkeystone.rxandroid.library.http.Transformer;
import com.hnkeystone.rxandroid.library.model.Result;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.disposables.CompositeDisposable;

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

    /**
     * 如果在请求的过程中Activity已经退出了, 这个时候如果回到主线程去更新UI, 那么APP肯定就崩溃了
     * 使用Disposable 是个开关, 调用它的dispose()方法时就会切断水管, 使得下游收不到事件, 既然收不到事件,
     * 那么也就不会再去更新UI了. 因此我们可以在Activity中将这个Disposable 保存起来, 当Activity退出时,
     * 切断它即可.
     * RxJava中已经内置了一个容器CompositeDisposable,
     * 每当我们得到一个Disposable时就调用
     * CompositeDisposable.add()将它添加到容器中,
     * 在退出的时候, 调用CompositeDisposable.clear()
     * 即可切断所有的水管.
     */
    private CompositeDisposable disposables = new CompositeDisposable();

    public ProgressDialog pd;
    private boolean showLoading = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_activity);

        progress2 = findViewById(R.id.progress);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl1, new FL1Fragment());
        fragmentTransaction.add(R.id.fl2, new FL2Fragment());
        fragmentTransaction.commit();

        pd = new ProgressDialog(this);
        Map<String, String> params = new TreeMap<>();
        params.put("key", "1e5260888a805");
        params.put("city", "郑州");
        RetrofitUtils.getInstance().getService(APIService.class).weather(params)
                .compose(new Transformer(pd))
                .subscribe(new BaseObserver<List<Result>>(FMActivity.this, pd) {
                    @Override
                    public void onHandleSuccess(List<Result> s) {
                        LogUtils.d(s.toString());
                    }
                });

        MobPush.setAlias("test");//设置别名
//        MobPush.addTags(java.lang.String[]tags);//设置标签.

        MobPush.addPushReceiver(new MobPushReceiver() {
            @Override
            public void onCustomMessageReceive(Context context, MobPushCustomMessage message) {
                //接收自定义消息
                LogUtils.d("onCustomMessageReceive");
            }
            @Override
            public void onNotifyMessageReceive(Context context, MobPushNotifyMessage message) {
                //接收通知消息

                LogUtils.d("onNotifyMessageReceive");
            }

            @Override
            public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage message) {
                //接收通知消息被点击事件
                LogUtils.d("onNotifyMessageOpenedReceive");
            }
            @Override
            public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {
                //接收tags的增改删查操作
                LogUtils.d("onTagsCallback");
            }
            @Override
            public void onAliasCallback(Context context, String alias, int operation, int errorCode) {
                //接收alias的增改删查操作
                LogUtils.d("onAliasCallback");
            }
        });

        //加进度
//        pd = new ProgressDialog(this);
//        Map<String, String> params = new TreeMap<>();
//        params.put("ueseId", "1734");
//        params.put("opinionId", "15");
//        RetrofitUtils.getInstance().getService(APIService.class).opinionMessage(params)
//                .compose(new Transformer(pd))
//                .map(new Function<Model<Model2>, Model<Model2>>() {
//                    //数据转换
//                    @Override
//                    public Model<Model2> apply(Model<Model2> o) throws Exception {
//                        o.getModel().setContent("123");
//                        return o;
//                    }
//                })
//                .subscribe(new BaseObserver<Model2>(FMActivity.this, pd) {
//                    @Override
//                    public void onHandleSuccess(Model2 s) {
//                        LogUtils.d(s.toString());
//                    }
//                });

        //发送单一数据
//        Single.just(23)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        LogUtils.d(integer);
//                    }
//                });

        //延时
//        Observable.interval(2, TimeUnit.SECONDS)
//                .take(10)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        LogUtils.d(aLong);
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

//        Observable.range(1, 10).take(3).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                LogUtils.d(integer);
//            }
//        });

//        Observable.just(2, 4, 1, 3)
//                .delaySubscription(5, TimeUnit.SECONDS)
//                .toSortedList()
//                .subscribe(new Consumer<List<Integer>>() {
//                    @Override
//                    public void accept(List<Integer> integers) {
//                        LogUtils.d("toSortedList onNext:" + integers);
//                    }
//                });

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

        //点击
        progress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new TreeMap<>();
                params.put("ueseId", "1734");
                params.put("opinionId", "15");
//                RetrofitUtils.getInstance().getService(APIService.class).opinionMessage(params)
//                        .subscribeOn(Schedulers.io())
//                        .doOnNext(new Consumer<ResponseBody>() {
//                            @Override
//                            public void accept(ResponseBody s) throws Exception {
//                                LogUtils.d("doOnNext---accept");
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<ResponseBody>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//                                //保存Disposable
//                                disposables.add(d);
//                                LogUtils.d("subscribe---onSubscribe");
//                            }
//
//                            @Override
//                            public void onNext(ResponseBody s) {
//                                try {
//                                    LogUtils.d("subscribe---onNext" + s.string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                e.printStackTrace();
//                                LogUtils.d("subscribe---onError");
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                LogUtils.d("subscribe---onComplete");
//                            }
//                        });
            }
        });


        //简单用法
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

        //上传文件
//        List<String> sdCardPaths = SDCardUtils.getSDCardPaths();
//        File file = new File(sdCardPaths.get(0) + File.separator + "123.apk");
//        UploadUtils.upload("/Upload/upload.do", file, new ProgressListener<ResponseBody>() {
//            @Override
//            public void onNext(ResponseBody responseBody) {
//                try {
//                    String string = responseBody.string();
//                    LogUtils.d(string);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//
//            @Override
//            public void onProgress(ProgressModel progressModel) {
//                LogUtils.d(progressModel.getCurrentBytes() + "-------" + progressModel.getContentLength() + "-------" + progressModel.getProgress());
////                LogUtils.d(contentLength + "+++++++" + contentLength + "+++++++" + isDone);
//            }
//        });


        //下载文件
//        List<String> sdCardPaths = SDCardUtils.getSDCardPaths();
//        File file = new File(sdCardPaths.get(0) + File.separator + "123.apk");
//        new DownloadUtils("http://192.168.1.51:8080", new DownloadListener() {
//            @Override
//            public void onStartDownload() {
//
//            }
//
//            @Override
//            public void onProgress(int progress) {
//                LogUtils.d(progress);
//            }
//
//            @Override
//            public void onFinishDownload() {
//
//            }
//
//            @Override
//            public void onFail(String errorInfo) {
//
//            }
//        }).download("/file/app/智汇郑州服务/app-release.apk", file.getPath(), new Observer<File>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(File file) {
//                LogUtils.d(file.getAbsolutePath());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

    @Override
    public void onButtonClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时断所有连接
        disposables.clear();
    }

}
