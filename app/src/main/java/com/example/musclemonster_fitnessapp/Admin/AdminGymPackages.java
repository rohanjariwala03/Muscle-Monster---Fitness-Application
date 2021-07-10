package com.example.musclemonster_fitnessapp.Admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.musclemonster_fitnessapp.R;

public class AdminGymPackages extends AppCompatActivity {

    Button golenButton, silverButton, platinumButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_gym_packages);

        getSupportActionBar().setTitle("Gym Packages");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));

        golenButton = findViewById(R.id.goldButton);
        silverButton = findViewById(R.id.silverButton);
        platinumButton = findViewById(R.id.platinumButton);


        

    }
}