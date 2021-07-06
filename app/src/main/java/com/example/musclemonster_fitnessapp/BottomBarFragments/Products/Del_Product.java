package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.Admin.Admin_Del_Prod;
import com.example.musclemonster_fitnessapp.Admin.MyProducts_Admin;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.MyProducts_User;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Del_Product extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_product);
        progressDialog = new ProgressDialog(Del_Product.this);
        progressDialog.setTitle("Advertisement is Deleting....");
        progressDialog.show();
        String ChildKey = getIntent().getStringExtra("ItemKey").toString();
        FirebaseDatabase.getInstance().getReference("Product_Detail_Database").child(ChildKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"Advertisement Deleted" ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MyProducts_User.class);
                progressDialog.dismiss();
                startActivity(intent);
                finish();
            }
        });
    }
}