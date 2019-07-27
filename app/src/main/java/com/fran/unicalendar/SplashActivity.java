package com.fran.unicalendar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    FirebaseFirestore firebaseFirestore;
    User user;
    String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new GetFirebaseInstance().execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_splash);


    }

    /**
     * Async Task to understand if a firebaseUser is already logged or not
     */
    private class GetFirebaseInstance extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setContentView(R.layout.activity_splash);
            // before making http calls

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            setContentView(R.layout.activity_splash);
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setContentView(R.layout.activity_splash);
            // After completing http call
            // will close this activity and lauch main activity


            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();

            final Intent intent2;
            Intent intent;

            if (firebaseUser != null) {
                //L'utente è già loggato quindi viene inidirizzato alla HomeActivity
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();
                firebaseFirestore = FirebaseFirestore.getInstance();
                System.out.println(firebaseUser.getEmail());
                intent2 = new Intent(SplashActivity.this, HomeActivity.class);

                DocumentReference docRef = firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseUser.getEmail()));
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("", "DocumentSnapshot data: " + document.getData());
                                System.out.println(document.getData());

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

                                intent2.putExtra("utente", user);
                                startActivity(intent2);

                            } else {
                                Log.d("", "No such document");
                            }
                        } else {
                            Log.d("", "get failed with ", task.getException());
                        }
                    }
                });

            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }


            // close this activity
            finish();
        }

    }

    /*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        int secondsDelayed = 1;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (firebaseUser != null) {
                    //L'utente è già loggato quindi viene inidirizzato alla HomeActivity
                    finish();
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                } else {

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, secondsDelayed * 1000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (firebaseUser != null) {
            //L'utente è già loggato quindi viene inidirizzato alla HomeActivity
            finish();
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        } else {

            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }

    }

    */
}
