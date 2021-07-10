package com.example.musclemonster_fitnessapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musclemonster_fitnessapp.Admin.ActivityViewTrainer;
import com.example.musclemonster_fitnessapp.Admin.ActivityViewUsers;
import com.example.musclemonster_fitnessapp.Admin.Add_Prod_Admin;
import com.example.musclemonster_fitnessapp.Admin.Add_Trainer_Activity;
import com.example.musclemonster_fitnessapp.Admin.AdminGymPackages;
import com.example.musclemonster_fitnessapp.Admin.Admin_Profile;
import com.example.musclemonster_fitnessapp.Admin.MyProducts_Admin;
import com.example.musclemonster_fitnessapp.Admin.View_Prod_Admin;
import com.example.musclemonster_fitnessapp.LoginSignUp.ActivityLogIn;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_Home_Activity extends AppCompatActivity {

    ImageButton LogOut;
    FirebaseAuth myAuth;
    Button BtnAddTrainer,BtnAddProducts,BtnViewProduct,btnViewTrainer,BtnMyProducts, BtnProfile, BtnViewUsers, btnGymPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.support_toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));

        myAuth = FirebaseAuth.getInstance();
        LogOut = (ImageButton) findViewById(R.id.btnLogout);

        BtnAddTrainer = (Button) findViewById(R.id.btnAddTrainer);
        BtnAddProducts = (Button) findViewById(R.id.btnSellProductsAdmin);
        BtnViewProduct = (Button) findViewById(R.id.btnBuyProductsAdmin);
        btnViewTrainer = (Button) findViewById(R.id.btnViewTrainer);
        BtnMyProducts = (Button) findViewById(R.id.btnMyProductsAdmin);
        BtnProfile = (Button) findViewById(R.id.btnMyProfileAdmin);
        BtnViewUsers = (Button) findViewById(R.id.btnViewUsers);
        btnGymPackage = (Button) findViewById(R.id.btnManageGymPackage);

        BtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home_Activity.this, Admin_Profile.class);
                startActivity(intent);
            }
        });

        BtnMyProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home_Activity.this, MyProducts_Admin.class);
                startActivity(intent);
            }
        });

        BtnAddProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home_Activity.this, Add_Prod_Admin.class);
                startActivity(intent);
            }
        });

        btnViewTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home_Activity.this, ActivityViewTrainer.class);
                startActivity(intent);
            }
        });

        BtnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home_Activity.this, ActivityViewUsers.class);
                startActivity(intent);
            }
        });

        BtnViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home_Activity.this, View_Prod_Admin.class);
                startActivity(intent);
            }
        });

        BtnAddTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home_Activity.this, Add_Trainer_Activity.class);
                startActivity(intent);
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAuth.signOut();
                finish();
                Intent intent = new Intent(Admin_Home_Activity.this , ActivityLogIn.class);
                startActivity(intent);
            }
        });

        btnGymPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home_Activity.this, AdminGymPackages.class);
                startActivity(intent);
            }
        });

    }
}