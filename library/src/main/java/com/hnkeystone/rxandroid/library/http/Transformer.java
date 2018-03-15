package com.hnkeystone.rxandroid.library.http;

import android.app.ProgressDialog;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema: 网络请求转换基类
 ***      Author: HC 
 **       2018/3/15.                        
 *********************************************/
public class Transformer implements ObservableTransformer {

    private ProgressDialog pd;

    public Transformer(ProgressDialog pd) {
        this.pd = pd;
    }

    @Override
    public ObservableSource apply(Observable upstream) {
        return upstream.retry(1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (NetworkUtils.isConnected()) {
                            if (pd != null && !pd.isShowing()) {
                                pd.show();
                            }
                        } else {
                            ToastUtils.showShort("网络连接异常，请检查网络");
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
