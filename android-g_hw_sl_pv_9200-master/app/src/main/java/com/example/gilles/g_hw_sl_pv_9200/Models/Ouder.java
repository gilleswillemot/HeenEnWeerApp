package com.example.gilles.g_hw_sl_pv_9200.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 23-11-2017.
 */

public class Ouder extends Gebruiker implements Serializable {

    private List<Gezin> gezinnen;
    private String adres;
    private String voogdijRegeling;

    public List<Gezin> getGezinnen() {
        return gezinnen;
    }

    public void voegGezinToe(Gezin gezin) {
        this.gezinnen.add(gezin);
    }

    public Ouder(String naam, String voornaam, String id) {
        super(naam,voornaam, id);
        this.gezinnen=new ArrayList<>();
    }

    public Ouder(String naam, String voornaam,String adres, String voogdijRegeling, String id) {
        this(naam,voornaam, id);
        this.adres = adres;
        this.voogdijRegeling = voogdijRegeling;
    }

    public void setAdres(String adres){this.adres = adres;}

    public String getAdres(){return this.adres; }

    public void setVoogdijRegeling(String voogdijRegeling){this.voogdijRegeling = voogdijRegeling;}

    public String getVoogdijRegeling(){return this.voogdijRegeling; }




}
