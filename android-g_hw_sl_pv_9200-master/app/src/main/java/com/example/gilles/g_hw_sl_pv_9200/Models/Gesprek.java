package com.example.gilles.g_hw_sl_pv_9200.Models;

import java.util.ArrayList;

/**
 * Created by Lucas on 8-12-2017.
 */

public class Gesprek {

    private String naam;
    private String[] deelnemers;
    private String[] berichten;
    private ArrayList<ChatMessage> berichtenArray;
    //private int idAuteurLaatsteBericht;
    private int aantalNieuweBerichten;
    private Activiteit activiteit;
    private String _id;


    public Gesprek(String naam, String[] deelnemers, String[] berichten, int aantalNieuweBerichten, Activiteit activiteit, String _id) {
        this.naam = naam;
        this.deelnemers = deelnemers;
        this.berichten = berichten;
        this.aantalNieuweBerichten = aantalNieuweBerichten;
        this.activiteit = activiteit;
        this._id = _id;
    }

//    public ArrayList<ChatMessage> getBerichten() {
////        ArrayList<ChatMessage> berichtenArray = new ArrayList<>();
////        DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
////        Call<List<ChatMessage>> call2 = dataInterface.getBerichtenVanGesprek("5a3430b1bceaec35805f0438");
////        List<ChatMessage> response = null;
////        try {
////            response = call2.execute().body();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        for (ChatMessage cm: response) {
////                berichtenArray.add(cm);
////       }
////
//////        call2.enqueue(new Callback<List<ChatMessage>>() {
//////            @Override
//////            public void onResponse(Call<List<ChatMessage>> call, Response<List<ChatMessage>> response) {
//////                Log.d("Berichten", "zoeken van berichten van gesprek");
//////                Log.d("berichten", response.body().toString());
//////                for (ChatMessage cm:response.body()) {
//////                    String auId = cm.getAuteurId();
//////                    Log.d("auteursid",auId);
////////                    Log.d("datum",cm.getDate());
//////                    if(auId.equals(userId)){
//////                        cm.setMe(true);
//////                    }
////////                    Log.d("isme", "" + cm.getIsme());
//////                    ChatMessage ct = new ChatMessage(cm.getId(),cm.getIsme(),cm.getMessage(),cm.getAuteurId(),cm.getDate());
//////                    berichtenArray.add(ct);
////////                    Log.d("berichtenid",cm.getId());
////////                    Log.d("bericht", cm.getMessage());
//////                }
//////            }
//////
//////            @Override
//////            public void onFailure(Call<List<ChatMessage>> call, Throwable t) {
//////                Log.d("Error:",t.getMessage()+"  ");
//////            }
//////        });
////        Log.d("berichten", berichtenArray.toString());
//////        Log.d("bericht1", berichtenArray.get(0).getMessage()+berichtenArray.get(0).getAuteurId());
//        String[] id = {userId};
//        List<ChatMessage> msgs = new BerichtenOphalen().doInBackground(userId);
//        for (ChatMessage cm:msgs) {
//            berichtenArray.add(cm);
//        }
//        return berichtenArray;
//    }

    public String getNaam() {
        return naam;
    }

    public String[] getDeelnemers() {
        return deelnemers;
    }

    public int getAantalNieuweBerichten() {
        return aantalNieuweBerichten;
    }

    public Activiteit getActiviteit() {
        return activiteit;
    }

    public String getId() {
        return _id;
    }

    public void voegBerichtToe(ChatMessage msg){
        this.berichtenArray.add(msg);
    }


//    private class BerichtenOphalen extends AsyncTask<String, Void, List<ChatMessage>> {
//        @Override
//        protected List<ChatMessage> doInBackground(String...id) {
//            String _id = id[0];
//            DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
//            List<ChatMessage> berichten = new ArrayList<>();
//            Call<List<ChatMessage>> call2 = dataInterface.getBerichtenVanGesprek(_id);
//            call2.enqueue(new Callback<List<ChatMessage>>() {
//                @Override
//                public void onResponse(Call<List<ChatMessage>> call, Response<List<ChatMessage>> response) {
//                    for (ChatMessage cm:response.body()) {
//                        berichten.add(cm);
//                        Log.d("berichten ophalen", "gelukt");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<ChatMessage>> call, Throwable t) {
//                    Log.d("Error:",t.getMessage()+"  ");
//                }
//            });
//            return berichten;
//        }
//        @Override
//        protected void onPostExecute(List<ChatMessage> result){
//            super.onPostExecute(result);
//        }
//
//    }
}
