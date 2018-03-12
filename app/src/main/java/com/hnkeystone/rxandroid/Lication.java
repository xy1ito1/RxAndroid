package com.hnkeystone.rxandroid;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.hnkeystone.rxandroid.library.http.RetrofitUtils;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/2/23.                        
 *********************************************/

public class Lication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);

        LogUtils.getConfig().setGlobalTag("CUSTOM");

        RetrofitUtils.getInstance()
                .isDebug(true)  //设置日志输出
                .setContext(this)
                .init();

    }

}
