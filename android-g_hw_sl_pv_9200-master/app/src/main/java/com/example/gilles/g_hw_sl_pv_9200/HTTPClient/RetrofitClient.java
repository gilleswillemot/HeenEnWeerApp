package com.example.gilles.g_hw_sl_pv_9200.HTTPClient;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.0.128:3000/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(Context context){
        SharedPreferences prefs =  context.getSharedPreferences("myPref", MODE_PRIVATE);
        String token = prefs.getString("token", "0");

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        retrofit = new Retrofit.Builder().client(client).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetrofitClient getInstance(Context c){
        if (mInstance == null){
            mInstance = new RetrofitClient(c);
        }
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
