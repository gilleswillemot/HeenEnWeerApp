package com.example.gilles.g_hw_sl_pv_9200.SharedClasses;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    boolean userSelect = false;

    public SpinnerInteractionListener(Spinner start, Spinner end, int number){

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (userSelect) {
            // Your selection handling code here
            userSelect = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
