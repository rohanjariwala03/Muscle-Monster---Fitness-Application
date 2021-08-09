package com.example.musclemonster_fitnessapp.Admin.GymPackage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Admin_Activity_Manage_Gym_Package extends AppCompatActivity {

    Button btnUpdateData;
    CheckBox cbAccess,cbProgram,cbShower,cbPersonal,cbChair,cbClasses,cbBeds,cbGroupClass;
    CheckBox cbAccessGold,cbProgramGold,cbShowerGold,cbPersonalGold,cbChairGold,cbClassesGold,cbBedsGold,cbGroupClassGold;
    CheckBox cbAccessPlatinum,cbProgramPlatinum,cbShowerPlatinum,cbPersonalPlatinum,cbChairPlatinum,cbClassesPlatinum,cbBedsPlatinum,cbGroupClassPlatinum;
    EditText etPrice,etPriceGold,etPricePlatinum;

    String s1="false",s2="false",s3="false",s4="false",s5="false",s6="false",s7="false",s8="false",s9="false"
            ,s10="false",s11="false",s12="false",s13="false",s14="false",s15="false",s16="false",s17="false"
            ,s18="false",s19="false",s20="false",s21="false",s22="false",s23="false",s24="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_gym_package);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        btnUpdateData=findViewById(R.id.btnUpdatePackageData);

        etPrice=findViewById(R.id.SilverPriceText);
        etPriceGold=findViewById(R.id.GoldPriceText);
        etPricePlatinum=findViewById(R.id.PlatinumPriceText);

        cbAccess=findViewById(R.id.SilverAccessText);
        cbAccessGold=findViewById(R.id.GoldAccessText);
        cbAccessPlatinum=findViewById(R.id.PlatinumAccessText);

        cbProgram=findViewById(R.id.SilverTrainingProgramsText);
        cbProgramGold=findViewById(R.id.GoldTrainingProgramsText);
        cbProgramPlatinum=findViewById(R.id.PlatinumTrainingProgramsText);

        cbShower=findViewById(R.id.SilverShowerText);
        cbShowerGold=findViewById(R.id.GoldShowerText);
        cbShowerPlatinum=findViewById(R.id.PlatinumShowerText);

        cbPersonal=findViewById(R.id.SilverpersonalTrainerText);
        cbPersonalGold=findViewById(R.id.GoldpersonalTrainerText);
        cbPersonalPlatinum=findViewById(R.id.PlatinumpersonalTrainerText);

        cbChair=findViewById(R.id.SilverMassageChairsText);
        cbChairGold=findViewById(R.id.GoldMassageChairsText);
        cbChairPlatinum=findViewById(R.id.PlatinumMassageChairsText);

        cbClasses=findViewById(R.id.SilverVirtualClassesText);
        cbClassesGold=findViewById(R.id.GoldVirtualClassesText);
        cbClassesPlatinum=findViewById(R.id.PlatinumVirtualClassesText);

        cbBeds=findViewById(R.id.SilverHydroMassageBedsText);
        cbBedsGold=findViewById(R.id.GoldHydroMassageBedsText);
        cbBedsPlatinum=findViewById(R.id.PlatinumHydroMassageBedsText);

        cbGroupClass=findViewById(R.id.SilverLiveGroupClassesText);
        cbGroupClassGold=findViewById(R.id.GoldLiveGroupClassesText);
        cbGroupClassPlatinum=findViewById(R.id.PlatinumLiveGroupClassesText);

        getPackageData();

        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbAccess.isChecked())
                    s1="true";
                if(cbProgram.isChecked())
                    s2="true";
                if(cbShower.isChecked())
                    s3="true";
                if(cbPersonal.isChecked())
                    s4="true";
                if(cbChair.isChecked())
                    s5="true";
                if(cbClasses.isChecked())
                    s6="true";
                if(cbBeds.isChecked())
                    s7="true";
                if(cbGroupClass.isChecked())
                    s8="true";
                if(cbAccessGold.isChecked())
                    s9="true";
                if(cbProgramGold.isChecked())
                    s10="true";
                if(cbShowerGold.isChecked())
                    s11="true";
                if(cbPersonalGold.isChecked())
                    s12="true";
                if(cbChairGold.isChecked())
                    s13="true";
                if(cbClassesGold.isChecked())
                    s14="true";
                if(cbBedsGold.isChecked())
                    s15="true";
                if(cbGroupClassGold.isChecked())
                    s16="true";
                if(cbAccessPlatinum.isChecked())
                    s17="true";
                if(cbProgramPlatinum.isChecked())
                    s18="true";
                if(cbShowerPlatinum.isChecked())
                    s19="true";
                if(cbPersonalPlatinum.isChecked())
                    s20="true";
                if(cbChairPlatinum.isChecked())
                    s21="true";
                if(cbClassesPlatinum.isChecked())
                    s22="true";
                if(cbBedsPlatinum.isChecked())
                    s23="true";
                if(cbGroupClassPlatinum.isChecked())
                    s24="true";


                Pojo_Admin_Activity_Manage_Gym_Package obj=new Pojo_Admin_Activity_Manage_Gym_Package(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,
                        s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24, etPrice.getText().toString(),
                        etPriceGold.getText().toString(),etPricePlatinum.getText().toString());

                FirebaseDatabase.getInstance().getReference("Package_Management").setValue(obj)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @SuppressLint("UseCompatLoadingForDrawables")
                            @Override
                            public void onSuccess(Void unused) {
                                //Data successfully Saved
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        //Task have some errors
                    }
                });
            }
        });
    }

    private void getPackageData() {
        Query query=FirebaseDatabase.getInstance().getReference("Package_Management");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if(snapshot.child("cbAccess").getValue(String.class).equals("true"))
                        cbAccess.setChecked(true);
                    if(snapshot.child("cbProgram").getValue(String.class).equals("true"))
                        cbProgram.setChecked(true);
                    if(snapshot.child("cbShower").getValue(String.class).equals("true"))
                        cbShower.setChecked(true);
                    if(snapshot.child("cbPersonal").getValue(String.class).equals("true"))
                        cbPersonal.setChecked(true);
                    if(snapshot.child("cbChair").getValue(String.class).equals("true"))
                        cbChair.setChecked(true);
                    if(snapshot.child("cbClasses").getValue(String.class).equals("true"))
                        cbClasses.setChecked(true);
                    if(snapshot.child("cbBeds").getValue(String.class).equals("true"))
                        cbBeds.setChecked(true);
                    if(snapshot.child("cbGroupClass").getValue(String.class).equals("true"))
                        cbGroupClass.setChecked(true);
                    if(snapshot.child("cbAccessGold").getValue(String.class).equals("true"))
                        cbAccessGold.setChecked(true);
                    if(snapshot.child("cbProgramGold").getValue(String.class).equals("true"))
                        cbProgramGold.setChecked(true);
                    if(snapshot.child("cbShowerGold").getValue(String.class).equals("true"))
                        cbShowerGold.setChecked(true);
                    if(snapshot.child("cbPersonalGold").getValue(String.class).equals("true"))
                        cbPersonalGold.setChecked(true);
                    if(snapshot.child("cbChairGold").getValue(String.class).equals("true"))
                        cbChairGold.setChecked(true);
                    if(snapshot.child("cbClassesGold").getValue(String.class).equals("true"))
                        cbClassesGold.setChecked(true);
                    if(snapshot.child("cbBedsGold").getValue(String.class).equals("true"))
                        cbBedsGold.setChecked(true);
                    if(snapshot.child("cbGroupClassGold").getValue(String.class).equals("true"))
                        cbGroupClassGold.setChecked(true);
                    if(snapshot.child("cbAccessPlatinum").getValue(String.class).equals("true"))
                        cbAccessPlatinum.setChecked(true);
                    if(snapshot.child("cbProgramPlatinum").getValue(String.class).equals("true"))
                        cbProgramPlatinum.setChecked(true);
                    if(snapshot.child("cbShowerPlatinum").getValue(String.class).equals("true"))
                        cbShowerPlatinum.setChecked(true);
                    if(snapshot.child("cbPersonalPlatinum").getValue(String.class).equals("true"))
                        cbPersonalPlatinum.setChecked(true);
                    if(snapshot.child("cbChairPlatinum").getValue(String.class).equals("true"))
                        cbChairPlatinum.setChecked(true);
                    if(snapshot.child("cbClassesPlatinum").getValue(String.class).equals("true"))
                        cbClassesPlatinum.setChecked(true);
                    if(snapshot.child("cbBedsPlatinum").getValue(String.class).equals("true"))
                        cbBedsPlatinum.setChecked(true);
                    if(snapshot.child("cbGroupClassPlatinum").getValue(String.class).equals("true"))
                        cbGroupClassPlatinum.setChecked(true);

                    etPrice.setText(snapshot.child("silverPrice").getValue(String.class));
                    etPriceGold.setText(snapshot.child("goldPrice").getValue(String.class));
                    etPricePlatinum.setText(snapshot.child("platinumPrice").getValue(String.class));
                    Toast.makeText(getApplicationContext(),"Package Successfully Updated",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }
}