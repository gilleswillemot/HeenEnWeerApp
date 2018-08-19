package com.example.gilles.g_hw_sl_pv_9200.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.ActiviteitDetailFragment;
import com.example.gilles.g_hw_sl_pv_9200.Fragments.ActiviteitlijstFragment;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;
import com.example.gilles.g_hw_sl_pv_9200.Models.Activiteit;
import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Data.Databank;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gebruiker;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kind;
import com.example.gilles.g_hw_sl_pv_9200.Models.MyEventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lucas on 13-11-2017.
 */

public class KalenderActivity extends AppCompatActivity implements ActiviteitlijstFragment.Callback{

    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;
    private Calendar calendar;
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();

    private Databank data;

    private List<Activiteit> responseActiviteiten = new ArrayList<>();

    private List<Activiteit> activiteiten = new ArrayList<>();

    private TextView txtVerblijf;
    private TextView txtDatum;

    private String verblijf = "verblijven bij ";
    private String [] maanden = { "januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober","november","december"};
    private String [] dagen = {"Zondag","Maandag","Dinsdag","Woensdag","Donderdag","Vrijdag","Zaterdag"};

    private static View view;


    /**
     * Kalenderview aanmaken met opvullen van kalender en avtiviteitenlijst (fragment) onderaan
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = new Databank();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalender_layout);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        txtVerblijf = (TextView) findViewById(R.id.txtVerblijf);

        txtDatum = (TextView) findViewById(R.id.txtDatum);

        FragmentManager fragmentManager = getSupportFragmentManager();

        /*Date date = new Date();
        calendar = Calendar.getInstance();
        EventDay myEventDay = new EventDay(calendar,R.drawable.redcirlce);
        mEventDays.add(myEventDay);
        mCalendarView.setEvents(mEventDays);*/


        HashMap<String,EventDay>event = new HashMap<>();

        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
        Call<List<Activiteit>> call = dataInterface.getActiviteiten(Utils.GEBRUIKER.getId());
        //Log.d("calltostring",call);
        call.enqueue(new Callback<List<Activiteit>>() {
            @Override
            public void onResponse(Call<List<Activiteit>> call, Response<List<Activiteit>> response) {
                if(response.body()!=null){
                    responseActiviteiten = response.body();
                }

                for(Activiteit ac : responseActiviteiten){
                    Log.d("activiteit", ac.toString());
                    Log.d("datum ac to string", ac.getDatumStart().toString());
//                    Log.d("activitetidatum",ac.getDatumStart().toString());
//                    Log.d("Activiteiten", "Activiteiten opgezocht");
//                    Calendar cal = Calendar.getInstance();
//                    Date date = ac.getDatumStart();
//                    Log.d("Datum activiteit", date.toString());
//                    cal.setTime(date);
//                    cal.set(2017,11,15);
//                    Log.d("Calendar datum:",cal.toString());
//                    event.put(cal.getTime().toString(),new EventDay(cal, R.drawable.circlered));
                    Log.d("alle activiteten","rode bollen geven");
                    Calendar cals = Calendar.getInstance();
                    int maand = ac.getDatumStart().getMonth();
                    int dag = ac.getDatumStart().getDate();
                    int jaar = ac.getDatumStart().getYear();
                    cals.set(2017,maand,dag);
                    Log.d("Calendar datum:",jaar + "/" + maand + "/" + dag + " cal: "+ cals.toString());
                    event.put(cals.getTime().toString(),new EventDay(cals, R.drawable.circlered));

                }
                int i;
                for(i=27;i<31;i++){
                    Calendar cal = Calendar.getInstance();
                    cal.set(2017,10,i);
                    if(event.containsKey(cal.getTime().toString())){
                        event.put(cal.getTime().toString(),new EventDay(cal, R.drawable.circleredhome));
                    }else{event.put(cal.getTime().toString(),new EventDay(cal,R.drawable.ic_action_home));
                    }
                }

                for(i=1;i<4;i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(2017,10,i);
                    if (event.containsKey(cal.getTime().toString())) {
                        event.put(cal.getTime().toString(), new EventDay(cal, R.drawable.circleredhome));
                    } else {
                        event.put(cal.getTime().toString(), new EventDay(cal, R.drawable.ic_action_home));
                    }
                }


                ArrayList<EventDay> events = new ArrayList<>(event.values());
                mCalendarView.setEvents(events);
//                for(Activiteit ac :  activiteiten){
//                    Calendar cal = Calendar.getInstance();
//                    Date date = ac.getDatumStart();
//                    cal.set(2017,date.getMonth()-1,date.getDate());
//                    event.put(cal.getTime().toString(),new EventDay(cal, R.drawable.circlered));
//                    // events.add(new EventDay(cal, R.drawable.circlered));
//                }
            }



            @Override
            public void onFailure(Call<List<Activiteit>> call, Throwable t) {
                Log.d("Error:",""+t.getMessage());
            }
        });
//        for(Activiteit ac : responseActiviteiten){
//            Calendar cal = Calendar.getInstance();
//            Date date = ac.getDatumStart();
//            date.setYear(ac.getDatumStart().getYear()+1900);
//            cal.set(2017,date.getMonth()-1,date.getDate());
//            event.put(cal.getTime().toString(),new EventDay(cal, R.drawable.circlered));
//            // events.add(new EventDay(cal, R.drawable.circlered));
//
//        }
//        for (Activiteit ac:responseActiviteiten) {
//            Log.d("alle activiteten","rode bollen geven");
//            Calendar cals = Calendar.getInstance();
//            int maand = ac.getDatumStart().getMonth();
//            int dag = ac.getDatumStart().getDate();
//            cals.set(2017,maand,dag);
//            Log.d("Calendar datum:",maand + " " + dag + " "+ cals.toString());
//            event.put(cals.getTime().toString(),new EventDay(cals, R.drawable.circlered));
//        }


        //todo : verblijf    !!! als die dag verblijf moet er een huisje komen + nadien checken op activiteiten dan ipv huisje een huisje met circlered



        //    FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
     /*   floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });*/
        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                // previewNote(eventDay);
                Log.d("caldayclicked:",eventDay.getCalendar().toString());
                Calendar cl = eventDay.getCalendar();
                int dag = cl.getTime().getDay();
                int datum = cl.getTime().getDate();
//                Log.d("Cal zijn datum", " "+datum);
                int maand = cl.getTime().getMonth()+1;
//                Log.d("Cal zijn maand", " "+maand);
                int jaar = cl.getTime().getYear()+1900;
//                Log.d("Cal zijn jaar", " "+jaar);
                String datee = " "+dagen[dag] +" " + datum + " " + maanden[maand-1];
                txtDatum.setText(datee);
                txtDatum.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                String verb = data.getGezin().getKinderen().get(0).getVoorNaam()+", " + data.getGezin().getKinderen().get(1).getVoorNaam()+" "+verblijf + data.getGezin().getVader().getVoorNaam();
                txtVerblijf.setText(verb);

                /*
                HashMap<String,String> basisinfo = new HashMap<>();
                basisinfo.clear();

                //Als ze verblijven
                if(0<eventDay.getCalendar().getTime().getDay()&&eventDay.getCalendar().getTime().getDay()<6){
                    basisinfo.put("16u     Van school oppikken",  "              " + data.getGezin().getKinderen().get(0).getVoorNaam() + ", " + data.getGezin().getKinderen().get(1).getVoorNaam());
                    basisinfo.put("8u30    Naar school brengen", "              " + data.getGezin().getKinderen().get(0).getVoorNaam() + ", " + data.getGezin().getKinderen().get(1).getVoorNaam());
                }




                //Alle activiteiten
                int i;
                for (i = 0; i < data.getGezin().getKinderen().size() ; i++) {
                    for (int x = 0; x < data.getGezin().getKinderen().get(i).getActiviteiten().size() ; x++) {
                        if(data.getGezin().getKinderen().get(i).getActiviteiten().get(x).getDatumStart().getDate()==cl.getTime().getDate()/*
                                && data.getGezin().getKinderen().get(i).getActiviteiten().get(x).getDatum().getMonth()==cl.getTime().getMonth()
                        &&data.getGezin().getKinderen().get(i).getActiviteiten().get(x).getDatum().getYear()==(cl.getTime().getYear()+1900)*//*) {
                            basisinfo.put(data.getGezin().getKinderen().get(i).getActiviteiten().get(x).getDatumStart().getHours()
                                    + "u"+ data.getGezin().getKinderen().get(i).getActiviteiten().get(x).getDatumStart().getMinutes()+ "     "
                                    + data.getGezin().getKinderen().get(i).getActiviteiten().get(x).getBeschrijving(), "              " + data.getGezin().getKinderen().get(i).getVoorNaam());
                        }
                        x++;
                    }
                    i++;
                }





                List<HashMap<String,String>> listItems = new ArrayList<>();
                SimpleAdapter adapter  = new SimpleAdapter(listActiviteiten.getContext(), listItems, R.layout.list_item, new String[]{"lijn1", "lijn2"}, new int[]{R.id.text1, R.id.text2});
                */

                /*
                List<String> your_array_list = new ArrayList<>();
                your_array_list.add("8u30");
                your_array_list.add("16u");
                your_array_list.add("18u");
                your_array_list.add("18u30");
*//*
                Iterator it = basisinfo.entrySet().iterator();
                while(it.hasNext()){
                    HashMap<String, String> resultMap = new HashMap<>();
                    Map.Entry paar = (Map.Entry) it.next();
                    resultMap.put("lijn1",paar.getKey().toString());
                    resultMap.put("lijn2",paar.getValue().toString());
                    listItems.add(resultMap);
                }

                listActiviteiten.setAdapter(adapter);
                */

                activiteiten.clear();
                ArrayList<String> kinderen = new ArrayList<>();
                for (Kind kind: data.getGezin().getKinderen()) {
                    kinderen.add(kind.getVoorNaam());
                }
                // lijst van activiteiten door te geven aan fragment
                //als ze verblijven !!! extra check als ze verblijven of niet

                if(0<dag&&dag<6){
                    activiteiten.add(new Activiteit("School",new Date(jaar,maand,datum,8,30),
                            new Date(jaar,maand,datum,16,00),
                            "",kinderen));
                }
                //Alle andere activiteiten
                //for (Kind kind: data.getGezin().getKinderen()) {
                for (Activiteit ac : responseActiviteiten) {
//                    Log.d("Act startdatum",ac.getDatumStart().getDate() + " " + ac.getDatumStart().getMonth() + ac.getDatumStart().getYear());
                    if(ac.getDatumStart().getDate()==datum
                            &&ac.getDatumStart().getMonth()==maand-1
                            &&ac.getDatumStart().getYear()==jaar-1900){
                        activiteiten.add(ac);
                    }
                    List<String> deelnemers = new ArrayList<>();
                    for (String deeln: ac.getDeelnemersId()) {
                        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
                        Call<Gebruiker> call = dataInterface.getGebruiker(deeln);
                        call.enqueue(new Callback<Gebruiker>() {
                            @Override
                            public void onResponse(Call<Gebruiker> call, Response<Gebruiker> response) {
                                if(response.body()!=null){
                                    deelnemers.add(response.body().getVoorNaam());
                                    ac.setDeelnemers(deelnemers);
                                }

                            }

                            @Override
                            public void onFailure(Call<Gebruiker> call, Throwable t) {
                                Log.d("Error:",t.getMessage());
                            }
                        });
                    }

                }


                // }

                Collections.sort(activiteiten, new Comparator<Activiteit>() {
                    public int compare(Activiteit a1, Activiteit a2) {
                        int tijdstip1 = a1.getDatumStart().getHours()*100+a1.getDatumStart().getMinutes();
                        int tijdstip2 = a2.getDatumStart().getHours()*100+a2.getDatumStart().getMinutes();
//                        if (a1.getDatumStart() != null && a2.getDatumStart() != null && a1.getDatumStart().compareTo(a1.getDatumStart()) != 0) {
//                            return a1.getDatumStart().compareTo(a2.getDatumStart());
//                        } else {
//                            return a1.getDatumEinde().compareTo(a2.getDatumEinde());
//                        }
                        return tijdstip1-tijdstip2;
                    }
                });


                ActiviteitlijstFragment frag = (ActiviteitlijstFragment) fragmentManager.findFragmentById(R.id.activiteitenlijstFragment);
                frag.updateAdapter();
            }

        });
    }

    public List<Activiteit> getActiviteiten(){
        return activiteiten;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
            MyEventDay myEventDay = data.getParcelableExtra(RESULT);
            mCalendarView.setDate(myEventDay.getCalendar());
            mEventDays.add(myEventDay);
            mCalendarView.setEvents(mEventDays);
        }
    }/*
    private void addNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);
    }
    private void previewNote(EventDay eventDay) {
        Intent intent = new Intent(this, NotePreviewActivity.class);
        if(eventDay instanceof MyEventDay){
            intent.putExtra(EVENT, (MyEventDay) eventDay);
        }
        startActivity(intent);
    }*/

    /**
     * methode voor het aanklikken van een activiteit
     * @param item
     */
    @Override
    public void onListFragmentInteraction(Activiteit item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ActiviteitDetailFragment fragment = ActiviteitDetailFragment.newInstance(item);
        ft.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.fragmentContainerKalender,fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        // TODO ; werkt nog niet
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(Utils.ActiviteitDetailFragment);


        // Check if both are null or not
        // If both are not null then replace login fragment else do backpressed
        // task

        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }
        else
            super.onBackPressed();
    }
}
/*
    private static final String TAG = "KalenderActivity";
    private CalendarView kalenderView;
    private TextView txtVerblijf;
   // private TextView txtActiviteit;
  //  private TextView lblActiviteit;
    private TextView txtDatum;
    private ListView listActiviteiten;
    private String [] maanden = {"januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober","november", "december"};
    private String verblijf = "Jan verblijft bij Kurt";
    private String activiteit = "Voetbaltraining voor Jan:\n    Gevoerd door Kurt\n    Opgepikt door Eva";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalender_layout);
        //kalenderView = (CalendarView) findViewById(R.id.kalenderView);
        txtVerblijf = (TextView) findViewById(R.id.txtVerblijf);
      //  txtActiviteit = (TextView) findViewById(R.id.txtActiviteit);
       // lblActiviteit = (TextView) findViewById(R.id.lblActiviteit);
        listActiviteiten = (ListView) findViewById(R.id.listActiviteiten);
        //txtActiviteit.setVisibility(View.INVISIBLE);
        listActiviteiten.setVisibility(View.INVISIBLE);
        txtDatum = (TextView) findViewById(R.id.txtDatum);
/*

        kalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = " " + i2 + " " + maanden[i1] + " " + i;
                Log.d(TAG, "onSelectedDayChange: " + date);
                txtDatum.setText(date);
                txtDatum.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                txtVerblijf.setText(verblijf);
                listActiviteiten.setVisibility(View.VISIBLE);
                //txtActiviteit.setVisibility(View.INVISIBLE);
                //lblActiviteit.setVisibility(View.INVISIBLE);
                //txtActiviteit.setText(activiteit);
            }
        });*/

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
*/