package com.example.baseapplication;

import android.app.Application;

//Application全局只存在一个，所以不用做单例处理
public class TemplateApplication extends Application {
    private static TemplateApplication app;
    public static TemplateApplication getInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }
}
