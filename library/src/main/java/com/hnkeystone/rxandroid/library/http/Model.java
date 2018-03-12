package com.hnkeystone.rxandroid.library.http;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/2/26.                        
 *********************************************/

public class Model {

    private String statusCode;

    private String message;

    private String model;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Model{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
