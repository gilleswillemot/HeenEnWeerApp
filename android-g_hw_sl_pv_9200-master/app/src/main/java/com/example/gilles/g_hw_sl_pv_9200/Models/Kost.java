package com.example.gilles.g_hw_sl_pv_9200.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tomde on 11/13/2017.
 */

public class Kost implements Parcelable {
    public String naamKost, beschrijving, categorie; //aanmakerId
    // public String creatieDatum;
    private String aangekochtVoor;
    public float bedrag;
    private String _id;
    private String aangekochtDoor;
    private Date aankoopDatum;
    private String kleur;
    private boolean isUitzonderlijkeKost;
    private String status; // goedgekeurd, afgekeurd, onbepaald

    /**
     * @param naamKost
     * @param bedrag
     * @param beschrijving
     * @param categorie
     */
    public Kost(String naamKost, float bedrag, Date aankoopDatum, String aangekochtDoor, String gekochtVoor,
                String beschrijving, String categorie, String kleur, boolean isUitzonderlijkeKost, String status) {
        this.naamKost = naamKost;
        this.aangekochtDoor = aangekochtDoor;
        this.aangekochtVoor = gekochtVoor;
        this.aankoopDatum = aankoopDatum;
        this.bedrag = bedrag;
        this.beschrijving = beschrijving;
        this.categorie = categorie;
        this.kleur = kleur;
        this.isUitzonderlijkeKost = isUitzonderlijkeKost;
        this.status = status;
    }

    protected Kost(Parcel in) {
        naamKost = in.readString();
        beschrijving = in.readString();
        categorie = in.readString();
        aangekochtVoor = in.readString();
        bedrag = in.readFloat();
        _id = in.readString();
        aangekochtDoor = in.readString();
        kleur = in.readString();
        aankoopDatum = (java.util.Date) in.readSerializable();
        isUitzonderlijkeKost = in.readByte() != 0;
        status = in.readString();
    }

    public static final Creator<Kost> CREATOR = new Creator<Kost>() {
        @Override
        public Kost createFromParcel(Parcel in) {
            return new Kost(in);
        }

        @Override
        public Kost[] newArray(int size) {
            return new Kost[size];
        }
    };

    public String getAangekochtVoor() {
        return aangekochtVoor;
    }

    public String getAangekochtDoor() {
        return aangekochtDoor;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isGoedGekeurd() {
        return status.equalsIgnoreCase("goedgekeurd");
    }

    public String getKleur() {
        return kleur;
    }

    public String getNaam() {
        return naamKost;
    }

    public void setNaam(String naam) {
        this.naamKost = naam;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public float getBedrag() {
        return bedrag;
    }

    public void setKost(int bedrag) {
        this.bedrag = bedrag;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String mededeling) {
        this.beschrijving = mededeling;
    }

    public String toString() {
        return getNameSpecial() + " " + this.categorie + "\t" + aangekochtVoor;
    }

    public String getBedragSpecial() {
        return "€ " + this.bedrag;
    }

    public boolean isUitzonderlijkeKost() {
        return isUitzonderlijkeKost;
    }

    public String getNameSpecial() {
        String name = "";
        if (isUitzonderlijkeKost) name += "*";
        if (naamKost.length() > 10) name += naamKost.substring(0, 10);
        else name += getNaam();
        return name;
    }

    public String getId() {
        return this._id;
    }

    public Date getAankoopDatum() {
        return this.aankoopDatum;
    }

    //    public DateFormat getAankoopDatumDateFormat(){
//        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat();
//
//        return this.aankoopDatum
//    }
    public int getPurchasingYear() {
//        String dateFromDB = "";
//        SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
//        Date yourDate = parser.parse(aankoopDatum.toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aankoopDatum);
        return calendar.get(Calendar.YEAR);
    }

    public int getPurchasingMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aankoopDatum);
        return calendar.get(Calendar.MONTH);
    }

    public int getPurchasingDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aankoopDatum);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(naamKost);
        dest.writeString(beschrijving);
        dest.writeString(categorie);
        dest.writeString(aangekochtVoor);
        dest.writeFloat(bedrag);
        dest.writeString(_id);
        dest.writeString(aangekochtDoor);
        dest.writeString(kleur);
        dest.writeSerializable(aankoopDatum);
        dest.writeByte((byte) (isUitzonderlijkeKost ? 1 : 0));
        dest.writeString(status);
    }
}
