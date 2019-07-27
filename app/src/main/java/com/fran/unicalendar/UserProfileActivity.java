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

import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {


    ImageView edit, save;
    EditText email;
    Spinner spinnerTipo;
    String nome;
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
    Intent intent;
    TextView nomeCognome;
    User user;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    Map<String, Object> hashMapUser;

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

    public void settingView() {

        nomeCognome.setText(user.getNome().concat(" ").concat(user.getCognome()));
        email.setText(user.getEmail());

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
