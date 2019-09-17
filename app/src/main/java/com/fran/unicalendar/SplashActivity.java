package com.fran.unicalendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private User user;
    private Intent goToLogin;
    private Intent goToHome;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean shouldExecuteOnResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initFirebaseReference();
        initIntents();

        shouldExecuteOnResume = false;

        if (firebaseUser != null) {
            //L'utente è già loggato quindi viene inidirizzato alla HomeActivity

            DocumentReference docRef = firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseUser.getEmail()));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (document.exists()) {
                                Log.d("", "DocumentSnapshot data: " + document.getData());

                                user = new User();

                                user.setNome((String) document.get("Name"));
                                user.setCognome((String) document.get("Surname"));
                                user.setId((String) document.get("Id"));
                                user.setSuddivisione((String) document.get("Subdivision"));
                                user.setTipoSuddivisione((String) document.get("SubdivisionType"));
                                user.setEmail((String) document.get("Email"));
                                user.setSemestre((String) document.get("Semester"));
                                user.setDepartment((String) document.get("Department"));
                                user.setAnno((String) document.get("Year"));
                                user.setUniversity((String) document.get("University"));
                                user.setUniversityTipe((String) document.get("UniversityType"));
                                user.setPassword((String) document.get("Password"));
                                user.setCalendario((boolean) document.get("HasCalendario"));

                                gson = new Gson();
                                String json = gson.toJson(user);

                                sharedPreferences = getSharedPreferences("User_Preferences", Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();

                                editor.putString("user", json);

                                editor.apply();

                                //goToHome.putExtra("utente", user);
                                startActivity(goToHome);

                            } else {
                                Log.d("", "No such document");
                            }
                        } else {
                            Log.d("", "get failed with ", task.getException());
                        }
                    }
                }
            });

        } else {

            new Handler().postDelayed(new Runnable() {

                // Using handler with postDelayed called runnable run method

                @Override
                public void run() {

                    startActivity(goToLogin);

                }
            }, 2 * 1000);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (shouldExecuteOnResume) {

            initFirebaseReference();
            initIntents();

            if (firebaseUser != null) {

                //goToHome.putExtra("utente", user);
                startActivity(goToHome);

            } else {

                startActivity(goToLogin);

            }

        } else {

            shouldExecuteOnResume = true;

        }

    }

    private void initFirebaseReference() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    private void initIntents() {

        goToHome = new Intent(SplashActivity.this, HomeActivity.class);
        goToLogin = new Intent(SplashActivity.this, LoginActivity.class);

    }
}