package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;

import java.util.Date;

public class MainActivity extends AppCompatActivity {



    private Button btnKalender;
    private Button btnAanmelden;
    //private Button btnKindInfo;
    private Button btnGezin;
    private Button btnMessages;
    private Button btnKosten;
    private String [] maanden = {"januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober","november", "december"};
    private String [] dagen = {"Zondag","Maandag","Dinsdag","Woensdag","Donderdag","Vrijdag","Zaterdag"};


    /**
     * mainactivity aanmaken, homescreen met buttons naar andere activities
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnKalender = (Button) findViewById(R.id.btnKalender);
        Date now = new Date();
        String kalender = dagen[now.getDay()] + " " + now.getDate() + " " + maanden[now.getMonth()];
        btnKalender.setText(kalender);

        btnKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KalenderActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Aanmelden
         */
        Context context = this;
        btnAanmelden = (Button) findViewById((R.id.btnAanmelden));

        btnAanmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.USERID=null;
                Utils.GEBRUIKER=null;
                Toast.makeText(context, "Succesvol afgemeld", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(MainActivity.this, LoginLauncherActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Gezin overzicht
         */

        btnGezin = (Button) findViewById((R.id.btnGezin));

        btnGezin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GezinActivity.class);
                startActivity(intent);
            }

        });

        /**
         * KostenOverzicht
         */

        btnKosten = (Button) findViewById((R.id.btnKosten));

        btnKosten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KostOverzichtScherm.class);
                startActivity(intent);
            }

        });

//        btnKindInfo = (Button) findViewById((R.id.btnKindInfo));
//
//        btnKindInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, KindProfielActivity.class);
//                startActivity(intent);
//            }
//        });

        btnMessages = (Button) findViewById((R.id.btnMessages));

        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Messenger_Activity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
    }
}
/*
import com.example.gilles.g_hw_sl_pv_9200.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        // If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new Login_Fragment(),
                            Utils.Login_Fragment).commit();
        }

        // On close icon click finish activity
        findViewById(R.id.close_activity).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        finish();

                    }
                });

    }

    // Replace Login Fragment with animation
    protected void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment(),
                        Utils.Login_Fragment).commit();
    }

    @Override
    public void onBackPressed() {

        // Find the tag of signup and forgot password fragment
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(Utils.ForgotPassword_Fragment);

        // Check if both are null or not
        // If both are not null then replace login fragment else do backpressed
        // task

        if (SignUp_Fragment != null)
            replaceLoginFragment();
        else if (ForgotPassword_Fragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }
}

*/