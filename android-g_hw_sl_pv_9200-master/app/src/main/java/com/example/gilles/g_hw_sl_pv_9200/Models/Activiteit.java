package com.example.gilles.g_hw_sl_pv_9200.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 23-11-2017.
 */

public class Activiteit implements Parcelable {

    private Date startDatum;
    private Date eindDatum;

    private String titel;
    private String beschrijving;
    private String kleur;
    private String[] personen;
    private List<String> deelnemers;

    public Activiteit(Date startDatum, Date eindDatum, String titel, String beschrijving, String kleur, String[] personen) {
        this.startDatum = startDatum;
        this.eindDatum = eindDatum;
        this.titel = titel;
        this.beschrijving = beschrijving;
        this.kleur = kleur;
        this.personen = personen;
    }
    public Activiteit(String titel, Date datum, Date datumEinde, String besch, ArrayList<String> deelnemers) {
        this.startDatum = datum;
        this.titel = titel;
        this.eindDatum = datumEinde;
        this.beschrijving = besch;
        personen= deelnemers.toArray(new String[deelnemers.size()]);
        this.deelnemers = deelnemers;
    }
    public void setDeelnemers(List<String> deelnemers) {
        this.deelnemers = deelnemers;
    }

    public Date getDatumStart() {
        return startDatum;
    }

    public Date getDatumEinde() {
        return eindDatum;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String[] getDeelnemersId(){
        return personen;
    }
    public String getDeelnemers(){
        StringBuilder result = new StringBuilder();
        int i;
        if (deelnemers != null) {
            for(i = 0;i<deelnemers.size();i++){
                if(i!=0){result.append(", ");}
                result.append(deelnemers.get(i));
            }
        }

        return result.toString();
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    /**
     * activiteit moet parceble zijn om het in de argumenten van de fragment te kunnen steken
     * @param in
     */
    protected Activiteit(Parcel in) {
        titel = in.readString();
        beschrijving = in.readString();
        kleur = in.readString();
        personen = in.createStringArray();
    }

    public static final Creator<Activiteit> CREATOR = new Creator<Activiteit>() {
        @Override
        public Activiteit createFromParcel(Parcel in) {
            return new Activiteit(in);
        }

        @Override
        public Activiteit[] newArray(int size) {
            return new Activiteit[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titel);
        parcel.writeString(beschrijving);
        parcel.writeString(kleur);
        parcel.writeStringArray(personen);
    }
}


