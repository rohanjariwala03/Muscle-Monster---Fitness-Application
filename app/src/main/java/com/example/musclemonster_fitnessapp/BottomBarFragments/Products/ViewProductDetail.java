package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.LoginSignUp.ActivitySignUp;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewProductDetail extends AppCompatActivity {

    String ItemKey,ItemName,ItemPrice,ItemDesc,ItemCat,ItemImageUri,ItemWeight;
    ImageView ImgView;
    TextView EditProName, EditProPrice, EditProCat, EditProDesc, EditProWeight;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_detail);

        getSupportActionBar().setTitle("Product Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = findViewById(R.id.fab);

        EditProName = (TextView)findViewById(R.id.txtItemName);
        EditProPrice = (TextView)findViewById(R.id.txtItemPrice);
        EditProCat = (TextView)findViewById(R.id.txtItemCat);
        EditProDesc = (TextView)findViewById(R.id.txtItemDesc);
        EditProWeight = (TextView)findViewById(R.id.txtItemWeight);
        ImgView = (ImageView) findViewById(R.id.ItemImageView);

        ItemKey = getIntent().getStringExtra("ItemKey");
        ItemName = getIntent().getStringExtra("ItemName");
        ItemPrice = getIntent().getStringExtra("ItemPrice");
        ItemDesc = getIntent().getStringExtra("ItemDesc");
        ItemCat = getIntent().getStringExtra("ItemCat");
        ItemImageUri = getIntent().getStringExtra("ItemImageUri");
        ItemWeight = getIntent().getStringExtra("ItemWeight");

        EditProName.setText(ItemName);
        EditProPrice.setText(ItemPrice);
        EditProCat.setText(ItemCat);
        EditProWeight.setText(ItemWeight);
        EditProDesc.setText(ItemDesc);
        Glide.with(this)
                .load(ItemImageUri)
                .into(ImgView);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProductDetail.this, product_cart.class);
                intent.putExtra("ItemKey",ItemKey);
                intent.putExtra("ItemName",ItemName);
                intent.putExtra("ItemPrice",ItemPrice);
                intent.putExtra("ItemCat",ItemCat);
                intent.putExtra("ItemDesc",ItemDesc);
                intent.putExtra("ItemImageUri",ItemImageUri);
                intent.putExtra("ItemWeight",ItemWeight);
                startActivity(intent);
            }
        });

        /*if(ItemKey!=null)
            txtvid.setText(ItemKey + ItemName);*/

    }
}