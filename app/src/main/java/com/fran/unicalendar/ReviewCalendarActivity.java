package com.fran.unicalendar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewCalendarActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Gson gson;

    List<Corso> corsi;
    List<Lezione> lezioni;
    Calendario calendario;
    List<Giorno> giorni;
    List<Evento> eventiLunedi;
    List<Evento> eventiMartedi;
    List<Evento> eventiMercoledi;
    List<Evento> eventiGiovedi;
    List<Evento> eventiVenerdi;
    Giorno lunedi;
    Giorno martedi;
    Giorno mercoledi;
    Giorno giovedi;
    Giorno venerdi;
    Evento evento;

    LayoutInflater layoutInflater;
    LinearLayout corsoLayout;
    LinearLayout lezioneLayout;
    LinearLayout options;
    ViewGroup mainLayout;

    TextView Corso, Materia, Docente;
    TextView Aula, OrarioInizio, OrarioFine, Tipologia, Giorno;

    ImageView Delete;
    ImageView Save;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_calendar);

        getCalendarFromsharedPreferences();

        initObjects();

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


                            evento = new Evento();
                            evento.setAula(lezioni.get(n).getAula());
                            evento.setInizioLezione(lezioni.get(n).getOraDiInizio());
                            evento.setFineLezione(lezioni.get(n).getOraDiFine());
                            evento.setTipologiaLezione(lezioni.get(n).getTipologia());
                            evento.setDocente(corsi.get(i).getDocente());
                            evento.setMateria(corsi.get(i).getMateria());


                            switch (lezioni.get(n).getGiornoDellaLezione()) {
                                case "Lunedi'":

                                    eventiLunedi.add(evento);
                                    lunedi.setGiorno(0);
                                    lunedi.setEventi(eventiLunedi);

                                    break;
                                case "Martedi'":

                                    eventiMartedi.add(evento);
                                    martedi.setGiorno(1);
                                    martedi.setEventi(eventiMartedi);

                                    break;
                                case "Mercoledi'":

                                    eventiMercoledi.add(evento);
                                    mercoledi.setGiorno(2);
                                    mercoledi.setEventi(eventiMercoledi);

                                    break;
                                case "Giovedi'":

                                    eventiGiovedi.add(evento);
                                    giovedi.setGiorno(3);
                                    giovedi.setEventi(eventiGiovedi);

                                    break;
                                case "Venerdi'":

                                    eventiVenerdi.add(evento);
                                    venerdi.setGiorno(4);
                                    venerdi.setEventi(eventiVenerdi);

                                    break;
                            }

                        }

                        System.out.println("*********************************\n");

                    }

                    System.out.println("*********************************\n");

                }

                layoutInflater = getLayoutInflater();
                options = (LinearLayout) layoutInflater.inflate(R.layout.review_calendar_options, null);
                mainLayout = findViewById(R.id.mainLayout_ReviewCalendarActivity);
                mainLayout.addView(options, mainLayout.getChildCount());

                giorni.add(lunedi);
                giorni.add(martedi);
                giorni.add(mercoledi);
                giorni.add(giovedi);
                giorni.add(venerdi);
            }

        }

        for (int count = 0; count < giorni.size(); count++) {

            if (giorni.get(count).getEventi() != null) {

                System.out.println("Giorno numero : " + count + "\nNumero eventi : " + giorni.get(count).getEventi().size());

                for (int eventi = 0; eventi < giorni.get(count).getEventi().size(); eventi++) {

                    System.out.println("Aula : " + giorni.get(count).getEventi().get(eventi).getAula());
                    System.out.println("Orario di Inizio Lezione : " + giorni.get(count).getEventi().get(eventi).getInizioLezione());
                    System.out.println("Orario di Fine Lezione : " + giorni.get(count).getEventi().get(eventi).getFineLezione());
                    System.out.println("Tipo di Lezione : " + giorni.get(count).getEventi().get(eventi).getTipologiaLezione());
                    System.out.println("Docente : " + giorni.get(count).getEventi().get(eventi).getDocente());
                    System.out.println("Materia : " + giorni.get(count).getEventi().get(eventi).getMateria());

                }

            }

        }

        sortEventi();

        initViews();

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO

                startActivity(new Intent(ReviewCalendarActivity.this, HomeActivity.class));

                finish();

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO

                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Caricamento Calendario in corso...");
                progressDialog.show();

                uploadDatabase();

            }
        });

    }

    public void getCalendarFromsharedPreferences() {

        sharedPreferences = getSharedPreferences("Calendar", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("calendar", "");
        calendario = gson.fromJson(json, Calendario.class);

    }

    public void initObjects() {

        lezioni = new ArrayList<>();
        eventiLunedi = new ArrayList<>();
        eventiMartedi = new ArrayList<>();
        eventiMercoledi = new ArrayList<>();
        eventiGiovedi = new ArrayList<>();
        eventiVenerdi = new ArrayList<>();
        lunedi = new Giorno();
        martedi = new Giorno();
        mercoledi = new Giorno();
        giovedi = new Giorno();
        venerdi = new Giorno();
        giorni = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void initViews() {

        Delete = findViewById(R.id.delete_ReviewCalendarOptions);
        Save = findViewById(R.id.save_ReviewCalendarOptions);

    }

    public void sortEventi() {

        for (int count = 0; count < giorni.size(); count++) {

            if (giorni.get(count).getEventi() != null) {

                Evento evento;
                for (int i = 1; i < giorni.get(count).getEventi().size(); i++) {
                    for (int j = i; j > 0; j--) {
                        if (fromStringToInt(giorni.get(count).getEventi().get(j).getInizioLezione()) <
                                fromStringToInt(giorni.get(count).getEventi().get(j - 1).getInizioLezione())) {
                            evento = giorni.get(count).getEventi().get(j);
                            giorni.get(count).getEventi().set(j, giorni.get(count).getEventi().get(j - 1));
                            giorni.get(count).getEventi().set(j - 1, evento);
                        }
                    }
                }
                for (int i = 0; i < giorni.get(count).getEventi().size(); i++) {

                    System.out.println("Aula : " + giorni.get(count).getEventi().get(i).getAula());
                    System.out.println("Orario di Inizio Lezione : " + giorni.get(count).getEventi().get(i).getInizioLezione());
                    System.out.println("Orario di Fine Lezione : " + giorni.get(count).getEventi().get(i).getFineLezione());
                    System.out.println("Tipo di Lezione : " + giorni.get(count).getEventi().get(i).getTipologiaLezione());
                    System.out.println("Docente : " + giorni.get(count).getEventi().get(i).getDocente());
                    System.out.println("Materia : " + giorni.get(count).getEventi().get(i).getMateria());

                }

            }

        }

    }

    public int fromStringToInt(String orario) {

        String clear = null;

        int lunghezza = orario.length();

        if (lunghezza == 4) {
            if (Character.getNumericValue(orario.charAt(2)) == 3) {
                clear = orario.substring(0, 1).concat("5").concat(orario.substring(3, 4));
            } else {
                clear = orario.substring(0, 1).concat(orario.substring(2, 4));
            }
        } else if (lunghezza == 5) {
            if (Character.getNumericValue(orario.charAt(3)) == 3) {
                clear = orario.substring(0, 2).concat("5").concat(orario.substring(4, 5));
            } else {
                clear = orario.substring(0, 2).concat(orario.substring(3, 5));
            }
        }

        return Integer.parseInt(clear);

    }

    public void uploadDatabase() {

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("TimeTables")
                .document(calendario.getUniversityType()).collection(calendario.getAnno()).document(calendario.getUniversity())
                .collection(calendario.getDepartment()).document(calendario.getSemestre())
                .collection(calendario.getTipoSuddivisione().concat(" - ").concat(calendario.getSuddivisione())).document("Calendario")
                .set(calendario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d("OnSucces", "Calendario caricato!");
                        uploadTimeTableIntoDatabase();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.w("TAG", "Error writing document", e);

            }
        });


    }

    public void uploadTimeTableIntoDatabase() {

        firebaseFirestore = FirebaseFirestore.getInstance();
        TimeTable timeTable = new TimeTable(giorni);

        firebaseFirestore.collection("TimeTables")
                .document(calendario.getUniversityType()).collection(calendario.getAnno()).document(calendario.getUniversity())
                .collection(calendario.getDepartment()).document(calendario.getSemestre())
                .collection(calendario.getTipoSuddivisione().concat(" - ").concat(calendario.getSuddivisione())).document("TimeTable")
                .set(timeTable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d("OnSucces", "TimeTable caricato!");
                        uploadUserIntoDatabase();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.w("TAG", "Error writing document", e);

            }
        });

    }

    public void uploadUserIntoDatabase() {

        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseUser.getEmail()));

        docRef.update("HasCalendario", true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                setSharedPreferences();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    public void setSharedPreferences() {

        gson = new Gson();
        @SuppressWarnings("UnstableApiUsage")
        Type timetable = new TypeToken<List<Giorno>>() {
        }.getType();
        String json = gson.toJson(giorni, timetable);

        sharedPreferences = getSharedPreferences("TimeTable", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("timeTable", json);

        editor.apply();

        progressDialog.dismiss();

        startActivity(new Intent(ReviewCalendarActivity.this, CalendarActivity.class));

        finish();

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(a);
        finish();
    }

}
