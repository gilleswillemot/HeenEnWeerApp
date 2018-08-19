package com.example.gilles.g_hw_sl_pv_9200.Backend;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pietl on 17/12/2017.
 */

public class BackgroundService extends IntentService {



    public BackgroundService(){
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

//get client & call object for the request
        DataInterface dataInterface = retrofit.create(DataInterface.class);
        Call<List<Kost>> call = dataInterface.getKosten("5a2d50249802d505ecb61f03");
        try {
            Response<List<Kost>> result = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
