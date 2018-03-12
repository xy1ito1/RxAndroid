package com.hnkeystone.rxandroid.library.upload;

import io.reactivex.observers.DefaultObserver;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/3/12.                        
 *********************************************/

public abstract class ProgressListener<T> extends DefaultObserver<T> {

    public void onProgress(long currentBytes, long contentLength, boolean isDone) {

    }

}
