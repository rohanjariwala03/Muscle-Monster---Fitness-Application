package com.example.musclemonster_fitnessapp.Trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.musclemonster_fitnessapp.LoginSignUp.ActivityLogIn;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class trainer_home extends AppCompatActivity {

    Button btnlo;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_home);

        firebaseAuth=FirebaseAuth.getInstance();
        btnlo=findViewById(R.id.btnLogoutTrainer);
        
        btnlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(trainer_home.this, ActivityLogIn.class));

            }
        });
    }
}