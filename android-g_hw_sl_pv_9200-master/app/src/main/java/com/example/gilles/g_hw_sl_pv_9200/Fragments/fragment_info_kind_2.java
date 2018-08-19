package com.example.gilles.g_hw_sl_pv_9200.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kind;

/**
 * Created by Lucas on 22-11-2017.
 */

public class fragment_info_kind_2 extends Fragment {

    private static View view;
    private ListView hobby;
    private String [] maanden = {"januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober","november", "december"};
    private Kind kind;

    public fragment_info_kind_2(){

    }

    @SuppressLint("ValidFragment")
    public fragment_info_kind_2(Kind kind){
        this.kind = kind;
        System.out.println(this.kind);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info_kind_2, container,
                false);
        initViews();
        return view;
    }

    private void initViews() {

        hobby = (ListView) view.findViewById(R.id.hobby);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                hobby.getContext(),
                android.R.layout.simple_list_item_1,
                this.kind.getHobbys());

        hobby.setAdapter(arrayAdapter);
    }
}
