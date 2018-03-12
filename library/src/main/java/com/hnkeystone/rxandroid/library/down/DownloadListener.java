package com.hnkeystone.rxandroid.library.down;

/*********************************************
****    Copyright (C)  2018
****    河南坚磐电子科技有限公司
****    All Rights Reserved
****    Author:HC
****    Schema:下载进度回调
****    2018/3/12.
*********************************************/
public interface DownloadListener {

    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload();

    void onFail(String errorInfo);

}