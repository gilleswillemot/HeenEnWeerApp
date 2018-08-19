package com.example.gilles.g_hw_sl_pv_9200.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Models.Activiteit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActiviteitDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActiviteitDetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ACTIVITEIT = "activiteit";

    // TODO: Rename and change types of parameters
    private Activiteit mActiviteit;
    private Button btnClose;
    private ListView lijst;

    public ActiviteitDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ActiviteitDetailFragment.
     */
    public static ActiviteitDetailFragment newInstance(Activiteit activiteit) {
        ActiviteitDetailFragment fragment = new ActiviteitDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ACTIVITEIT, activiteit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActiviteit = getArguments().getParcelable(ARG_ACTIVITEIT);
        }
    }

    /**
     * hoe een activiteitdetailfragment wordt aangemaakt
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activiteit_detail, container, false);
        TextView titelView = (TextView) view.findViewById(R.id.titelActiviteit);
        titelView.setText(mActiviteit.getTitel());
        btnClose = (Button) view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);
        lijst = (ListView) view.findViewById(R.id.detailsActiviteit);


        LinkedHashMap<String,String> basisinfo = new LinkedHashMap<>();
        basisinfo.clear();
        String min;
        if(mActiviteit.getDatumStart().getMinutes()==0) {
            min="00";
        }
        else{
            min="" + mActiviteit.getDatumStart().getMinutes();
        }
        basisinfo.put("Start",mActiviteit.getDatumStart().getHours()+"u"+
                min+"  "+
                mActiviteit.getDatumStart().getDate() + " / " + mActiviteit.getDatumStart().getMonth() + " / " +
                mActiviteit.getDatumStart().getYear());
        String min2;
        if(mActiviteit.getDatumStart().getMinutes()==0) {
            min2="00";
        }
        else {
            min2 = "" + mActiviteit.getDatumStart().getMinutes();
        }
        basisinfo.put("Einde",mActiviteit.getDatumEinde().getHours()+"u"+
                min2+"  "+
                mActiviteit.getDatumEinde().getDate() + " / " + mActiviteit.getDatumEinde().getMonth() + " / " + mActiviteit.getDatumEinde().getYear());
        String deelnemers;
        if(mActiviteit.getDeelnemers().length()==0){
            deelnemers = getResources().getString(R.string.geen_deelnemers);
        }else{
            deelnemers = mActiviteit.getDeelnemers();
        }
        basisinfo.put("Deelnemers",deelnemers);
        String beschrijving;
        if(mActiviteit.getBeschrijving()==""){
            beschrijving = getResources().getString(R.string.lege_beschrijving);
        }else{
            beschrijving = mActiviteit.getBeschrijving();
        }
        basisinfo.put("Extra info",beschrijving);


        List<HashMap<String,String>> listItems = new ArrayList<>();
        SimpleAdapter adapter  = new SimpleAdapter(lijst.getContext(), listItems, R.layout.list_item, new String[]{"lijn1", "lijn2"}, new int[]{R.id.text1, R.id.text2});



                Iterator it = basisinfo.entrySet().iterator();
                while(it.hasNext()){
                    HashMap<String, String> resultMap = new HashMap<>();
                    Map.Entry paar = (Map.Entry) it.next();
                    resultMap.put("lijn1",paar.getKey().toString());
                    resultMap.put("lijn2",paar.getValue().toString());
                    listItems.add(resultMap);
                }

                lijst.setAdapter(adapter);


        return view;
    }


    @Override
    public void onClick(View view) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .remove(this).commit();
    }
}
