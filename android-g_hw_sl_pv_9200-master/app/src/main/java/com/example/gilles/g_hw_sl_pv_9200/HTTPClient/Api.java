package com.example.gilles.g_hw_sl_pv_9200.HTTPClient;

import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

     String api = "/API/";

     // USERS

     @FormUrlEncoded
     @POST("/users/login")
     Call<ResponseBody> login(
         @Field("username") String username,
         @Field("password") String password
     );

     // KOSTEN

     @GET(api + "kost/{id}")
     Call<ResponseBody> getKosten(@Path("id") String huidigGezinId);

     @POST(api + "kost/{id}")
     Call<Kost> voegKostToe(/*@Header("Authorization") String token,*/
             @Path("id") String huidigGezinId, @Body Kost kost);
     @DELETE(api + "kost/{id}/{kostId}")
     Call<ResponseBody> verwijderKost(
             @Path("id") String huidigGezinId,  @Path("kostId") String kostId);

     @PUT(api + "kost/{id}")
     Call<Kost> wijzigKost(
             @Path("id") String kostId, @Body Kost kost);

     // GEZIN
     @GET(api + "gezin/{id}")
     Call<ResponseBody> getGezin(@Path("id") String huidigGezinId);
}
