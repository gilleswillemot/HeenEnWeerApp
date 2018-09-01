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
import com.example.gilles.g_hw_sl_pv_9200.SharedClasses.MyHashAdapter;
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
    @BindView(R.id.kostenAanvaardenButton)
    Button kostenAanvaardenButton;
    @BindView(R.id.maandAfrekeningBtn)
    Button maandAfrekeningBtn;
    @BindView(R.id.listViewKosten)
    ListView kostenListView;
    @BindView(R.id.geenKostenMessage)
    TextView geenKostenMessage;
    @BindView(R.id.kostenListViewHeader)
    TextView kostenListViewHeader;

    // initiation of 'kosten' needed in order for custom adapter to have a reference, otherwise => nullp.
    private List<Kost> kosten = new ArrayList<>();
    private String token;
    private String huidigGezinId;
    private MyHashAdapter<Kost> myHashAdapter;
    private List<HashMap<String, String>> kostenLijst = new ArrayList<>();

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

        // huidigGezinId ophalen uit local storage (sharedPreferences)
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        huidigGezinId = prefs.getString("huidigGezinId", "0");
        token = prefs.getString("token", "0");
        initKostenAdapter();

        getKostenFromDatabase(huidigGezinId);
    }

    public void kostenAanvaarden(View v) {
        if (!kostenLijst.isEmpty()) {
            for (HashMap<String, String> hm : kostenLijst) {
                Kost kost = kosten.get(Integer.parseInt(hm.values().toArray()[1].toString()));
                kost.setStatus("goedgekeurd");
                editCost(kost);
            }
        }
        Toast.makeText(KostOverzichtScherm.this, "Kosten zijn aanvaard.", Toast.LENGTH_LONG).show();
        myHashAdapter.notifyDataSetChanged();
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

                            showGeenKostenMessage();
                            maandAfrekeningBtn.setVisibility(View.INVISIBLE);

                            // hide dateSelectorFragment
                            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                            fm.beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                    .hide(getDateSelectorFragment())
                                    .commit();
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

    private void showGeenKostenMessage() {
        geenKostenMessage.setVisibility(View.VISIBLE);

        kostenListViewHeader.setVisibility(View.INVISIBLE);
        kostenListView.setVisibility(View.INVISIBLE);
        kostenAanvaardenButton.setVisibility(View.INVISIBLE);
    }

    private void hideGeenKostenMessage() {
        geenKostenMessage.setVisibility(View.INVISIBLE);

        kostenListViewHeader.setVisibility(View.VISIBLE);
        kostenListView.setVisibility(View.VISIBLE);
        kostenAanvaardenButton.setVisibility(View.VISIBLE);
    }

    private void orderKostenByDescendingPurchasingDate() {
        Collections.sort(kosten, this::compareDescendingDate);
    }

    public DateSelectorFragment getDateSelectorFragment() {
        DateSelectorFragment dateSelectorFragment = (DateSelectorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dateSelector);
        return dateSelectorFragment;
    }

    private void initKostenAdapter() {
        String[] from = {"Info", "Bedrag"};
        int[] to = new int[]{R.id.naamKost, R.id.bedragKost};

        myHashAdapter = new MyHashAdapter(this, kostenLijst, R.layout.simplerow, from, to,
               /* kosten,*/ true);
        kostenListView.setAdapter(myHashAdapter);

        kostenListView.setOnItemClickListener((parent, view, position, id) ->
        {
            int index = Integer.parseInt(kostenLijst.get(position).values().toArray()[1].toString());
            // index 1, because Id comes after Info and before Bedrag alfabeticly

            Kost selectedCost = kosten.get(index);
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

        performUpdate();
    }

    private void performUpdate(){
        if (kostenLijst.isEmpty()) {
            showGeenKostenMessage();
        } else {
            hideGeenKostenMessage();
            myHashAdapter.notifyDataSetChanged();
        }
    }

    private void updateAdapterData(int month, int year) {
        month = month - 1; // index 0 equals default value, so january starts at index 1
        kostenLijst.clear();

        int teller = 0;
        for (Kost kost : kosten) {
            boolean addKost = false;
            int kostMaand = kost.getPurchasingMonth();
            int kostJaar = kost.getPurchasingYear();

            if (month == -1 && year == 0) addKost = true;
            if (month == -1) {
                if (year == kostJaar) addKost = true;
            } else if (year == 0) {
                if (month == kostMaand) addKost = true;
            } else if (kostMaand == month && kostJaar == year) addKost = true;

            if (addKost) {
                kostenLijst.add(makeKostHashMap(kost, teller));
            }
            teller++;
        }
        performUpdate();
    }

    private void fillAdapterData(List<Kost> list) {
        int teller = 0;
        for (Kost kost : list) {
            kostenLijst.add(makeKostHashMap(kost, teller++));
        }
    }

    private HashMap<String, String> makeKostHashMap(Kost kost, int index) {
        HashMap<java.lang.String, java.lang.String> hm = new HashMap<>();
        hm.put("Info", kost.toString());
        hm.put("Bedrag", kost.getBedragSpecial());
        hm.put("Id", kost.getId());
        hm.put("Index", String.valueOf(index));
        return hm;
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

    public int compareDescendingDate(Kost lhs, Kost rhs) {
        return rhs.getAankoopDatum().compareTo(lhs.getAankoopDatum());
    }

    public int compareAscendingDate(Kost lhs, Kost rhs) {
        return lhs.getAankoopDatum().compareTo(rhs.getAankoopDatum());
    }
}