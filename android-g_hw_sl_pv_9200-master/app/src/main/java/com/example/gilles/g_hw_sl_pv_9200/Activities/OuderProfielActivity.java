package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Data.Databank;
import com.example.gilles.g_hw_sl_pv_9200.Models.Ouder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OuderProfielActivity extends AppCompatActivity {

    private Databank data;
    private Ouder ouder;
    private String gebruikerId;
    private TextView naam;
    private ListView info;

    /**
     * aanmaken van het profiel voor een ouder
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouder_profiel);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                this.gebruikerId= null;
            } else {
                this.gebruikerId= extras.getString("Gebruiker");
            }
        } else {
            this.gebruikerId= (String) savedInstanceState.getSerializable("Gebruiker");
        }

        this.data = new Databank();
        this.ouder = (Ouder) this.data.getGezin().getGezinslidMetId(this.gebruikerId);
        this.naam = (TextView) findViewById(R.id.naam);
        this.naam.setText(this.ouder.getVoorNaam() + " " + this.ouder.getNaam());

        HashMap<String,String> basisinfo = new HashMap<>();
        basisinfo.clear();
        basisinfo.put("Adres", ouder.getAdres());
        basisinfo.put("Voogdij regeling", ouder.getVoogdijRegeling());

        this.info = (ListView) findViewById(R.id.infoList);

        List<HashMap<String,String>> listItems = new ArrayList<>();
        SimpleAdapter adapter  = new SimpleAdapter(info.getContext(), listItems, R.layout.list_item, new String[]{"lijn1", "lijn2"}, new int[]{R.id.text1, R.id.text2});

        Iterator it = basisinfo.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String, String> resultMap = new HashMap<>();
            Map.Entry paar = (Map.Entry) it.next();
            resultMap.put("lijn1",paar.getKey().toString());
            resultMap.put("lijn2",paar.getValue().toString());
            listItems.add(resultMap);
        }

        info.setAdapter(adapter);

    }
}
