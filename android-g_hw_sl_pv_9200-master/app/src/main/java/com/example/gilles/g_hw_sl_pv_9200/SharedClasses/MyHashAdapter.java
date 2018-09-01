package com.example.gilles.g_hw_sl_pv_9200.SharedClasses;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.Activities.KostOverzichtScherm;
import com.example.gilles.g_hw_sl_pv_9200.Activities.MaandAfrekeningActivity;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;
import com.example.gilles.g_hw_sl_pv_9200.R;

import java.util.HashMap;
import java.util.List;

public class MyHashAdapter<String> extends SimpleAdapter {

    private List<Kost> kosten;
    private Context context;
    private boolean withImages;

    public MyHashAdapter(Context context, List items, int resource, java.lang.String[] from, int[] to,
                       /* List<Kost> kosten,*/ boolean withImages) {
        super(context, items, resource, from, to);
//        this.kosten = kosten;
        this.context = context;
        this.withImages = withImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        Kost viewKost; //kost die wordt weergegeven op deze itemview.

        HashMap<String, String> hm = (HashMap<String, String>)getItem(position);
        java.lang.String index =  hm.values().toArray()[1].toString();
        if(context instanceof KostOverzichtScherm){
           kosten = ((KostOverzichtScherm)context).getKosten();
        } else{
            kosten = ((MaandAfrekeningActivity)context).getKosten();
        }
        viewKost = kosten.get(Integer.parseInt(index));

        TextView naamKost = v.findViewById(R.id.naamKost);

//            if(kost.isUitzonderlijkeKost()){
//                v.; set border thickness higher?
//            }

        java.lang.String kostStatus = viewKost.getStatus().toLowerCase();

        if (withImages) {
            Drawable img;

            if (kostStatus.equals("onbepaald")) {
                img = ContextCompat.getDrawable(context, R.drawable.question_mark);
            } else if (kostStatus.equals("goedgekeurd")) {
                img = ContextCompat.getDrawable(context, R.drawable.accept);
            } else {
                img = ContextCompat.getDrawable(context, R.drawable.cancel);
            }
            img.setBounds(0, 0, 50, 50);
            naamKost.setCompoundDrawables(img, null, null, null);
        }

        java.lang.String kleur = viewKost.getKleur().toLowerCase();
        v.setBackgroundColor(getColor(kleur));

        return v;
    }

    private int getColor(java.lang.String kleur) {
        int c;

        switch (kleur) {
            case "rood":
                c = Color.RED;
                break;
            case "blauw":
                c = Color.BLUE;
                break;
            case "geel":
                c = Color.YELLOW;
                break;
            case "groen":
                c = Color.GREEN;
                break;
            case "wit":
                c = Color.WHITE;
                break;
            case "cyaan":
                c = Color.CYAN;
                break;
            case "grijs":
                c = Color.GRAY;
                break;
            case "paars":
                c = Color.MAGENTA;
                break;

            default:
                c = Color.YELLOW;
        }
        return c;
    }
}

