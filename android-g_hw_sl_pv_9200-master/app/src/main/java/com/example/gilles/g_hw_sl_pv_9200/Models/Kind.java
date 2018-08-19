package com.example.gilles.g_hw_sl_pv_9200.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 20-11-2017.
 */

public class Kind extends Gebruiker implements Serializable {

    private Date geboortedatum;
    private String bloedgroep;
    private String[] allergieën;
    private String[] hobbys;
    private List<Activiteit> activiteiten;


    public Kind(String naam, String voornaam, String id) {
        super(naam, voornaam, id);
        activiteiten = new ArrayList<>();
        this.hobbys = new String[]{"Geen hobbies"};
    }

    public List<Activiteit> getActiviteiten() {
        return activiteiten;
    }

    public void voegActiviteitToe(Activiteit activiteit) {
        this.activiteiten.add(activiteit);
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public String getBloedgroep() {
        return bloedgroep;
    }


    public void setBloedgroep(String bloedgroep) {
        this.bloedgroep = bloedgroep;
    }

    public String[] getAllergieën() {
        return allergieën;
    }

    public void setAllergieën(String[] allergieën) {
        this.allergieën = allergieën;
    }

    public String[] getHobbys() {
        return hobbys;
    }

    public void setHobbys(String[] hobbys) {
        this.hobbys = new String[]{};
        this.hobbys = hobbys;
    }

    public int getLeeftijd() {
        Date now = new Date();
        return ((now.getYear() + 1900) - this.geboortedatum.getYear());
    }
}
