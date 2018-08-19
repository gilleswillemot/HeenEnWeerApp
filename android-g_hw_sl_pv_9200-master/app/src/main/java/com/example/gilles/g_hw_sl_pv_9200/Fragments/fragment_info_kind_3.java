package com.example.gilles.g_hw_sl_pv_9200.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Models.Activiteit;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kind;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22-11-2017.
 */

public class fragment_info_kind_3 extends Fragment {

    private static View view;
    private ListView activiteit;
    private Kind kind;

    public fragment_info_kind_3(){

    }

    @SuppressLint("ValidFragment")
    public fragment_info_kind_3(Kind kind){
        this.kind = kind;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info_kind_3, container,
                false);
        initViews();
        return view;
    }

    /**
     * initialzer van de view van tab3 van het kindinfoscherm,
     * de laatste 4 activiteiten van het kind ophalen en weergeven
     */
    private void initViews() {
//        Kind kind = new Kind("Demey", "Piet", "5");
//        Date gbd = new Date(2010, 11, 19);
//        kind.setGeboortedatum(gbd);
//        kind.setBloedgroep("O-negatief");
//        String[] aller = {"hooikoorst", "lactose"};
//        String[] hobbys = {"voetbal", "muziekschool","boeken lezen"};
//        kind.setAllergieën(aller);
//        kind.setHobbys(hobbys);


        HashMap<String,String> basisinfo = new HashMap<>();
        basisinfo.clear();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy   HH");

        for(Activiteit activiteit : kind.getActiviteiten()){
            basisinfo.put(activiteit.getBeschrijving(), formatter.format(activiteit.getDatumStart())+"u");
        }

//        basisinfo.put("Voetbaltrainging", "28/11/2017 19u");
//        basisinfo.put("Muziekschool", "29/11/2017 17u");
//        basisinfo.put("Voetbaltraining", "1/12/2017 19u");
//        basisinfo.put("Voetbalwedstrijd", "2/12/2017 14u");


        activiteit = (ListView) view.findViewById(R.id.activiteit);

        List<HashMap<String,String>> listItems = new ArrayList<>();
        SimpleAdapter adapter  = new SimpleAdapter(activiteit.getContext(), listItems, R.layout.list_item, new String[]{"lijn1", "lijn2"}, new int[]{R.id.text1, R.id.text2});

        Iterator it = basisinfo.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String, String> resultMap = new HashMap<>();
            Map.Entry paar = (Map.Entry) it.next();
            resultMap.put("lijn1",paar.getKey().toString());
            resultMap.put("lijn2",paar.getValue().toString());
            listItems.add(resultMap);
        }

        activiteit.setAdapter(adapter);
    }
}
