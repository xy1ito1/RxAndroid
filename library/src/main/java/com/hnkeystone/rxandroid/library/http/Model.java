package com.hnkeystone.rxandroid.library.http;

import java.io.Serializable;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/2/26.                        
 *********************************************/

public class Model<T> implements Serializable{

    private String statusCode;

    private String message;

    private T model;

    private int code;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Model{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                ", model=" + model +
                ", code=" + code +
                '}';
    }
}
