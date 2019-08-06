package com.fran.unicalendar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {


    ImageView edit, save;
    EditText email;
    AutoCompleteTextView autoTipo;
    ArrayAdapter<CharSequence> adapterTipo;
    String tipo;
    String changedTipo;
    AutoCompleteTextView autoAnno;
    ArrayAdapter<CharSequence> adapterAnno;
    String anno;
    String changedAnno;
    AutoCompleteTextView autoUniversita;
    ArrayAdapter<CharSequence> adapterUniversita;
    String universita;
    String changedUniversita;
    AutoCompleteTextView autoDipartimento;
    ArrayAdapter<CharSequence> adapterDipartimento;
    String dipartimento;
    String changedDipartimento;
    RadioButton checkSinistra, checkDestra;
    String semestre;
    String changedSemestre;
    Intent intent;
    TextView nomeCognome;
    User user;
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
        setContentView(R.layout.activity_user_profile);

        //intent = getIntent();
        //user = intent.getParcelableExtra("utente");
        handler = new Handler();

        sharedPreferences = getSharedPreferences("User_Preferences", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        user = gson.fromJson(json, User.class);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        /*
        System.out.println(firebaseUser.getEmail());

        DocumentReference docRef = firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseUser.getEmail()));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("", "DocumentSnapshot data: " + document.getData());
                        System.out.println(document.getData());
                        System.out.println(document.get("Name"));
                    } else {
                        Log.d("", "No such document");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
*/
        //hashMapUser = (Map<String, Object>) firebaseFirestore.collection("Users")
        //      .whereEqualTo(Objects.requireNonNull(firebaseUser.getEmail()), true)
        //    .get().getResult();


        initView();


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit.setVisibility(View.INVISIBLE);
                save.setVisibility(View.VISIBLE);
                enableView();
                enableAdapterTipo();
                enableAdapterAnno();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit.setVisibility(View.VISIBLE);
                save.setVisibility(View.INVISIBLE);
                /*
                verifyChange();
                if (tipo.equals(changedTipo)) {
                    System.out.println("Changed Value: " + changedAnno + "\nPrevious Value: " + anno);
                    System.out.println("Changed Value: " + changedTipo + "\nPrevious Value: " + tipo);
                    System.out.println(changedDipartimento);
                    System.out.println(changedUniversita);
                    disableView();
                } else {
                    Toast.makeText(getApplicationContext(), "Changed detected", Toast.LENGTH_LONG).show();

                }
                */


            }
        });

        /*
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                initTipo(adapterView);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAnno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                changedAnno = adapterView.getSelectedItem().toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerUniversita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                initUniversities(adapterView);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDipartimento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                changedDipartimento = adapterView.getSelectedItem().toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        */

        checkSinistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changedSemestre = checkSinistra.getText().toString();
                Log.d("changedSemestre", changedSemestre);

            }
        });

    }

    public void initView() {

        setupView();
        settingView();
        setupImageUser();
        disableView();

    }

    public void setupView() {

        nomeCognome = findViewById(R.id.nomeCognomeUtente_UserProfileActivity);
        save = findViewById(R.id.save_UserProfileActivity);
        edit = findViewById(R.id.edit_UserProfileActivity);
        email = findViewById(R.id.email_UserProfileActivity);
        autoTipo = findViewById(R.id.tipologies_UserProfileActivity);
        autoAnno = findViewById(R.id.years_UserProfileActivity);
        autoUniversita = findViewById(R.id.univerisites_UserProfileActivity);
        autoDipartimento = findViewById(R.id.department_UserProfileActivity);
        checkSinistra = findViewById(R.id.primoSemestre_UserProfileActivity);
        checkDestra = findViewById(R.id.secondoSemestre_UserProfileActivity);
        imageUser = findViewById(R.id.image_UserProfileActivity);

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

    public void settingView() {

        nomeCognome.setText(user.getNome().concat(" ").concat(user.getCognome()));
        email.setText(user.getEmail());
        autoTipo.setText(user.getUniversityTipe());
        autoAnno.setText(user.getAnno());
        autoUniversita.setText(user.getUniversity());
        autoDipartimento.setText(user.getDepartment());

        /*
        settingSpinnerTipo();
        settingSpinnerAnno();
        settingSpinnerUniversita();
        settingSpinnerDipartimento();
        */
        settingSemester();

    }

    /*
    public void settingSpinnerTipo() {

        tipo = user.getUniversityTipe();

        adapterTipo = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.tipologies,
                android.R.layout.simple_spinner_dropdown_item);

        selectSpinnerItemByValue(spinnerTipo, adapterTipo, tipo);

    }

    public void settingSpinnerAnno() {

        anno = user.getAnno();
        System.out.println(anno);

        if (tipo.equals("Laurea Triennale")) {
            adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Triennale,
                    android.R.layout.simple_spinner_dropdown_item);
        } else if (tipo.equals("Laurea Magistrale")) {
                adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Magistrale,
                        android.R.layout.simple_spinner_dropdown_item);
            System.out.println("ECCOMI");
        } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Magistrale_ciclo_unico_di_5_anni,
                        android.R.layout.simple_spinner_dropdown_item);
        } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Magistrale_ciclo_unico_di_6_anni,
                        android.R.layout.simple_spinner_dropdown_item);
        }

        selectSpinnerItemByValue(spinnerAnno, adapterAnno, anno);

    }

    public void settingSpinnerUniversita() {

        universita = user.getUniversity();

        adapterUniversita = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Universities,
                android.R.layout.simple_spinner_dropdown_item);

        selectSpinnerItemByValue(spinnerUniversita, adapterUniversita, universita);

    }

    public void settingSpinnerDipartimento() {

        dipartimento = user.getDepartment();

        if (tipo.equals("Laurea Triennale")) {
            switch (universita) {
                case ("Universita' degli Studi di Salerno"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Federico II"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - L'Orientale"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Parthenope"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Suor Orsola Benincasa"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi del Sannio"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi della Campania - Luigi Vanvitelli"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
            }
        } else if (tipo.equals("Laurea Magistrale")) {
            switch (universita) {
                case ("Universita' degli Studi di Salerno"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Federico II"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - L'Orientale"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Parthenope"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Suor Orsola Benincasa"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi del Sannio"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi della Campania - Luigi Vanvitelli"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                            android.R.layout.simple_spinner_item);

                    break;
                }
            }
        } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
            switch (universita) {
                case ("Universita' degli Studi di Salerno"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Federico II"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - L'Orientale"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Parthenope"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Suor Orsola Benincasa"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi del Sannio"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi della Campania - Luigi Vanvitelli"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
            }
        } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
            switch (universita) {
                case ("Universita' degli Studi di Salerno"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Federico II"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - L'Orientale"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Parthenope"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi di Napoli - Suor Orsola Benincasa"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi del Sannio"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
                case ("Universita' degli Studi della Campania - Luigi Vanvitelli"): {
                    adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                            R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                            android.R.layout.simple_spinner_item);

                    break;
                }
            }
        }

        selectSpinnerItemByValue(spinnerDipartimento, adapterDipartimento, dipartimento);

    }

*/
    @SuppressLint("SetTextI18n")
    public void settingSemester() {

        semestre = user.getSemestre();
        if (semestre.equals("Primo Semestre")) {
            checkSinistra.setText(semestre);
            checkDestra.setText("Secondo Semestre");
        } else if (semestre.equals("Secondo Semestre")) {
            checkSinistra.setText(semestre);
            checkDestra.setText("Primo Semestre");
        }

    }

    public void disableView() {

        email.setEnabled(false);
        autoTipo.setEnabled(false);
        autoAnno.setEnabled(false);
        autoUniversita.setEnabled(false);
        autoDipartimento.setEnabled(false);
        checkSinistra.setEnabled(false);
        checkDestra.setVisibility(View.INVISIBLE);

    }

    public void enableView() {

        autoTipo.setEnabled(true);
        autoAnno.setEnabled(true);
        autoUniversita.setEnabled(true);
        autoDipartimento.setEnabled(true);
        checkSinistra.setEnabled(true);
        checkDestra.setVisibility(View.VISIBLE);

    }

    public void enableAdapterTipo() {

        adapterTipo = ArrayAdapter.createFromResource(UserProfileActivity.this,
                R.array.tipologies,
                android.R.layout.simple_spinner_item);
        autoTipo.setAdapter(adapterTipo);

    }

    public void enableAdapterAnno() {

        if (autoTipo.getText().toString().equals("Laurea Triennale")) {

            Log.d("autoTipo", autoTipo.getText().toString());
            adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this,
                    R.array.Triennale,
                    android.R.layout.simple_spinner_item);
            autoAnno.setAdapter(adapterAnno);

        } else if (autoTipo.getText().toString().equals("Laurea Magistrale")) {

            adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this,
                    R.array.Magistrale,
                    android.R.layout.simple_spinner_item);
            autoAnno.setAdapter(adapterAnno);

        } else if (autoTipo.getText().toString().equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {

            adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this,
                    R.array.Magistrale_ciclo_unico_di_5_anni,
                    android.R.layout.simple_spinner_item);
            autoAnno.setAdapter(adapterAnno);

        } else if (autoTipo.getText().toString().equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {

            adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this,
                    R.array.Magistrale_ciclo_unico_di_6_anni,
                    android.R.layout.simple_spinner_item);
            autoAnno.setAdapter(adapterAnno);

        }

    }

    public void initTipo(AdapterView<?> adapterView) {

        if (adapterView.getSelectedItem().toString().equals("Laurea Triennale")) {
            if (universita.equals("Universita' degli Studi di Salerno")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Federico II")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - L'Orientale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Parthenope")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi del Sannio")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                        android.R.layout.simple_spinner_item);
            }
            adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Triennale,
                    android.R.layout.simple_spinner_item);
        } else if (adapterView.getSelectedItem().toString().equals("Laurea Magistrale")) {
            if (universita.equals("Universita' degli Studi di Salerno")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Federico II")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - L'Orientale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Parthenope")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi del Sannio")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                        android.R.layout.simple_spinner_item);
            }
            adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Magistrale,
                    android.R.layout.simple_spinner_item);
        } else if (adapterView.getSelectedItem().toString().equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
            if (universita.equals("Universita' degli Studi di Salerno")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Federico II")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - L'Orientale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Parthenope")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi del Sannio")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            }
            adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Magistrale_ciclo_unico_di_5_anni,
                    android.R.layout.simple_spinner_item);
        } else if (adapterView.getSelectedItem().toString().equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
            if (universita.equals("Universita' degli Studi di Salerno")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Federico II")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - L'Orientale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Parthenope")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi del Sannio")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
            adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Magistrale_ciclo_unico_di_6_anni,
                    android.R.layout.simple_spinner_item);
        }

        adapterDipartimento.notifyDataSetChanged();

        adapterAnno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoAnno.setAdapter(adapterAnno);
        changedTipo = adapterView.getSelectedItem().toString().trim();

        adapterDipartimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoDipartimento.setAdapter(adapterDipartimento);

    }

    public void initUniversities(AdapterView<?> adapterView) {

        adapterDipartimento.notifyDataSetChanged();

        if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Salerno")) {
            changedUniversita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Napoli - Federico II")) {
            changedUniversita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Napoli - L'Orientale")) {
            changedUniversita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Napoli - Parthenope")) {
            changedUniversita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
            changedUniversita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi del Sannio")) {
            changedUniversita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
            changedUniversita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(UserProfileActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        }

        adapterDipartimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoDipartimento.setAdapter(adapterDipartimento);

    }

    private void verifyChange() {

        semesterValidator();

        if (!semestre.equals(changedSemestre)) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Upload in corso...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            refactorSemestre(semestre, changedSemestre);

            uploadFirestore(changedSemestre, progressDialog);

        }
        /*
        if(tipo.equals(changedTipo)&&(anno.equals(changedAnno))
            &&universita.equals(changedUniversita)&&dipartimento.equals(changedDipartimento)){
            System.out.println("Changed Value: "+changedAnno+"\nPrevioud Value: "+ anno);
            System.out.println("Changed Value: "+changedTipo+"\nPrevioud Value: "+ tipo);
            System.out.println(changedDipartimento);
            System.out.println(changedUniversita);

        } else {
            Toast.makeText(getApplicationContext(),"Changed detected", Toast.LENGTH_LONG).show();

        }
        */
    }

    public void semesterValidator() {

        if (checkDestra.isChecked()) {
            changedSemestre = checkDestra.getText().toString();
            checkSinistra.setChecked(true);
        } else if (checkSinistra.isChecked()) {
            changedSemestre = checkSinistra.getText().toString();
        }


    }

    public void refactorSemestre(String semestre, String changedSemestre) {

        checkSinistra.setText(changedSemestre);
        checkDestra.setText(semestre);

    }

    public void uploadFirestore(String data, final ProgressDialog progressDialog) {

        DocumentReference washingtonRef = firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseUser.getEmail()));
        System.out.println(data);
// Set the "isCapital" field of the city 'DC'
        washingtonRef
                .update("Semester", data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });

    }
}
