package com.fran.unicalendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecuperaPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_password);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();

    }
}
