package com.example.musclemonster_fitnessapp.Trainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.musclemonster_fitnessapp.LoginSignUp.ActivityLogIn;
import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Trainer.Chat.Activity_Chat_User_List;
import com.google.firebase.auth.FirebaseAuth;

public class trainer_home extends AppCompatActivity {

    Button btnlo,btnUserlst;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_home);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.support_toolbar);

        firebaseAuth=FirebaseAuth.getInstance();
        btnlo=findViewById(R.id.btnLogoutTrainer);
        btnUserlst=findViewById(R.id.btnUserListTrainer);


        btnUserlst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(trainer_home.this, Activity_Chat_User_List.class));
            }
        });
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