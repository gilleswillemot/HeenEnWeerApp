package com.example.gilles.g_hw_sl_pv_9200.Activities;

import com.example.gilles.g_hw_sl_pv_9200.Models.Gezin;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;

import java.util.List;

/**
 * Created by tomde on 11/13/2017.
 */

public class KostenSingleton {
    private static KostenSingleton instance = new KostenSingleton();
    private Gezin gezin;
    private KostenSingleton(){
    }

    public static KostenSingleton getInstance(){
        return instance;
    }

    public void VoegKostToe(Kost kost){
        gezin.voegKostToe(kost);
    }

    public List<Kost> getKosten(){
        return gezin.getKosten();
    }

    public Gezin getGezin(){
        return gezin;
    }

    public void setGezin(Gezin gezin){
        this.gezin = gezin;
    }
}
