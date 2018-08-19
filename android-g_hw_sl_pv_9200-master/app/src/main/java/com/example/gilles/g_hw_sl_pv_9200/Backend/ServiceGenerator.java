package com.example.gilles.g_hw_sl_pv_9200.Backend;

import android.util.Base64;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pietl on 11/12/2017.
 */

public class ServiceGenerator {

    private static final String BASE_URL = "http://192.168.0.128:3000/";

    private static Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();
//
//    private static HttpLoggingInterceptor logging =
//            new HttpLoggingInterceptor()
//                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
//        if (!httpClient.interceptors().contains(logging)) {
//            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
//        }

        return retrofit.create(serviceClass);
    }

    /**
     * methode die een DataInterface object aanmaakt
     * @param email
     * @param password
     * @return
     */
    public static DataInterface getRetrofit(String email, String password) {

        String credentials = email + ":" + password;
        String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .header("Authorization", basic)
                    .header("Accept","application/json")
                    .header("Content-Type","application/json")
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        });
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DataInterface.class);
    }
}
