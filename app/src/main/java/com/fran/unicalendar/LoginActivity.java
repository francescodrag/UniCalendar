package com.fran.unicalendar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
    private ProgressDialog progressDialog;

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
            }
        });

        PasswordDimenticata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RecuperaPasswordActivity.class));
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
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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

}
