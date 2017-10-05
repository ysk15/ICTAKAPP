package com.example.user.ictakapp;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by Ociuz on 8/1/2017.
 */

public interface APIInterface {

    @FormUrlEncoded
    @POST("loginemp.php")
    Call<ResponseBody> login(@Field("uname") String user, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("statusupdate.php")
    Call<ResponseBody> status(@Field("cid") String cid, @Field("status") String status);

@FormUrlEncoded
@POST("locationenter.php")
Call<ResponseBody> sendlocation(@Field("lat") String lat, @Field("log") String log, @Field("wrkid") String wrkid);

    @GET("foodlist.php")
    Call<ResponseBody> gettests();

    @FormUrlEncoded
    @POST("booking.php")
    Call<ResponseBody> testconfirm(@Field("tstname") String tname, @Field("btot") String trate, @Field("pid") String usrid, @Field("pmode") String pmode, @Field("image") String image);

        @POST("/fcm/send")
        Call<ResponseBody> sendMessage(@HeaderMap Map<String,String> map, @Body Message message);


}
