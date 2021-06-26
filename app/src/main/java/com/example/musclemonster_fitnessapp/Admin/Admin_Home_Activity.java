package com.example.musclemonster_fitnessapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.musclemonster_fitnessapp.LoginSignUp.ActivityLogIn;
import com.example.musclemonster_fitnessapp.LoginSignUp.ActivitySignUp;
import com.example.musclemonster_fitnessapp.R;

public class Admin_Home_Activity extends AppCompatActivity {

    Button addTrainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

//        addTrainer = findViewById(R.id.btnAddTrainer);
//        addTrainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Admin_Home_Activity.this, Add_Trainer_Activity.class));
//            }
//        });
    }
}