package com.fran.unicalendar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    EditText Name, Surname;
    Spinner Universities;
    FirebaseFirestore firebaseFirestore;
    CollectionReference universitiesRef;
    ArrayList<String> universities;
    ArrayAdapter<String> universitiesAdapter;
    String uni;
    List array = new ArrayList<>();
    DatabaseReference ref;
    FirebaseDatabase root;
    ArrayAdapter<String> areasAdapter;
    //final DatabaseReference.CompletionListener onComplete;
    Spinner spinnerTipo;
    Spinner spinnerAnno;
    Spinner spinnerUniversita;
    Spinner spinnerDipartimento;
    String tipo = "Laurea Triennale", anno = "Primo Anno", universita = "Universita' degli Studi di Salerno";
    ArrayAdapter<CharSequence> adapterTipo, adapterUniversita, adapterDipartimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        spinnerTipo = findViewById(R.id.tipologies_RegistrationActivity);
        spinnerAnno = findViewById(R.id.years_RegistrationActivity);
        spinnerUniversita = findViewById(R.id.univerisites_RegistrationActivity);
        spinnerDipartimento = findViewById(R.id.departments_RegistrationActivity);

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItem().toString().equals("Laurea Triennale")) {
                    if (universita.equals("Universita' degli Studi di Salerno")) {
                        adapterUniversita = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Triennale,
                                android.R.layout.simple_spinner_item);
                    } else if (universita.equals("Universita' degli Studi di Napoli - Federico II")) {
                        adapterUniversita = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Napoli_Federico_Triennale,
                                android.R.layout.simple_spinner_item);
                    }
                    adapterTipo = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Triennale,
                            android.R.layout.simple_spinner_item);
                } else if (adapterView.getSelectedItem().toString().equals("Laurea Magistrale")) {
                    if (universita.equals("Universita' degli Studi di Salerno")) {
                        adapterUniversita = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale,
                                android.R.layout.simple_spinner_item);
                    }

                    adapterTipo = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Magistrale,
                            android.R.layout.simple_spinner_item);
                } else if (adapterView.getSelectedItem().toString().equals("Laurea Magistrale a Ciclo Unico di 5 Anni")) {
                    if (universita.equals("Universita' degli Studi di Salerno")) {
                        adapterUniversita = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_5_anni,
                                android.R.layout.simple_spinner_item);
                    }
                    adapterTipo = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Magistrale_ciclo_unico_di_5_anni,
                            android.R.layout.simple_spinner_item);
                } else if (adapterView.getSelectedItem().toString().equals("Laurea Magistrale a Ciclo Unico di 6 Anni")) {
                    if (universita.equals("Universita' degli Studi di Salerno")) {
                        adapterUniversita = ArrayAdapter.createFromResource(RegistrationActivity.this,
                                R.array.Universita_Degli_Studi_Di_Salerno_Magistrale_a_Ciclo_Unico_di_6_anni,
                                android.R.layout.simple_spinner_item);
                    }
                    adapterTipo = ArrayAdapter.createFromResource(RegistrationActivity.this, R.array.Magistrale_ciclo_unico_di_6_anni,
                            android.R.layout.simple_spinner_item);
                }

                adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAnno.setAdapter(adapterTipo);
                tipo = adapterView.getSelectedItem().toString().trim();
                Toast.makeText(getApplicationContext(), tipo, Toast.LENGTH_LONG).show();

                adapterUniversita.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDipartimento.setAdapter(adapterUniversita);
                adapterUniversita.notifyDataSetChanged();
                adapterTipo.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerUniversita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterTipo.notifyDataSetChanged();
                adapterUniversita.notifyDataSetChanged();
                spinnerDipartimento.setSelection(0);
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
                }


                adapterDipartimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDipartimento.setAdapter(adapterDipartimento);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


/*
        root = FirebaseDatabase.getInstance();
        ref = root.getReference();

            ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("Universities").getValue(String.class);

                    Spinner areaSpinner = (Spinner) findViewById(R.id.univerisites_RegistrationActivity);
                    final String[] areas = {areaName};
                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>
                            (RegistrationActivity.this, android.R.layout.simple_spinner_item, areas);
                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areasAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

*/

        //Let's access to Firestore Cloud instance of Database
        //firebaseFirestore = FirebaseFirestore.getInstance();
        //universitiesRef = firebaseFirestore.collection("Universities");
        /*
        final Task<QuerySnapshot> querySnapshotTask = universitiesRef.get().addOnCompleteListener(new FirebaseDatabase().getReference().OnCompletionListener(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

            }
        });
        universitiesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) { int i = 0;
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        array.add(document.getId());
                        System.out.println(array.get(i));
                        i++;
                        if (task.getResult().isEmpty()) {
                            Spinner areaSpinner = (Spinner) findViewById(R.id.univerisites_RegistrationActivity);
                            areasAdapter = new ArrayAdapter<String>
                                    (RegistrationActivity.this, android.R.layout.simple_spinner_item, array);
                            areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            areaSpinner.setAdapter(areasAdapter);


                            Universities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String str = Universities.getItemAtPosition(position).toString();
                                    // Show Toast
                                    Toast.makeText(getApplicationContext(),
                                            "Click su posizione n." + position + ": " + str, Toast.LENGTH_LONG)
                                            .show();
                                    System.out.println(str);
                                }

                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }

                    }

                    }
                    //universitiesAdapter.notifyDataSetChanged();
                }

        });

      */
/*
        universitiesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(int i=0 ; i<queryDocumentSnapshots.size();i++){
                    System.out.println(queryDocumentSnapshots.getDocuments().get(i).getId());
                    array.add(queryDocumentSnapshots.getDocuments().get(i).getId().trim());
                }
                Spinner areaSpinner = (Spinner) findViewById(R.id.univerisites_RegistrationActivity);
                areasAdapter = new ArrayAdapter<String>
                        (RegistrationActivity.this, android.R.layout.simple_spinner_item, array);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
*/
  /*
        universitiesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("tag", document.getId() + " => " + document.getData());
                                Spinner areaSpinner = (Spinner) findViewById(R.id.univerisites_RegistrationActivity);
                                areasAdapter = new ArrayAdapter<String>
                                        (RegistrationActivity.this, android.R.layout.simple_spinner_item, array);
                                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                areaSpinner.setAdapter(areasAdapter);
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }

                            Universities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String str = Universities.getItemAtPosition(position).toString();
                                    // Show Toast
                                    Toast.makeText(getApplicationContext(),
                                            "Click su posizione n." + position + ": " + str, Toast.LENGTH_LONG)
                                            .show();
                                    System.out.println(str);
                                }

                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }

                });
*/

/*
        Universities = findViewById(R.id.univerisites_RegistrationActivity);

        Log.d("DEBUG", "ListView create: listView=" + Universities);


            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this, R.layout.universities_spinner_element,
                            R.id.univeristy_element_nome, array);

            Log.d("DEBUG", "ArrayAdapter create: arrayAdapter=" + arrayAdapter);

            Universities.setAdapter(arrayAdapter);

            Log.d("DEBUG", "Done!");

        ArrayAdapter<String> universitiesAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, array);
        universitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Universities.setAdapter(universitiesAdapter);
*/





/*
        universitiesRef = firebaseFirestore.collection("Universities");
        universitiesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) { int i =0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        v[i] = document.getId();
                        i++;
                    }
                    universitiesAdapter.notifyDataSetChanged();
                }
            }
        });

        Name = findViewById(R.id.name_RegistrationActivity);
        Surname = findViewById(R.id.surname_RegistrationActivity);
        Universities = findViewById(R.id.univerisites_RegistrationActivity);

        //Let's access to Firestore Cloud instance of Database
        firebaseFirestore = FirebaseFirestore.getInstance();

        UniversitiesSpinnerAdapter customAdapter =
                new UniversitiesSpinnerAdapter(this, R.layout.universities_spinner_element, new ArrayList<String>());
        //initUniversitiesSpinner();
        Universities.setAdapter(customAdapter);

        for (int i=0; i<v.length; i++) {
            String nome = v[i];
            customAdapter.add(nome);
        }

        Universities.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  str  = Universities.getItemAtPosition(position).toString();
                // Show Toast
                Toast.makeText(getApplicationContext(),
                        "Click su posizione n."+position+": " +str, Toast.LENGTH_LONG)
                        .show();
            }
        });


        Universities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true); //mi setta il colore dell'anno verde
                //Toast.makeText(getApplicationContext(),YearList.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
                uni = parent.getItemAtPosition(position).toString().trim();

                //Toast.makeText(getApplicationContext(), uni, Toast.LENGTH_SHORT).show();
            }
        });

*/




        /*
        //Let's access to Firestore Cloud instance of Database
        firebaseFirestore = FirebaseFirestore.getInstance();

        universitiesRef = firebaseFirestore.collection("Universities").document("Universita' degli Studi del Sanno di Benevento").collection("Triennali");

        final List<String> univeristies = new ArrayList<>();
        final ArrayAdapter<String> universitiesAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, univeristies);
        universitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Universities.setAdapter(universitiesAdapter);

        universitiesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("nome");
                        univeristies.add(subject);
                    }
                    universitiesAdapter.notifyDataSetChanged();
                }
            }
        });

*/
    }
/*
    public void initUniversitiesSpinner(){
        universitiesRef = firebaseFirestore.collection("Universities");
        universities = new ArrayList<>();
        universitiesAdapter = new ArrayAdapter<>(RegistrationActivity.this,
                android.R.layout.simple_spinner_item, universities);
        universitiesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String university = document.getId();
                        universities.add(university);
                    }
                    universitiesAdapter.notifyDataSetChanged();
                }
            }
        });
        universitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Universities.setAdapter(universitiesAdapter);
    }
    */
}
