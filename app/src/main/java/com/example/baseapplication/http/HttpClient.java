package com.example.baseapplication.http;

import com.example.baseapplication.BuildConfig;
import com.example.baseapplication.TemplateApplication;
import com.example.baseapplication.util.SharedPreferencesUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private String BASE_URL = "http://clips.vorwaerts-gmbh.de/";
    private Retrofit retrofit;

    //静态内部类实现单例模式
    private HttpClient() {

    }
    public static HttpClient getInstance() {
        return SingletonHolder.instance;
    }
    private static class SingletonHolder{
        private static final HttpClient instance = new HttpClient();
    }

    private void initRetrofit() {
        if (retrofit == null) {
            //缓存
            File cacheFile = new File(TemplateApplication.getInstance().getCacheDir(), "cache");
            //100Mb
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
            //增加头部信息
            Interceptor headerInterceptor = chain -> {
                Request.Builder builder = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json");

                String[] cookies = ((String) SharedPreferencesUtil.getParam("cookies", "")).split("-");
                for (String cookie : cookies) {
                    builder.addHeader("Cookie", cookie);
                }

                Request request = builder.build();
                return chain.proceed(request);
            };

            //增加cookie信息
            Interceptor cookieInterceptor = chain -> {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (String cookie : originalResponse.headers("Set-Cookie")) {
                        sb.append(cookie).append("-");
                    }
                    SharedPreferencesUtil.setParam("cookies", sb.length() > 0 ? sb.subSequence(0, sb.length() - 1).toString() : "");
                }
                return originalResponse;
            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.addInterceptor(headerInterceptor);
            builder.addInterceptor(cookieInterceptor);
            if (BuildConfig.DEBUG) {
                //Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                //选择拦截级别
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }
            builder.cache(cache);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build();
        }
    }

    public HttpService getApiService() {
        initRetrofit();
        return retrofit.create(HttpService.class);
    }
}
