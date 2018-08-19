package com.example.gilles.g_hw_sl_pv_9200.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 21-11-2017.
 */
public class ChatMessage implements Parcelable {
    private String _id;
    private String voornaam;
    private String inhoud;
    private String auteursId;

    public ChatMessage(String inhoud, String auteursId, String datum,String voornaam) {
        this.inhoud = inhoud;
        this.auteursId = auteursId;
        this.datum = datum;
        this.voornaam = voornaam;
    }

    private String datum;

//    public ChatMessage(String _id, String inhoud, String auteursId, String datum) {
//        this._id = _id;
//        this.inhoud = inhoud;
//        this.auteursId = auteursId;
//        this.datum = datum;
//    }



    public String getId() {
        return _id;
    }
    public void setId(String id) {
        this._id = id;
    }
    public String getMessage() {
        return inhoud;
    }
    public void setMessage(String message) {
        this.inhoud = message;
    }
    public String getAuteursId() {
        return auteursId;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public void setAuteurId(String auteurId) {
        this.auteursId = auteurId;
    }

    public String getDate() {
        return datum;
    }

    public void setDate(String dateTime) {
        this.datum = dateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(inhoud);
        parcel.writeString(auteursId);
        parcel.writeString(datum);
    }

    /**
     * chatmessage implementeert parceble om in de argumenten van het chatfragment bij te houden
     * @param in
     */
    protected ChatMessage(Parcel in) {
        _id = in.readString();
        inhoud = in.readString();
        auteursId = in.readString();
        datum = in.readString();
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };
}