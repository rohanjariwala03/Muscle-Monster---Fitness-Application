package com.example.musclemonster_fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.BottomBarFragments.Fragment_More;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Update_Profile extends AppCompatActivity {


    EditText mEmail,mContact,mLastName,mFirstName,mpassword, mHeight, mWeight;
    Button btn_update;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private Uri resultUri;

    private String userId, Fname,LName, phone,email,password, Height, Weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mEmail=findViewById(R.id.editemail);
        mContact =findViewById(R.id.editcontact);
        mLastName=findViewById(R.id.editlastname);
        mFirstName=findViewById(R.id.editfirstname);
        mHeight=findViewById(R.id.editHeight);
        mWeight=findViewById(R.id.editWeight);
        btn_update=findViewById(R.id.btn_UpdateInfo);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        getUserData();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  phone=mContact.getText().toString();
                Fname=mFirstName.getText().toString();
                LName=mLastName.getText().toString();
                email=mEmail.getText().toString();
                Height=mHeight.getText().toString();
                Weight=mWeight.getText().toString();*/

                /*if(!LName.equals(mLastName.getText().toString()) ||
                        !phone.equals(mContact.getText().toString()) ||
                        !Fname.equals(mFirstName.getText().toString()) ||
                        !Height.equals(mHeight.getText().toString()) ||
                        !Weight.equals(mWeight.getText().toString()) ) {

                    try {
                        mUserDatabase.child("firstName").setValue(Fname);
                        mUserDatabase.child("lastName").setValue(LName);
                        mUserDatabase.child("phoneNumber").setValue(phone);
                        mUserDatabase.child("height").setValue(Height);
                        mUserDatabase.child("weight").setValue(Weight);
                        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Profile Not Updated", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "No Changes Made", Toast.LENGTH_LONG).show();
*/


                /*Map userInfo = new HashMap();
                userInfo.put("firstName", Fname);
                userInfo.put("lastName", LName);
                userInfo.put("email", email);
                userInfo.put("phoneNumber", phone);
                userInfo.put("height", Height);
                userInfo.put("weight", Weight);

                mUserDatabase.updateChildren(userInfo);*/



                /*Log.i("Error",Fname.toString());
                Log.i("Error",LName.toString());
                Log.i("Error",userId.toString());*/


                if(isFirstNameChanged() || isLastNameUpdate() || isContactUpdate() || isEmailUpdate() || isHeightUpdate() || isWeightUpdate()){
                    Toast.makeText(getApplicationContext(),"Profile Updated", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"No Changes Made", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean isEmailUpdate() {
        if(!email.equals(mEmail.getText().toString())){
            mUserDatabase.child("email").setValue(mEmail.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isContactUpdate() {
        if(!phone.equals(mContact.getText().toString())){
            mUserDatabase.child("phoneNumber").setValue(mContact.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isLastNameUpdate() {
        if(!LName.equals(mLastName.getText().toString())){
            mUserDatabase.child("lastName").setValue(mLastName.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isHeightUpdate() {
        if(!Height.equals(mHeight.getText().toString())){
            mUserDatabase.child("height").setValue(mHeight.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isWeightUpdate() {
        if(!Weight.equals(mWeight.getText().toString())){
            mUserDatabase.child("weight").setValue(mWeight.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isFirstNameChanged() {
        if(!Fname.equals(mFirstName.getText().toString())){
            mUserDatabase.child("firstName").setValue(mFirstName.getText().toString());
            return true;
        }
        else{
            return false;
        }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
        }
    }
}