package com.hnkeystone.rxandroid.library.http;

import java.io.Serializable;

/*********************************************
 ***      Copyright (C) 2017               
 ***      河南坚磐电子科技有限公司        
 ***      All Rights Reserved             
 ***      Schema:                       
 ***      Author: HC 
 **       2018/3/15.                        
 *********************************************/
public class Model2 implements Serializable {
    private String content;

    private String title;
    private String type_name;
    private String create_time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Model2{" +
                "content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", type_name='" + type_name + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
