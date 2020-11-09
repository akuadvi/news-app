package com.example.android.myapplication;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {
    @GET("top-headlines")
    Call<ResponseModel> derive(@Query("country") String country,@Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ResponseModel> derive2(@Query("sources") String sources,@Query("apiKey") String apiKey);

    @GET
    Call<ResponseModel> sports(@Url String url);

    @GET
    Call<ResponseModel> articles(@Url String url);


}
