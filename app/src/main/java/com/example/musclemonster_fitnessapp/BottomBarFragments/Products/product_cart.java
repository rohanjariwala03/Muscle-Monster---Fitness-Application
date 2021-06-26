package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.musclemonster_fitnessapp.R;

public class product_cart extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);



        Log.i("Cart",getIntent().getStringExtra("ItemName"));
        /*ItemPrice = getIntent().getStringExtra("ItemPrice");
        ItemDesc = getIntent().getStringExtra("ItemDesc");
         ItemKey = getIntent().getStringExtra("ItemKey");
        ItemCat = getIntent().getStringExtra("ItemCat");
        ItemImageUri = getIntent().getStringExtra("ItemImageUri");
        ItemWeight = getIntent().getStringExtra("ItemWeight");*/
    }
}