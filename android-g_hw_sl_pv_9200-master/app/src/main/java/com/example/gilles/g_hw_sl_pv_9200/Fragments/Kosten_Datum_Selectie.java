package com.example.gilles.g_hw_sl_pv_9200.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gilles.g_hw_sl_pv_9200.Activities.KostOverzichtScherm;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;
import com.example.gilles.g_hw_sl_pv_9200.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Kosten_Datum_Selectie extends Fragment {
    @BindView(R.id.sortSpinner)
    Spinner sortSpinner;
    @BindView(R.id.filterSpinner)
    Spinner filterSpinner;
    @BindView(R.id.resetVelden)
    Button resetVelden;

    private KostOverzichtScherm activity;
    private DateSelectionFragment startDateSelFrag;
    private DateSelectionFragment endDateSelFrag;

    private boolean filterFlag = false;
    private boolean sortFlag = false;

    private String[] sortOptionsArray = {"/", "Goedgekeurde kosten", "Afgekeurde kosten"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kosten_datum_selectie, container, false);
        ButterKnife.bind(this, view);

        getStartDateSelFrag().isStartDateSelector(true);
        getEndDateSelFrag().isStartDateSelector(false);

        activity = (KostOverzichtScherm) getActivity();

//        initSortSpinner();
//        initFilterSpinner();

        return view;
    }

//    private void initSortSpinner(){
//        String[] sortOptionsArray = {"dalende datum", "stijgende datum", "categorieën", "betrokken persoon", "stijgend bedrag",
//        "dalend bedrag"};
//        ArrayList<String> sortOptions = new ArrayList<>(Arrays.asList(sortOptionsArray));//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sortOptions);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sortSpinner.setAdapter(adapter);
//        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                if (sortFlag)
//                activity.sorterenOp("tst");
//                else sortFlag = !sortFlag;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                return;
//            }
//        });
//    }
//
//    private void initFilterSpinner(){
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sortOptionsArray);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        filterSpinner.setAdapter(adapter);
//        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//               if (filterFlag)
//                activity.filterenOp(sortOptionsArray[position]);
//               else filterFlag = !filterFlag;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                return;
//            }
//        });
//    }

    public DateSelectionFragment getStartDateSelFrag() {
        if (startDateSelFrag == null)
            startDateSelFrag = (DateSelectionFragment) getChildFragmentManager()
                    .findFragmentById(R.id.startDateSelectionFragment);
        return startDateSelFrag;
    }

    public DateSelectionFragment getEndDateSelFrag() {
        if (endDateSelFrag == null)
            endDateSelFrag = (DateSelectionFragment) getChildFragmentManager()
                    .findFragmentById(R.id.endDateSelectionFragment);
        return endDateSelFrag;
    }

    /**
     * Reset alle filter velden.
     * @param view
     */
    public void resetVelden(View view){
        //sortSpinner.setSelection(0);
        activity.resetFilteredAdapterData(); // resets the data of the adapter in the parent activity.
    }
}


