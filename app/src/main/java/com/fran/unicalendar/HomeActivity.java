package com.fran.unicalendar;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;


public class HomeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GridLayout gridLayout;
    int i;
    Intent intent;
    User user;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    Gson gson;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUser();

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

                            intent = new Intent(HomeActivity.this, AddCalendarActivity.class);
                            //intent.putExtra("utente", user);
                            startActivity(intent);

                        }
                    });

                    break;
                }
                case (1): {

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

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
