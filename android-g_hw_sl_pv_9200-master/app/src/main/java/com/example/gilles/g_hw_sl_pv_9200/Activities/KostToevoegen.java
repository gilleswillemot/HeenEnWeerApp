package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.DateSelectionFragment;
import com.example.gilles.g_hw_sl_pv_9200.HTTPClient.RetrofitClient;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gebruiker;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gezin;
import com.example.gilles.g_hw_sl_pv_9200.Models.User;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KostToevoegen extends AppCompatActivity {
    @BindView(R.id.bedragTextField)
    EditText bedrag;
    @BindView(R.id.naamKostTextField)
    EditText naamKost;
    @BindView(R.id.btnKostVerwijderen)
    Button btnKostVerwijderen;
    @BindView(R.id.mededelingEditText)
    EditText mededeling;
    @BindView(R.id.btnKostToevoegen)
    Button btnKostToevoegen;
    @BindView(R.id.buyerSpinner)
    Spinner buyerSpinner;
    @BindView(R.id.involvedPersSpinner)
    Spinner involvedPersSpinner;
    @BindView(R.id.statusSpinner)
    Spinner statusSpinner;
    @BindView(R.id.categorieSpinner)
    Spinner categorySpinner;
    @BindView(R.id.colorSpinner)
    Spinner colorSpinner;
    @BindView(R.id.uitzonderlijkeKostCheckBox)
    CheckBox uitzonderlijkeKostCheckBox;
    @BindView(R.id.statusTextView)
    TextView statusTextView;

    private String huidigGezinId;
    private String loggedInUserId;
    private String token;
    private String username;
    private String previousActivity;

    private List<User> gezinsLeden;
    private String[] categories = {"Vervoer", "Medisch", "Sport", "Voeding", "Kledij", "Ander"};
    private String[] colors = {"rood", "blauw", "geel", "groen", "wit", "cyaan", "grijs", "paars"};
    private String[] statuses = {"onbepaald" , "goedgekeurd", "afgekeurd"};

    private List<String> buyers = new ArrayList<>();
    private List<String> involvedPersons = new ArrayList<>(Arrays.asList("Iedereen"));

    private Kost geselecteerdeKost;

    private boolean statusFlag = false;

    /**
     * aanmaken van het kosttoevoegenfragment
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Check if a Cost object was included (via selection in overview) in the intent,
        // if so => show details; else create cost.
        geselecteerdeKost = getIntent().getParcelableExtra("geselecteerdeKost");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_toevoegen);
        ButterKnife.bind(this);

        // huidigGezinId ophalen uit local storage (sharedPreferences)
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        huidigGezinId = prefs.getString("huidigGezinId", "0");
        loggedInUserId = prefs.getString("userId", "0");
        username = prefs.getString("username", "0");
        token = prefs.getString("token", "0");

        previousActivity = getIntent().getStringExtra("FROM_ACTIVITY");


        getGezinsLedenFromDatabase();

        initColorPickerSpinner();

        initCategorySpinner();

        if (geselecteerdeKost != null) { // Geef details van deze Kost weer.
            btnKostToevoegen.setText("Wijzigen doorvoeren");
            bedrag.setText(Float.toString(geselecteerdeKost.bedrag));
            naamKost.setText(geselecteerdeKost.naamKost);
            colorSpinner.setSelection(Arrays.asList(colors).indexOf(geselecteerdeKost.getKleur()));
            categorySpinner.setSelection(Arrays.asList(categories).indexOf(geselecteerdeKost.categorie));
            mededeling.setText(geselecteerdeKost.getBeschrijving());
            getDateSelectionFragment().setSelectedDate(geselecteerdeKost.getAankoopDatum());
            uitzonderlijkeKostCheckBox.setChecked(geselecteerdeKost.isUitzonderlijkeKost());

            btnKostVerwijderen.setVisibility(View.VISIBLE);

            initStatusSpinner(geselecteerdeKost.getStatus());
            if(username.equalsIgnoreCase(geselecteerdeKost.getAangekochtDoor())) {
                statusSpinner.setEnabled(false);
                statusSpinner.setClickable(false);
            }
        } else { // maak nieuwe kost
            statusSpinner.setVisibility(View.INVISIBLE);
            statusTextView.setVisibility(View.INVISIBLE);
        }

    }

    private void initStatusSpinner(String status){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
        statusSpinner.setSelection(Arrays.asList(statuses).indexOf(status));
    }

    private void getGezinsLedenFromDatabase() {
        Call<ResponseBody> call = RetrofitClient.getInstance(this).getApi().getGezin(huidigGezinId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = response.body().string();
                    Type listType = new TypeToken<ArrayList<User>>() {
                    }.getType();
                    gezinsLeden = new Gson().fromJson(responseString, listType);
                    initBuyerSpinner();
                    initInvolvedPersonSpinner();

                    if(geselecteerdeKost != null){
                        involvedPersSpinner.setSelection(involvedPersons.indexOf(geselecteerdeKost.getAangekochtVoor()));
                        buyerSpinner.setSelection(buyers.indexOf(geselecteerdeKost.getAangekochtDoor()));
                        buyerSpinner.setEnabled(false);
                        buyerSpinner.setClickable(false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(KostToevoegen.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void voegKostToeAanDatabase(Kost nieuwKost) {
        // kost toevoegen via API request
        Call<Kost> call = RetrofitClient.getInstance(this).getApi().voegKostToe(huidigGezinId, nieuwKost);
        call.enqueue(new Callback<Kost>() {
            @Override
            public void onResponse(Call<Kost> call, Response<Kost> response) {
                Intent intent = new Intent(KostToevoegen.this, KostOverzichtScherm.class);
                startActivity(intent);
                Toast.makeText(KostToevoegen.this, "Kost succesvol toegevoegd", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Kost> call, Throwable t) {
                Toast.makeText(KostToevoegen.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editCost(Kost cost) {
        // kost toevoegen via API request
        Call<Kost> call = RetrofitClient.getInstance(this).getApi().wijzigKost(geselecteerdeKost.getId(), cost);
        call.enqueue(new Callback<Kost>() {
            @Override
            public void onResponse(Call<Kost> call, Response<Kost> response) {
                Intent intent = new Intent(KostToevoegen.this, KostOverzichtScherm.class);
                startActivity(intent);
                Toast.makeText(KostToevoegen.this, "Kost succesvol gewijzigd", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Kost> call, Throwable t) {
                Toast.makeText(KostToevoegen.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void verwijderKost(View view) {
        // kost verwijderen via API call
        Call<ResponseBody> call = RetrofitClient.getInstance(this).getApi().verwijderKost(huidigGezinId, geselecteerdeKost.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent intent = new Intent(KostToevoegen.this, KostOverzichtScherm.class);
                startActivity(intent);
                Toast.makeText(KostToevoegen.this, "Kost succesvol verwijderd", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(KostToevoegen.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Toevoegen van een nieuwe kost aan de DB.
     */
    public void voegKostToe(View v) {
        if (bedrag.getText().toString().equals("") || bedrag.getText().toString().length() == 0
                || naamKost.getText().toString().equals("") || naamKost.getText().toString().length() == 0
                ) {
            new CustomToast().Show_Toast(getApplicationContext(), getWindow().getDecorView().getRootView(),
                    "De velden naam en bedrag zijn verplicht in te vullen");
        } else {
            String bedragString = bedrag.getText().toString();
            boolean firstTry = true;
            float kostBedrag = 0;
            while (firstTry) {
                try {
                    kostBedrag = Float.parseFloat(bedragString);
                    firstTry = false;
                } catch (NumberFormatException e) {
                    if (firstTry) {
                        bedragString = bedragString.replace(",", ".");
                    } else {
                        Toast.makeText(this, "Gelieve een correct bedrag in te geven." +
                                        " U heeft waarschijnlijk een \".\" (punt) gebruikt terwijl het een \",\" (komma) moet zijn"
                                , Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            Date aankoopDatum = getDateSelectionFragment().getSelectedDate();
            String aangekochtDoor = buyerSpinner.getSelectedItem().toString();
            String aangekochtVoor = involvedPersSpinner.getSelectedItem().toString();
            int selectedStatusPosition = statusSpinner.getSelectedItemPosition();
            String status = geselecteerdeKost == null || selectedStatusPosition == -1 ?
                    "onbepaald" : statuses[selectedStatusPosition];

            Kost nieuweKost = new Kost(naamKost.getText().toString(), kostBedrag, aankoopDatum,
                    aangekochtDoor, aangekochtVoor, mededeling.getText().toString(), categorySpinner.getSelectedItem().toString(),
                    colorSpinner.getSelectedItem().toString(), uitzonderlijkeKostCheckBox.isChecked(),
                    status);
            if (geselecteerdeKost != null) // edit / update cost.
            editCost(nieuweKost);
            else{
                voegKostToeAanDatabase(nieuweKost);
            }
        }
    }

    public DateSelectionFragment getDateSelectionFragment() {
        return (DateSelectionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dateSelector);
    }

    private void initBuyerSpinner() {
        for (User user : gezinsLeden) {
            if (user.isOuder()) {
                String voornaam = user.getVoornaam();
                buyers.add(voornaam);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                buyers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buyerSpinner.setAdapter(adapter);
    }

    private void initInvolvedPersonSpinner() {
        int counter = 0;
        for (User user : gezinsLeden) {
            if(!user.isOuder()) counter++;
            String voornaam = user.getVoornaam();
            involvedPersons.add(voornaam);
        }
        if (counter > 1) involvedPersons.add(1,"kinderen"); // als er meerdere kinderen zijn.

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                involvedPersons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        involvedPersSpinner.setAdapter(adapter);
    }

    private void initColorPickerSpinner() {
        List<String> spinnerArray = new ArrayList<>();
        for (String color : colors) {
            spinnerArray.add(color);
        }

        MyAdapter<String> adapter = new MyAdapter<>(this, android.R.layout.simple_spinner_item,
                spinnerArray);
        colorSpinner.setAdapter(adapter);
    }

    private void initCategorySpinner() {
        List<String> spinnerArray = new ArrayList<>();
        for (String category : categories) {
            spinnerArray.add(category);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        if (previousActivity.equalsIgnoreCase("KostOverzichtScherm"))
        {
              intent = new Intent(KostToevoegen.this, KostOverzichtScherm.class);
        } else {
            intent = new Intent(KostToevoegen.this, MaandAfrekeningActivity.class);
        }
        startActivity(intent);
    }

    public class MyAdapter<String> extends ArrayAdapter<String> {

        public MyAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            v.setBackgroundColor(getColor(position));
            return v;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View itemView = super.getDropDownView(position, convertView, parent);

            itemView.setBackgroundColor(getColor(position));

            return itemView;
        }

        private int getColor(int position) {
            int c;

            switch (position) {
                case 0:
                    c = Color.RED;
                    break;
                case 1:
                    c = Color.BLUE;
                    break;
                case 2:
                    c = Color.YELLOW;
                    break;
                case 3:
                    c = Color.GREEN;
                    break;
                case 4:
                    c = Color.WHITE;
                    break;
                case 5:
                    c = Color.CYAN;
                    break;
                case 6:
                    c = Color.GRAY;
                    break;
                case 7:
                    c = Color.MAGENTA;
                    break;

                default:
                    c = Color.YELLOW;
            }
            return c;
        }
    }
}
