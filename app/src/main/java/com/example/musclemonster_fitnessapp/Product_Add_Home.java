package com.example.musclemonster_fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Product_Add_Home extends AppCompatActivity {

    public static int id = 1;
    String Storage_Path;
    String Database_Path;
    Button BtnShow,BtnUpload,BtnSubmit;
    EditText ProName,ProWeight,ProCat,ProPrice,ProDesc;
    Uri FilePathUri;
    ImageView ImgView;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add_home);

        getSupportActionBar().setTitle("Sell Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Folder path for Firebase Storage.
        Storage_Path = "Product_Images/";

        // Root Database Name for Firebase Database.
        Database_Path = "Product_Detail_Database";

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path);
        Log.i("Product_Add_Home", "Datax : " + databaseReference);

        ProName = (EditText) findViewById(R.id.EditProdName);
        ProWeight = (EditText) findViewById(R.id.EditProdWeight);
        ProCat = (EditText) findViewById(R.id.EditProdCategory);
        ProPrice = (EditText) findViewById(R.id.EditProdPrice);
        ProDesc = (EditText) findViewById(R.id.EditProdDesc);

        BtnShow = (Button) findViewById(R.id.BtnShowImage);
        BtnUpload = (Button) findViewById(R.id.BtnUploadImage);
        BtnSubmit = (Button) findViewById(R.id.BtnSubmit);

        //ImgView = (ImageView) findViewById(R.id.ShowImageView);

        progressDialog = new ProgressDialog(Product_Add_Home.this);

        /*BtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, "Please Select Image"),1);
            }
        });


        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calling method to upload selected image on Firebase storage.

            }
        });*/


        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadData();
            }
        });

    }

    private void UploadData() {

        String ProductName = ProName.getText().toString().trim();
        String ProductWeight = ProWeight.getText().toString().trim();
        String ProductPrice = ProPrice.getText().toString().trim();
        String ProductCat = ProCat.getText().toString().trim();
        String ProductDesc = ProDesc.getText().toString().trim();
        String ImgUri = "Default";

        ProductUpload_POJO ProUploadPOJO = new ProductUpload_POJO(ProductName,ProductWeight,ProductPrice,ProductCat,ProductDesc,ImgUri);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path);
        String id = databaseReference.push().getKey();
        //databaseReference.child(id).setValue(ProUploadPOJO);
        databaseReference.push().setValue(ProUploadPOJO);
        Log.i("Product_Add_Home", "Datax : " + id);
        Log.i("Product_Add_Home", "Datax : " + ProUploadPOJO.getProductName());
        Toast.makeText(Product_Add_Home.this,"Inserted",Toast.LENGTH_LONG).show();
    }

}