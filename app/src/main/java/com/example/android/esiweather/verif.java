package com.example.android.esiweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class verif extends AppCompatActivity {
    FirebaseAuth fAuth;
    Button verif ;
  //  ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);



       // progressBar = findViewById(R.id.progressBarVerif);
       // progressBar.setVisibility(View.INVISIBLE);
        verif =findViewById(R.id.button2);
        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  progressBar.setVisibility(View.VISIBLE);
               // Log.d("f5", FirebaseAuth.getInstance().getCurrentUser().getEmail());
               // Log.d("f5", FirebaseAuth.getInstance().getCurrentUser().().);

                    startActivity(new Intent(getApplicationContext() , Login.class));
                    finish();




            }
        });


    }



}