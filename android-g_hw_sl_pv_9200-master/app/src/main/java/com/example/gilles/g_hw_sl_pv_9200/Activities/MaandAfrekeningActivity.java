package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.gilles.g_hw_sl_pv_9200.Fragments.DateSelectionFragment;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;
import com.example.gilles.g_hw_sl_pv_9200.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaandAfrekeningActivity extends AppCompatActivity {

    @BindView(R.id.listViewKosten)
    ListView kostenListView;
    private List<Kost> kosten;
    private List<HashMap<String, String>> kostenLijst = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maand_afrekening);
        ButterKnife.bind(this);

        boolean[] hiddenSelectors = {true, false, false}; // hide daySpinner
        DateSelectionFragment frag = getDateSelectionFragment();
        frag.hideSelectors(hiddenSelectors);
        frag.setSelectedDate(new Date()); // set dateSelector on month & year of today.

        kosten = getIntent().getParcelableArrayListExtra("kosten");

        initKostenListViewAdapter(); // eventueel mogelijkheid tot doorgeven van adapter in kostenoverzicht via intent?
    }

    public DateSelectionFragment getDateSelectionFragment() {
        return (DateSelectionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dateSelector);
    }

    public void initKostenListViewAdapter() {
        vulKostenLijst();
        //hashmap
        myHashAdapter = new KostOverzichtScherm.MyHashAdapter(this, kostenLijst, R.layout.simplerow, from, to);
        kostenListView.setAdapter(myHashAdapter);

        kostenListView.setOnItemClickListener((parent, view, position, id) ->
        {
            Kost selectedCost = this.kosten.get(position);
            gaNaarKostDetailScherm(selectedCost);
        });
        SimpleAdapter adapter = new SimpleAdapter(this, kostenLijst, R.layout.simplerow)
    }

    private void vulKostenLijst()(
    int maand)

    {
        for (Kost kost : kosten) {
            if (kost.isGoedGekeurd() && kost.getPurchasingMonth() == maand)
                kostenLijst.add(kost);
        }
    }
}
