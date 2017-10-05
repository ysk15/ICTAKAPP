package com.example.user.ictakapp;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/*
 * Created by Ociuz on 8/1/2017.
 */

public class APIClient {

    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.3:81/POLYCARE/POLY%20CARE%20WEB%20SERVICE/")
                .client(client)
                .build();
        return retrofit;
    }

    static Retrofit getClient1() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/fcm/send")
                .client(client)
                .build();
        return retrofit;
    }
}
