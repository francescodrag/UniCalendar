package com.fran.unicalendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ReviewCalendarActivity extends AppCompatActivity {


    //SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    //SharedPreferences userPreferences;
    Gson gson;

    //Corso corso;
    List<Corso> corsi;
    //Lezione lezione;
    List<Lezione> lezioni;
    Calendario calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_calendar);

        sharedPreferences = getSharedPreferences("Calendar", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("calendar", "");
        calendario = gson.fromJson(json, Calendario.class);

        lezioni = new ArrayList<>();

        if (calendario != null) {

            System.out.println("*********************************");
            System.out.println("Calendario ricevuto con successo!\n");
            System.out.println("Descomposizione in corso!\n");
            System.out.println("UniversityTipe : " + calendario.getUniversityType());
            System.out.println("Anno : " + calendario.getAnno());
            System.out.println("University : " + calendario.getUniversity());
            System.out.println("Department : " + calendario.getDepartment());
            System.out.println("Semestre : " + calendario.getSemestre());
            System.out.println("Suddivision Type : " + calendario.getTipoSuddivisione());
            System.out.println("Suddivision : " + calendario.getSuddivisione());
            System.out.println("*********************************\n");

            corsi = calendario.getCorsi();

            if (corsi != null) {

                System.out.println("Corsi scaricati");

                System.out.println("*********************************");
                System.out.println("Descomposizione in corso!\n" + corsi.size());


                for (int i = 0; i < corsi.size(); i++) {

                    System.out.println("Materia : " + corsi.get(i).getMateria());
                    System.out.println("Docente : " + corsi.get(i).getDocente());

                    lezioni = corsi.get(i).getLezioni();
                    if (lezioni != null) {

                        System.out.println("Lezioni scaricate");

                        System.out.println("*********************************");
                        System.out.println("Descomposizione in corso!\n");

                        for (int n = 0; n < lezioni.size(); n++) {

                            System.out.println("Aula : " + lezioni.get(n).getAula());
                            System.out.println("Orario di Inizio Lezione : " + lezioni.get(n).getOraDiInizio());
                            System.out.println("Orario di Fine Lezione : " + lezioni.get(n).getOraDiFine());
                            System.out.println("Tipo di Lezione : " + lezioni.get(n).getTipologia());
                            System.out.println("Giorno della Lezione : " + lezioni.get(n).getGiornoDellaLezione());

                        }

                        System.out.println("*********************************\n");

                    }

                    System.out.println("*********************************\n");

                }
            }

        }


    }
}
