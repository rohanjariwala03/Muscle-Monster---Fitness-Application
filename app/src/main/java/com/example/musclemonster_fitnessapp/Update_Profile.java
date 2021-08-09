package com.example.musclemonster_fitnessapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class Update_Profile extends AppCompatActivity {


    EditText mEmail,mContact,mLastName,mFirstName,mHeight, mWeight;
    Button btn_update;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;


    private String userId, Fname,LName, phone,email,Height, Weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEmail=findViewById(R.id.editemail);
        mContact =findViewById(R.id.editcontact);
        mLastName=findViewById(R.id.editlastname);
        mFirstName=findViewById(R.id.editfirstname);
        mHeight=findViewById(R.id.editHeight);
        mWeight=findViewById(R.id.editWeight);
        btn_update=findViewById(R.id.btn_UpdateInfo);

        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        getUserData();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!LName.equals(mLastName.getText().toString()) ||
                        !phone.equals(mContact.getText().toString()) ||
                        !Fname.equals(mFirstName.getText().toString()) ||
                        !Height.equals(mHeight.getText().toString()) ||
                        !Weight.equals(mWeight.getText().toString()) ) {

                    try {
                        phone=mContact.getText().toString();
                        Fname=mFirstName.getText().toString();
                        LName=mLastName.getText().toString();
                        email=mEmail.getText().toString();
                        Height=mHeight.getText().toString();
                        Weight=mWeight.getText().toString();

                        mUserDatabase.child("firstName").setValue(Fname);
                        mUserDatabase.child("lastName").setValue(LName);
                        mUserDatabase.child("phoneNumber").setValue(phone);
                        mUserDatabase.child("height").setValue(Height);
                        mUserDatabase.child("weight").setValue(Weight);
                        Toast.makeText(Update_Profile.this, "Profile Updated", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Update_Profile.this, "Profile Not Updated", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(Update_Profile.this, "No Changes Made", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void getUserData(){
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                    if (map.get("firstName") != null) {
                        Fname = map.get("firstName").toString();
                        mFirstName.setText(Fname);
                       // mLabelName.setText(Fname);
                    } if (map.get("lastName") != null) {
                        LName = map.get("lastName").toString();
                        mLastName.setText(LName);
                       // mLabelName.setText(Fname);
                    } if (map.get("email") != null) {
                         email= map.get("email").toString();
                        mEmail.setText(email);
                       // mLabelName.setText(Fname);
                    }if (map.get("phoneNumber") != null) {
                         phone= map.get("phoneNumber").toString();
                        mContact.setText(phone);
                       // mLabelName.setText(Fname);
                    }

                    if (map.get("height") != null) {
                        Height= map.get("height").toString();
                        mHeight.setText(Height);
                    }else
                        mHeight.setHint("Enter Height");

                    if (map.get("weight") != null) {
                        Weight= map.get("weight").toString();
                        mWeight.setText(Weight);
                    }else
                        mWeight.setHint("Enter Weight");
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}