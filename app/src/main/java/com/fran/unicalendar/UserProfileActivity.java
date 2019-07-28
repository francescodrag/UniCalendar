package com.fran.unicalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {


    ImageView edit, save;
    EditText email;
    Spinner spinnerTipo;
    ArrayAdapter<CharSequence> adapterTipo;
    String tipo;
    Spinner spinnerAnno;
    ArrayAdapter<CharSequence> adapterAnno;
    String anno;
    Spinner spinnerUniversita;
    ArrayAdapter<CharSequence> adapterUniversita;
    String universita;
    Spinner spinnerDipartimento;
    ArrayAdapter<CharSequence> adapterDipartimento;
    String dipartimento;
    RadioButton primoSemestre;
    String semestre;
    Intent intent;
    TextView nomeCognome;
    User user;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        intent = getIntent();
        user = intent.getParcelableExtra("utente");

        /*
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
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

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit.setVisibility(View.VISIBLE);
                save.setVisibility(View.INVISIBLE);

            }
        });

    }

    public void initView() {

        setupView();
        settingView();
        disableView();

    }

    public void setupView() {

        nomeCognome = findViewById(R.id.nomeCognomeUtente_UserProfileActivity);
        save = findViewById(R.id.save_UserProfileActivity);
        edit = findViewById(R.id.edit_UserProfileActivity);
        email = findViewById(R.id.email_UserProfileActivity);
        spinnerTipo = findViewById(R.id.tipologies_UserProfileActivity);
        spinnerAnno = findViewById(R.id.years_UserProfileActivity);
        spinnerUniversita = findViewById(R.id.univerisites_UserProfileActivity);
        spinnerDipartimento = findViewById(R.id.departments_UserProfileActivity);
        primoSemestre = findViewById(R.id.primoSemestre_UserProfileActivity);

    }

    public static void selectSpinnerItemByValue(Spinner spinner, ArrayAdapter<CharSequence> adapter, String value) {
        spinner.setAdapter(adapter);
        for (int position = 0; position < adapter.getCount(); position++) {
            System.out.println(adapter.getItem(position));
            if (Objects.equals(adapter.getItem(position), value)) {
                spinner.setSelection(position);
                return;
            }
        }
    }

    public void settingView() {

        nomeCognome.setText(user.getNome().concat(" ").concat(user.getCognome()));
        email.setText(user.getEmail());
        settingSpinnerTipo();
        settingSpinnerAnno();
        settingSpinnerUniversita();
        settingSpinnerDipartimento();
        settingSemester();

    }

    public void settingSpinnerTipo() {

        tipo = user.getUniversityTipe();

        adapterTipo = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.tipologies,
                android.R.layout.simple_spinner_item);

        selectSpinnerItemByValue(spinnerTipo, adapterTipo, tipo);

    }

    public void settingSpinnerAnno() {

        anno = user.getAnno();

        switch (tipo) {
            case ("Laurea Triennale"): {
                adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Triennale,
                        android.R.layout.simple_spinner_item);
                break;
            }
            case ("Laurea Magistrale"): {
                adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Magistrale,
                        android.R.layout.simple_spinner_item);
                break;
            }
            case ("Laurea Magistrale a Ciclo Unico di 5 Anni"): {
                adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Magistrale_ciclo_unico_di_5_anni,
                        android.R.layout.simple_spinner_item);
                break;
            }
            case ("Laurea Magistrale a Ciclo Unico di 6 Anni"): {
                adapterAnno = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Magistrale_ciclo_unico_di_6_anni,
                        android.R.layout.simple_spinner_item);
                break;
            }
        }

        selectSpinnerItemByValue(spinnerAnno, adapterAnno, anno);

    }

    public void settingSpinnerUniversita() {

        universita = user.getUniversity();

        adapterUniversita = ArrayAdapter.createFromResource(UserProfileActivity.this, R.array.Universities,
                android.R.layout.simple_spinner_item);

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

    public void settingSemester() {

        semestre = user.getSemestre();

        primoSemestre.setText(semestre);

    }

    public void disableView() {

        email.setEnabled(false);
        spinnerTipo.setEnabled(false);
        spinnerAnno.setEnabled(false);
        spinnerUniversita.setEnabled(false);
        spinnerDipartimento.setEnabled(false);
        primoSemestre.setEnabled(false);

    }

}
