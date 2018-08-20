package com.example.gilles.g_hw_sl_pv_9200.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.gilles.g_hw_sl_pv_9200.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DateSelectorFragment extends Fragment {

    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.yearSpinner)
    Spinner yearSpinner;

    private Actions mCallback;
    private Calendar calendar;

    private boolean monthFlag = false;
    private boolean yearFlag = false;

    private List<String> years;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_selector, container, false);
        ButterKnife.bind(this, view);

        initMonthSpinner();

        return view;
    }

    private void initMonthSpinner() {
        String[] mndn = {/*"maand",*/ "januari", "februari", "maart", "april", "mei", "juni", "juli",
                "augustus", "september", "oktober", "november", "december"};
        ArrayList<String> maanden = new ArrayList<>(Arrays.asList(mndn));
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, maanden);

        monthSpinner.setAdapter(mAdapter);
        monthSpinner.setSelection(calendar.get(Calendar.MONTH)); // zero based ?

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (monthFlag) {
                    int year = Integer.parseInt(years.get(yearSpinner.getSelectedItemPosition()));
                    mCallback.monthSpinnerOnClick(position, year);
                } else monthFlag = !monthFlag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }
        });
    }

    public void initYearSpinner(List<String> years) {
        this.years = years;
        ArrayAdapter<String> yAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(yAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (yearFlag) {
                    int month = monthSpinner.getSelectedItemPosition();
                    int year = Integer.parseInt(years.get(position));
                    mCallback.yearSpinnerOnClick(month, year);
                } else yearFlag = !yearFlag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }
        });
    }

    @Override
    public void onDetach() {
        mCallback = null; // => avoid leaking
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (DateSelectorFragment.Actions) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Actions");
        }
    }

    public interface Actions {
       // int getYearOfOldestExpense();
        void monthSpinnerOnClick(int month, int year);
        void yearSpinnerOnClick(int month, int year);
    }

}