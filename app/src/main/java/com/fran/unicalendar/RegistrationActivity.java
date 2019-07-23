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

    User user;
    EditText Name;
    String nome;
    EditText Surname;
    String cognome;
    Spinner spinnerTipo;
    String tipo = "Laurea Triennale";
    Spinner spinnerAnno;
    ArrayAdapter<CharSequence> adapterAnno;
    String anno = "Primo Anno";
    Spinner spinnerUniversita;
    String universita = "Universita' degli Studi di Salerno";
    Spinner spinnerDipartimento;
    ArrayAdapter<CharSequence> adapterDipartimento;
    String dipartimento = "Chimica";
    RadioButton primoSemestre;
    String semestre = "Primo Semestre";
    ImageView goToRegistration2;
    ImageView goBackToLogin;

    protected static boolean nameValidator(String nome) {

        Pattern pattern;
        Matcher matcher;
        final String Name_Pattern =
                "^[A-Z][a-z]{2,15}$";
        pattern = Pattern.compile(Name_Pattern);
        matcher = pattern.matcher(nome);

        return matcher.matches();

    }

    protected static boolean surnameValidator(String userSurname) {

        Pattern pattern;
        Matcher matcher;
        final String Surname_Pattern =
                "^[A-Z][A-Za-z']{2,15}$";
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
    public void setupView() {
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

    public boolean validator() {

        if (nome.isEmpty()) {
            Name.setError("Il Nome non puo' essere vuoto!\nInserisci un Nome che inizi con la lettera maiuscola compreso tra i 3 ed i 16 caratteri.");
            Name.requestFocus();
            return false;
        } else if (!nameValidator(nome)) {
            Name.setError("Il nome inserito non e' ammesso!\nLa lunghezza dev'essere minimo di 3 caratteri e massimo 16.");
            Name.requestFocus();
            return false;
        } else if (cognome.isEmpty()) {
            Surname.setError("Il cognome non puo' essere vuoto!\nInserisci un cognome, che inizi con la lettera maiuscola e sia compreso tra 3 e 16 caratteri.");
            Surname.requestFocus();
            return false;
        } else if (!surnameValidator(cognome)) {
            Surname.setError("Il Cognome inserito non e' ammesso!\nLa lunghezza dev'essere minimo di 3 caratteri e massimo 16.");
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
    public void SemesterValidator() {
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

    public void initTipo(AdapterView<?> adapterView) {

        if (adapterView.getSelectedItem().toString().equals("Laurea Triennale")) {
            if (universita.equals("Universita' degli Studi di Salerno")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Federico II")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - L'Orientale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Parthenope")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi del Sannio")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                        android.R.layout.simple_spinner_item);
            }
            adapterAnno = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Triennale,
                    android.R.layout.simple_spinner_item);
        } else if (adapterView.getSelectedItem().toString().equals("Laurea Magistrale")) {
            if (universita.equals("Universita' degli Studi di Salerno")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Federico II")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - L'Orientale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Parthenope")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi del Sannio")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                        android.R.layout.simple_spinner_item);
            }
            adapterAnno = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Magistrale,
                    android.R.layout.simple_spinner_item);
        } else if (adapterView.getSelectedItem().toString().equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
            if (universita.equals("Universita' degli Studi di Salerno")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Federico II")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - L'Orientale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Parthenope")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi del Sannio")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            }
            adapterAnno = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Magistrale_ciclo_unico_di_5_anni,
                    android.R.layout.simple_spinner_item);
        } else if (adapterView.getSelectedItem().toString().equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
            if (universita.equals("Universita' degli Studi di Salerno")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Federico II")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - L'Orientale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Parthenope")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi del Sannio")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            } else if (universita.equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
            adapterAnno = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Magistrale_ciclo_unico_di_6_anni,
                    android.R.layout.simple_spinner_item);
        }

        adapterDipartimento.notifyDataSetChanged();

        adapterAnno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnno.setAdapter(adapterAnno);
        tipo = adapterView.getSelectedItem().toString().trim();

        adapterDipartimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDipartimento.setAdapter(adapterDipartimento);

    }

    public void initUniversities(AdapterView<?> adapterView) {

        adapterDipartimento.notifyDataSetChanged();

        if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Salerno")) {
            universita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Napoli - Federico II")) {
            universita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Federico_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Napoli - L'Orientale")) {
            universita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orientale_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Napoli - Parthenope")) {
            universita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Parthenope_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi di Napoli - Suor Orsola Benincasa")) {
            universita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Di_Napoli_Orsola_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi del Sannio")) {
            universita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Del_Sannio_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        } else if (adapterView.getSelectedItem().toString().equals("Universita' degli Studi della Campania - Luigi Vanvitelli")) {
            universita = adapterView.getSelectedItem().toString().trim();
            if (tipo.equals("Laurea Triennale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Triennale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
            } else if (tipo.equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                adapterDipartimento = ArrayAdapter.createFromResource(RegistrationActivity.this,
                        R.array.Universita_Degli_Studi_Della_Campania_Magistrale_a_Ciclo_Unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
            }
        }

        adapterDipartimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDipartimento.setAdapter(adapterDipartimento);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}