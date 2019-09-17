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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private GridLayout gridLayout;
    private int i;
    private Intent intent;
    private User user;
    private TimeTable timeTable;

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private List<Giorno> giorni;
    private Calendario calendario;
    private TextView bentornato;

    private AlertDialog.Builder builder;
    private LayoutInflater layoutInflater;
    private View view;
    private AlertDialog alertDialog;

    private FirebaseFirestore firebaseFirestore;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUser();

        bentornato = findViewById(R.id.benvenutoUtente_HomeActivity);
        bentornato.append(user.getNome().concat(" ").concat(user.getCognome()));

        if (user.isCalendario()) {

            setTimeTableIntoSharedPreferences();

        } else {
            if (!isPresentTimeTable()) {

                searchIntoDB();

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


    private void RemoveSharedPreferencesForCounterCorso() {

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

                            createDialogLogout();

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
    private void createDialogCalendario() {

        builder = new AlertDialog.Builder(this);
        layoutInflater = getLayoutInflater();
        view = layoutInflater.inflate(R.layout.add_calendar_dialog, null);

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

    /*
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
*/
    private void searchIntoDB() {

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("TimeTables")
                .document(user.getUniversityTipe()).collection(user.getAnno()).document(user.getUniversity())
                .collection(user.getDepartment()).document(user.getSemestre())
                .collection(user.getTipoSuddivisione().concat(" - ").concat(user.getSuddivisione()))
                .document("Calendario")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        calendario = documentSnapshot.toObject(Calendario.class);
                        if (calendario != null) {
                            Log.d("onSucces", "Calendario scaricato");
                            createDialogCalendario();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("onFailure", "Calendario non scaricato");
            }
        });

    }

    @SuppressWarnings("Unchecked")
    private void setTimeTableIntoSharedPreferences() {

        firebaseFirestore = FirebaseFirestore.getInstance();
        timeTable = new TimeTable();

        firebaseFirestore.collection("TimeTables")
                .document(user.getUniversityTipe()).collection(user.getAnno()).document(user.getUniversity())
                .collection(user.getDepartment()).document(user.getSemestre())
                .collection(user.getTipoSuddivisione().concat(" - ").concat(user.getSuddivisione()))
                .document("TimeTable")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        timeTable = documentSnapshot.toObject(TimeTable.class);
                        Log.d("onSucces", "Giorni scaricati");

                        giorni = timeTable.getGiorni();

                        gson = new Gson();
                        @SuppressWarnings("UnstableApiUsage")
                        Type timetable = new TypeToken<List<Giorno>>() {
                        }.getType();
                        String json = gson.toJson(giorni, timetable);

                        sharedPreferences = getSharedPreferences("TimeTable", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("timeTable", json);

                        editor.apply();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void signOut() {

        AuthUI.getInstance()
                .signOut(HomeActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        progressDialog.dismiss();
                        removeSharedPreferencesTimeTable();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                    }
                });

    }

    private void removeSharedPreferencesTimeTable() {

        sharedPreferences = getSharedPreferences("TimeTable", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove("timeTable");
        editor.apply();

    }

    private void setupProcessDialog(String info) {

        progressDialog.setMessage(info);
        progressDialog.show();

    }

    private boolean isPresentTimeTable() {

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

    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void createDialogLogout() {

        builder = new AlertDialog.Builder(this);
        layoutInflater = getLayoutInflater();
        view = layoutInflater.inflate(R.layout.logout_dialog, null);

        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Logout");
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

                        intent = new Intent(HomeActivity.this, LoginActivity.class);
                        setupProcessDialog("Logout in corso...");
                        signOut();

                        dialogInterface.cancel();
                        dialogInterface.dismiss();

                    }

                });

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (alertDialog != null && alertDialog.isShowing()) {

            alertDialog.cancel();
            alertDialog.dismiss();

        } else if (progressDialog != null && progressDialog.isShowing()) {

            progressDialog.cancel();
            progressDialog.dismiss();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (alertDialog != null && alertDialog.isShowing()) {

            alertDialog.cancel();
            alertDialog.dismiss();

        } else if (progressDialog != null && progressDialog.isShowing()) {

            progressDialog.cancel();
            progressDialog.dismiss();

        }

    }

}
