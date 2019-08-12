package com.fran.unicalendar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText Email;
    EditText Password;
    TextView Info;
    Button Login;
    Button Signup;
    TextView PasswordDimenticata;
    int Contatore = 5;
    String info = "Tentativi restanti: ";
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    User user;
    Intent intent2;
    FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = Email.getText().toString().trim();
                String userPassword = Password.getText().toString().trim();
                valida(userEmail, userPassword);
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });

        PasswordDimenticata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CalendarActivity.class));
            }
        });
    }

    public void initView() {
        Email = findViewById(R.id.email_Login);
        Password = findViewById(R.id.password_Login);
        Info = findViewById(R.id.info_Login);
        Login = findViewById(R.id.login_Login);
        Signup = findViewById(R.id.signup_Login);
        PasswordDimenticata = findViewById(R.id.recuperaPassword_Login);
    }

/*
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser utente = firebaseAuth.getCurrentUser();

        //controlla se l'utente è già loggato
        if (utente != null) {
            //L'utente è già loggato quindi viene inidirizzato alla HomeActivity
            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }

    }
*/

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Contatore = savedInstanceState.getInt("Contatore");
        Info.setText(info.concat(String.valueOf(Contatore)));
        if (Contatore == 0) {
            Login.setEnabled(false); //disabilita il bottone del login
        }

    }

    //è un metodo che gestisce il login
    private void valida(String userEmail, String userPassword) {
/*
        if (userEmail.equals("") && userPassword.equals("")) {
            checkCounter();

            Toast.makeText(LoginActivity.this, "Inserisci Email e Password", Toast.LENGTH_LONG).show();
        } else */
        if (userEmail.equals("")) {
            checkCounter();
            Email.setError("Il campo email non puo' essere vuoto.\nInserisci l'email per favore.");
            Email.requestFocus();
            Toast.makeText(LoginActivity.this, "Inserisci Email", Toast.LENGTH_LONG).show();
        } else if (userPassword.equals("")) {
            checkCounter();
            Password.setError("Il campo password non puo' essere vuoto.\nInserisci la password per favore.");
            Password.requestFocus();
            Toast.makeText(LoginActivity.this, "Inserisci Password", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setMessage("Login in corso...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        checkEmailVerification();
                    } else if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        //bisogna specificare il context perchè ci troviamo all'interno di un loop
                        Toast.makeText(LoginActivity.this, "Login fallito", Toast.LENGTH_LONG).show();
                        checkCounter();
                    }
                }
            });
        }

    }

    public void checkCounter() {
        if (Contatore > 0) {
            Contatore--;
            Info.setText(info.concat(String.valueOf(Contatore))); //cambia il text view
        }
        if (Contatore == 0) {
            Login.setEnabled(false); //disabilita il bottone del login
        }
    }


    private void checkEmailVerification() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //true se l'email e' stata verificata, false altrimenti.
        if (((firebaseUser != null) && (firebaseUser.isEmailVerified()))) {
            getUserFromDatabase();
            //startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        } else {
            Toast.makeText(LoginActivity.this, "Account non verificato.", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            checkCounter();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Contatore", Contatore);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void getUserFromDatabase() {
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        intent2 = new Intent(LoginActivity.this, HomeActivity.class);

        DocumentReference docRef = firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseUser.getEmail()));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d("", "DocumentSnapshot data: " + document.getData());
                            System.out.println(document.getData());

                            user = new User();

                            user.setNome((String) document.get("Name"));
                            user.setCognome((String) document.get("Surname"));
                            user.setId((String) document.get("Id"));
                            user.setSuddivisione((String) document.get("Subdivision"));
                            user.setTipoSuddivisione((String) document.get("SubdivisionType"));
                            user.setEmail((String) document.get("Email"));
                            user.setSemestre((String) document.get("Semester"));
                            user.setDepartment((String) document.get("Department"));
                            user.setAnno((String) document.get("Year"));
                            user.setUniversity((String) document.get("University"));
                            user.setUniversityTipe((String) document.get("UniversityType"));
                            user.setPassword((String) document.get("Password"));
                            user.setCalendario((boolean) document.get("HasCalendario"));

                            gson = new Gson();
                            String json = gson.toJson(user);

                            sharedPreferences = getSharedPreferences("User_Preferences", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();

                            editor.putString("user", json);

                            editor.apply();

                            //intent2.putExtra("utente", user);
                            startActivity(intent2);
                            finish();

                        } else {
                            Log.d("", "No such document");
                        }
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
    }
}
