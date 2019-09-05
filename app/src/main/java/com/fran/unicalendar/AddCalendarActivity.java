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
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCalendarActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    Gson gson;

    Corso corso;
    List<Corso> corsi;
    Lezione lezione;
    List<Lezione> lezioni;
    Calendario calendario;
    User user;

    int counterLezioni = 1;
    ImageView addCorso;

    LayoutInflater layoutInflater;
    LinearLayout infoLession;
    ViewGroup mainLayout;
    ImageView saveCalendar;
    TextView twcorso;
    EditText materia;
    EditText professore;
    ImageView addLezione;

    TextView aula;
    TextView orarioDiInizio;
    TextView orarioDiFine;
    TextView tipo;
    TextView giorno;
    private Handler handler;

    TextView deleteLession;

    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View view;
    ProgressDialog progressDialog;

    EditText Aula;
    Spinner OrarioInizio;
    Spinner OrarioFine;
    Spinner Giorno;
    RadioButton Tipologia;

    private static boolean materiaValidator(String materia) {

        Pattern pattern;
        Matcher matcher;
        final String Materia_Pattern =
                "[A-Za-z\\s]{2,70}$";
        pattern = Pattern.compile(Materia_Pattern);
        matcher = pattern.matcher(materia);

        return !matcher.matches();

    }

    protected static boolean aulaValidator(String aula) {

        Pattern pattern;
        Matcher matcher;
        final String Name_Pattern =
                "[A-Za-z0-9'\\s]{2,30}$";
        pattern = Pattern.compile(Name_Pattern);
        matcher = pattern.matcher(aula);

        return matcher.matches();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);

        setupViews();
        setupObject();

        getData();

        addCorso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkLezioni()) {

                    if (validator()) {

                        if (checkOverlappingLessons()) {

                            setupLezioni();

                            progressDialog.dismiss();
                            progressDialog.cancel();

                            startActivity(new Intent(getApplicationContext(), AddCalendarActivityBucle.class));

                            finish();

                        }

                    }

                } else {

                    Toast toast = Toast.makeText(AddCalendarActivity.this,
                            "Un corso deve contenere almeno una lezione!",
                            Toast.LENGTH_LONG);

                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.getView().setBackgroundColor(Color.parseColor("#B22222"));
                    toast.show();

                }


            }
        });

        addLezione.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("InflateParams")
            @Override
            public void onClick(View view) {

                if (validator()) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    layoutInflater = getLayoutInflater();
                                    infoLession = (LinearLayout) layoutInflater.inflate(R.layout.info_lession_view, null);
                                    mainLayout = findViewById(R.id.mainLayout);

                                    if (mainLayout.getChildCount() < 7) {

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
                                        giorno = (TextView) linearLayout1.getChildAt(4);
                                        tipo.addTextChangedListener(new CustomTextWatcher(tipo, infoLession, mainLayout));

                                        //openDialog();
                                        createDialogAddLesson();

                                    } else {

                                        Toast toast = Toast.makeText(AddCalendarActivity.this,
                                                "Un corso non puo' avere piu' di 5 lezioni!",
                                                Toast.LENGTH_LONG);

                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.getView().setBackgroundColor(Color.parseColor("#B22222"));
                                        toast.show();

                                    }


                                }
                            });
                        }
                    }).start();
                }
            }
        });

        saveCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkLezioni()) {

                    if (validator()) {

                        if (checkOverlappingLessons()) {

                            createDialogSalvaCalendario();

                        }

                    }
                } else {

                    Toast toast = Toast.makeText(AddCalendarActivity.this,
                            "Un corso deve contenere almeno una lezione!",
                            Toast.LENGTH_LONG);

                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.getView().setBackgroundColor(Color.parseColor("#B22222"));
                    toast.show();

                }

            }
        });

    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    public void createDialogAddLesson() {

        builder = new AlertDialog.Builder(this);
        layoutInflater = getLayoutInflater();
        view = layoutInflater.inflate(R.layout.add_lesson_dialog_layout, null);

        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Reset Password");
        title.setPadding(10, 20, 10, 0);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextSize(30);

        builder.setView(view)
                .setCustomTitle(title);

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {

                ImageButton conferma = view.findViewById(R.id.yes_AddLessonDialogLayout);
                ImageButton annulla = view.findViewById(R.id.no_AddLessonDialogLayout);
                Aula = view.findViewById(R.id.aula_AddLessonDialogLayout);
                OrarioInizio = view.findViewById(R.id.inizioLezione_AddLessonDialogLayout);
                OrarioFine = view.findViewById(R.id.fineLezione_AddLessonDialogLayout);
                Tipologia = view.findViewById(R.id.radioButton_AddLessonDialogLayout);
                Giorno = view.findViewById(R.id.spinnerGiornoLezione_AddLessonDialogLayout);

                annulla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.cancel();
                        alertDialog.dismiss();

                        tipo.append("");

                    }
                });

                conferma.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        if (Aula.getText().toString().trim().isEmpty()) {

                            Aula.setError("Il campo aula non puo' essere vuoto!");
                            Aula.requestFocus();

                        } else if (!aulaValidator(Aula.getText().toString().trim())) {

                            Aula.setError("Il nome dell'aula non e' ammesso!");
                            Aula.requestFocus();

                        } else if (OrarioInizio.getSelectedItem().toString().trim().equals(OrarioFine.getSelectedItem().toString().trim())) {

                            Toast.makeText(getApplicationContext(), "Ehi Einstein, chi e' il docente di questa lezione? Speedy Gonzales?", Toast.LENGTH_LONG).show();

                        } else if (fromStringToInt(OrarioInizio.getSelectedItem().toString())
                                > fromStringToInt(OrarioFine.getSelectedItem().toString())) {

                            Toast.makeText(getApplicationContext(), "Viaggi nel tempo? Una lezione non puo' iniziare dopo essere finita!", Toast.LENGTH_LONG).show();

                        } else {

                            alertDialog.dismiss();
                            alertDialog.cancel();

                            aula.append(Aula.getText().toString().trim());
                            orarioDiInizio.append(OrarioInizio.getSelectedItem().toString().trim());
                            orarioDiFine.append(OrarioFine.getSelectedItem().toString().trim());
                            if (Tipologia.isChecked())
                                tipo.append(Tipologia.getText().toString().trim());
                            else
                                tipo.append("Laboratorio");
                            giorno.append(Giorno.getSelectedItem().toString().trim());

                        }

                    }
                });


            }
        });

        alertDialog.show();

    }

    @SuppressWarnings("all")
    public int fromStringToInt(String orario) {

        String clear = null;

        int lunghezza = orario.length();
        System.out.println("Lunghezza = " + orario.length());
        System.out.println("Orario = " + orario);

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

    @SuppressLint({"InflateParams", "SetTextI18n"})
    public void createDialogSalvaCalendario() {

        builder = new AlertDialog.Builder(this);
        layoutInflater = getLayoutInflater();
        view = layoutInflater.inflate(R.layout.save_calendar_dialog, null);

        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Salvataggio Calendario");
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

                        setupLezioni();
                        getUser();

                        startActivity(new Intent(getApplicationContext(), ReviewCalendarActivity.class));

                        finish();

                        progressDialog.dismiss();
                        dialogInterface.cancel();
                        dialogInterface.dismiss();

                    }

                });

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

    }

    public void setupViews() {

        materia = findViewById(R.id.materia_AddCalendarActivity);
        professore = findViewById(R.id.professore_AddCalendarActivity);
        addLezione = findViewById(R.id.addLezione_AddCalendarActivity);
        addCorso = findViewById(R.id.addCorso_AddCalendarActivity);
        saveCalendar = findViewById(R.id.saveCalendar_AddCalendarActivity);
        twcorso = findViewById(R.id.Corso_AddCalendarActivity);


    }

    public void setupObject() {

        handler = new Handler();
        corsi = new ArrayList<>();
        lezioni = new ArrayList<>();


    }

    public boolean checkOverlappingLessons() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        int i = mainLayout.getChildCount();

        for (int c = 2; c < i; c++) {

            LinearLayout alias = (LinearLayout) mainLayout.getChildAt(c);
            CardView cardView = (CardView) alias.getChildAt(0);
            LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
            LinearLayout linearLayout1 = (LinearLayout) linearLayout.getChildAt(0);
            deleteLession = (TextView) linearLayout.getChildAt(1);
            aula = (TextView) linearLayout1.getChildAt(0);
            orarioDiInizio = (TextView) linearLayout1.getChildAt(1);
            orarioDiFine = (TextView) linearLayout1.getChildAt(2);
            tipo = (TextView) linearLayout1.getChildAt(3);
            giorno = (TextView) linearLayout1.getChildAt(4);

            for (int v = 3; v < i; v++) {

                LinearLayout aliasV = (LinearLayout) mainLayout.getChildAt(v);
                CardView cardViewV = (CardView) aliasV.getChildAt(0);
                LinearLayout linearLayoutV = (LinearLayout) cardViewV.getChildAt(0);
                LinearLayout linearLayout1V = (LinearLayout) linearLayoutV.getChildAt(0);

                TextView orarioDiInizioV = (TextView) linearLayout1V.getChildAt(1);
                TextView giornoV = (TextView) linearLayout1V.getChildAt(4);

                if (fromStringToInt(orarioDiFine.getText().toString().substring(26)) > fromStringToInt(orarioDiInizioV.getText().toString().substring(27)) &&
                        giorno.getText().toString().trim().substring(9).equals(giornoV.getText().toString().trim().substring(9))) {

                    Toast.makeText(getApplicationContext(), "Le lezioni numero " + (c - 1) + " e " + (v - 1) + " si sovrappongono.", Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                    progressDialog.cancel();

                    return false;

                } else if (fromStringToInt(orarioDiInizio.getText().toString().substring(27)) == fromStringToInt(orarioDiInizioV.getText().toString().substring(27)) &&
                        giorno.getText().toString().trim().substring(9).equals(giornoV.getText().toString().trim().substring(9))) {

                    Toast.makeText(getApplicationContext(), "Le lezioni numero " + (c - 1) + " e " + (v - 1) + " si sovrappongono.", Toast.LENGTH_LONG).show();

                    progressDialog.cancel();
                    progressDialog.dismiss();

                    return false;

                }

            }

        }

        return true;

    }

    public void setupLezioni() {

        int i = mainLayout.getChildCount();

        for (int c = 2; c < i; c++) {

            LinearLayout alias = (LinearLayout) mainLayout.getChildAt(c);
            CardView cardView = (CardView) alias.getChildAt(0);
            LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
            LinearLayout linearLayout1 = (LinearLayout) linearLayout.getChildAt(0);
            deleteLession = (TextView) linearLayout.getChildAt(1);
            aula = (TextView) linearLayout1.getChildAt(0);
            orarioDiInizio = (TextView) linearLayout1.getChildAt(1);
            orarioDiFine = (TextView) linearLayout1.getChildAt(2);
            tipo = (TextView) linearLayout1.getChildAt(3);
            giorno = (TextView) linearLayout1.getChildAt(4);

            lezione = new Lezione(aula.getText().toString().substring(15), orarioDiInizio.getText().toString().substring(27),
                    orarioDiFine.getText().toString().substring(26), tipo.getText().toString().substring(15), giorno.getText().toString().substring(9));

            System.out.println("Iterazione numero " + c + " del ciclo for per instanziare le lezioni.");
            System.out.println("L'oggetto lezione contiene i valori: " + lezione.getAula() + "\n" + lezione.getOraDiInizio() + "\n" +
                    lezione.getOraDiFine() + "\n" + lezione.getTipologia() + "\n" + lezione.getGiornoDellaLezione());

            lezioni.add(lezione);

        }

        setupCorso(lezioni);

    }

    public void setupCorso(List<Lezione> lezioni) {

        corso = new Corso(materia.getText().toString(), professore.getText().toString(), lezioni);
        corsi.add(corso);

        saveData();

    }

    public boolean checkLezioni() {

        mainLayout = findViewById(R.id.mainLayout);

        return mainLayout.getChildCount() != 2;

    }

    public boolean validator() {

        if (materia.getText().toString().isEmpty()) {
            materia.setError("Il campo relativo alla materia non puo' essere vuoto!");
            materia.requestFocus();
            return false;
        } else if (materiaValidator(materia.getText().toString())) {
            materia.setError("La materia inserita non e' ammessa!\nLa lunghezza dev'essere minimo di 2 caratteri e massimo 70.");
            materia.requestFocus();
            return false;
        } else if (professore.getText().toString().isEmpty()) {
            professore.setError("Il campo relativo al docente non puo' essere vuoto!");
            professore.requestFocus();
            return false;
        } else if (materiaValidator(professore.getText().toString())) {
            professore.setError("Il nome del docente inserito non e' ammesso!\nLa lunghezza dev'essere minimo di 2 caratteri e massimo 70.");
            professore.requestFocus();
            return false;
        }

        return true;
    }

    /*

    public void openDialog() {

        handler.post(new Runnable() {
            @Override
            public void run() {

                AddLessonDialog exampleDialog = new AddLessonDialog();
                exampleDialog.setCancelable(false);
                exampleDialog.show(getSupportFragmentManager(), "example dialog");

            }
        });

    }

    @Override
    public void applyTexts(String aula1, String inizioLezione1, String fineLezione1, String tipoLezione, String giornoLezione) {

        aula.setText(aula.getText().toString().concat(aula1));
        orarioDiInizio.setText(orarioDiInizio.getText().toString().concat(inizioLezione1));
        orarioDiFine.setText(orarioDiFine.getText().toString().concat(fineLezione1));
        tipo.setText(tipo.getText().toString().concat(tipoLezione));
        giorno.setText(giorno.getText().toString().concat(giornoLezione));

    }

    */

    //Save counterLession info and corsi Object
    public void saveData() {

        gson = new Gson();
        String json = gson.toJson(corsi);

        sharedPreferences = getSharedPreferences("Counter_Corso", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        int count = counterLezioni + 1;

        editor.putInt("counter", count);
        editor.putString("corsi", json);

        editor.apply();

    }

    @SuppressWarnings("unchecked")
    public void getData() {

        sharedPreferences = getSharedPreferences("Counter_Corso", Context.MODE_PRIVATE);
        //editor = sharedPreferences.edit();

        int count = sharedPreferences.getInt("counter", -1);

        if (count > 2) {
            counterLezioni = count;
            gson = new Gson();
            String json = sharedPreferences.getString("corsi", "");
            corsi = (List<Corso>) gson.fromJson(json, List.class);
            saveCalendar.setVisibility(View.VISIBLE);
        }
        twcorso.append(Integer.toString(counterLezioni));

    }

    public void getUser() {

        sharedPreferences = getSharedPreferences("User_Preferences", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        user = gson.fromJson(json, User.class);

        createCalendario(user);

    }

    public void createCalendario(User user) {

        calendario = new Calendario();
        calendario.setUniversityType(user.getUniversityTipe());
        calendario.setUniversity(user.getUniversity());
        calendario.setAnno(user.getAnno());
        calendario.setDepartment(user.getDepartment());
        calendario.setSemestre(user.getSemestre());
        calendario.setSuddivisione(user.getSuddivisione());
        calendario.setTipoSuddivisione(user.getTipoSuddivisione());
        calendario.setCorsi(corsi);

        saveCalendar();

    }

    public void saveCalendar() {

        gson = new Gson();
        String json = gson.toJson(calendario);

        sharedPreferences = getSharedPreferences("Calendar", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("calendar", json);

        editor.apply();

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

    @Override
    public void onBackPressed() {

        createDialogBackToHome();

    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    public void createDialogBackToHome() {

        builder = new AlertDialog.Builder(this);
        layoutInflater = getLayoutInflater();
        view = layoutInflater.inflate(R.layout.back_to_home_dialog, null);

        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Home Page");
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

                        Intent a = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(a);

                        finish();

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
