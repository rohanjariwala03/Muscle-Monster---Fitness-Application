package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Activity_Gym_Packages extends AppCompatActivity {

    Button golenButton, silverButton, platinumButton;
    TextView cbAccess,cbProgram,cbShower,cbPersonal,cbChair,cbClasses,cbBeds,cbGroupClass;
    TextView cbAccessGold,cbProgramGold,cbShowerGold,cbPersonalGold,cbChairGold,cbClassesGold,cbBedsGold,cbGroupClassGold;
    TextView cbAccessPlatinum,cbProgramPlatinum,cbShowerPlatinum,cbPersonalPlatinum,cbChairPlatinum,cbClassesPlatinum,cbBedsPlatinum,cbGroupClassPlatinum;
    TextView etPrice,etPriceGold,etPricePlatinum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_packages);

        getSupportActionBar().setTitle("Gym Packages");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));

        golenButton = findViewById(R.id.goldButton);
        silverButton = findViewById(R.id.silverButton);
        platinumButton = findViewById(R.id.platinumButton);

        etPrice=findViewById(R.id.txtSilverPrice);
        etPriceGold=findViewById(R.id.txtGoldPrice);
        etPricePlatinum=findViewById(R.id.txtPlatinumPrice);

        cbAccess=findViewById(R.id.txtAccessSilver);
        cbAccessGold=findViewById(R.id.txtAccessGold);
        cbAccessPlatinum=findViewById(R.id.txtAccessPlatinum);

        cbProgram=findViewById(R.id.txtProgramSilver);
        cbProgramGold=findViewById(R.id.txtProgramGold);
        cbProgramPlatinum=findViewById(R.id.txtProgramPlatinum);

        cbShower=findViewById(R.id.txtShowerSilver);
        cbShowerGold=findViewById(R.id.txtShowerGold);
        cbShowerPlatinum=findViewById(R.id.txtShowerPlatinum);

        cbPersonal=findViewById(R.id.txtPersonalSilver);
        cbPersonalGold=findViewById(R.id.txtPersonalGold);
        cbPersonalPlatinum=findViewById(R.id.txtPersonalPlatinum);

        cbChair=findViewById(R.id.txtChairSilver);
        cbChairGold=findViewById(R.id.txtChairGold);
        cbChairPlatinum=findViewById(R.id.txtChairPlatinum);

        cbClasses=findViewById(R.id.txtClassesSilver);
        cbClassesGold=findViewById(R.id.txtClassesGold);
        cbClassesPlatinum=findViewById(R.id.txtClassesPlatinum);

        cbBeds=findViewById(R.id.txtBedsSilver);
        cbBedsGold=findViewById(R.id.txtBedsGold);
        cbBedsPlatinum=findViewById(R.id.txtBedsPlatinum);

        cbGroupClass=findViewById(R.id.txtGroupSilver);
        cbGroupClassGold=findViewById(R.id.txtGroupGold);
        cbGroupClassPlatinum=findViewById(R.id.txtGroupPlatinum);

        getPackageData();

    }

    private void getPackageData() {
        Query query= FirebaseDatabase.getInstance().getReference("Package_Management");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if(snapshot.child("cbAccess").getValue(String.class).equals("true")) {
                        cbAccess.setText("\u2713");
                        cbAccess.setTextColor(0xFF56F403);
                    }else{
                        cbAccess.setText("X");
                        cbAccess.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbProgram").getValue(String.class).equals("true")) {
                        cbProgram.setText("\u2713");
                        cbProgram.setTextColor(0xFF56F403);
                    }else{
                        cbProgram.setText("X");
                        cbProgram.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbShower").getValue(String.class).equals("true")) {
                        cbShower.setText("\u2713");
                        cbShower.setTextColor(0xFF56F403);
                    }else{
                        cbShower.setText("X");
                        cbShower.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbPersonal").getValue(String.class).equals("true")) {
                        cbPersonal.setText("\u2713");
                        cbPersonal.setTextColor(0xFF56F403);
                    }else{
                        cbPersonal.setText("X");
                        cbPersonal.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbChair").getValue(String.class).equals("true")) {
                        cbChair.setText("\u2713");
                        cbChair.setTextColor(0xFF56F403);
                    }else{
                        cbChair.setText("X");
                        cbChair.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbClasses").getValue(String.class).equals("true")) {
                        cbClasses.setText("\u2713");
                        cbClasses.setTextColor(0xFF56F403);
                    }else{
                        cbClasses.setText("X");
                        cbClasses.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbBeds").getValue(String.class).equals("true")) {
                        cbBeds.setText("\u2713");
                        cbBeds.setTextColor(0xFF56F403);
                    }else{
                        cbBeds.setText("X");
                        cbBeds.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbGroupClass").getValue(String.class).equals("true")) {
                        cbGroupClass.setText("\u2713");
                        cbGroupClass.setTextColor(0xFF56F403);
                    }else{
                        cbGroupClass.setText("X");
                        cbGroupClass.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbAccessGold").getValue(String.class).equals("true")) {
                        cbAccessGold.setText("\u2713");
                        cbAccessGold.setTextColor(0xFF56F403);
                    }else{
                        cbAccessGold.setText("X");
                        cbAccessGold.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbProgramGold").getValue(String.class).equals("true")) {
                        cbProgramGold.setText("\u2713");
                        cbProgramGold.setTextColor(0xFF56F403);
                    }else{
                        cbProgramGold.setText("X");
                        cbProgramGold.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbShowerGold").getValue(String.class).equals("true")) {
                        cbShowerGold.setText("\u2713");
                        cbShowerGold.setTextColor(0xFF56F403);
                    }else{
                        cbShowerGold.setText("X");
                        cbShowerGold.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbPersonalGold").getValue(String.class).equals("true")) {
                        cbPersonalGold.setText("\u2713");
                        cbPersonalGold.setTextColor(0xFF56F403);
                    }else{
                        cbPersonalGold.setText("X");
                        cbPersonalGold.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbChairGold").getValue(String.class).equals("true")) {
                        cbChairGold.setText("\u2713");
                        cbChairGold.setTextColor(0xFF56F403);
                    }else{
                        cbChairGold.setText("X");
                        cbChairGold.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbClassesGold").getValue(String.class).equals("true")) {
                        cbClassesGold.setText("\u2713");
                        cbClassesGold.setTextColor(0xFF56F403);
                    }else{
                        cbClassesGold.setText("X");
                        cbClassesGold.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbBedsGold").getValue(String.class).equals("true")) {
                        cbBedsGold.setText("\u2713");
                        cbBedsGold.setTextColor(0xFF56F403);
                    }else{
                        cbBedsGold.setText("X");
                        cbBedsGold.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbGroupClassGold").getValue(String.class).equals("true")) {
                        cbGroupClassGold.setText("\u2713");
                        cbGroupClassGold.setTextColor(0xFF56F403);
                    }else{
                        cbGroupClassGold.setText("X");
                        cbGroupClassGold.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbAccessPlatinum").getValue(String.class).equals("true")) {
                        cbAccessPlatinum.setText("\u2713");
                        cbAccessPlatinum.setTextColor(0xFF56F403);
                    }else{
                        cbAccessPlatinum.setText("X");
                        cbAccessPlatinum.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbProgramPlatinum").getValue(String.class).equals("true")) {
                        cbProgramPlatinum.setText("\u2713");
                        cbProgramPlatinum.setTextColor(0xFF56F403);
                    }else{
                        cbProgramPlatinum.setText("X");
                        cbProgramPlatinum.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbShowerPlatinum").getValue(String.class).equals("true")) {
                        cbShowerPlatinum.setText("\u2713");
                        cbShowerPlatinum.setTextColor(0xFF56F403);
                    }else{
                        cbShowerPlatinum.setText("X");
                        cbShowerPlatinum.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbPersonalPlatinum").getValue(String.class).equals("true")) {
                        cbPersonalPlatinum.setText("\u2713");
                        cbPersonalPlatinum.setTextColor(0xFF56F403);
                    }else{
                        cbPersonalPlatinum.setText("X");
                        cbPersonalPlatinum.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbChairPlatinum").getValue(String.class).equals("true")) {
                        cbChairPlatinum.setText("\u2713");
                        cbChairPlatinum.setTextColor(0xFF56F403);
                    }else{
                        cbChairPlatinum.setText("X");
                        cbChairPlatinum.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbClassesPlatinum").getValue(String.class).equals("true")) {
                        cbClassesPlatinum.setText("\u2713");
                        cbClassesPlatinum.setTextColor(0xFF56F403);
                    }else{
                        cbClassesPlatinum.setText("X");
                        cbClassesPlatinum.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbBedsPlatinum").getValue(String.class).equals("true")) {
                        cbBedsPlatinum.setText("\u2713");
                        cbBedsPlatinum.setTextColor(0xFF56F403);
                    }else{
                        cbBedsPlatinum.setText("X");
                        cbBedsPlatinum.setTextColor(0xFFFF0000);
                    }
                    if(snapshot.child("cbGroupClassPlatinum").getValue(String.class).equals("true")) {
                        cbGroupClassPlatinum.setText("\u2713");
                        cbGroupClassPlatinum.setTextColor(0xFF56F403);
                    }else{
                        cbGroupClassPlatinum.setText("X");
                        cbGroupClassPlatinum.setTextColor(0xFFFF0000);
                    }
                    etPrice.setText(snapshot.child("silverPrice").getValue(String.class));
                    etPriceGold.setText(snapshot.child("goldPrice").getValue(String.class));
                    etPricePlatinum.setText(snapshot.child("platinumPrice").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

    }
}