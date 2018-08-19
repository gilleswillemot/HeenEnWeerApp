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
import com.example.gilles.g_hw_sl_pv_9200.Data.Databank;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22-11-2017.
 */

public class fragment_info_kind_1 extends Fragment {

    private static View view;
    private ListView basicInfoKind;
    private String [] maanden = {"januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober","november", "december"};
    private Kind kind;

    public fragment_info_kind_1(){

    }

    @SuppressLint("ValidFragment")
    public fragment_info_kind_1(Kind kind){
        this.kind = kind;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info_kind_1, container,
                false);
        initViews();
        return view;
    }

    /**
     * initializer voor tab 1 van het kindinfoscherm (de basisinfo van het kind)
     */
    private void initViews() {
        Databank data = new Databank();

        HashMap<String,String> basisinfo = new HashMap<>();
        basisinfo.clear();
        basisinfo.put("Verjaardag",kind.getGeboortedatum().getDay() + " " + maanden[kind.getGeboortedatum().getMonth()]);
        basisinfo.put("Leeftijd", ""+kind.getLeeftijd());
        basisinfo.put("Verblijf", "3/4 bij Sandra, 1/4 bij Kurt");
        basisinfo.put("School", "Vrije basisschool Zilverberg");
        StringBuilder aller = new StringBuilder();
        for(String s:kind.getAllergieën()){
            aller.append(s+", ");
        };
        basisinfo.put("Allergieën",aller.substring(0,aller.length()-2));
        basisinfo.put("Bloedgroep",kind.getBloedgroep());


        basicInfoKind = (ListView) view.findViewById(R.id.basicInfoList);

        List<HashMap<String,String>> listItems = new ArrayList<>();
        SimpleAdapter adapter  = new SimpleAdapter(basicInfoKind.getContext(), listItems, R.layout.list_item, new String[]{"lijn1", "lijn2"}, new int[]{R.id.text1, R.id.text2});

        Iterator it = basisinfo.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String, String> resultMap = new HashMap<>();
            Map.Entry paar = (Map.Entry) it.next();
            resultMap.put("lijn1",paar.getKey().toString());
            resultMap.put("lijn2",paar.getValue().toString());
            listItems.add(resultMap);
        }

        basicInfoKind.setAdapter(adapter);
    }
}
