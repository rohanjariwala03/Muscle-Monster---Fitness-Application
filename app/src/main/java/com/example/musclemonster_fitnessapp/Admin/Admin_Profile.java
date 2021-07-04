package com.example.musclemonster_fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Admin_Profile extends AppCompatActivity {

    EditText mEmail,mContact,mLastName,mFirstName;
    Button btn_update;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private Uri resultUri;

    private String AdminId, Fname,LName, phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        mEmail=findViewById(R.id.editemail);
        mContact =findViewById(R.id.editcontact);
        mLastName=findViewById(R.id.editlastname);
        mFirstName=findViewById(R.id.editfirstname);
        btn_update=findViewById(R.id.btn_UpdateInfo);

        mAuth = FirebaseAuth.getInstance();
        AdminId = mAuth.getCurrentUser().getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Admin").child(AdminId);

        getUserData();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isFirstNameChanged() || isLastNameUpdate() || isContactUpdate() || isEmailUpdate()){
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
                    } if (map.get("lastName") != null) {
                        LName = map.get("lastName").toString();
                        mLastName.setText(LName);
                    } if (map.get("email") != null) {
                        email= map.get("email").toString();
                        mEmail.setText(email);
                    }if (map.get("phoneNumber") != null) {
                        phone= map.get("phoneNumber").toString();
                        mContact.setText(phone);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}