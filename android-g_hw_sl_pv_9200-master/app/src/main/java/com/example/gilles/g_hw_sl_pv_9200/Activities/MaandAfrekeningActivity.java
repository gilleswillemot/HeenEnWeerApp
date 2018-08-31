package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilles.g_hw_sl_pv_9200.Fragments.DateSelectionFragment;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.DateSelectorFragment;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.Kosten_Datum_Selectie;
import com.example.gilles.g_hw_sl_pv_9200.HTTPClient.RetrofitClient;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaandAfrekeningActivity extends AppCompatActivity implements DateSelectorFragment.Actions {

    @BindView(R.id.listViewKosten)
    ListView kostenListView;
    @BindView(R.id.totaleKostTextView)
    TextView totaleKostTextView;
    @BindView(R.id.aantalKostenTextView)
    TextView aantalKostenTextView;
    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.yearSpinner)
    Spinner yearSpinner;

    private List<Kost> kosten; // alle kosten van het gezin
    private List<HashMap<String, String>> kostenLijst = new ArrayList<>(); // weergegeven kosten.
    private SimpleAdapter kostenAdapter;
    private Calendar calendar = Calendar.getInstance();
    private ArrayList<String> years = new ArrayList<>();

    private String huidigGezinId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maand_afrekening);
        ButterKnife.bind(this);

//        boolean[] hiddenSelectors = {true, false, false}; // hide daySpinner
//        DateSelectionFragment frag = getDateSelectionFragment();
//        frag.hideSelectors(hiddenSelectors);
//        frag.setSelectedDate(new Date()); // set dateSelector on month & year of today.

//        kosten = getIntent().getParcelableArrayListExtra("kosten");

        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        huidigGezinId = prefs.getString("huidigGezinId", "0");

        getKostenFromDatabase(huidigGezinId);
    }

//    public DateSelectionFragment getDateSelectionFragment() {
//        return (DateSelectionFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.dateSelector);
//    }

    public void initKostenListViewAdapter() {
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);


        String[] from = {"Info", "Bedrag"};
        int[] to = new int[]{R.id.naamKost, R.id.bedragKost};

        kostenAdapter = new SimpleAdapter(this, kostenLijst, R.layout.simplerow, from, to);
        kostenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        kostenListView.setAdapter(kostenAdapter);
        updateAdapterData(thisMonth, thisYear); // toon maandafrekening van deze maand.

        kostenListView.setOnItemClickListener((parent, view, position, id) ->
        {
            int index = Integer.parseInt(kostenLijst.get(position).values().toArray()[1].toString());
            // 2nd index, because Id comes after Info and before Bedrag alfabeticly
            Kost selectedCost = kosten.get(index);
            if (selectedCost != null)
                gaNaarKostDetailScherm(selectedCost);
        });
    }

    private void gaNaarKostDetailScherm(Kost selectedCost) {
        Intent intent = new Intent(this, KostToevoegen.class);
        intent.putExtra("geselecteerdeKost", selectedCost);
        intent.putExtra("FROM_ACTIVITY", "MaandAfrekeningActivity"/*getLocalClassName()*/);

        startActivity(intent);
    }

    private void updateAdapterData(int maand, int year) {
        kostenLijst.clear();

        float kostenSom = 0;
        int aantalKosten = 0;
        int teller = 0;
        for (Kost kost : kosten) {
            int kostMaand = kost.getPurchasingMonth();
            int kostJaar = kost.getPurchasingYear();

            if (kostMaand == maand && kostJaar == year) {
                kostenSom += kost.bedrag;
                aantalKosten++;

                HashMap<String, String> hm = new HashMap<>();
                hm.put("Info", kost.toString());
                hm.put("Bedrag", kost.getBedragSpecial());
                hm.put("Id", kost.getId());
                hm.put("Index", String.valueOf(teller));
                kostenLijst.add(hm);
            }
            teller++;
        }

        String sourceString1 = "Aantal kosten: <b>" + String.valueOf(aantalKosten) + "</b>";
        aantalKostenTextView.setText(Html.fromHtml(sourceString1));
        String sourceString = "Totale kosten: <b>" + String.valueOf(kostenSom / 2) + "</b>";
        totaleKostTextView.setText(Html.fromHtml(sourceString));
        kostenAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MaandAfrekeningActivity.this, KostOverzichtScherm.class);
        startActivity(intent);
    }

    public int getYearOfOldestExpense() {
        int beginYear = calendar.get(Calendar.YEAR);
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
                            List<Kost> tempList = new ArrayList<>();
                            for (Kost kost : kosten) {
                                if (kost.isGoedGekeurd()) {
                                    tempList.add(kost);
                                }
                            }

                            kosten = tempList;
                            // eventueel mogelijkheid tot doorgeven van adapter in kostenoverzicht via intent?
                            initKostenListViewAdapter();

                            getYearOfOldestExpense(); // inits years
                            DateSelectorFragment frag = getDateSelectorFragment();
                            frag.initYearSpinner(false, years);
                            frag.initMonthSpinner(false);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MaandAfrekeningActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public DateSelectorFragment getDateSelectorFragment() {
//        if (dateSelectorFragment == null)
        DateSelectorFragment dateSelectorFragment = (DateSelectorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dateSelector);
        return dateSelectorFragment;
    }

    private void orderKostenByDescendingPurchasingDate() {
        Collections.sort(kosten, this::compareDescendingDate);
    }

    public int compareDescendingDate(Kost lhs, Kost rhs) {
        return rhs.getAankoopDatum().compareTo(lhs.getAankoopDatum());
    }

}
