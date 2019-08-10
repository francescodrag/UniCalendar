package com.fran.unicalendar;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GridLayout gridLayout;
    boolean flag;
    int i;
    Intent intent;
    User user;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    Gson gson;
    List<Giorno> giorni;
    Calendario calendario;
    TextView bentornato;

    AlertDialog.Builder builder;
    LayoutInflater layoutInflater;
    View view;
    AlertDialog alertDialog;

    FirebaseFirestore firebaseFirestore;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUser();

        bentornato = findViewById(R.id.benvenutoUtente_HomeActivity);
        bentornato.append(user.getNome().concat(" ").concat(user.getCognome()));

        if (!isPresentTimeTable()) {

            if (!searchIntoDB()) {

                createDialogCalendario();

            }

        }

        //Intent intent1 = getIntent();
        //user = intent1.getParcelableExtra("utente");

        RemoveSharedPreferencesForCounterCorso();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);

        gridLayout = findViewById(R.id.GridLayout_HomeActivity);

        setSingleEvent(gridLayout);

    }


    public void RemoveSharedPreferencesForCounterCorso() {

        sharedPreferences = getSharedPreferences("Counter_Corso", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove("counter");
        editor.remove("corsi");
        editor.apply();

        sharedPreferences = getSharedPreferences("Calendar", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove("calendar");
        editor.apply();

    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (i = 0; i < mainGrid.getChildCount(); i++) {

            CardView cardView = (CardView) mainGrid.getChildAt(i);

            switch (i) {
                case (0): {

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (!isPresentTimeTable()) {

                                intent = new Intent(HomeActivity.this, AddCalendarActivity.class);
                                //intent.putExtra("utente", user);
                                startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(), "Hai gia' aggiunto un calendario!", Toast.LENGTH_LONG).show();

                            }


                        }
                    });

                    break;
                }
                case (1): {

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (isPresentTimeTable()) {

                                intent = new Intent(HomeActivity.this, CalendarActivity.class);
                                //intent.putExtra("utente", user);
                                startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(), "Devi prima aggiungere un calendario!", Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                    break;
                }
                case (2): {

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            intent = new Intent(HomeActivity.this, UserProfileActivity.class);
                            //intent.putExtra("utente", user);
                            startActivity(intent);

                        }
                    });

                    break;
                }
                case (3): {

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            allert();

                        }
                    });

                    break;
                }

            }
        }


    }

    private void getUser() {

        sharedPreferences = getSharedPreferences("User_Preferences", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        user = gson.fromJson(json, User.class);

    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    public void createDialogCalendario() {

        builder = new AlertDialog.Builder(this);
        layoutInflater = getLayoutInflater();
        view = layoutInflater.inflate(R.layout.add_lesson_dialog_layout, null);

        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Calendario");
        title.setPadding(10, 20, 10, 0);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextSize(30);

        builder.setView(view)
                .setCustomTitle(title)
                .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        gson = new Gson();
                        String json = gson.toJson(calendario);

                        sharedPreferences = getSharedPreferences("Calendar", Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();

                        editor.putString("calendar", json);

                        editor.apply();

                        startActivity(new Intent(HomeActivity.this, ReviewCalendarActivity.class));

                        dialogInterface.cancel();
                        dialogInterface.dismiss();

                    }

                });

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

    }

    private void allert() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(HomeActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(HomeActivity.this);
        }
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setTitle("Attenzione!")
                .setMessage("Sei sicuro di voler effettuare il logout?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(HomeActivity.this, LoginActivity.class);
                        dialog.dismiss();
                        setupProcessDialog("Logout in corso...");
                        signOut();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public boolean searchIntoDB() {

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("TimeTables")
                .document(user.getUniversityTipe()).collection(user.getAnno()).document(user.getUniversity())
                .collection(user.getDepartment()).document(user.getSemestre())
                .collection(user.getTipoSuddivisione().concat(" - ").concat(user.getSuddivisione()))
                .limit(1)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                calendario = queryDocumentSnapshots.toObjects(Calendario.class).get(0);
                Log.d("onSucces", "Calendario scaricato");
                flag = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                flag = false;
            }
        });

        return flag;

    }

    public void signOut() {

        AuthUI.getInstance()
                .signOut(HomeActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        progressDialog.dismiss();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                    }
                });

    }

    public void setupProcessDialog(String info) {

        progressDialog.setMessage(info);
        progressDialog.show();

    }

    public boolean isPresentTimeTable() {

        sharedPreferences = getSharedPreferences("TimeTable", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("timeTable", "");
        @SuppressWarnings("UnstableApiUsage")
        Type type = new TypeToken<List<Giorno>>() {
        }.getType();
        giorni = gson.fromJson(json, type);

        return giorni != null;

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
