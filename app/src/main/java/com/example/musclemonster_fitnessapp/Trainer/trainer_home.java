package com.example.musclemonster_fitnessapp.Trainer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musclemonster_fitnessapp.LoginSignUp.ActivityLogIn;
import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Trainer.Chat.Activity_Chat_User_List;
import com.google.firebase.auth.FirebaseAuth;

public class trainer_home extends AppCompatActivity {

    Button chatbtn, btnviewprofile,btnRatings;
    ImageButton btnhome, btnlogout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_home);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.support_toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E8CBC0")));

        firebaseAuth=FirebaseAuth.getInstance();
        btnlogout =findViewById(R.id.btnLogout);
        chatbtn = findViewById(R.id.btnchat);
        btnhome = findViewById(R.id.btnhomepage);

        btnRatings = findViewById(R.id.btnViewRatings);
        btnviewprofile = findViewById(R.id.btnviewprofile);

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnviewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(trainer_home.this, trainer_profile_update.class));
            }
        });

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(trainer_home.this, Activity_Chat_User_List.class));
            }
        });



        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(trainer_home.this, ActivityLogIn.class));

            }
        });

        btnRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(trainer_home.this, Activity_View_Ratings.class));
            }
        });
    }
}