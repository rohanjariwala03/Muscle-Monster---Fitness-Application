package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.MainActivity;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateMyProduct extends AppCompatActivity {

    private String Storage_Path;
    private String Database_Path , FLAG;
    private Button BtnUpload,BtnSubmit;
    private EditText ProName,ProWeight,ProCat,ProPrice,ProDesc;

    private ImageView ImgView;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private Uri resultUri;

    private ProgressDialog progressDialog ;

    String ItemKey,ItemName,ItemPrice,ItemDesc,ItemCat,ItemImageUri,ItemWeight,UserKey;

    private LinearLayout ImageLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_product);

        getSupportActionBar().setTitle("Sell Product");

        // Folder path for Firebase Storage.
        Storage_Path = "Product_Images/";

        // Root Database Name for Firebase Database.
        Database_Path = "Product_Detail_Database";

       /* myAuth = FirebaseAuth.getInstance();*/
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path);
        Log.i("Product_Add_Home", "Datax : " + databaseReference);

        ProName = (EditText) findViewById(R.id.EditProdName);
        ProWeight = (EditText) findViewById(R.id.EditProdWeight);
        ProCat = (EditText) findViewById(R.id.EditProdCategory);
        ProPrice = (EditText) findViewById(R.id.EditProdPrice);
        ProDesc = (EditText) findViewById(R.id.EditProdDesc);

        BtnUpload = (Button) findViewById(R.id.BtnUploadImage);
        BtnSubmit = (Button) findViewById(R.id.BtnSubmit);

        ImgView = (ImageView) findViewById(R.id.ShowImageView);

        progressDialog = new ProgressDialog(UpdateMyProduct.this);

        ImageLL = (LinearLayout) findViewById(R.id.ImgLinearLayout);

        FLAG="NOT CLICKED";

        GetData();
        SetData();

        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ImageLL.setVisibility(View.VISIBLE) ;
                // Creating intent.
                Intent intent = new Intent();
                FLAG="CLICKED";
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
                if(FLAG.equals("CLICKED"))
                    UploadData();
                else {
                    progressDialog.setTitle("Advertisement is Updating....");
                    // Showing progressDialog.
                    progressDialog.show();
                    /*progressDialog.setProgress(2);*/
                    UpdateChild(ItemImageUri);
                }
            }
        });

    }

    private void GetData()
    {
        ItemKey = getIntent().getStringExtra("ItemKey");
        ItemName = getIntent().getStringExtra("ItemName");
        ItemPrice = getIntent().getStringExtra("ItemPrice");
        ItemDesc = getIntent().getStringExtra("ItemDesc");
        ItemCat = getIntent().getStringExtra("ItemCat");
        ItemImageUri = getIntent().getStringExtra("ItemImageUri");
        ItemWeight = getIntent().getStringExtra("ItemWeight");
        UserKey = getIntent().getStringExtra("UserKey");

    }

    private void SetData()
    {
        ProName.setText(ItemName);
        ProWeight.setText(ItemWeight);
        ProCat.setText(ItemCat);
        ProPrice.setText(ItemPrice);
        ProDesc.setText(ItemDesc);
        Glide.with(this)
                .load(ItemImageUri)
                .into(ImgView);

    }

    private void UploadData() {

        // Checking whether FilePathUri Is empty or not.
        if (resultUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Advertisement is Updating....");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(resultUri));

            storageReference2nd.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            UpdateChild(uri.toString());

                        }
                    });
                }
            });
        }
        else {

            Toast.makeText(UpdateMyProduct.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    //Method To Update Child
    private void UpdateChild( String s)
    {
        String ProID = ItemKey;
        String ProductName = ProName.getText().toString().toLowerCase().trim();
        String ProductWeight = ProWeight.getText().toString().toLowerCase().trim();
        String ProductPrice = ProPrice.getText().toString().toLowerCase().trim();
        String ProductCat = ProCat.getText().toString().toLowerCase().trim();
        String ProductDesc = ProDesc.getText().toString().toLowerCase().trim();
        String ImgUri = s;
        String UKey = UserKey;

        // Showing toast message after done uploading.
        Toast.makeText(getApplicationContext(), "Advertisement Updated Successfully ", Toast.LENGTH_LONG).show();

                            /*@SuppressWarnings("VisibleForTests")
                            ProductUpload_POJO ProUploadPOJO = new ProductUpload_POJO(ProID,ProductName,ProductWeight,ProductPrice,ProductCat,ProductDesc,ImgUri,UKey);
*/
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path).child(ItemKey);

        databaseReference.child("imageUri").setValue(ImgUri);
        databaseReference.child("productCat").setValue(ProductCat);
        databaseReference.child("productName").setValue(ProductName);
        databaseReference.child("productWeight").setValue(ProductWeight);
        databaseReference.child("productPrice").setValue(ProductPrice);
        databaseReference.child("productDesc").setValue(ProductDesc);

        progressDialog.dismiss();


        Intent intent = new Intent(getApplicationContext(), MyProducts_User.class);
        startActivity(intent);
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