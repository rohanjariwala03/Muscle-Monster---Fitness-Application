package com.example.musclemonster_fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Product_Add_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add_home);

        getSupportActionBar().setTitle("Sell Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}