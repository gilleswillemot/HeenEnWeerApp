package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Data.Databank;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gebruiker;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gezin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GezinActivity extends AppCompatActivity {

    private TextView gezinNaam;
    private ListView gezinsLeden;
    private Databank db;
    private Gezin gezin;
    private Map<String, String> map;

    /**
     * aanmaken Gezinsview met gebruikerslijst
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.db = new Databank();
        this.gezin = this.db.getGezin();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gezin);

        gezinNaam = (TextView) findViewById(R.id.textViewGezinsNaam);
        gezinNaam.setText("Deman - Vandewamme");

        gezinsLeden = (ListView) findViewById(R.id.listViewGezinsleden);

        List<Gebruiker> values = db.getGezinsLedenInfo();
        this.map = new HashMap<>();

        for(Gebruiker g : values)
            this.map.put(g.getVoorNaam()+" "+g.getNaam(),g.getId());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>(map.keySet()));

        gezinsLeden.setAdapter(adapter);
        gezinsLeden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition  = position;
                String itemValue = (String) gezinsLeden.getItemAtPosition(position);

                Gebruiker gebruiker = null;
                for(String value: map.keySet()){
                    if(itemValue == value)
                        gebruiker = db.getGezin().getGezinslidMetId(map.get(itemValue));
                }

                if(gebruiker != null) {
                    if(gebruiker.isOuder()) {
                        Intent intent = new Intent(GezinActivity.this, OuderProfielActivity.class);
                        intent.putExtra("Gebruiker", gebruiker.getId());
                        startActivity(intent);
                    } else{
                        Intent intent = new Intent(GezinActivity.this, KindProfielActivity.class);
                        intent.putExtra("Gebruiker", gebruiker.getId());
                        startActivity(intent);
                    }

                }
            }
        });

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gezin);
//        GezinActivity act = this;
////        this.db = new Databank();
////        this.gezin = this.db.getGezin();
//        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
//        ArrayList<Gezin> berichtenArray = new ArrayList<>();
//        Call<Gezin> call2 = dataInterface.getHuidigGezin(Utils.GEBRUIKER.getId());
//        call2.enqueue(new Callback<Gezin>() {
//            @Override
//            public void onResponse(Call<Gezin> call, Response<Gezin> response) {
//                gezin=response.body();
//                gezinNaam = (TextView) findViewById(R.id.textViewGezinsNaam);
//                gezinNaam.setText(gezin.getGezinsNaam());
//
//                gezinsLeden = (ListView) findViewById(R.id.listViewGezinsleden);
//                List<Gebruiker> values = gezin.getGezinsLeden();
//                map = new HashMap<>();
//
//                for(Gebruiker g : values)
//                    map.put(g.getVoorNaam()+" "+g.getNaam(),g.getId());
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(act,
//                        android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>(map.keySet()));
//
//                gezinsLeden.setAdapter(adapter);
//                gezinsLeden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        int itemPosition  = position;
//                        String itemValue = (String) gezinsLeden.getItemAtPosition(position);
//
//                        Gebruiker gebruiker = null;
//                        for(String value: map.keySet()){
//                            if(itemValue == value)
//                                gebruiker = db.getGezin().getGezinslidMetId(map.get(itemValue));
//                        }
//
//                        if(gebruiker != null) {
//                            if(gebruiker.isOuder()) {
//                                Intent intent = new Intent(GezinActivity.this, OuderProfielActivity.class);
//                                intent.putExtra("Gebruiker", gebruiker.getId());
//                                startActivity(intent);
//                            } else{
//                                Intent intent = new Intent(GezinActivity.this, KindProfielActivity.class);
//                                intent.putExtra("Gebruiker", gebruiker.getId());
//                                startActivity(intent);
//                            }
//
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<Gezin> call, Throwable t) {
//                Log.d("Error:",t.getMessage()+"  ");
//            }
//        });
    }
}
