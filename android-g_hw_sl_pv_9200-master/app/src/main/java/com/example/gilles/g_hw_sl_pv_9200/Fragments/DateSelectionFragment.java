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
import android.widget.Spinner;

import com.example.gilles.g_hw_sl_pv_9200.Activities.KostOverzichtScherm;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;
import com.example.gilles.g_hw_sl_pv_9200.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DateSelectionFragment extends Fragment {
    @BindView(R.id.daySpinner)
    public Spinner daySpinner;
    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.yearSpinner)
    Spinner yearSpinner;

    private Actions mCallback;
    private int beginYear;
    private int[] onClickListenerFlags;
    private boolean isStartDateSelector;
    private boolean manualChange = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_selection, container, false);
        ButterKnife.bind(this, view);

        onClickListenerFlags = new int[3];
        initDateSelectSpinners();
        setOnClickListenersForSpinners();

        return view;
    }

    public void initDateSelectSpinners() {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        initDaySpinner(day, thisMonth, thisYear);

        // init monthSpinner
        String[] mndn = {"maand", "januari", "februari", "maart", "april", "mei", "juni", "juli",
                "augustus", "september", "oktober", "november", "december"};
        ArrayList<String> maanden = new ArrayList<String>(Arrays.asList(mndn));
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, maanden);

        monthSpinner.setAdapter(mAdapter);
        monthSpinner.setSelection(thisMonth);

        // init yearSpinner

        ArrayList<String> years = new ArrayList<>();
        years.add("jaar");
        beginYear = mCallback.getYearOfOldestExpense();
        for (int i = thisYear; i >= beginYear; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);

        yearSpinner.setAdapter(yAdapter);
        yearSpinner.setSelection(1);
    }

    private void initDaySpinner(int day, int month, int year) {
        // init daySpinner
        ArrayList<String> daysList = new ArrayList<>();
        daysList.add("dag");
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        int days = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= days; i++) {
            daysList.add(Integer.toString(i));
        }
        ArrayAdapter<String> dAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, daysList);

        daySpinner.setAdapter(dAdapter);

        int selectedDay = day > days ? days : day; // if current selected day is bigger than days in month.
        daySpinner.setSelection(selectedDay);
    }

    /**
     *
     * @param selectors: boolean. Size of array is 3 (for each spinner: day, month and year)
     *                 example: if selectors[0] == true, then hide daySpinner
     *                 if selectors[1] == false, don't hide monthSpinner
     */
    public void hideSelectors(boolean[] selectors){
        for (int i = 0; i < selectors.length; i++) {
            if(selectors[i]){
                if(i == 0){
                    daySpinner.setVisibility(View.GONE);
                } else if (i == 1){
                    monthSpinner.setVisibility(View.GONE);
                }else{
                    yearSpinner.setVisibility(View.GONE);
                }
            }
        }
    }

    public Date getSelectedDate(){
        int day = daySpinner.getSelectedItemPosition();
        int month = monthSpinner.getSelectedItemPosition();
        int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());

        if(day + month + year == 0) return new Date(); // niets geselecteerd

        String dayString = day < 10 ? "0" + day : String.valueOf(day);
        String monthString = month < 10 ? "0" + month : String.valueOf(month);
        String yearString = String.valueOf(year);

        String startDateString = dayString + "/" + monthString + "/" + yearString;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = new Date();
        try {
            startDate = df.parse(startDateString);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public void setSelectedDate(int day, int month, int year) {
        daySpinner.setSelection(day);
        monthSpinner.setSelection(month);
        yearSpinner.setSelection(year);
    }

    public void changeDateManually(int input, int spinner){
        manualChange = true;
        if(spinner == 1){
            daySpinner.setSelection(input, false);
        }
        else if(spinner == 2){
            monthSpinner.setSelection(input);
        }
        else yearSpinner.setSelection(getPositionOfYear(input));
    }

    public int getPositionOfYear(int year){
       return year - mCallback.getYearOfOldestExpense() + 1; // + 1 because first index is default value "jaar".
    }

    public void setSelectedDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
       // int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);


        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        initDaySpinner(day, month, mCallback.getYearOfOldestExpense());

        daySpinner.setSelection(day);
        monthSpinner.setSelection(month + 1); // selection @ index 0 is default 'month' option.
        yearSpinner.setSelection(yearSpinner.getAdapter().getCount() - 1); // index 0 = default option, index 1 is year of selectedCost (see initDateSpinners)
    }

        public void isStartDateSelector(boolean isStartDateSelector) {
        this.isStartDateSelector = isStartDateSelector;
        this.daySpinner.setSelection(0);
        this.monthSpinner.setSelection(0);
        this.yearSpinner.setSelection(0);
    }

    private void setOnClickListenersForSpinners() {
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (onClickListenerFlags[0] >= 1 && !manualChange) {
                    mCallback.daySpinnerOnClick(isStartDateSelector, position);
                } else {
                    onClickListenerFlags[0]++;
                    manualChange = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (onClickListenerFlags[1] >= 1 && !manualChange) {
                    if (position != 0 && yearSpinner.getSelectedItemPosition() != 0){
                        int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                        initDaySpinner(daySpinner.getSelectedItemPosition(), position, year);
                    }
                mCallback.monthSpinnerOnClick(isStartDateSelector, position);
                } else{
                     onClickListenerFlags[1]++;
                     manualChange = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }
        });
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (onClickListenerFlags[2] >= 1 && !manualChange) {
                    if (position != 0 && yearSpinner.getSelectedItemPosition() != 0) {
                        int month = monthSpinner.getSelectedItemPosition();
                        int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                        initDaySpinner(daySpinner.getSelectedItemPosition(), month, year);
                        mCallback.yearSpinnerOnClick(isStartDateSelector, year);
                    }
                } else{
                    onClickListenerFlags[2]++;
                    manualChange = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (Actions) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Actions");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null; // => avoid leaking
        super.onDetach();
    }

    public interface Actions {
//        public void changeCurrentColor(int color);
//
//        public void drawSquares(boolean checked);
//
//        public void togglePixelSize(boolean bigPixels);
//
//        public void resetCanvas();

        int getYearOfOldestExpense();
        void daySpinnerOnClick(boolean isStartDateSelector, int day);
        void monthSpinnerOnClick(boolean isStartDateSelector, int month);
        void yearSpinnerOnClick(boolean isStartDateSelector, int year);
    }

//    public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {
//
//        boolean userSelect = false;
//        mCallback;
//        public SpinnerInteractionListener(Spinner start, Spinner end, int number, mCallback, string nameOfSpinner){
//
//        }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            userSelect = true;
//            return false;
//        }
//
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//            if (userSelect) {
//                // Your selection handling code here
//                mCallback.action
//                userSelect = false;
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
}

