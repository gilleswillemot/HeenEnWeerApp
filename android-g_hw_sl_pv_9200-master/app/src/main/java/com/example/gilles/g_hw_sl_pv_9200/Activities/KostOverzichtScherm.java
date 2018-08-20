package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilles.g_hw_sl_pv_9200.Fragments.DateSelectionFragment;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.DateSelectorFragment;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.Kosten_Datum_Selectie;
import com.example.gilles.g_hw_sl_pv_9200.HTTPClient.RetrofitClient;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KostOverzichtScherm extends AppCompatActivity implements DateSelectorFragment.Actions {
    @BindView(R.id.textViewKostenHeader)
    TextView naam;
    @BindView(R.id.voegKostToeBtn)
    Button voegKostToeBtn;
    //    @BindView(R.id.displaySettingsBtn)
//    Button showSortOptionsBtn;
    @BindView(R.id.listViewKosten)
    ListView kostenListView;
    @BindView(R.id.geenKostenMessage)
    TextView geenKostenMessage;
    @BindView(R.id.kostenListViewHeader)
    TextView kostenListViewHeader;
    @BindView(R.id.kostenAanvaardenButton)
    Button kostenAanvaardenButton;

    private List<Kost> kosten;
    private String token;
    private String huidigGezinId;
    //    private boolean filterOptionsAreHidden = true;
//    private Kosten_Datum_Selectie filterSettingsFragment;
    private MyHashAdapter<Kost> myHashAdapter;
    private List<HashMap<String, String>> kostenLijst = new ArrayList<>();
//    private int[][] selectedDatesArray = {{0, 0, 0}, {0, 0, 0}};

    private Calendar calendar = Calendar.getInstance();
    private ArrayList<String> years = new ArrayList<>();

    /**
     * Aanmaken van het kostoverzichtscherm en hierin gebeurt
     * het ophalen van alle kosten van het huidig gezin.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_overzicht_scherm);
        ButterKnife.bind(this);

        // hideFilterOptions();
        // showSortOptionsBtn.setVisibility(View.GONE); // NOT FULLY IMPLEMENTED YET.

        // huidigGezinId ophalen uit local storage (sharedPreferences)
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        huidigGezinId = prefs.getString("huidigGezinId", "0");
        token = prefs.getString("token", "0");
        initKostenAdapter();

        getKostenFromDatabase(huidigGezinId);
    }

    public void kostenAanvaarden(View v) {
        List<Kost> aanvaardeKosten = new ArrayList<>();
        if (!kostenLijst.isEmpty()) {
            for (HashMap<String, String> hm : kostenLijst) {
                for (Kost kost : kosten) {
                    if (hm.values().toArray()[1].equals(kost.getId())) {
                        kost.setStatus("goedgekeurd");
                        editCost(kost);
                    }
                    aanvaardeKosten.add(kost);
                }
            }
        }
        Toast.makeText(KostOverzichtScherm.this, "Kosten zijn aanvaard.", Toast.LENGTH_LONG).show();
        updateAdapterData(aanvaardeKosten);
    }

    private void editCost(Kost cost) {
        // kost toevoegen via API request
        Call<Kost> call = RetrofitClient.getInstance(this).getApi().wijzigKost(cost.getId(), cost);
        call.enqueue(new Callback<Kost>() {
            @Override
            public void onResponse(Call<Kost> call, Response<Kost> response) {
            }

            @Override
            public void onFailure(Call<Kost> call, Throwable t) {
                Toast.makeText(KostOverzichtScherm.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void gaNaarKostDetailScherm(Kost selectedCost) {
        Intent intent = new Intent(this, KostToevoegen.class);
        intent.putExtra("geselecteerdeKost", selectedCost);
        intent.putExtra("FROM_ACTIVITY", "KostOverzichtScherm");

        startActivity(intent);
    }

    private void getKostenFromDatabase(String huidigGezinId) {
        // kosten ophalen via API request
        Call<ResponseBody> call = RetrofitClient.getInstance(this).getApi().getKosten(huidigGezinId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String resp = response.body().string();
                    if (resp != null) {
                        // deserialize response string to list of Kost objects.
                        Type listType = new TypeToken<ArrayList<Kost>>() {
                        }.getType();
                        kosten = new Gson().fromJson(resp, listType);

                        if (kosten != null && !kosten.isEmpty()) {
                            orderKostenByDescendingPurchasingDate();
                            // initiate adapter in order to show list of Kost objects in view.
                            getYearOfOldestExpense(); // inits years
                            DateSelectorFragment frag = getDateSelectorFragment();
                            frag.initYearSpinner(true, years);
                            frag.initMonthSpinner(true);

                            //initKostenAdapter(kosten);
                            updateAdapterData(kosten);

                        } else { // list of costs is yet empty.
                            // hide listview (list of all cost objects)
                            kostenListViewHeader.setVisibility(View.INVISIBLE);
                            kostenListView.setVisibility(View.INVISIBLE);
//                            showSortOptionsBtn.setVisibility(View.GONE);
                            geenKostenMessage.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(KostOverzichtScherm.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void orderKostenByDescendingPurchasingDate() {
        Collections.sort(kosten, this::compareDescendingDate);
    }

    public DateSelectorFragment getDateSelectorFragment() {
//        if (dateSelectorFragment == null)
        DateSelectorFragment dateSelectorFragment = (DateSelectorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dateSelector);
        return dateSelectorFragment;
    }

    private void initKostenAdapter() {


        String[] from = {"Info", "Bedrag"};
        int[] to = new int[]{R.id.naamKost, R.id.bedragKost};

        myHashAdapter = new MyHashAdapter(this, kostenLijst, R.layout.simplerow, from, to);
        kostenListView.setAdapter(myHashAdapter);

        kostenListView.setOnItemClickListener((parent, view, position, id) ->
        {
            Object[] test = kostenLijst.get(position).values().toArray();
            // index 1, because Id comes after Info and before Bedrag alfabeticly

            String kostId = test[1].toString();
            Kost selectedCost = null;
            for (Kost kost : this.kosten) {
                if (kost.getId().equals(kostId))
                    selectedCost = kost;
            }
            if (selectedCost != null)
                gaNaarKostDetailScherm(selectedCost);
        });
    }

    public void resetFilteredAdapterData() {
        updateAdapterData(kosten);
    }

    private void updateAdapterData(List<Kost> filteredKostenLijst) {
        kostenLijst.clear();
        fillAdapterData(filteredKostenLijst);

        myHashAdapter.notifyDataSetChanged();
    }

    private void updateAdapterData(int month, int year) {
        month = month - 1; // index 0 equals default value, so january starts at index 1
        kostenLijst.clear();

        for (Kost kost : kosten) {
            boolean addKost = false;
            int kostMaand = kost.getPurchasingMonth();
            int kostJaar = kost.getPurchasingYear();

            if(month == -1 && year == 0) addKost = true;
            if (month == -1) {
                if (year == kostJaar) addKost = true;
            } else if (year == 0) {
                if (month == kostMaand) addKost = true;
            } else if (kostMaand == month && kostJaar == year) addKost = true;

            if (addKost) {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("Info", kost.toString());
                hm.put("Bedrag", kost.getBedragSpecial());
                hm.put("Id", kost.getId());
                kostenLijst.add(hm);
            }
        }
        myHashAdapter.notifyDataSetChanged();

        if (kostenLijst.isEmpty())
            kostenAanvaardenButton.setVisibility(View.INVISIBLE);
        else kostenAanvaardenButton.setVisibility(View.VISIBLE);
    }

    private void fillAdapterData(List<Kost> list) {
        for (Kost kost : list) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("Info", kost.toString());
            hm.put("Bedrag", kost.getBedragSpecial());
            hm.put("Id", kost.getId());
            kostenLijst.add(hm);
        }
    }

    public void voegKostToe(View view) {
        Intent intent = new Intent(KostOverzichtScherm.this, KostToevoegen.class);
        intent.putExtra("FROM_ACTIVITY", "KostOverzichtScherm");
        startActivity(intent);
    }

    public void gaNaarMaandAfrekening(View view) {
        Intent intent = new Intent(KostOverzichtScherm.this, MaandAfrekeningActivity.class);
        //intent.putParcelableArrayListExtra("kosten", (ArrayList<? extends Parcelable>) kosten);
        //kostenListView.getAdapter().set
        // intent.putExtra("kostenAdapter")
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KostOverzichtScherm.this, MainActivity.class);
        startActivity(intent);
    }

    public List<Kost> getKosten() {
        return kosten;
    }

    public int getYearOfOldestExpense() {
        int beginYear = calendar.get(Calendar.YEAR);
        years.add("jaar");
        if (kosten != null && !kosten.isEmpty()) {
            for (Kost kost : kosten) {
                if (kost.getPurchasingYear() < beginYear) beginYear = kost.getPurchasingYear();
            }
        }
        for (int i = calendar.get(Calendar.YEAR); i >= beginYear; i--) {
            years.add(Integer.toString(i));
        }
        return beginYear;
    }

    @Override
    public void monthSpinnerOnClick(int month, int year) {
        updateAdapterData(month, year);
    }

    @Override
    public void yearSpinnerOnClick(int month, int year) {
        updateAdapterData(month, year);
    }

    public class MyHashAdapter<String> extends SimpleAdapter {

        public MyHashAdapter(Context context, List items, int resource, java.lang.String[] from, int[] to) {
            super(context, items, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            Kost viewKost = null; //kost die wordt weergegeven op deze itemview.


            java.lang.String kostId = kostenLijst.get(position).values().toArray()[1].toString();
            for (Kost kost : kosten) {
                if(kost.getId().equalsIgnoreCase(kostId)) viewKost = kost;
            }
            TextView naamKost = v.findViewById(R.id.naamKost);

//            if(kost.isUitzonderlijkeKost()){
//                v.; set border thickness higher?
//            }

            java.lang.String kostStatus = viewKost.getStatus().toLowerCase();
            Drawable img;

            if (kostStatus.equals("onbepaald")) {
                img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.question_mark);
            } else if (kostStatus.equals("goedgekeurd")) {
                img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.accept);
            } else {
                img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.cancel);
            }
            img.setBounds(0, 0, 50, 50);
            naamKost.setCompoundDrawables(img, null, null, null);


            java.lang.String kleur = viewKost.getKleur().toLowerCase();
            v.setBackgroundColor(getColor(kleur));

            return v;
        }

        private int getColor(java.lang.String kleur) {
            int c;

            switch (kleur) {
                case "rood":
                    c = Color.RED;
                    break;
                case "blauw":
                    c = Color.BLUE;
                    break;
                case "geel":
                    c = Color.YELLOW;
                    break;
                case "groen":
                    c = Color.GREEN;
                    break;
                case "wit":
                    c = Color.WHITE;
                    break;
                case "cyaan":
                    c = Color.CYAN;
                    break;
                case "grijs":
                    c = Color.GRAY;
                    break;
                case "magenta":
                    c = Color.MAGENTA;
                    break;

                default:
                    c = Color.YELLOW;
            }
            return c;
        }
    }

    public int compareDescendingDate(Kost lhs, Kost rhs) {
        return rhs.getAankoopDatum().compareTo(lhs.getAankoopDatum());
    }

    public int compareAscendingDate(Kost lhs, Kost rhs) {
        return lhs.getAankoopDatum().compareTo(rhs.getAankoopDatum());
    }

//    private void hideFilterOptions() {
//        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction()
//                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                .hide(getFilterSettingsFragment())
//                .commit();
//        // TODO fragment has to come in foreground.
//    }
//    private void showFilterOptions() {
//        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction()
//                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                .show(getFilterSettingsFragment())
//                .commit();
//    }

//    public void SortOptionsBtnClick(View v) {
//        if (filterOptionsAreHidden) {
//            showFilterOptions();
//            kostenListViewHeader.setVisibility(View.GONE);
//            kostenListView.setVisibility(View.GONE);
//            showSortOptionsBtn.setText("Sorteer Opties Verbergen");
//        } else {
//            hideFilterOptions();
//            kostenListViewHeader.setVisibility(View.VISIBLE);
//            kostenListView.setVisibility(View.VISIBLE);
//            showSortOptionsBtn.setText("Sorteer Opties");
//        }
//        filterOptionsAreHidden = !filterOptionsAreHidden;
//    }
//    public void sorterenOp(String x) {
//
//    }
//
//    public void filterenOp(String x) {
//        List<Kost> filteredCosts = new ArrayList<>();
//        for (Kost kost : kosten) {
//            //if(kost.)
//        }
//        // descending date
//        Collections.sort(kosten, this::compareAscendingDate);
//
//        //ascending date
//        //
//        Collections.sort(kosten, this::compareAscendingDate);
//
//        updateAdapterData(filteredCosts);
//    }
//    private DateSelectionFragment getEndDateSelectorFragment() {
//        DateSelectionFragment childFrag = (DateSelectionFragment) getFilterSettingsFragment().
//                getChildFragmentManager().findFragmentById(R.id.endDateSelectionFragment);
//        return childFrag;
//    }
//
//    private DateSelectionFragment getStartDateSelectorFragment() {
//        DateSelectionFragment childFrag = (DateSelectionFragment) getFilterSettingsFragment().
//                getChildFragmentManager().findFragmentById(R.id.startDateSelectionFragment);
//        return childFrag;
//    }
//    private void filterKostenLijst() {
//        List<Kost> filteredKostenList = new ArrayList<>();
//        List<Kost> filteredKostenList2 = new ArrayList<>();
//        List<Kost> filteredKostenList3 = new ArrayList<>();
//
//        int startDay = selectedDatesArray[0][0];
//        int endDay = selectedDatesArray[1][0];
//
//        int startMonth = selectedDatesArray[0][1];
//        int endMonth = selectedDatesArray[1][1];
//
//        int startYear = selectedDatesArray[0][2];
//        int endYear = selectedDatesArray[1][2];
//
//        // eventueel beginnen met filteren op jaar => als default (=0) dan moet je enkel volledige kostenlijst teruggeven.
//
//        if (startDay > 0 && endDay > 0) { // if a specific day was selected in the filter, otherwise its default.
//            for (Kost kost : kosten) {
//                int purchasingDay = kost.getPurchasingDay();
//                if (purchasingDay >= startDay && purchasingDay <= endDay)
//                    filteredKostenList.add(kost);
//            }
//        } else filteredKostenList = kosten;
//
//        if (startMonth > 0 && endMonth > 0) { // if a specific month was selected in the filter, otherwise its default.
//            for (Kost kost : filteredKostenList) {
//                int purchasingMonth = kost.getPurchasingMonth();
//                if (purchasingMonth >= startMonth && purchasingMonth <= endMonth)
//                    filteredKostenList2.add(kost);
//            }
//        } else filteredKostenList2 = filteredKostenList;
//
//        if (startYear > 0 && startYear > 0) { // if a specific year was selected in the filter, otherwise its default.
//            for (Kost kost : filteredKostenList) {
//                int purchasingMonth = kost.getPurchasingYear();
//                if (purchasingMonth >= startYear && purchasingMonth <= endYear)
//                    filteredKostenList2.add(kost);
//            }
//        } else filteredKostenList3 = filteredKostenList2;
//
//        updateAdapterData(filteredKostenList3);
//    }
//
//    /**
//     *
//     */
//    public void daySpinnerOnClick(boolean isStartDateSpinner, int day) {
//        if (day == 0) {
//            selectedDatesArray[0][0] = 0;
//            selectedDatesArray[1][0] = 0;
//        } else if (isStartDateSpinner) {
//            if (selectedDatesArray[1][2] == selectedDatesArray[0][2] // same year
//                    && selectedDatesArray[1][1] == selectedDatesArray[0][1] // same month
//                    && selectedDatesArray[1][0] < day) // if end-date is lower than start-date day.
//            {
//                getEndDateSelectorFragment().changeDateManually(day, 1);
//                selectedDatesArray[1][0] = day;
//            }
//            selectedDatesArray[0][0] = day;
//        } else // if day in end date selector changed
//        {
//            if (selectedDatesArray[1][2] == selectedDatesArray[0][2] // same year
//                    && selectedDatesArray[1][1] == selectedDatesArray[0][1] // same month
//                    && selectedDatesArray[0][0] > day) // if start-date day is higher than end-date day.
//            {
//                getStartDateSelectorFragment().changeDateManually(day, 1);
//                selectedDatesArray[0][0] = day;
//            }
//            selectedDatesArray[1][0] = day;
//        }
//        filterKostenLijst();
//    }
//
//    public void monthSpinnerOnClick(boolean isStartDateSpinner, int month) {
//        if (month == 0) {
//            selectedDatesArray[0][1] = 0;
//            selectedDatesArray[1][1] = 0;
//        } else if (isStartDateSpinner) {
//            if (selectedDatesArray[1][2] == selectedDatesArray[0][2] // same year
//                    && selectedDatesArray[1][1] < month) // end date month is lower than start date month
//            {
//                getEndDateSelectorFragment().changeDateManually(month, 2);
//                selectedDatesArray[1][1] = month;
//            }
//            if (
//                // equal month, but start day is bigger than end day
//                    selectedDatesArray[1][2] == selectedDatesArray[0][2] && // same year
//                            selectedDatesArray[1][1] == selectedDatesArray[0][1] // same month
//                            && selectedDatesArray[1][0] < selectedDatesArray[0][0]) // smaller day
//            {
//                getEndDateSelectorFragment().changeDateManually(selectedDatesArray[0][0], 1);
//                selectedDatesArray[1][0] = selectedDatesArray[0][0];
//            }
//            selectedDatesArray[0][1] = month;
//        } else // if month in end date selector changed
//        {
//            if (selectedDatesArray[1][2] == selectedDatesArray[0][2] // same year
//                    && selectedDatesArray[0][1] > month) {// start date month is higher / bigger than start date month
//                getStartDateSelectorFragment().changeDateManually(month, 2);
//                selectedDatesArray[0][1] = month;
//            }
//            if (
//                // equal month, but start day is bigger than end day
//                    selectedDatesArray[1][2] == selectedDatesArray[0][2] && // same year
//                            selectedDatesArray[0][1] == selectedDatesArray[1][1] // same month
//                            && selectedDatesArray[0][0] > selectedDatesArray[1][0]) // bigger day
//            {
//                getStartDateSelectorFragment().changeDateManually(selectedDatesArray[1][0], 1);
//                selectedDatesArray[0][0] = selectedDatesArray[1][0];
//            }
//            selectedDatesArray[1][1] = month;
//        }
//        filterKostenLijst();
//    }
//
//    public void yearSpinnerOnClick(boolean isStartDateSpinner, int year) {
//        if (year == 0) {
//            selectedDatesArray[0][2] = 0;
//            selectedDatesArray[1][2] = 0;
//        } else if (isStartDateSpinner) {
//            if (selectedDatesArray[1][2] < year) { // if end date year is < new value of year
//                getEndDateSelectorFragment().changeDateManually(year, 3);
//                selectedDatesArray[1][2] = year;
//            }
//            if (selectedDatesArray[1][2] == selectedDatesArray[0][2] // same year
//                    && selectedDatesArray[1][1] < selectedDatesArray[0][1]) // end date month is lower than start date month
//            {
//                getEndDateSelectorFragment().changeDateManually(selectedDatesArray[0][1], 2);
//                selectedDatesArray[1][1] = selectedDatesArray[0][1];
//            }
//            if (
//                // equal month, but start day is bigger than end day
//                    selectedDatesArray[1][2] == selectedDatesArray[0][2] && // same year
//                            selectedDatesArray[1][1] == selectedDatesArray[0][1] // same month
//                            && selectedDatesArray[1][0] < selectedDatesArray[0][0]) // smaller day
//            {
//                getEndDateSelectorFragment().changeDateManually(selectedDatesArray[0][0], 1);
//                selectedDatesArray[1][0] = selectedDatesArray[0][0];
//            }
//            selectedDatesArray[0][2] = year;
//        } else // if month in end date selector changed
//        {
//            if (selectedDatesArray[0][2] > year) { // if start date year is > new value of year
//                getStartDateSelectorFragment().changeDateManually(year, 3);
//                selectedDatesArray[0][2] = year;
//            }
//            if (selectedDatesArray[1][2] == selectedDatesArray[0][2] // same year
//                    && selectedDatesArray[0][1] > selectedDatesArray[1][1]) // start month is > end date month
//            {
//                getStartDateSelectorFragment().changeDateManually(selectedDatesArray[1][1], 2);
//                selectedDatesArray[0][1] = selectedDatesArray[1][1];
//            }
//            if (
//                // equal month, but start day is bigger than end day
//                    selectedDatesArray[1][2] == selectedDatesArray[0][2] && // same year
//                            selectedDatesArray[1][1] == selectedDatesArray[0][1] // same month
//                            && selectedDatesArray[0][0] > selectedDatesArray[1][0]) // bigger day
//            {
//                getStartDateSelectorFragment().changeDateManually(selectedDatesArray[1][0], 1);
//                selectedDatesArray[0][0] = selectedDatesArray[1][0];
//            }
//            selectedDatesArray[1][2] = year;
//        }
//        filterKostenLijst();
//    }
//    public Kosten_Datum_Selectie getFilterSettingsFragment() {
//        if (filterSettingsFragment == null)
//            filterSettingsFragment = (Kosten_Datum_Selectie) getSupportFragmentManager()
//                    .findFragmentById(R.id.filterSettingsFragment);
//        return filterSettingsFragment;
//    }
}
