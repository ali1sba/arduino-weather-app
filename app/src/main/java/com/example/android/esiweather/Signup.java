package com.example.android.esiweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText mEmail , mPassward , mUsername , mphone ;
    Button singup , login;
    FirebaseAuth fAuth;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        mEmail = findViewById(R.id.editTextTextEmailAddresssignup);
        mPassward = findViewById(R.id.editTextTextPasswordsignup);
        mUsername = findViewById(R.id.editTextTextusernamesignup);
        mphone = findViewById(R.id.editTextTextphonenumbersignup);
        singup = findViewById(R.id.signup_signupact);
        login = findViewById(R.id.login_signupact);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarsignup);
        progressBar.setVisibility(View.INVISIBLE);

        if (fAuth.getCurrentUser() != null) {
            if (fAuth.getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } //else {
              //  startActivity(new Intent(getApplicationContext(), verif.class));
              //  finish();
           // }

        }

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                String passward = mPassward.getText().toString().trim();
                final String phone = mphone.getText().toString().trim();
                final String username = mUsername.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("email is required");
                    return;
                }

                if (TextUtils.isEmpty(passward)) {

                    mPassward.setError("password is required");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,passward).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("fin", "signup done");
                            Toast.makeText(Signup.this, "SignUpWithEmail:succes.",
                                    Toast.LENGTH_LONG).show();
                            DatabaseReference userRef = database.getReference().child("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() );
                            userRef.child("email").setValue(email);
                            userRef.child("phone_number").setValue(phone);
                            userRef.child("username").setValue(username);
                            DatabaseReference notifRef = database.getReference().child("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() +"/notif");
                            notifRef.child("hum_checkbox").setValue(0);
                            notifRef.child("humidity_valeur_max").setValue("0");
                            notifRef.child("humidity_valeur_min").setValue("0");
                            notifRef.child("press_checkbox").setValue(0);
                            notifRef.child("pressure_valeur_max").setValue("0");
                            notifRef.child("pressure_valeur_min").setValue("0");
                            notifRef.child("temp_checkbox").setValue(0);
                            notifRef.child("temperature_valeur_max").setValue("0");
                            notifRef.child("temperature_valeur_min").setValue("0");
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){


                                       // final DatabaseReference actionRef = database.getReference().child("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() +"/actions");
                                       // actionRef.child("action1").child("name").setValue("test action 1");
                                      //  actionRef.child("action1").child("state").setValue("off");
                                      //  actionRef.child("action2").child("name").setValue("test action 2");
                                       // actionRef.child("action2").child("state").setValue("off");
                                      //  actionRef.child("action3").child("name").setValue("test action 3");
                                      //  actionRef.child("action3").child("state").setValue("off");
                                        progressBar.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(getApplicationContext() , verif.class));
                                        finish();
                                    }
                                }
                            });

                        }else {
                            Log.d("fin", "signup error");
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Signup.this, "Authentication failed.  " + task.getException(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , Login.class));
            }
        });



    }
}