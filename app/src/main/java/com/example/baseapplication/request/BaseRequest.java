package com.example.baseapplication.request;

import com.example.baseapplication.BuildConfig;
import com.example.baseapplication.util.SharedPreferencesUtil;

public class BaseRequest {
    private String token = (String) SharedPreferencesUtil.getParam("token","");
    private int version = BuildConfig.VERSION_CODE;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
