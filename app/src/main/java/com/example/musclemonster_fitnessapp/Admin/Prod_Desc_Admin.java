package com.example.musclemonster_fitnessapp.Admin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Prod_Desc_Admin extends AppCompatActivity {

    String ItemKey,ItemName,ItemPrice,ItemDesc,ItemCat,ItemImageUri,ItemWeight,UserKey,PubName, PubMail,Gen;
    ImageView ImgView;
    TextView EditProName, EditProPrice, EditProCat, EditProDesc, EditProWeight , TxtPubName, TxtPubMail, TxtProGen;
    FloatingActionButton fab;
    FirebaseDatabase database;
    DatabaseReference reff;
    LinearLayout GenLL;
    Button BtnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_desc_admin);

        getSupportActionBar().setTitle("Product Description");
        getSupportActionBar().setCustomView(R.layout.support_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));

        fab = findViewById(R.id.fab);

        EditProName = (TextView)findViewById(R.id.txtItemName);
        EditProPrice = (TextView)findViewById(R.id.txtItemPrice);
        EditProCat = (TextView)findViewById(R.id.txtItemCat);
        EditProDesc = (TextView)findViewById(R.id.txtItemDesc);
        EditProWeight = (TextView)findViewById(R.id.txtItemWeight);
        TxtPubMail = (TextView)findViewById(R.id.txtItemPublisherMail);
        TxtPubName = (TextView)findViewById(R.id.txtItemPublisherName);
        ImgView = (ImageView) findViewById(R.id.ItemImageView);
        TxtProGen = (TextView) findViewById(R.id.txtItemGen);
        BtnDel = (Button) findViewById(R.id.BtnDelAd);
        GenLL = (LinearLayout) findViewById(R.id.LLGen);

        ItemKey = getIntent().getStringExtra("ItemKey");
        ItemName = getIntent().getStringExtra("ItemName");
        ItemPrice = getIntent().getStringExtra("ItemPrice");
        ItemDesc = getIntent().getStringExtra("ItemDesc");
        ItemCat = getIntent().getStringExtra("ItemCat");
        ItemImageUri = getIntent().getStringExtra("ItemImageUri");
        ItemWeight = getIntent().getStringExtra("ItemWeight");
        UserKey = getIntent().getStringExtra("UserKey");
        Gen = getIntent().getStringExtra("ItemGen");
        Log.i("PRod_Desc_Admin","UserKey"+ UserKey);

        database = FirebaseDatabase.getInstance();
        reff = database.getReference("Users").child(UserKey);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.i("PRod_Desc_Admin","Entered If ");
                    TxtPubMail.setText(snapshot.child("email").getValue(String.class));
                    TxtPubName.setText((snapshot.child("firstName").getValue(String.class)) + " " + snapshot.child("lastName").getValue(String.class));
                }
                else
                {
                    reff = database.getReference("Admin").child(UserKey);
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Log.i("PRod_Desc_Admin","Entered If ");
                                TxtPubMail.setText(snapshot.child("email").getValue(String.class));
                                TxtPubName.setText((snapshot.child("firstName").getValue(String.class)) + " " + snapshot.child("lastName").getValue(String.class));
                            }
                            else
                            {
                                Log.i("Prod_Desc_Admin", "NO Data : " );
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.i("Prod_Desc_Admin : ", "Cancelled : " );
            }
        });

        EditProName.setText(ItemName);
        EditProPrice.setText(ItemPrice);
        EditProCat.setText(ItemCat);
        EditProWeight.setText(ItemWeight);
        EditProDesc.setText(ItemDesc);
        TxtPubMail.setText(PubMail);
        TxtPubName.setText(PubName);
        if(ItemCat.equals("clothing")) {
            TxtProGen.setText(Gen);
            GenLL.setVisibility(View.VISIBLE);
        }
        else
        {
            GenLL.setVisibility(View.GONE);
        }

        Glide.with(this)
                .load(ItemImageUri)
                .into(ImgView);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Prod_Desc_Admin.this, Admin_Cart.class);
                intent.putExtra("ItemKey",ItemKey);
                intent.putExtra("ItemName",ItemName);
                intent.putExtra("ItemPrice",ItemPrice);
                intent.putExtra("ItemCat",ItemCat);
                intent.putExtra("ItemDesc",ItemDesc);
                intent.putExtra("ItemImageUri",ItemImageUri);
                intent.putExtra("ItemWeight",ItemWeight);
                intent.putExtra("ItemWeight",ItemWeight);
                intent.putExtra("UserKey",UserKey);
                startActivity(intent);
            }
        });

        BtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff = database.getReference("Product_Detail_Database").child(ItemKey);
                reff.removeValue();
                Toast.makeText(Prod_Desc_Admin.this,"Advertisement Deleted" , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Prod_Desc_Admin.this,View_Prod_Admin.class);
                startActivity(intent);
            }
        });

    }
}