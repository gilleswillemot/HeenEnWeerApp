package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.ChatFragment;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.MessageListFragment;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;
import com.example.gilles.g_hw_sl_pv_9200.Models.ChatMessage;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gesprek;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Messenger_Activity extends AppCompatActivity implements MessageListFragment.Callback {


    private ArrayList<Gesprek> gesprekken = new ArrayList<>();
    private static FragmentManager fragmentManager;
    private TextView txtGeenGesprekken;


    public ArrayList<Gesprek> getGesprekken() {
        return gesprekken;
    }

    /**
     * aanmaken van de messenger activity
     * ophalen van gesprekken om de lijst te vullen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
        Call<List<Gesprek>> call = dataInterface.getGesprekken(Utils.USERID);
        call.enqueue(new Callback<List<Gesprek>>() {
            @Override
            public void onResponse(Call<List<Gesprek>> call, Response<List<Gesprek>> response) {
                if(response.body()!=null){
                    for (Gesprek gspr: response.body()) {
                        gesprekken.add(gspr);
                        Log.d("gesprekken","opgehaald");
                    }
                }
                setContentView(R.layout.activity_messenger);
                txtGeenGesprekken = (TextView) findViewById(R.id.txtGeenGesprekken);
                if(response.body()==null){txtGeenGesprekken.setVisibility(View.VISIBLE);}
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
            }

            @Override
            public void onFailure(Call<List<Gesprek>> call, Throwable t) {
                Log.d("Error:",t.getMessage());
            }
        });




        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    @Override
    public void onListFragmentInteraction(Gesprek item) {
        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
        ArrayList<ChatMessage> berichtenArray = new ArrayList<>();
        Call<List<ChatMessage>> call2 = dataInterface.getBerichtenVanGesprek(item.getId());
        call2.enqueue(new Callback<List<ChatMessage>>() {
            @Override
            public void onResponse(Call<List<ChatMessage>> call, Response<List<ChatMessage>> response) {
                for (ChatMessage cm:response.body()) {
                    berichtenArray.add(cm);

                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ChatFragment fragment = ChatFragment.newInstance(berichtenArray, item.getId());
                ft.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.fragmentContainerMessenger,fragment);
                ft.commit();
            }

            @Override
            public void onFailure(Call<List<ChatMessage>> call, Throwable t) {
                Log.d("Error:",t.getMessage()+"  ");
            }
        });

//        ArrayList<ChatMessage> berichten = item.getBerichten();
//        Intent intent = new Intent(Messenger_Activity.this, BackgroundService.class);
//        startService(intent);
//        Log.d("berichten in msngr",berichten.toString());

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager
                .findFragmentByTag("ChatFragment");
        if (fragment != null){
            fragment.getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                    .remove(fragment).commit();
        }else{
            super.onBackPressed();
        }
//




    }
}
