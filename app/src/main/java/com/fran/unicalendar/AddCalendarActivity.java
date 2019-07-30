package com.fran.unicalendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class AddCalendarActivity extends AppCompatActivity implements AddLessonDialog.ExampleDialogListener {

    EditText materia;
    EditText professore;
    ImageView addLezione;
    LayoutInflater layoutInflater;
    LinearLayout infoLession;
    ViewGroup mainLayout;
    TextView aula;
    TextView orarioDiInizio;
    TextView orarioDiFine;
    TextView tipo;
    TextView deleteLession;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);


        materia = findViewById(R.id.materia_AddCalendarActivity);
        professore = findViewById(R.id.professore_AddCalendarActivity);
        addLezione = findViewById(R.id.addLezione_AddCalendarActivity);
        handler = new Handler();

        addLezione.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("InflateParams")
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                layoutInflater = getLayoutInflater();
                                infoLession = (LinearLayout) layoutInflater.inflate(R.layout.info_lession_view, null);
                                mainLayout = findViewById(R.id.mainLayout);
                                infoLession.setTag(mainLayout.getChildCount() + 1);
                                //System.out.println("View aggiunta con TAG : "+ mainLayout.getChildCount());
                                mainLayout.addView(infoLession, mainLayout.getChildCount());
                                Log.d("lession add with TAG ", infoLession.getTag().toString());
                                Log.d("child count ", Integer.toString(mainLayout.getChildCount()));

                                LinearLayout alias = (LinearLayout) mainLayout.getChildAt(mainLayout.getChildCount() - 1);
                                CardView cardView = (CardView) alias.getChildAt(0);
                                LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
                                LinearLayout linearLayout1 = (LinearLayout) linearLayout.getChildAt(0);
                                deleteLession = (TextView) linearLayout.getChildAt(1);
                                aula = (TextView) linearLayout1.getChildAt(0);
                                orarioDiInizio = (TextView) linearLayout1.getChildAt(1);
                                orarioDiFine = (TextView) linearLayout1.getChildAt(2);
                                tipo = (TextView) linearLayout1.getChildAt(3);
                                tipo.addTextChangedListener(new CustomTextWatcher(tipo, infoLession, mainLayout));

                                openDialog();

                            }
                        });
                    }
                }).start();

            }
        });

    }

    public void openDialog() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        AddLessonDialog exampleDialog = new AddLessonDialog();
                        exampleDialog.setCancelable(false);
                        exampleDialog.show(getSupportFragmentManager(), "example dialog");

                    }
                });
            }
        }).start();

    }

    @Override
    public void applyTexts(String aula1, String inizioLezione1, String fineLezione1, String tipoLezione) {
        aula.setText(aula.getText().toString().concat(aula1));
        orarioDiInizio.setText(orarioDiInizio.getText().toString().concat(inizioLezione1));
        orarioDiFine.setText(orarioDiFine.getText().toString().concat(fineLezione1));
        tipo.setText(tipo.getText().toString().concat(tipoLezione));
    }

    public class CustomTextWatcher implements TextWatcher {

        LinearLayout infoLession;
        ViewGroup mainLayout;
        private TextView textView;

        private CustomTextWatcher(TextView textView, LinearLayout infoLession, ViewGroup mainLayout) {
            this.textView = textView;
            this.infoLession = infoLession;
            this.mainLayout = mainLayout;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (textView.getText().toString().equals("Tipo Lezione : ")) {
                                mainLayout.removeViewAt((Integer) infoLession.getTag() - 1);
                                Log.d("lession ann with TAG ", infoLession.getTag().toString());
                                Log.d("child count after ann ", Integer.toString(mainLayout.getChildCount()));
                            }
                        }
                    });
                }
            }).start();

        }

        @Override
        public void afterTextChanged(Editable s) {

            deleteLession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    if((Integer) infoLession.getTag()==(mainLayout.getChildCount())){
                        Log.d("Child count before = ", Integer.toString(mainLayout.getChildCount()));
                        Log.d("Clickable", "sto cliccando sull'info lession numero : " + infoLession.getTag().toString() );
                        System.out.println("View rimossa con TAG : " + infoLession.getTag().toString());
                        mainLayout.removeViewAt((Integer) infoLession.getTag()-1);

                        Log.d("Child count after = ", Integer.toString(mainLayout.getChildCount()));
                    contatore--;
                    } */
                    //int prova = mainLayout.getChildCount()-1;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    if ((Integer) infoLession.getTag() == mainLayout.getChildCount()) {
                                        mainLayout.removeViewAt((Integer) infoLession.getTag() - 1);
                                        Log.d("Rimosso tramite if", Integer.toString(mainLayout.getChildCount()));

                                    } else {

                                        int count;// = (Integer)infoLession.getTag();
                                        Log.d("Else, lession TAG", Integer.toString((Integer) infoLession.getTag()));
                                        Log.d("Else, childCount", Integer.toString(mainLayout.getChildCount()));

                                        if (mainLayout.getChildCount() == 3) {
                                            mainLayout.getChildAt(2).setTag(2);
                                            mainLayout.removeViewAt(2);
                                            Log.d("Else, childCo after rem", Integer.toString(mainLayout.getChildCount()));
                                        } else {
                                            mainLayout.removeViewAt((Integer) infoLession.getTag() - 1);
                                            int prova = mainLayout.getChildCount() - 1;
                                            Log.d("Rimozione lession", "inizio");
                                            for (count = 3; count < prova; count++) {
                                                mainLayout.getChildAt(count).setTag(count);
                                                Log.d("lession Tag", Integer.toString(count));
                                                Log.d("Tag del child", Integer.toString((Integer) mainLayout.getChildAt(count).getTag()));
                                            }
                                            Log.d("Rimozione lession", "fine");

                                        }

                                    }

                                }
                            });
                        }
                    }).start();
                }
            });

        }
    }
}
