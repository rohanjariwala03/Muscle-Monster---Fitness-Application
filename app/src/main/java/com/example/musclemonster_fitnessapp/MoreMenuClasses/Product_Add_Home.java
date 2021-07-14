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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.UUID;

public class Product_Add_Home extends AppCompatActivity {

    private String Storage_Path,DDSelected,GENDER;
    private String Database_Path;
    private Button BtnUpload,BtnSubmit;
    private EditText ProName,ProWeight,ProCat,ProPrice,ProDesc;

    private ImageView ImgView;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    LinearLayout LLGender;

    private Uri resultUri;

    private ProgressDialog progressDialog ;

    private LinearLayout ImageLL;

    private Spinner spinner, spinnerGen;
    private static final String[] paths = {"machines", "dumbbell", "clothing","bench","barbell","bicycle","balanceball","kettlebell","plates","treadmill"};
    private static final String[] gender = {"male", "female"};
    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add_home);

        DDSelected = "";
        GENDER="NA";

        getSupportActionBar().setTitle("Sell Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Folder path for Firebase Storage.
        Storage_Path = "Product_Images/";

        // Root Database Name for Firebase Database.
        Database_Path = "Product_Detail_Database";

        myAuth =FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path);
        Log.i("Product_Add_Home", "Datax : " + databaseReference);

        ProName = (EditText) findViewById(R.id.EditProdName);
        ProWeight = (EditText) findViewById(R.id.EditProdWeight);
        /*ProCat = (EditText) findViewById(R.id.EditProdCategory);*/
        ProPrice = (EditText) findViewById(R.id.EditProdPrice);
        ProDesc = (EditText) findViewById(R.id.EditProdDesc);

        BtnUpload = (Button) findViewById(R.id.BtnUploadImage);
        BtnSubmit = (Button) findViewById(R.id.BtnSubmit);

        ImgView = (ImageView) findViewById(R.id.ShowImageView);

        progressDialog = new ProgressDialog(Product_Add_Home.this);

        ImageLL = (LinearLayout) findViewById(R.id.ImgLinearLayout);

        LLGender = (LinearLayout) findViewById(R.id.LinearGender);
        //ImageLL.setVisibility(View.INVISIBLE) ;

        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Product_Add_Home.this,
                android.R.layout.simple_spinner_item,paths);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        LLGender.setVisibility(View.GONE);
                        break;
                    case 1:
                        LLGender.setVisibility(View.GONE);
                        // Whatever you want to happen when the second item gets selected
                        break;
                    case 2:
                            LLGender.setVisibility(View.VISIBLE);
                        break;

                    case 3:
                        LLGender.setVisibility(View.GONE);
                        // Whatever you want to happen when the second item gets selected
                        break;

                    case 4:
                        LLGender.setVisibility(View.GONE);
                        // Whatever you want to happen when the second item gets selected
                        break;

                    case 5:
                        LLGender.setVisibility(View.GONE);
                        // Whatever you want to happen when the second item gets selected
                        break;

                    case 6:
                        LLGender.setVisibility(View.GONE);
                        // Whatever you want to happen when the second item gets selected
                        break;

                    case 7:
                        LLGender.setVisibility(View.GONE);
                        // Whatever you want to happen when the second item gets selected
                        break;


                    case 8:
                        LLGender.setVisibility(View.GONE);
                        // Whatever you want to happen when the second item gets selected
                        break;

                    case 9:
                        LLGender.setVisibility(View.GONE);
                        // Whatever you want to happen when the second item gets selected
                        break;

                    default:
                        LLGender.setVisibility(View.GONE);
                        break;
                }
                DDSelected = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerGen = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Product_Add_Home.this,
                android.R.layout.simple_spinner_item,gender);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGen.setAdapter(adapter1);
        spinnerGen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        GENDER = adapterView.getSelectedItem().toString();
                        break;

                    case 1:
                        GENDER = adapterView.getSelectedItem().toString();
                        break;

                    default:
                        GENDER = "NA";
                        break;
                }
               }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
                UploadData();
            }
        });

    }

    private void UploadData() {

        /*String ProductName ;
        String ProductWeight ;
        String ProductPrice;
        String ProductCat;
        String ProductDesc;
        String ImgUri = "Default";*/

        /*ProductUpload_POJO ProUploadPOJO = new ProductUpload_POJO(ProductName,ProductWeight,ProductPrice,ProductCat,ProductDesc,ImgUri);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path);
        String id = databaseReference.push().getKey();
        //databaseReference.child(id).setValue(ProUploadPOJO);
        databaseReference.push().setValue(ProUploadPOJO);
        Log.i("Product_Add_Home", "Datax : " + id);
        Log.i("Product_Add_Home", "Datax : " + ProUploadPOJO.getProductName());
        Toast.makeText(Product_Add_Home.this,"Inserted",Toast.LENGTH_LONG).show();

        */



        // Checking whether FilePathUri Is empty or not.
        if (resultUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Advertisement is Publishing....");

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
                            String ProID = UUID.randomUUID().toString();
                            String ProductName = ProName.getText().toString().toLowerCase().trim();
                            String ProductWeight = ProWeight.getText().toString().toLowerCase().trim();
                            String ProductPrice = ProPrice.getText().toString().toLowerCase().trim();
                            String ProductCat = DDSelected;
                            String ProductDesc = ProDesc.getText().toString().toLowerCase().trim();
                            String ImgUri = uri.toString();
                            String UserKey = myAuth.getCurrentUser().getUid().toString();
                            String Gen = GENDER;
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Advertisement Published Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            ProductUpload_POJO ProUploadPOJO = new ProductUpload_POJO(ProID,ProductName,ProductWeight,ProductPrice,ProductCat,ProductDesc,ImgUri,UserKey,Gen);

                            databaseReference.push().setValue(ProUploadPOJO).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("fragmentNumber",3); //for example
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            });

            //Log.i("Product ADd",storageReference.getDownloadUrl().toString());
            // Adding addOnSuccessListener to second StorageReference.
           /* storageReference2nd.putFile(resultUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            String ProductName = ProName.getText().toString().trim();
                            String ProductWeight = ProWeight.getText().toString().trim();
                            String ProductPrice = ProPrice.getText().toString().trim();
                            String ProductCat = ProCat.getText().toString().trim();
                            String ProductDesc = ProDesc.getText().toString().trim();
                            String ImgUri = taskSnapshot.getMetadata().getPath();
                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            ProductUpload_POJO ProUploadPOJO = new ProductUpload_POJO(ProductName,ProductWeight,ProductPrice,ProductCat,ProductDesc,ImgUri);
                            //ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempImageName, taskSnapshot.getDownloadUrl().toString());

                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            //databaseReference.child(ImageUploadId).setValue(ProUploadPOJO);
                            databaseReference.push().setValue(ProUploadPOJO);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(Product_Add_Home.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });*/
        }
        else {

            Toast.makeText(Product_Add_Home.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

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