package com.hnkeystone.rxandroid.library.upload;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/3/12.                        
 *********************************************/
public class ProgressModel {

    private long currentBytes;
    private long contentLength;
    private boolean isDone;

    public ProgressModel(long currentBytes, long contentLength, boolean isDone) {
        this.currentBytes = currentBytes;
        this.contentLength = contentLength;
        this.isDone = isDone;
    }

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
