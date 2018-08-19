package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.Fragments.fragment_info_kind_1;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.fragment_info_kind_2;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.fragment_info_kind_3;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Data.Databank;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gebruiker;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kind;

public class KindProfielActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TextView naamKind;
    private ProgressBar progressBar;
    private Databank data;
    private Gebruiker selectedGebruiker;

    /**
     * kindprofiel aanmaken en alle info laden
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind_profiel);

        String gebruikerId;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                gebruikerId= null;
            } else {
                gebruikerId= extras.getString("Gebruiker");
            }
        } else {
            gebruikerId= (String) savedInstanceState.getSerializable("Gebruiker");
        }

        this.data = new Databank();
        this.selectedGebruiker = this.data.getGezin().getGezinslidMetId(gebruikerId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.selectedGebruiker);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        naamKind = (TextView) findViewById(R.id.naamKind);
        naamKind.setText(this.selectedGebruiker.getVoorNaam()+" "+this.selectedGebruiker.getNaam());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(80);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_kind, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Kind selectedKind;

        public SectionsPagerAdapter(FragmentManager fm, Gebruiker selectedGebruiker) {
            super(fm);
            this.selectedKind = (Kind) selectedGebruiker;
            System.out.println(this.selectedKind);
        }

        @Override
        public Fragment getItem(int position) {
           switch (position){
               case 0 : fragment_info_kind_1 tab1 = new fragment_info_kind_1(this.selectedKind);
                    return tab1;
               case 1 : fragment_info_kind_2 tab2 = new fragment_info_kind_2(this.selectedKind);
                   return tab2;
               case 2 : fragment_info_kind_3 tab3 = new fragment_info_kind_3(this.selectedKind);
                   return tab3;
               default: return null;
           }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
