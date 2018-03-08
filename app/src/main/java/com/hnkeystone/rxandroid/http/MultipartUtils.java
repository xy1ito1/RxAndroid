package com.hnkeystone.rxandroid.http;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/2/26.                        
 *********************************************/

public class MultipartUtils {

    /**
     * 单个文件
     *
     * @param file
     * @return
     */
    public static MultipartBody.Part getSingleFile(File file, ProgressCallBack progressCallBack) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        UploadFileRequestBody body = new UploadFileRequestBody(requestBody, progressCallBack, file);
        /**
         * 创建多部分拿上面的请求体做参数
         * img 是上传是的参数key,根据需要更改为自己的
         */
        MultipartBody.Part part =
                MultipartBody.Part.createFormData("file", file.getName(), body);
        return part;
    }

    /**
     * 多个文件
     *
     * @return
     */
    public Map<String, RequestBody> getMapFile(List<File> fileList, ProgressCallBack progressCallBack) {
        Map<String, RequestBody> params = new HashMap<>();
        for (File file : fileList) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            UploadFileRequestBody body = new UploadFileRequestBody(requestBody, progressCallBack, file);

            params.put("file\"; filename=\"" + file.getName() + "", body);
        }
        return params;
    }
}
