package com.example.musclemonster_fitnessapp.Admin;

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

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.MyProducts_User;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.UpdateMyProduct;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Admin_Update_Prod extends AppCompatActivity {

    private String Storage_Path,DDSelected,GENDER;
    private String Database_Path , FLAG;
    private Button BtnUpload,BtnSubmit;
    private EditText ProName,ProWeight,ProPrice,ProDesc;

    private ImageView ImgView;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    LinearLayout LLGender;

    private Uri resultUri;

    private ProgressDialog progressDialog ;

    String ItemKey,ItemName,ItemPrice,ItemDesc,ItemCat,ItemImageUri,ItemWeight,UserKey;

    private LinearLayout ImageLL;

    private Spinner spinner, spinnerGen;
    private static final String[] paths = {"machines", "dumbbell", "clothing","bench","barbell","bicycle","balanceball","kettlebell","plates","treadmill"};
    private static final String[] gender = {"male", "female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_prod);

        getSupportActionBar().setTitle("Update a Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DDSelected = "";
        GENDER="NA";

        // Folder path for Firebase Storage.
        Storage_Path = "Product_Images/";

        // Root Database Name for Firebase Database.
        Database_Path = "Product_Detail_Database";

        /* myAuth = FirebaseAuth.getInstance();*/
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path);

        ProName = (EditText) findViewById(R.id.EditProdName);
        ProWeight = (EditText) findViewById(R.id.EditProdWeight);
        /*ProCat = (EditText) findViewById(R.id.EditProdCategory);*/
        ProPrice = (EditText) findViewById(R.id.EditProdPrice);
        ProDesc = (EditText) findViewById(R.id.EditProdDesc);

        BtnUpload = (Button) findViewById(R.id.BtnUploadImage);
        BtnSubmit = (Button) findViewById(R.id.BtnSubmit);

        ImgView = (ImageView) findViewById(R.id.ShowImageView);

        progressDialog = new ProgressDialog(Admin_Update_Prod.this);

        ImageLL = (LinearLayout) findViewById(R.id.ImgLinearLayout);

        FLAG="NOT CLICKED";

        GetData();
        SetData();

        LLGender = (LinearLayout) findViewById(R.id.LinearGender);
        //ImageLL.setVisibility(View.INVISIBLE) ;

        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Admin_Update_Prod.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(ItemCat));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                    case 1:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        LLGender.setVisibility(View.GONE);
                        GENDER = "NA";
                        break;

                    case 2:
                        LLGender.setVisibility(View.VISIBLE);
                        break;

                    default:
                        LLGender.setVisibility(View.GONE);
                        break;
                }
                DDSelected = adapterView.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "Item : " + DDSelected,Toast.LENGTH_LONG).show();;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerGen = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Admin_Update_Prod.this,
                android.R.layout.simple_spinner_item,gender);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGen.setAdapter(adapter1);
        spinnerGen.setSelection(adapter1.getPosition(GENDER));
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
                GENDER = spinnerGen.getSelectedItem().toString();
            }
        });

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
        GENDER = getIntent().getStringExtra("ItemGen");
        DDSelected = ItemCat;
    }

    private void SetData()
    {
        ProName.setText(ItemName);
        ProWeight.setText(ItemWeight);
        /*ProCat.setText(ItemCat);*/
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

            Toast.makeText(Admin_Update_Prod.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    //Method To Update Child
    private void UpdateChild( String s)
    {
        String ProductName = ProName.getText().toString().toLowerCase().trim();
        String ProductWeight = ProWeight.getText().toString().toLowerCase().trim();
        String ProductPrice = ProPrice.getText().toString().toLowerCase().trim();
        String ProductCat = spinner.getSelectedItem().toString();
        String ProductDesc = ProDesc.getText().toString().toLowerCase().trim();
        String ImgUri = s;

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
        databaseReference.child("prodGen").setValue(GENDER);

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