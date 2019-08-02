package com.fran.unicalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

    LayoutInflater layoutInflater;
    LinearLayout corsoLayout;
    LinearLayout lezioneLayout;
    ViewGroup mainLayout;

    TextView Corso, Materia, Docente;
    TextView Aula, OrarioInizio, OrarioFine, Tipologia, Giorno;

    //int position = 0;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_calendar);

        sharedPreferences = getSharedPreferences("Calendar", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("calendar", "");
        calendario = gson.fromJson(json, Calendario.class);

        lezioni = new ArrayList<>();

        //layoutInflater = getLayoutInflater();
        //corsoLayout = (LinearLayout) layoutInflater.inflate(R.layout.corso_layout, null);
        //lezioneLayout = (CardView) layoutInflater.inflate(R.layout.lezione_layout, null);
        //mainLayout = findViewById(R.id.mainLayout_ReviewCalendarActivity);
        //mainLayout.removeAllViews();


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

                    layoutInflater = getLayoutInflater();
                    corsoLayout = (LinearLayout) layoutInflater.inflate(R.layout.corso_layout, null);
                    mainLayout = findViewById(R.id.mainLayout_ReviewCalendarActivity);
                    mainLayout.addView(corsoLayout, mainLayout.getChildCount());


                    LinearLayout main = (LinearLayout) mainLayout.getChildAt(mainLayout.getChildCount() - 1);
                    CardView cardView = (CardView) main.getChildAt(0);
                    LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
                    Corso = (TextView) linearLayout.getChildAt(0);
                    Corso.append(Integer.toString(i + 1));
                    Materia = (TextView) linearLayout.getChildAt(1);
                    Materia.append(corsi.get(i).getMateria());
                    Docente = (TextView) linearLayout.getChildAt(2);
                    Docente.append(corsi.get(i).getDocente());


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

                            layoutInflater = getLayoutInflater();
                            lezioneLayout = (LinearLayout) layoutInflater.inflate(R.layout.lezione_layout, null);
                            mainLayout = findViewById(R.id.mainLayout_ReviewCalendarActivity);
                            mainLayout.addView(lezioneLayout, mainLayout.getChildCount());

                            LinearLayout mainLezione = (LinearLayout) mainLayout.getChildAt(mainLayout.getChildCount() - 1);
                            CardView cardViewLezione = (CardView) mainLezione.getChildAt(0);
                            LinearLayout linearLayoutLezione = (LinearLayout) cardViewLezione.getChildAt(0);
                            Aula = (TextView) linearLayoutLezione.getChildAt(0);
                            Aula.append(lezioni.get(n).getAula());
                            OrarioInizio = (TextView) linearLayoutLezione.getChildAt(1);
                            OrarioInizio.append(lezioni.get(n).getOraDiInizio());
                            OrarioFine = (TextView) linearLayoutLezione.getChildAt(2);
                            OrarioFine.append(lezioni.get(n).getOraDiFine());
                            Tipologia = (TextView) linearLayoutLezione.getChildAt(3);
                            Tipologia.append(lezioni.get(n).getTipologia());
                            Giorno = (TextView) linearLayoutLezione.getChildAt(4);
                            Giorno.append(lezioni.get(n).getGiornoDellaLezione());

                        }

                        System.out.println("*********************************\n");

                    }

                    System.out.println("*********************************\n");

                }
            }

        }


    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(a);
    }

}
