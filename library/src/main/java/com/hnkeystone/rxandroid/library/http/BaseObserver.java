package com.hnkeystone.rxandroid.library.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.hnkeystone.rxandroid.library.model.Wether;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/*********************************************
 ****    Copyright (C)  2018
 ****    河南坚磐电子科技有限公司
 ****    All Rights Reserved
 ****    Author:HC
 ****    Schema:数据处理基类
 ****    2018/3/15.
 *********************************************/
public abstract class BaseObserver<T> implements Observer<Wether<T>> {
    private Context mContext;
    private ProgressDialog mDialog;
    private Disposable mDisposable;
    private final int SUCCESS_CODE = 200;

    public BaseObserver(Context context, ProgressDialog dialog) {
        mContext = context;
        mDialog = dialog;

        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mDisposable.dispose();
            }
        });
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(Wether<T> value) {
        if (value.getRetCode().equals("200")) {
            T t = value.getResult();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getCode(), value.getErrorMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e(e.getMessage());

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }

        Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete() {
        LogUtils.i("onComplete");

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public abstract void onHandleSuccess(T t);

    void onHandleError(int code, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}  