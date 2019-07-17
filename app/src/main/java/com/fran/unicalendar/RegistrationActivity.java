package com.fran.unicalendar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    EditText Name, Surname;
    Spinner spinnerTipo;
    Spinner spinnerAnno;
    Spinner spinnerUniversita;
    Spinner spinnerDipartimento;
    String tipo = "Laurea Triennale", anno = "Primo Anno", universita = "Universita' degli Studi di Salerno", dipartimento = "Chimica";
    ArrayAdapter<CharSequence> adapterDipartimento, adapterAnno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupView();

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                initTipo(adapterView);

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

    }

    public void setupView() {
        Name = findViewById(R.id.name_RegistrationActivity);
        Surname = findViewById(R.id.surname_RegistrationActivity);
        spinnerTipo = findViewById(R.id.tipologies_RegistrationActivity);
        spinnerAnno = findViewById(R.id.years_RegistrationActivity);
        spinnerUniversita = findViewById(R.id.univerisites_RegistrationActivity);
        spinnerDipartimento = findViewById(R.id.departments_RegistrationActivity);
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

}