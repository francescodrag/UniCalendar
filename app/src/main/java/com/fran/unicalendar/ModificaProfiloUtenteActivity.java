package com.fran.unicalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Map;
import java.util.Objects;

public class ModificaProfiloUtenteActivity extends AppCompatActivity {

    ImageView undo, save;
    EditText email;
    Spinner tipoUniversita;
    Spinner annoUniversita;
    Spinner universita;
    Spinner dipartimento;
    Spinner semestre;
    Spinner tipoSuddivisione;
    Spinner suddivisione;

    ArrayAdapter<CharSequence> adapterTipo;
    String tipo;
    //String changedTipo;
    ArrayAdapter<CharSequence> adapterAnno;
    String anno;
    //String changedAnno;
    ArrayAdapter<CharSequence> adapterUniversita;
    String Universita;
    //String changedUniversita;
    ArrayAdapter<CharSequence> adapterDipartimento;
    String Dipartimento;
    ArrayAdapter<CharSequence> adapterSemestre;
    String Semestre;
    ArrayAdapter<CharSequence> adapterTipoSuddivisione;
    String TipoSuddivisione;
    ArrayAdapter<CharSequence> adapterSuddivisione;
    String Suddivisione;
    String originalTipo;
    //String changedDipartimento;
    //RadioButton checkSinistra, checkDestra;
    //String semestre;
    //String changedSemestre;

    Intent intent;
    TextView nomeCognome;
    User user;
    Map<String, Object> hashMapUser;
    ImageView imageUser;
    Handler handler;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_profilo_utente);

        handler = new Handler();

        getUserFromSharedPreferences();

        initFirebase();

        initView();


        tipoUniversita.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tipoUniversita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        initTipo(adapterView);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                return false;

            }

        });

        annoUniversita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                anno = adapterView.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        universita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                initUniversities(adapterView);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dipartimento.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                dipartimento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Dipartimento = adapterView.getSelectedItem().toString().trim();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                return false;

            }
        });

        tipoSuddivisione.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                initTipoSuddivisione(adapterView);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        suddivisione.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Suddivisione = adapterView.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Dipartimento.equals("-")) {
                    Toast.makeText(getApplicationContext(), "L'" + Universita + " non offre corsi di " + tipo, Toast.LENGTH_LONG).show();
                } else
                    updateUser();

            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                finish();

            }
        });

    }

    public void updateUser() {

        user.setUniversityTipe(tipo);
        user.setAnno(anno);
        user.setUniversity(Universita);
        user.setDepartment(Dipartimento);
        user.setSemestre(Semestre);
        user.setTipoSuddivisione(TipoSuddivisione);
        user.setSuddivisione(Suddivisione);
        user.setCalendario(false);

        updateDataBase();

    }

    public void updateDataBase() {

        hashMapUser = user.getHashMapUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Users").document(user.getEmail())
                .set(hashMapUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                        updateSharedPreferences();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    public void updateSharedPreferences() {

        gson = new Gson();
        String json = gson.toJson(user);

        sharedPreferences = getSharedPreferences("User_Preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("user", json);

        editor.apply();

        removeSharedPreferencesTimeTable();

    }

    public void removeSharedPreferencesTimeTable() {

        sharedPreferences = getSharedPreferences("TimeTable", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove("timeTable");
        editor.apply();

        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
        finish();

    }

    public void getUserFromSharedPreferences() {

        sharedPreferences = getSharedPreferences("User_Preferences", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        user = gson.fromJson(json, User.class);

    }

    public void initFirebase() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    public void initView() {

        setupView();
        settingView();
        setupImageUser();
        disableView();

    }

    public void setupView() {

        nomeCognome = findViewById(R.id.nomeCognomeUtente_ModificaProfiloUtenteActivity);
        save = findViewById(R.id.save_ModificaProfiloUtenteActivity);
        undo = findViewById(R.id.undo_ModificaProfiloUtenteActivity);
        email = findViewById(R.id.email_ModificaProfiloUtenteActivity);
        tipoUniversita = findViewById(R.id.tipologies_ModificaProfiloUtenteActivity);
        annoUniversita = findViewById(R.id.years_ModificaProfiloUtenteActivity);
        universita = findViewById(R.id.univerisites_ModificaProfiloUtenteActivity);
        dipartimento = findViewById(R.id.department_ModificaProfiloUtenteActivity);
        semestre = findViewById(R.id.semester_ModificaProfiloUtenteActivity);
        tipoSuddivisione = findViewById(R.id.tipoSuddivisione_ModificaProfiloUtenteActivity);
        suddivisione = findViewById(R.id.suddivisione_ModificaProfiloUtenteActivity);
        imageUser = findViewById(R.id.image_ModificaProfiloUtenteActivity);

    }

    public void settingView() {

        nomeCognome.setText(user.getNome().concat(" ").concat(user.getCognome()));
        email.setText(user.getEmail());
        settingSpinnerTipo();
        settingSpinnerAnno();
        settingSpinnerUniversita();
        settingSpinnerDipartimento();
        settingSpinnerSemestre();
        settingSpinnerTipoSuddivisione();
        settingSpinnerSuddivisione();

    }

    public void setupImageUser() {

        handler.post(new Runnable() {
            @Override
            public void run() {

                switch (user.getUniversity()) {
                    case "Universita' degli Studi di Salerno":
                        imageUser.setBackgroundResource(R.drawable.unisa);

                        break;
                    case "Universita' degli Studi di Napoli - Federico II":

                        imageUser.setBackgroundResource(R.drawable.unina_federico);

                        break;
                    case "Universita' degli Studi di Napoli - L'Orientale":

                        imageUser.setBackgroundResource(R.drawable.unina_orientale);

                        break;
                    case "Universita' degli Studi di Napoli - Parthenope":

                        imageUser.setBackgroundResource(R.drawable.unina_parthenope);

                        break;
                    case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":

                        imageUser.setBackgroundResource(R.drawable.unina_orsola);

                        break;
                    case "Universita' degli Studi del Sannio":

                        imageUser.setBackgroundResource(R.drawable.unibe);

                        break;
                    case "Universita' degli Studi della Campania - Luigi Vanvitelli":

                        imageUser.setBackgroundResource(R.drawable.unica);

                        break;
                }

            }
        });
    }

    public void disableView() {

        email.setEnabled(false);

    }

    public void selectSpinnerItemByValue(Spinner spinner, ArrayAdapter<CharSequence> adapter, String value) {
        spinner.setAdapter(adapter);
        for (int position = 0; position < adapter.getCount(); position++) {
            if (Objects.equals(adapter.getItem(position), value)) {
                spinner.setSelection(position);
                System.out.println("La variabile contiene: " + value);
                return;
            }
        }
    }

    public void settingSpinnerTipo() {

        tipo = user.getUniversityTipe();
        originalTipo = tipo;

        adapterTipo = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.tipologies,
                android.R.layout.simple_spinner_dropdown_item);

        selectSpinnerItemByValue(tipoUniversita, adapterTipo, tipo);

    }

    public void settingSpinnerAnno() {

        anno = user.getAnno();
        System.out.println(anno);

        switch (tipo) {
            case "Laurea Triennale":
                adapterAnno = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Triennale,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Laurea Magistrale":
                adapterAnno = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Magistrale,
                        android.R.layout.simple_spinner_dropdown_item);
                System.out.println("ECCOMI");
                break;
            case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                adapterAnno = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Magistrale_ciclo_unico_di_5_anni,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                adapterAnno = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Magistrale_ciclo_unico_di_6_anni,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
        }

        selectSpinnerItemByValue(annoUniversita, adapterAnno, anno);

    }

    public void settingSpinnerUniversita() {

        Universita = user.getUniversity();

        adapterUniversita = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Universities,
                android.R.layout.simple_spinner_dropdown_item);

        selectSpinnerItemByValue(universita, adapterUniversita, Universita);

    }

    public void settingSpinnerDipartimento() {

        Dipartimento = user.getDepartment();

        switch (tipo) {
            case "Laurea Triennale":
                switch (Universita) {
                    case ("Universita' degli Studi di Salerno"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Federico II"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - L'Orientale"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Parthenope"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Suor Orsola Benincasa"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi del Sannio"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi della Campania - Luigi Vanvitelli"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                }
                break;
            case "Laurea Magistrale":
                switch (Universita) {
                    case ("Universita' degli Studi di Salerno"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Federico II"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - L'Orientale"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Parthenope"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Suor Orsola Benincasa"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi del Sannio"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi della Campania - Luigi Vanvitelli"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                }
                break;
            case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                switch (Universita) {
                    case ("Universita' degli Studi di Salerno"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Federico II"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - L'Orientale"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Parthenope"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Suor Orsola Benincasa"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi del Sannio"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi della Campania - Luigi Vanvitelli"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                }
                break;
            case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                switch (Universita) {
                    case ("Universita' degli Studi di Salerno"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Federico II"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - L'Orientale"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Parthenope"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi di Napoli - Suor Orsola Benincasa"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi del Sannio"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                    case ("Universita' degli Studi della Campania - Luigi Vanvitelli"): {
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_dropdown_item);

                        break;
                    }
                }
                break;
        }

        selectSpinnerItemByValue(dipartimento, adapterDipartimento, Dipartimento);

    }

    public void settingSpinnerSemestre() {

        Semestre = user.getSemestre();

        adapterSemestre = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Semestri,
                android.R.layout.simple_spinner_dropdown_item);

        selectSpinnerItemByValue(semestre, adapterSemestre, Semestre);

    }

    public void settingSpinnerTipoSuddivisione() {

        TipoSuddivisione = user.getTipoSuddivisione();

        adapterTipoSuddivisione = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Tipo_Suddivisione,
                android.R.layout.simple_spinner_dropdown_item);

        selectSpinnerItemByValue(tipoSuddivisione, adapterTipoSuddivisione, TipoSuddivisione);

    }

    public void settingSpinnerSuddivisione() {

        Suddivisione = user.getSuddivisione();
        System.out.println(Suddivisione);

        switch (TipoSuddivisione) {
            case "Matricola Pari o Dispari":
                adapterSuddivisione = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Suddivisione_Pari_o_Dispari,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Resto della Matricola":
                adapterSuddivisione = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Suddivisione_Resto_della_Matricola,
                        android.R.layout.simple_spinner_dropdown_item);
                System.out.println("ECCOMI");
                break;
            case "Cognome":
                adapterSuddivisione = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Suddivisione_Cognome,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
        }

        selectSpinnerItemByValue(suddivisione, adapterSuddivisione, Suddivisione);

    }

    public void initTipo(AdapterView<?> adapterView) {

        switch (adapterView.getSelectedItem().toString()) {
            case "Laurea Triennale":
                switch (Universita) {
                    case "Universita' degli Studi di Salerno":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Federico II":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - L'Orientale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Parthenope":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi del Sannio":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                adapterAnno = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Triennale,
                        android.R.layout.simple_spinner_item);
                break;
            case "Laurea Magistrale":
                switch (Universita) {
                    case "Universita' degli Studi di Salerno":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Federico II":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - L'Orientale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Parthenope":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi del Sannio":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                adapterAnno = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Magistrale,
                        android.R.layout.simple_spinner_item);
                break;
            case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                switch (Universita) {
                    case "Universita' degli Studi di Salerno":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Federico II":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - L'Orientale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Parthenope":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi del Sannio":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                adapterAnno = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Magistrale_ciclo_unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
                break;
            case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                switch (Universita) {
                    case "Universita' degli Studi di Salerno":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Federico II":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - L'Orientale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Parthenope":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi del Sannio":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                adapterAnno = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Magistrale_ciclo_unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
                break;
        }

        adapterDipartimento.notifyDataSetChanged();

        adapterAnno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        annoUniversita.setAdapter(adapterAnno);
        tipo = adapterView.getSelectedItem().toString().trim();

        adapterDipartimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dipartimento.setAdapter(adapterDipartimento);

    }

    public void initUniversities(AdapterView<?> adapterView) {

        adapterDipartimento.notifyDataSetChanged();

        switch (adapterView.getSelectedItem().toString()) {
            case "Universita' degli Studi di Salerno":
                Universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi di Napoli - Federico II":
                Universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi di Napoli - L'Orientale":
                Universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi di Napoli - Parthenope":
                Universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                Universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi del Sannio":
                Universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                Universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
        }

        adapterDipartimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dipartimento.setAdapter(adapterDipartimento);

    }

    public void initTipoSuddivisione(AdapterView<?> adapterView) {

        switch (adapterView.getSelectedItem().toString()) {
            case "Matricola Pari o Dispari":
                adapterSuddivisione = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Suddivisione_Pari_o_Dispari,
                        android.R.layout.simple_spinner_item);
                break;
            case "Resto della Matricola":
                adapterSuddivisione = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Suddivisione_Resto_della_Matricola,
                        android.R.layout.simple_spinner_item);
                break;
            case "Cognome":
                adapterSuddivisione = ArrayAdapter.createFromResource(ModificaProfiloUtenteActivity.this, R.array.Suddivisione_Cognome,
                        android.R.layout.simple_spinner_item);
                break;
        }

        adapterSuddivisione.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suddivisione.setAdapter(adapterSuddivisione);
        TipoSuddivisione = adapterView.getSelectedItem().toString().trim();

    }

}
