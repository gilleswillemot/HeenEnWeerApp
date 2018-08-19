package com.example.gilles.g_hw_sl_pv_9200.Models;

/**
 * Created by Lucas on 14-12-2017.
 */

public class User {

    public String getId() {
        return _id;
    }

    private String _id;
    private String token;
    private String username;
    private String voornaam;
    private String familienaam;
    private String password;
    private boolean isOuder;

    public User(String id, String token) {
        this._id = id;
        this.token = token;
    }

    public User(String username, String voornaam, String familienaam, String password) {
        this.username = username;
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.password = password;
    }

    public String getVoornaam(){
        return voornaam;
    }

    public boolean isOuder(){
        return isOuder;
    }
}
