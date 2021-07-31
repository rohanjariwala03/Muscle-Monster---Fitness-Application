package com.example.musclemonster_fitnessapp.Admin;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.musclemonster_fitnessapp.AdapterClasses.ViewPagerAdapter;
import com.example.musclemonster_fitnessapp.Admin.ProductsFragments.AFragment_Current_Prod;
import com.example.musclemonster_fitnessapp.Admin.ProductsFragments.AFragment_Purchased_Products;
import com.example.musclemonster_fitnessapp.Admin.ProductsFragments.AFragment_Sold_Products;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MyProducts_Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products_admin);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.AddFragment(new AFragment_Current_Prod(), "ON SALE");
        viewPagerAdapter.AddFragment(new AFragment_Purchased_Products(), "PURCHASED");
        viewPagerAdapter.AddFragment(new AFragment_Sold_Products(), "SOLD");

        viewPager.setAdapter(viewPagerAdapter);
    }
}