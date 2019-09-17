package com.fran.unicalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private User user;
    private EditText Name;
    private String nome;
    private EditText Surname;
    private String cognome;
    private Spinner spinnerTipo;
    private String tipo = "Laurea Triennale";
    private Spinner spinnerAnno;
    private ArrayAdapter<CharSequence> adapterAnno;
    private String anno = "Primo Anno";
    private Spinner spinnerUniversita;
    private String universita = "Universita' degli Studi di Salerno";
    private Spinner spinnerDipartimento;
    private ArrayAdapter<CharSequence> adapterDipartimento;
    private String dipartimento = "Chimica";
    private RadioButton primoSemestre;
    private String semestre = "Primo Semestre";
    private ImageView goToRegistration2;
    private ImageView goBackToLogin;

    private static boolean nameValidator(String nome) {

        Pattern pattern;
        Matcher matcher;
        final String Name_Pattern =
                "^[A-Z][A-Za-z\\s]{2,30}$";
        pattern = Pattern.compile(Name_Pattern);
        matcher = pattern.matcher(nome);

        return matcher.matches();

    }

    private static boolean surnameValidator(String userSurname) {

        Pattern pattern;
        Matcher matcher;
        final String Surname_Pattern =
                "^[A-Z][A-Za-z\\s']{2,30}$";
        pattern = Pattern.compile(Surname_Pattern);
        matcher = pattern.matcher(userSurname);

        return matcher.matches();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration); //This Activity is Locked to portrait Orientation Mode!

        //Call the setupView, that instantiate the element of View.
        setupView();

        user = new User();

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
                anno = adapterView.getSelectedItem().toString().trim();
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
                dipartimento = adapterView.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

        goToRegistration2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nome = Name.getText().toString().trim();
                cognome = Surname.getText().toString().trim();

                if (validator()) {
                    Intent intent = new Intent(RegistrationActivity.this, Registration2Activity.class);
                    intent.putExtra("Utente", user);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * Instantiate the element of View
     */
    private void setupView() {
        Name = findViewById(R.id.name_RegistrationActivity);
        Surname = findViewById(R.id.surname_RegistrationActivity);
        spinnerTipo = findViewById(R.id.tipologies_RegistrationActivity);
        spinnerAnno = findViewById(R.id.years_RegistrationActivity);
        spinnerUniversita = findViewById(R.id.univerisites_RegistrationActivity);
        spinnerDipartimento = findViewById(R.id.departments_RegistrationActivity);
        primoSemestre = findViewById(R.id.primoSemestre_RegistrationActivity);
        goToRegistration2 = findViewById(R.id.goToRegistration2_RegistrationActivity);
        goBackToLogin = findViewById(R.id.goBackToLogin_RegistrationActivity);
    }

    private boolean validator() {

        if (nome.isEmpty()) {
            Name.setError("Il Nome non puo' essere vuoto!\nInserisci un Nome che inizi con la lettera maiuscola compreso tra i 3 ed i 31 caratteri.");
            Name.requestFocus();
            return false;
        } else if (!nameValidator(nome)) {
            Name.setError("Il nome inserito non e' ammesso!\nLa lunghezza dev'essere minimo di 3 caratteri e massimo 31.");
            Name.requestFocus();
            return false;
        } else if (cognome.isEmpty()) {
            Surname.setError("Il cognome non puo' essere vuoto!\nInserisci un cognome, che inizi con la lettera maiuscola e sia compreso tra 3 e 31 caratteri.");
            Surname.requestFocus();
            return false;
        } else if (!surnameValidator(cognome)) {
            Surname.setError("Il Cognome inserito non e' ammesso!\nLa lunghezza dev'essere minimo di 3 caratteri e massimo 31.");
            Surname.requestFocus();
            return false;
        } else if (dipartimento.equals("-")) {
            Toast.makeText(getApplicationContext(), "L'" + universita + " non offre corsi di " + tipo, Toast.LENGTH_LONG).show();
            return false;
        }

        SemesterValidator();

        fillUser(nome, cognome, tipo, anno, universita, dipartimento, semestre);

        return true;
    }

    /**
     * Check the value of Semester, checked by the user
     */
    private void SemesterValidator() {
        if (primoSemestre.isChecked()) {
            semestre = "Primo Semestre";
        } else {
            semestre = "Secondo Semestre";
        }
    }

    private void fillUser(String userName, String userSurname, String userDegreeType, String userYear, String userUniversity, String userDepartment, String userSemester) {

        user.setNome(userName);
        user.setCognome(userSurname);
        user.setUniversityTipe(userDegreeType);
        user.setAnno(userYear);
        user.setUniversity(userUniversity);
        user.setDepartment(userDepartment);
        user.setSemestre(userSemester);

    }

    private void initTipo(AdapterView<?> adapterView) {

        switch (adapterView.getSelectedItem().toString()) {
            case "Laurea Triennale":
                switch (universita) {
                    case "Universita' degli Studi di Salerno":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Federico II":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - L'Orientale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Parthenope":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi del Sannio":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                adapterAnno = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Triennale,
                        android.R.layout.simple_spinner_item);
                break;
            case "Laurea Magistrale":
                switch (universita) {
                    case "Universita' degli Studi di Salerno":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Federico II":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - L'Orientale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Parthenope":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi del Sannio":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                adapterAnno = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Magistrale,
                        android.R.layout.simple_spinner_item);
                break;
            case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                switch (universita) {
                    case "Universita' degli Studi di Salerno":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Federico II":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - L'Orientale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Parthenope":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi del Sannio":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                adapterAnno = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Magistrale_ciclo_unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
                break;
            case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                switch (universita) {
                    case "Universita' degli Studi di Salerno":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Federico II":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - L'Orientale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Parthenope":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi del Sannio":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                adapterAnno = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Magistrale_ciclo_unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
                break;
        }

        adapterDipartimento.notifyDataSetChanged();

        adapterAnno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnno.setAdapter(adapterAnno);
        tipo = adapterView.getSelectedItem().toString().trim();

        adapterDipartimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDipartimento.setAdapter(adapterDipartimento);

    }

    private void initUniversities(AdapterView<?> adapterView) {

        adapterDipartimento.notifyDataSetChanged();

        switch (adapterView.getSelectedItem().toString()) {
            case "Universita' degli Studi di Salerno":
                universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi di Napoli - Federico II":
                universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi di Napoli - L'Orientale":
                universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi di Napoli - Parthenope":
                universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi di Napoli - Suor Orsola Benincasa":
                universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi del Sannio":
                universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            case "Universita' degli Studi della Campania - Luigi Vanvitelli":
                universita = adapterView.getSelectedItem().toString().trim();
                switch (tipo) {
                    case "Laurea Triennale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 5 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                    case "Laurea Magistrale a Ciclo Unico di 6 Anni":
                        adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                        break;
                }
                break;
        }

        adapterDipartimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDipartimento.setAdapter(adapterDipartimento);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}