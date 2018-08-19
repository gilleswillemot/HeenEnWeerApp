package com.example.gilles.g_hw_sl_pv_9200.Data;

import com.example.gilles.g_hw_sl_pv_9200.Models.Activiteit;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gebruiker;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gesprek;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gezin;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kind;
import com.example.gilles.g_hw_sl_pv_9200.Models.Kost;
import com.example.gilles.g_hw_sl_pv_9200.Models.Ouder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** hardcoded data voor het developen van de app zonder databank
 * Created by Lucas on 23-11-2017.
 */

public class Databank {
    private Gezin gezin;

    public Databank(){
        initData();

    }



    private void initData(){
        Ouder vader = new Ouder("Deman","Kurt","Geraard de Duivelstraat 5, 9000 Gent", "In het weekend om de 2 weken", "0");
        Ouder moeder = new Ouder("Vandewamme","Sandra", "Sint-Denijslaan 251, 9000 Gent", "Om de 2 weken het weekend niet","1");

        gezin = new Gezin(vader, moeder);


        Kind Jan = new Kind(vader.getNaam(), "Jan", "2");
        Date gbd = new Date(2010, 11, 19);
        Jan.setGeboortedatum(gbd);
        Jan.setBloedgroep("O-negatief");
        String[] aller = {"hooikoorst", "lactose"};
        String[] hobbys = {"voetbal", "muziekschool","boeken lezen"};
        Jan.setAllergieën(aller);
        Jan.setHobbys(hobbys);
        ArrayList<String> deeln = new ArrayList<>();
        deeln.add(Jan.getVoorNaam());
        Activiteit training = new Activiteit("Voetbaltraining",new Date(2017,11,24,19,00),
                new Date(2017,11,24,20,30),"Voetbaltraining",deeln);
        Activiteit voetbal = new Activiteit("Voetbaltraining",new Date(2017,11,28,19,00)
                ,new Date(2017,11,28,20,30),"Voetbaltraining",deeln);
        Activiteit blabla = new Activiteit("Voetbaltraining",new Date(2017,11,30,19,00),
                new Date(2017,11,30,20,30),"Voetbaltraining",deeln);
        Activiteit jaja = new Activiteit("Voetbalwedstrijd",new Date(2017,11,25,16,00),
                new Date(2017,11,25,18,00),"Wedstrijd tegen KAC Ardooie, zeer belangrijke wedstrijd, " +
                "als ze winnen worden ze kampioen anders worden ze tweede. Dus er zeker zijn indien mogelijk!!",deeln);

        Jan.voegActiviteitToe(training);
        Jan.voegActiviteitToe(voetbal);
        Jan.voegActiviteitToe(blabla);
        Jan.voegActiviteitToe(jaja);


        Kind Rachel = new Kind(vader.getNaam(),"Rachel", "3");
        Date gbd1 = new Date(2010, 11, 19);
        Rachel.setGeboortedatum(gbd);
        Rachel.setBloedgroep("O-negatief");
        String[] aller1 = {"hooikoorst"};
        String[] hobbys1 = {"dansen","boeken lezen"};
        Rachel.setAllergieën(aller1);
        Rachel.setHobbys(hobbys1);


        Activiteit dansen = new Activiteit("Dansles",new Date(2017,11,24,18,00),
                new Date(2017,11,24,20,00),"Dansles",deeln);
        Rachel.voegActiviteitToe(dansen);

        Rachel.setAllergieën(aller);
        Jan.setHobbys(hobbys);
        gezin.voegKindToe(Jan);
        gezin.voegKindToe(Rachel);

        vader.voegGezinToe(gezin);

        /**
         * Kosten aanmaken
         */
        ArrayList<Kost> mockKosten = new ArrayList<>();
//        mockKosten.add(new Kost("Kinderchampagne", "Kinderchampagne verjaardagfeest Tunsi", 150));
//        mockKosten.add(new Kost("voetbalkamp", "Voetbalkamp Tom", 50));
//        mockKosten.add(new Kost("nepkost1", "Dit is een nepkost voorbeeld", 50));
//        mockKosten.add(new Kost("Turnen", "Dit is een nepkost voorbeeld", 75));
//        mockKosten.add(new Kost("Zwemles Joost", "Dit is een nepkost voorbeeld", 90));

        this.gezin.kosten = mockKosten;

        //Einde aanmaken kosten

        //gesprekken toevoegen
        String vandaag = DateFormat.getDateTimeInstance().format(new Date());
        ArrayList<Gesprek> mockGesprekken = new ArrayList<>();
//        ChatMessage msg1 = new ChatMessage(1,true,"Vandaag was Jan een beetje ziek",Long.parseLong(gezin.getVader().getId()),vandaag);
//        ChatMessage msg2 = new ChatMessage(2,false,"Oej ik hoop dat hij snel beter wordt. Hou me zeker op de hoogte!",Long.parseLong(gezin.getMoeder().getId()),vandaag);
//        ChatMessage msg3 = new ChatMessage(3,true,"Hij is vandaag al een heel stuk beter",Long.parseLong(gezin.getVader().getId()),vandaag);
//        ArrayList<ChatMessage> msgs = new ArrayList<>();
//        msgs.add(msg1);
//        msgs.add(msg2);
//        msgs.add(msg3);
        Gebruiker[] gbr = {gezin.getVader(),gezin.getMoeder()};
//        Gesprek gesprek1 = new Gesprek("Jan is ziek",gbr,msgs,0,null,"1");
//        gezin.addGesprek(gesprek1);

//        ChatMessage msg4 = new ChatMessage(4,false,"Vandaag zijn we gaan bowlen voor Rachel haar verjaardag, super leuk",Long.parseLong(gezin.getMoeder().getId()),vandaag);
//        ChatMessage msg5 = new ChatMessage(5,true,"Wow, ze waren waarschijnlijk super blij",Long.parseLong(gezin.getVader().getId()),vandaag);
//        ChatMessage msg6 = new ChatMessage(6,false,"Ja hoor, zeker een aanrader voor een volgende keer!!",Long.parseLong(gezin.getMoeder().getId()),vandaag);
//        msgs.clear();
//        msgs.add(msg4);
//        msgs.add(msg5);
//        msgs.add(msg6);
//        Gesprek gesprek2 = new Gesprek("Bowlen met de kids",gbr,msgs,1,null,"2");
//        gezin.addGesprek(gesprek2);
    }

    public Gezin getGezin(){
        return gezin;
    }

    public List<Gebruiker> getGezinsLedenInfo(){
//        //Map<String, String> map = new HashMap<String, String>();
//        List<Object> list = new ArrayList<>();
//
//        this.getGezin().getGezinsLeden().forEach(g -> list.add(g));
//
//        for(Gebruiker gebruiker: this.gezin.getGezinsLeden()){
//            //map.put(gebruiker.getId(), gebruiker.getVoorNaam()+" "+gebruiker.getNaam());
//            //dit in backend :
//            this.gezin.getGezinsLeden().;
//        }
//        return map;

        return this.getGezin().getGezinsLeden();
    }
}
