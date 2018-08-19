package com.example.gilles.g_hw_sl_pv_9200;

import com.example.gilles.g_hw_sl_pv_9200.Models.Gebruiker;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lucas on 20-11-2017.
 */

public class Utils {

    //Email Validation pattern
    public static final String regEx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String ActiviteitDetailFragment = "ActiviteitDetailFragment";
    public static final String ChatFragment = "ChatFragment";


    public String getUSERID() {
        return USERID;
    }
    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public static String USERID = null;//"5a2d50249802d505ecb61f03";
    public static Gebruiker GEBRUIKER = null;//new Gebruiker("Testvoornaam", "Testfamilienaam","5a2d50249802d505ecb61f03");

}
