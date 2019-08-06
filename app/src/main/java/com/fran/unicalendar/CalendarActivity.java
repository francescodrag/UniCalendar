package com.fran.unicalendar;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {


    LayoutInflater layoutInflater;
    LinearLayout corsoLayout;
    LinearLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


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


    }
}
