package com.example.musclemonster_fitnessapp.Trainer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class trainer_profile_update extends AppCompatActivity {

    EditText email, contact, lastName, firstName, experience, weight, height, age;
    Button BtnUpload, uploadImageCloud;
    ImageView showImage;
    String userId, sEmail, sContact, sLastName, sFirstName, sExperience, sWeight, sHeight, sAge, sImage;
    String FLAG = "NOT CLICKED";

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile_update);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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
        uploadImageCloud = findViewById(R.id.BtnTrainerUploadImage);

        getTrainerData();

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
                        !sWeight.equals(weight.getText().toString()) ||
                        FLAG.equalsIgnoreCase("CLICKED") ){

                        try {

                            StorageReference storageReference2nd = FirebaseStorage.getInstance().getReference().child("Trainer_Images/" + System.currentTimeMillis() + "." + GetFileExtension(resultUri));
                            storageReference2nd.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_SHORT).show();
                                            mUserDatabase.child("imgUri").setValue(uri.toString());
                                        }
                                    });
                                }
                            });

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

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);

                // Setting up bitmap selected image into ImageView.
                showImage.setImageBitmap(bitmap);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        FLAG = "CLICKED";
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