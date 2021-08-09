package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;

public class Activity_FinancingPlans extends AppCompatActivity {

    TextView MonthPrice,YearPrice, YearMonthPrice;
    float num;
    String StrPackage,StrPrice,DatabasePath;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_plans);

        MonthPrice = findViewById(R.id.PriceMonthly);
        YearPrice = findViewById(R.id.PriceYearly);
        YearMonthPrice = findViewById(R.id.Yearly2);

        StrPackage = getIntent().getStringExtra("PackageName");
        /*StrPrice = getIntent().getStringExtra("PackagePrice");*/

        getSupportActionBar().setTitle(StrPackage + " Package");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));

        DatabasePath = StrPackage.toLowerCase() + "Price";
        System.out.println(DatabasePath);
        Query query = FirebaseDatabase.getInstance().getReference("Package_Management");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    StrPrice = snapshot.child(DatabasePath).getValue(String.class);
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMinimumFractionDigits(2);
                    nf.setMaximumFractionDigits(2);

                    num = (Float.parseFloat(StrPrice)/12) + 10 ;
                    MonthPrice.setText("$" + nf.format(num));

                    YearPrice.setText("$" + StrPrice);

                    num = (Float.parseFloat(StrPrice)/12);
                    YearMonthPrice.setText("Billed Annually ($" + nf.format(num) + "/Month)");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}