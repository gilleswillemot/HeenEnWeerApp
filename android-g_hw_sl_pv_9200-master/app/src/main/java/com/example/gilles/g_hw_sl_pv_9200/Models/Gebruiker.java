package com.example.gilles.g_hw_sl_pv_9200.Models;

import java.io.Serializable;

/**
 * Created by pietl on 3/12/2017.
 */

public class Gebruiker implements Serializable {

    private String naam;
    private String voornaam;
    private String id;

    public Gebruiker(String naam, String voornaam, String id){
        this.naam = naam;
        this.voornaam = voornaam;
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoorNaam() {
        return voornaam;
    }

    public void setVoorNaam(String voorNaam) {
        this.voornaam = voorNaam;
    }

    public String getId(){ return id;}

    public boolean isOuder(){
        return this.getClass().getName().equals(Ouder.class.getName());
    }
}
