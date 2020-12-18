package com.example.baseapplication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.baseapplication.BuildConfig;
import com.example.baseapplication.R;
import com.example.baseapplication.adapter.TemplateAdapter;
import com.example.baseapplication.event.TemplateEvent;
import com.example.baseapplication.http.HttpClient;
import com.example.baseapplication.request.TemplateRequest;
import com.example.baseapplication.response.BaseResponse;
import com.example.baseapplication.response.TemplateBean;
import com.example.baseapplication.response.TemplateResponse;
import com.example.baseapplication.util.SharedPreferencesUtil;
import com.example.baseapplication.view.CommonDialog;
import com.example.baseapplication.view.ToastMessage;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemplateActivity extends AppCompatActivity {
    //RecyclerView用
    private List<TemplateBean> beans;
    private TemplateAdapter adapter;

    //网络请求用
    private String token = (String) SharedPreferencesUtil.getParam("token","");
    private int version = BuildConfig.VERSION_CODE;

    private CommonDialog commonDialog;

    //ButterKnife和插件Android ButterKnife Zelezny一起使用，效率高
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_list)
    RecyclerView rlList;

    @OnClick({R.id.iv_back, R.id.tv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_menu:
                break;
        }
    }

    //EventBus用于组件通信，替代广播
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TemplateEvent event) {

    };

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initView();
        //Android 6.0之后的动态权限
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {

                    } else {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    public void initView(){
        //初始化RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlList.setLayoutManager(layoutManager);
        beans = new ArrayList<>();
        adapter = new TemplateAdapter(R.layout.item_template, beans);
        //点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });
        //子项点击事件
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

            }
        });
        rlList.setAdapter(adapter);

        commonDialog = new CommonDialog(this);
        commonDialog.setTitle("标题");
        commonDialog.setContent("正文");
        commonDialog.setMenuAndClick("取消", "确定", new CommonDialog.CancelButtonClickListener() {
            @Override
            public void onclick() {
                commonDialog.dismiss();
            }
        }, new CommonDialog.OkButtonClickListener() {
            @Override
            public void onclick() {
                commonDialog.dismiss();
            }
        });
        commonDialog.show();
    }
    //各类网络请求示例
    public void getTest(){
        HttpClient.getInstance().getApiService().getTest("test",version,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TemplateResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TemplateResponse value) {
                        if (value.isSuccess()){
                            if (value.getList() != null){
                                beans.clear();
                                beans.addAll(value.getList());
                                adapter.setNewInstance(beans);
                            }
                        }else {
                            ToastMessage.show(value.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postTest(){
        HttpClient.getInstance().getApiService().postTest("test",version,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TemplateResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TemplateResponse value) {
                        if (value.isSuccess()){
                            if (value.getList() != null){
                                beans.clear();
                                beans.addAll(value.getList());
                                adapter.setNewInstance(beans);
                            }
                        }else {
                            ToastMessage.show(value.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void fileUploadTest(){
        //文件地址
        String filePath = "";
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("video/mp4"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        HttpClient.getInstance().getApiService().fileUploadTest(body,version,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            ToastMessage.show("上传成功");
                        }else {
                            ToastMessage.show(baseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void fileDownloadTest(){
        //下载地址
        String downloadUrl = "big_buck_bunny.mp4";
        HttpClient.getInstance().getApiService().fileDownloadTest(downloadUrl).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.isSuccessful()) {
                    boolean toDisk = writeResponseBodyToDisk(response.body(),"test.mp4");
                    if (toDisk) {
                        ToastMessage.show("下载成功请查看");
                    } else {
                        ToastMessage.show("下载失败,请稍后重试");
                    }
                } else {
                    ToastMessage.show("服务器返回错误");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void postJsonTest(){
        TemplateRequest templateRequest = new TemplateRequest();
        String request = new Gson().toJson(templateRequest);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),request);

        HttpClient.getInstance().getApiService().postJsonTest(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TemplateResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TemplateResponse templateResponse) {
                        if (templateResponse.isSuccess()){
                            if (templateResponse.getList() != null){
                                beans.clear();
                                beans.addAll(templateResponse.getList());
                                adapter.setNewInstance(beans);
                            }
                        }else {
                            ToastMessage.show(templateResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 下载到本地
     * @param body 内容
     * @return 成功或者失败
     */
    private boolean writeResponseBodyToDisk(ResponseBody body,String filename) {
        try {
            //判断文件夹是否存在
            File files = new File("./sdcard/Download/");//跟目录一个文件夹
            if (!files.exists()) {
                //不存在就创建出来
                files.mkdirs();
            }
            //创建一个文件
            File futureStudioIconFile = new File("./sdcard/Download/" + filename);
            //初始化输入流
            InputStream inputStream = null;
            //初始化输出流
            OutputStream outputStream = null;
            try {
                //设置每次读写的字节
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                //请求返回的字节流
                inputStream = body.byteStream();
                //创建输出流
                outputStream = new FileOutputStream(futureStudioIconFile);
                //进行读取操作
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    //进行写入操作
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                //刷新
                outputStream.flush();
                //通知本地刷新
                Uri uri = Uri.fromFile(futureStudioIconFile);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                sendBroadcast(intent);
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    //关闭输入流
                    inputStream.close();
                }
                if (outputStream != null) {
                    //关闭输出流
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}