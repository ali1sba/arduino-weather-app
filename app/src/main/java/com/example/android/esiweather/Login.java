package com.example.android.esiweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {
    EditText mEmail , mPassward;
    Button singin , login;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.editTextTextEmailAddresslogin);
        mPassward = findViewById(R.id.editTextTextPasswordlogin);
        singin = findViewById(R.id.signup_loginact);
        login = findViewById(R.id.login_loginact);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarlogin);
        progressBar.setVisibility(View.INVISIBLE);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String passward = mPassward.getText().toString();

                if (TextUtils.isEmpty(email) ) {

                    mEmail.setError("email is required");
                    return;
                }

                if (TextUtils.isEmpty(passward) ) {

                    mPassward.setError("password is required");
                    return;

                }

                progressBar.setVisibility(View.VISIBLE);




                        progressBar.setVisibility(View.VISIBLE);
                        fAuth.signInWithEmailAndPassword(email,passward).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if (fAuth.getCurrentUser().isEmailVerified()){
                                       // Log.d("fin", "signup done");
                                        progressBar.setVisibility(View.INVISIBLE);
                                        //Toast.makeText(Login.this, "SignInWithEmail:success.",
                                         //       Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext() , MainActivity.class));
                                    }else{
                                       // progressBar.setVisibility(View.INVISIBLE);
                                      //  Toast.makeText(Login.this, "verification failed",
                                       //         Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext() , verif.class));
                                        finish();
                                    }

                                }else {
                                    Log.d("fin", "signup error");
                                    progressBar.setVisibility(View.INVISIBLE);
                                 //   Toast.makeText(Login.this, "Authentication failed.  " + task.getException(),
                                   //         Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                       /* fAuth.signInWithEmailAndPassword(email,passward).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("fin", "signup done");
                                    Toast.makeText(Login.this, "SignInWithEmail:succes.",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext() , MainActivity.class));
                                }else {
                                    Log.d("fin", "signup error");
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/

                    }

                });
                
                
                




        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , Signup.class));
            }
        });

    }
}