package com.example.chl.campusnews.Http;

/**
 * 从org.apache.http.legacy包转换过来，做用法上的兼容，使修改的代码减少
 * Created by hydro on 2016/9/20.
 */
public class NameValuePair {
    private String name;
    private String value;

    public NameValuePair() {
    }

    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}

