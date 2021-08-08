package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_activity_workout_history;
import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.AdapterClasses.ViewPagerAdapter;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.Fragment_CurrentProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.Fragment_PurchasedProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments.Fragment_SoldProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.WorkoutHistoryFragments.Fragment_Saved_Workouts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.WorkoutHistoryFragments.Fragment_Workout_History;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_History_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity_Workout_History extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        getSupportActionBar().setTitle("Workout History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.AddFragment(new Fragment_Workout_History(), "Workout History");
        viewPagerAdapter.AddFragment(new Fragment_Saved_Workouts(), "Saved Workouts");

        viewPager.setAdapter(viewPagerAdapter);
    }
}