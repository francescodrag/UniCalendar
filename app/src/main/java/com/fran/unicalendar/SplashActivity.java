package com.fran.unicalendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

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
}
