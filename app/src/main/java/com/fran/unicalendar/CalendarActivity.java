package com.fran.unicalendar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class CalendarActivity extends AppCompatActivity {

    LayoutInflater layoutInflater;
    LinearLayout corsoLayout;
    LinearLayout mainLayout;
    LinearLayout empty;

    ImageButton deleteCalendar;
    TextView dipartimento;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;
    User user;

    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View view;
    ProgressDialog progressDialog;

    List<Giorno> giorni;
    List<String> colori;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getTimeTableFromSharedPreferences();
        getUserFromSharedPreferences();

        dipartimento = findViewById(R.id.dipartimento_CalendarActivity);
        dipartimento.setText(user.getDepartment());

        colori = new ArrayList<>();

        for (int c = 0; c < giorni.size(); c++) {
            System.out.println(giorni.size());

            if (giorni.get(c).getEventi() != null) {


                for (int i = 0; i < giorni.get(c).getEventi().size(); i++) {
                    if (!isPresent(colori, giorni.get(c).getEventi().get(i).getMateria())) {
                        colori.add(giorni.get(c).getEventi().get(i).getMateria());
                    }

                }
            }

        }

        for (int count = 0; count < giorni.size(); count++) {
            System.out.println(giorni.size());

            if (giorni.get(count).getEventi() != null) {

                switch (count) {
                    case 0:
                        mainLayout = findViewById(R.id.lunedi_CalendarActivity);
                        break;
                    case 1:
                        mainLayout = findViewById(R.id.martedi_CalendarActivity);
                        break;
                    case 2:
                        mainLayout = findViewById(R.id.mercoledi_CalendarActivity);
                        break;
                    case 3:
                        mainLayout = findViewById(R.id.giovedi_CalendarActivity);
                        break;
                    case 4:
                        mainLayout = findViewById(R.id.venerdi_CalendarActivity);
                        break;
                }

                int altezza = 850;
                for (int i = 0; i < giorni.get(count).getEventi().size(); i++) {
                    int orarioInizio = fromStringToInt(giorni.get(count).getEventi().get(i).getInizioLezione());
                    int orarioFine = fromStringToInt(giorni.get(count).getEventi().get(i).getFineLezione());

                    layoutInflater = getLayoutInflater();
                    corsoLayout = (LinearLayout) layoutInflater.inflate(R.layout.preview_lesson, mainLayout, false);
                    empty = new LinearLayout(this);

                    if (orarioInizio == altezza) {
                        int moltiplicatore = (orarioFine - orarioInizio) / 50;
                        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40 * moltiplicatore, getResources().getDisplayMetrics());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        params.height = (int) pixels;
                        corsoLayout.setLayoutParams(params);
                        mainLayout.addView(corsoLayout, mainLayout.getChildCount());
                        LinearLayout linearLayout = (LinearLayout) mainLayout.getChildAt(mainLayout.getChildCount() - 1);
                        CardView cardView = (CardView) linearLayout.getChildAt(0);
                        LinearLayout linearLayout1 = (LinearLayout) cardView.getChildAt(0);
                        TextView materia = (TextView) linearLayout1.getChildAt(0);
                        materia.setText(giorni.get(count).getEventi().get(i).getMateria());
                        TextView aula = (TextView) linearLayout1.getChildAt(1);
                        aula.setText(giorni.get(count).getEventi().get(i).getAula());
                        if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(0))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#039be5"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(1))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#f6bf26"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(2))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#0b8043"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(3))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#f4511e"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(4))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#8e24aa"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(5))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#e67c73"));
                        }
                        float a = (mainLayout.getChildAt(mainLayout.getChildCount() - 1).getLayoutParams().height) / (getResources().getDisplayMetrics().density);
                        altezza += (Math.round(a)) + 10 * moltiplicatore;
                        System.out.println("puntatore altezza = " + altezza);
                    } else {

                        int moltiplicatore = (orarioInizio - altezza) / 50;
                        System.out.println("moltiplicatore" + moltiplicatore);
                        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40 * moltiplicatore, getResources().getDisplayMetrics());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        params.height = (int) pixels;
                        empty.setLayoutParams(params);
                        mainLayout.addView(empty, mainLayout.getChildCount());
                        float a = (mainLayout.getChildAt(mainLayout.getChildCount() - 1).getLayoutParams().height) / (getResources().getDisplayMetrics().density);
                        altezza += (Math.round(a)) + 10 * moltiplicatore;

                        moltiplicatore = (orarioFine - orarioInizio) / 50;
                        pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40 * moltiplicatore, getResources().getDisplayMetrics());
                        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        params.height = (int) pixels;
                        corsoLayout.setLayoutParams(params);
                        mainLayout.addView(corsoLayout, mainLayout.getChildCount());
                        LinearLayout linearLayout = (LinearLayout) mainLayout.getChildAt(mainLayout.getChildCount() - 1);
                        CardView cardView = (CardView) linearLayout.getChildAt(0);
                        LinearLayout linearLayout1 = (LinearLayout) cardView.getChildAt(0);
                        TextView materia = (TextView) linearLayout1.getChildAt(0);
                        materia.setText(giorni.get(count).getEventi().get(i).getMateria());
                        TextView aula = (TextView) linearLayout1.getChildAt(1);
                        aula.setText(giorni.get(count).getEventi().get(i).getAula());
                        if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(0))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#039be5"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(1))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#f6bf26"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(2))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#0b8043"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(3))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#f4511e"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(4))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#8e24aa"));
                        } else if (giorni.get(count).getEventi().get(i).getMateria().equals(colori.get(5))) {
                            cardView.setCardBackgroundColor(Color.parseColor("#e67c73"));
                        }
                        a = (mainLayout.getChildAt(mainLayout.getChildCount() - 1).getLayoutParams().height) / (getResources().getDisplayMetrics().density);
                        altezza += (Math.round(a)) + 10 * moltiplicatore;
                        System.out.println("puntatore altezza = " + altezza);
                    }
                }
            }
        }

        deleteCalendar = findViewById(R.id.delete_CalendarActivity);

        deleteCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createDialogEliminaCalendario();

            }
        });

    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    public void createDialogEliminaCalendario() {

        builder = new AlertDialog.Builder(this);
        layoutInflater = getLayoutInflater();
        view = layoutInflater.inflate(R.layout.delete_calendar_dialog, null);

        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Elimina Calendario");
        title.setPadding(10, 20, 10, 0);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextSize(30);

        builder.setView(view)
                .setCustomTitle(title)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        uploadUserIntoDatabase();

                        dialogInterface.cancel();
                        dialogInterface.dismiss();

                    }

                });

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

    }

    public void uploadUserIntoDatabase() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Eliminazione Calendario in corso...");
        progressDialog.show();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseUser.getEmail()));

        docRef.update("HasCalendario", false).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                user.setCalendario(false);
                updateSharedPreferences();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void getUserFromSharedPreferences() {

        sharedPreferences = getSharedPreferences("User_Preferences", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        user = gson.fromJson(json, User.class);

    }

    public void updateSharedPreferences() {

        gson = new Gson();
        String json = gson.toJson(user);

        sharedPreferences = getSharedPreferences("User_Preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("user", json);

        editor.apply();

        removeSharedPreferencesTimeTable();

    }

    public void removeSharedPreferencesTimeTable() {

        sharedPreferences = getSharedPreferences("TimeTable", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove("timeTable");
        editor.apply();

        progressDialog.dismiss();

        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        finish();

    }
/*
        layoutInflater = getLayoutInflater();
        mainLayout = findViewById(R.id.lunedi_CalendarActivity);
        corsoLayout = (LinearLayout) layoutInflater.inflate(R.layout.preview_lesson, mainLayout, false);
        //corsoLayout.setMinimumHeight(240);
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = (int) pixels;
        corsoLayout.setLayoutParams(params);

        mainLayout.addView(corsoLayout, mainLayout.getChildCount());
        mainLayout.getChildAt(mainLayout.getChildCount() - 1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Cliccato child numero : " + (mainLayout.getChildCount() - 1), Toast.LENGTH_LONG).show();

            }
        });

        LinearLayout empty = new LinearLayout(this);
        empty.setLayoutParams(params);

        mainLayout.addView(empty, mainLayout.getChildCount());

        layoutInflater = getLayoutInflater();
        mainLayout = findViewById(R.id.lunedi_CalendarActivity);
        corsoLayout = (LinearLayout) layoutInflater.inflate(R.layout.preview_lesson, mainLayout, false);
        corsoLayout.setLayoutParams(params);

        mainLayout.addView(corsoLayout, mainLayout.getChildCount());
        //corsoLayout.getLayoutParams().height = 120;

*/

    public boolean isPresent(List<String> colors, String materia) {
        for (String color : colors) {
            if (color.equals(materia))
                return true;
        }

        return false;
    }

    public void getTimeTableFromSharedPreferences() {

        sharedPreferences = getSharedPreferences("TimeTable", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("timeTable", "");
        @SuppressWarnings("UnstableApiUsage")
        Type type = new TypeToken<List<Giorno>>() {
        }.getType();
        giorni = gson.fromJson(json, type);
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

}
