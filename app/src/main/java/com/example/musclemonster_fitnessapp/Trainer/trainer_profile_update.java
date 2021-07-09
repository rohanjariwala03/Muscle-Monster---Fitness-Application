package com.example.musclemonster_fitnessapp.Trainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.POJOClasses.TrainerPojo;
import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Update_Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class trainer_profile_update extends AppCompatActivity {

    EditText email, contact, lastName, firstName, experience, weight, height, age;
    Button BtnUpload, deleteButton, uploadImageCloud;
    ImageView showImage;
    String userId, sEmail, sContact, sLastName, sFirstName, sExperience, sWeight, sHeight, sAge, sImage;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile_update);


        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Trainer").child(userId);

        email = findViewById(R.id.EditTrainerEmailId);
        contact = findViewById(R.id.EditTrainerContact);
        lastName = findViewById(R.id.EditTrainerLastName);
        firstName = findViewById(R.id.EditTrainerFirstName);
        experience = findViewById(R.id.EditTrainerExperience);
        weight = findViewById(R.id.EditTrainerWeight);
        height = findViewById(R.id.EditTrainerHeight);
        age = findViewById(R.id.EditTrainerAge);

        showImage = findViewById(R.id.ShowImageView);

        BtnUpload = findViewById(R.id.BtnAdTrainerSubmit);
        deleteButton = findViewById(R.id.BtnTrainerDeleteImage);
        uploadImageCloud = findViewById(R.id.BtnTrainerUploadImage);

        getTrainerData();


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sImage = "";
            }
        });

        uploadImageCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
                //startActivityForResult(Intent.createChooser(intent, "Please Select Image"),1);
            }
        });

        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sLastName.equals(lastName.getText().toString()) ||
                        !sContact.equals(contact.getText().toString()) ||
                        !sFirstName.equals(firstName.getText().toString()) ||
                        !sEmail.equals(email.getText().toString()) ||
                        !sExperience.equals(experience.getText().toString()) ||
                        !sAge.equals(age.getText().toString()) ||
                        !sHeight.equals(height.getText().toString()) ||
                        !sWeight.equals(weight.getText().toString())) {

                    try {

                        sFirstName = firstName.getText().toString();
                        sLastName = lastName.getText().toString();
                        sContact = contact.getText().toString();
                        sEmail = email.getText().toString();
                        sExperience = experience.getText().toString();
                        sAge = age.getText().toString();
                        sHeight = height.getText().toString();
                        sWeight = weight.getText().toString();

                        mUserDatabase.child("firstname").setValue(sFirstName);
                        mUserDatabase.child("lastName").setValue(sLastName);
                        mUserDatabase.child("email").setValue(sEmail);
                        mUserDatabase.child("experience").setValue(sExperience);
                        mUserDatabase.child("contact").setValue(sContact);
                        mUserDatabase.child("height").setValue(sHeight);
                        mUserDatabase.child("weight").setValue(sWeight);
                        mUserDatabase.child("age").setValue(sAge);
                        Toast.makeText(trainer_profile_update.this, "Profile Updated", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(trainer_profile_update.this, "Profile Not Updated", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(trainer_profile_update.this, "No Changes Made", Toast.LENGTH_LONG).show();
            }
            });

    }


    public void getTrainerData()
    {
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {

                    //TrainerPojo trainerPojo = new TrainerPojo();
                    sEmail = snapshot.child("email").getValue(String.class);
                    email.setText(sEmail);
                    sContact = snapshot.child("contact").getValue(String.class);
                    contact.setText(sContact);
                    sLastName = snapshot.child("lastName").getValue(String.class);
                    lastName.setText(sLastName);
                    sFirstName = snapshot.child("firstname").getValue(String.class);
                    firstName.setText(sFirstName);
                    sExperience = snapshot.child("experience").getValue(String.class);
                    experience.setText(sExperience);
                    sWeight = snapshot.child("weight").getValue(String.class);
                    weight.setText(sWeight);
                    sHeight = snapshot.child("height").getValue(String.class);
                    height.setText(sHeight);
                    sAge = snapshot.child("age").getValue(String.class);
                    age.setText(sAge);
                    sImage = snapshot.child("imgUri").getValue(String.class);
                    Glide.with(trainer_profile_update.this)
                            .load(sImage)
                            .into(showImage);

                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}