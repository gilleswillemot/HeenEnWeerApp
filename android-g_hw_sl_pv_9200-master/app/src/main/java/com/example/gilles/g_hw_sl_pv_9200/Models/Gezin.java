package com.example.gilles.g_hw_sl_pv_9200.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomde on 11/13/2017.
 */

public class Gezin {
    private List<Kind> kinderen;
    private Ouder vader;
    private Ouder moeder;
    private String gezinsNaam;
    private List<Gebruiker> gezinsLeden;

    public List<Kost> kosten;



    public List<Gesprek> gesprekken;

    public Ouder getVader() {
        return vader;
    }

    public Ouder getMoeder() {
        return moeder;
    }

    public Gezin(Ouder vader, Ouder moeder){
        this.vader = vader;
        this.moeder = moeder;
        this.gezinsNaam = this.vader.getNaam() + " - " + this.moeder.getNaam();
        this.kinderen = new ArrayList<>();
        this.kosten = new ArrayList<>();
        this.gezinsLeden = new ArrayList<>();
        this.gezinsLeden.add(this.vader);
        this.gezinsLeden.add(this.moeder);
        gesprekken = new ArrayList<>();
    }

    public String getGezinsNaam(){
        return this.gezinsNaam;
    }

    public void voegKindToe(Kind kind){
        this.kinderen.add(kind);
        this.gezinsLeden.add(kind);
    }

    public List<Kind> getKinderen(){
        return kinderen;
    }

    public void voegKostToe(Kost nieuweKost){
        this.kosten.add(nieuweKost);
    }

    public List<Kost> getKosten() {
        return kosten;
    }

    public List<Gebruiker> getGezinsLeden(){
        return this.gezinsLeden;
    }

    public Gebruiker getGezinslidMetId(String id){
        for(Gebruiker lid: this.gezinsLeden){
            if(lid.getId().equals(id))
                return lid;
        }
        return null;
    }

    public List<Gesprek> getGesprekken() {
        return gesprekken;
    }

    public void addGesprek(Gesprek gesprek) {
        this.gesprekken.add(gesprek);
    }
}
