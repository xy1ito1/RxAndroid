package com.hnkeystone.rxandroid.library.model;

import java.io.Serializable;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/3/15.                        
 *********************************************/

public class Wether<T> implements Serializable {
    private String msg;
    private T result;
    private String retCode;

    private int code;
    private String errorMsg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "Wether{" +
                "msg='" + msg + '\'' +
                ", result=" + result +
                ", retCode='" + retCode + '\'' +
                ", code=" + code +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
