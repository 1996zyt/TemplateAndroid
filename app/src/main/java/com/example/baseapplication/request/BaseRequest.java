package com.example.baseapplication.request;

import com.example.baseapplication.BuildConfig;

public class BaseRequest {
    private int version = BuildConfig.VERSION_CODE;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
