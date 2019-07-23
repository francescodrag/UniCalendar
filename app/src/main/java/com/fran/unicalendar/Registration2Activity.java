package com.fran.unicalendar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration2Activity extends AppCompatActivity {

    User user;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    Map<String, Object> hashMapUser;
    Spinner spinnerTipoSuddivisione;
    String tipoSuddivisione = "Matricola Pari o Dispari";
    Spinner spinnerSuddivisione;
    ArrayAdapter<CharSequence> adapterSuddivisione;
    String suddivisione = "Pari";
    EditText Email;
    String email;
    EditText Password;
    String password;
    EditText RipetiPassword;
    String ripetiPassword;
    //ImageView goBackToRegistration1;
    ImageView signUp;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("Utente");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        //Toast.makeText(getApplicationContext(), utente.toString(), Toast.LENGTH_LONG).show();

        setupView();

        spinnerTipoSuddivisione.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                initTipoSuddivisione(adapterView);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSuddivisione.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                suddivisione = adapterView.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*
        goBackToRegistration1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration2Activity.this, RegistrationActivity.class));
            }
        });
*/
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Registration2Activity.this, HomeActivity.class));

                email = Email.getText().toString().trim();
                password = Password.getText().toString().trim();
                ripetiPassword = RipetiPassword.getText().toString().trim();

                if (validator()) {

                    progressDialog.setMessage("Registrazione in corso...");
                    progressDialog.show();

                    createAccount();

                }

            }
        });
    }

    public void setupView() {

        spinnerTipoSuddivisione = findViewById(R.id.tipoSuddivisone_Registration2Activity);
        spinnerSuddivisione = findViewById(R.id.suddivisione_Registration2Activity);
        Email = findViewById(R.id.email_Registration2Activity);
        Password = findViewById(R.id.password_Registration2Actitivy);
        RipetiPassword = findViewById(R.id.ripetiPassword_Registration2Activity);
        //goBackToRegistration1 = findViewById(R.id.goBackToRegistration1_Registration2Activity);
        signUp = findViewById(R.id.signUp_Registration2Activity);

    }

    public void initTipoSuddivisione(AdapterView<?> adapterView) {

        if (adapterView.getSelectedItem().toString().equals("Matricola Pari o Dispari")) {
            adapterSuddivisione = ArrayAdapter.createFromResource(Registration2Activity.this, R.array.Suddivisione_Pari_o_Dispari,
                    android.R.layout.simple_spinner_item);
        } else if (adapterView.getSelectedItem().toString().equals("Resto della Matricola")) {
            adapterSuddivisione = ArrayAdapter.createFromResource(Registration2Activity.this, R.array.Suddivisione_Resto_della_Matricola,
                    android.R.layout.simple_spinner_item);
        } else if (adapterView.getSelectedItem().toString().equals("Cognome")) {
            adapterSuddivisione = ArrayAdapter.createFromResource(Registration2Activity.this, R.array.Suddivisione_Cognome,
                    android.R.layout.simple_spinner_item);
        }

        adapterSuddivisione.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSuddivisione.setAdapter(adapterSuddivisione);
        tipoSuddivisione = adapterView.getSelectedItem().toString().trim();

    }

    public boolean validator() {

        if (email.isEmpty()) {
            Email.setError("Il Campo email non puo' essere vuoto!");
            Email.requestFocus();
            return false;
        } else if (!emailValidator()) {
            Email.setError("L'email inserita non e' valida!");
            Email.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            Password.setError("Il campo password non puo' essere vuoto!");
            Password.requestFocus();
            return false;
        } else if (!passwordValidator()) {
            Password.setError("La password inserita non e' valida!\n" +
                    "La password deve essere di almeno 8 caratteri, " +
                    "e deve contenere:\nAlmeno una lettere Maiuscola\n" +
                    "Almeno una lettera minuscola\n Almeno un numero\n" +
                    "Almeno un carattere speciale");
            Password.requestFocus();
            return false;
        } else if (ripetiPassword.isEmpty()) {
            RipetiPassword.setError("Il campo ripeti password non puo' essere vuoto!");
            RipetiPassword.requestFocus();
            return false;
        } else if (comparePassword()) {
            RipetiPassword.setError("Le due password non coincidono!");
            RipetiPassword.requestFocus();
            return false;
        }

        fillUser();

        return true;
    }

    public boolean emailValidator() {

        Pattern pattern;
        Matcher matcher;
        final String Email_Pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        pattern = Pattern.compile(Email_Pattern);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }

    public boolean passwordValidator() {

        Pattern pattern;
        Matcher matcher;
        final String Password_Pattern =
                "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
        pattern = Pattern.compile(Password_Pattern);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public boolean comparePassword() {

        return !password.equals(ripetiPassword);

    }

    public void fillUser() {

        user.setTipoSuddivisione(tipoSuddivisione);
        user.setSuddivisione(suddivisione);
        user.setEmail(email);
        user.setPassword(password);

    }

    //Crea un account di tipo Firebase con le informazioni date dall'utente (email e password)
    private void createAccount() {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            user.setId(firebaseUser.getUid());
                            Log.d("TAG", "createUserWithEmail:success");
                            sendEmailVerification();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registration2Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void sendEmailVerification() {
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        uploadData();
                    } else {
                        Toast.makeText(Registration2Activity.this, "Email di verifica non inviata!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void uploadData() {

        hashMapUser = user.getHashMapUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Users").document(email)
                .set(hashMapUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                        Toast.makeText(Registration2Activity.this, "Registrazione avvenuta con successo, email di verifica inviata!", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Registration2Activity.this, LoginActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    /*

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Registration2Activity.this, RegistrationActivity.class);
        startActivity(intent);
    }

*/

}
