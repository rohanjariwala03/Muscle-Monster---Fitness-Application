package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_MyProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.Fragment_CurrentProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.Fragment_PurchasedProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.Fragment_SoldProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.ViewPagerAdapter;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyProducts_User extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products_user);
        /*getSupportActionBar().setTitle("My Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

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