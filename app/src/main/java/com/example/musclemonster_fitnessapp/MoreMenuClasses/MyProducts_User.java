package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.musclemonster_fitnessapp.AdapterClasses.ViewPagerAdapter;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.Fragment_CurrentProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.Fragment_PurchasedProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.Fragment_SoldProducts;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.material.tabs.TabLayout;

public class MyProducts_User extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products_user);
        getSupportActionBar().setTitle("My Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.AddFragment(new Fragment_CurrentProducts(), "ON SALE");
        viewPagerAdapter.AddFragment(new Fragment_PurchasedProducts(), "PURCHASED");
        viewPagerAdapter.AddFragment(new Fragment_SoldProducts(), "SOLD");

        viewPager.setAdapter(viewPagerAdapter);
    }


}