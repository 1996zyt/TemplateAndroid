package com.example.baseapplication.http;

import com.example.baseapplication.response.BaseResponse;
import com.example.baseapplication.response.TemplateResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface HttpService {

    /**
     * GET请求示例
     */
    @GET("template/getTest")
    Observable<TemplateResponse> getTest(@Query("test") String test,
                                         @Query("version") int version,
                                         @Query("token") String token);

    /**
     * POST请求示例
     */
    @FormUrlEncoded
    @POST("template/postTest")
    Observable<TemplateResponse> postTest(@Field("test") String test,
                                          @Field("test") int version,
                                          @Field("test") String token);

    /**
     * 文件上传示例
     */
    @Multipart
    @POST("template/fileUploadTest")
    Observable<BaseResponse> fileUploadTest(@Part MultipartBody.Part file,
                                            @Query("version") int version,
                                            @Query("token") String token);

    /**
     * 文件下载示例
     * 如果下载大文件的一定要加上  @Streaming  注解
     */
    @GET
    Call<ResponseBody> fileDownloadTest(@Url String fileUrl);


    /*
     *以JSON格式提交示例
     */
    @POST(".")
    Observable<TemplateResponse> postJsonTest(@Body RequestBody request);
}
