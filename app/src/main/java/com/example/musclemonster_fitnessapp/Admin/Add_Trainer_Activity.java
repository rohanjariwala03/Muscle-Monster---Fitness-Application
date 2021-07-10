package com.example.musclemonster_fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.*;
import com.example.musclemonster_fitnessapp.LoginSignUp.ActivityLogIn;
import com.example.musclemonster_fitnessapp.LoginSignUp.ActivitySignUp;
import com.example.musclemonster_fitnessapp.LoginSignUp.Model;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Product_Add_Home;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.POJOClasses.TrainerPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public class Add_Trainer_Activity extends AppCompatActivity {

    private String Storage_Path;
    private String Database_Path;

    private ImageView ImgView;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private Uri resultUri;
    EditText firstName,lastName,email,password,contact,experience,weight,height,age;
    Button BtnUpload, BtnSubmit;

    private LinearLayout ImageLL;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__trainer_);

        getSupportActionBar().setTitle("Add Trainers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));


        // Folder path for Firebase Storage.
        Storage_Path = "Trainer_Images/";

        // Root Database Name for Firebase Database.
        Database_Path = "Trainer";

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path);
        Log.i("Trainer_Add_Home", "Datax : " + databaseReference);

        firstName = (EditText) findViewById(R.id.EditTrainerFirstName);
        lastName = (EditText) findViewById(R.id.EditTrainerLastName);
        email = (EditText) findViewById(R.id.EditTrainerEmailId);
        password = (EditText) findViewById(R.id.EditTrainerPassword);
        contact = (EditText) findViewById(R.id.EditTrainerContact);
        experience = (EditText) findViewById(R.id.EditTrainerExperience);
        weight = (EditText) findViewById(R.id.EditTrainerWeight);
        height = (EditText) findViewById(R.id.EditTrainerHeight);
        age = (EditText) findViewById(R.id.EditTrainerAge);

        BtnUpload = (Button) findViewById(R.id.BtnTrainerUploadImage);
        BtnSubmit = (Button) findViewById(R.id.BtnAdTrainerSubmit);

        ImgView = (ImageView) findViewById(R.id.ShowImageView);

        //progressDialog = new ProgressDialog(Add_Trainer_Activity.this);

        ImageLL = (LinearLayout) findViewById(R.id.ImgLinearLayout);

        //ImageLL.setVisibility(View.INVISIBLE) ;

        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ImageLL.setVisibility(View.VISIBLE) ;
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
                //startActivityForResult(Intent.createChooser(intent, "Please Select Image"),1);

            }
        });


        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadTrainer();
            }
        });
    }

    private void UploadTrainer() {

        // Checking whether FilePathUri Is empty or not.
        if (resultUri != null) {

            String EmailId = email.getText().toString().trim();
            String Password = password.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(EmailId, Password)

                    .addOnCompleteListener(Add_Trainer_Activity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(resultUri));


                                storageReference2nd.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                 String TrainerFirstname = firstName.getText().toString().trim();
                                                String LastName = lastName.getText().toString().trim();
                                                String TrainerID = "N";
                                                String Experience = experience.getText().toString().trim();
                                                String Weight = weight.getText().toString().trim();
                                                String Height = height.getText().toString().trim();
                                                String Age = age.getText().toString().trim();
                                                String Contact = contact.getText().toString().trim();

                                                String ImgUri = uri.toString();
                                                // Showing toast message after done uploading.
                                                Toast.makeText(getApplicationContext(), "Trainer Added Successfully ", Toast.LENGTH_LONG).show();

                                                TrainerPojo trainerPojo = new TrainerPojo(TrainerID, TrainerFirstname, LastName, EmailId, Password, Contact, Experience, Weight, Height, Age, ImgUri);


                                                FirebaseDatabase.getInstance().getReference("Trainer")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(trainerPojo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NotNull Task<Void> task) {
                                                        Toast.makeText(Add_Trainer_Activity.this, "User Created", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(Add_Trainer_Activity.this, Admin_Home_Activity.class));
                                                    }
                                                });
                                                // Getting image upload ID.
                                                //String ImageUploadId = databaseReference.push().getKey();

                                                // Adding image upload id s child element into databaseReference.
                                                //databaseReference.child(ImageUploadId).setValue(ProUploadPOJO);
                                                /*databaseReference.push().setValue(trainerPojo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Intent intent = new Intent(getApplicationContext(), Admin_Home_Activity.class);
                                                        intent.putExtra("fragmentNumber",3); //for example
                                                        startActivity(intent);
                                                    }
                                                });*/
                                            }
                                        });
                                    }
                                });


                            }else{
                                Log.i("avc :" , "unsuccess");
                                Toast.makeText(Add_Trainer_Activity.this, "User Created Unsuccessfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

//            // Setting progressDialog Title.
//            progressDialog.setTitle("Advertisement is Publishing....");
//
//            // Showing progressDialog.
//            progressDialog.show();

            // Creating second StorageReference.
         /*   StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(resultUri));


            storageReference2nd.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String TrainerID = UUID.randomUUID().toString();

                            String TrainerFirstname = firstName.getText().toString().trim();
                            String LastName = lastName.getText().toString().trim();
                            String EmailId = email.getText().toString().trim();
                            String Password = password.getText().toString().trim();
                            String Experience = experience.getText().toString().trim();
                            String Weight = weight.getText().toString().trim();
                            String Height = height.getText().toString().trim();
                            String Age = age.getText().toString().trim();
                            String Contact = contact.getText().toString().trim();

                            String ImgUri = uri.toString();
                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Trainer Added Successfully ", Toast.LENGTH_LONG).show();

                            TrainerPojo trainerPojo = new TrainerPojo(TrainerID, TrainerFirstname, LastName, EmailId, Password, Contact, Experience, Weight, Height, Age, ImgUri);

                            // Getting image upload ID.
                            //String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            //databaseReference.child(ImageUploadId).setValue(ProUploadPOJO);
                            databaseReference.push().setValue(trainerPojo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent intent = new Intent(getApplicationContext(), Admin_Home_Activity.class);
                                    intent.putExtra("fragmentNumber",3); //for example
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            });*/


        }
        else {

            Toast.makeText(Add_Trainer_Activity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
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
            //ImgView.setImageURI(resultUri);

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);

                // Setting up bitmap selected image into ImageView.
                ImgView.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                //BtnUpload.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

}