package com.hnkeystone.rxandroid.library.upload;

import java.io.Serializable;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/3/12.                        
 *********************************************/
public class ProgressModel implements Serializable {

    private long currentBytes;
    private long contentLength;

    public ProgressModel() {
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
        return currentBytes == contentLength;
    }

    public int getProgress() {
        int progress = (int) ((float) currentBytes / (float) contentLength * 100);
        return progress;
    }

    @Override
    public String toString() {
        return "ProgressModel{" +
                "currentBytes=" + currentBytes +
                ", contentLength=" + contentLength +
                '}';
    }
}
