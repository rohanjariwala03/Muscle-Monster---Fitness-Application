package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;

public class ViewProductDetail extends AppCompatActivity {

    String ItemKey,ItemName,ItemPrice,ItemDesc,ItemCat,ItemImageUri,ItemWeight;
    ImageView ImgView;
    EditText EditProName, EditProPrice, EditProCat, EditProDesc, EditProWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_detail);

        getSupportActionBar().setTitle("Product Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditProName = (EditText)findViewById(R.id.txtItemName);
        EditProPrice = (EditText)findViewById(R.id.txtItemPrice);
        EditProCat = (EditText)findViewById(R.id.txtItemCat);
        EditProDesc = (EditText)findViewById(R.id.txtItemDesc);
        EditProWeight = (EditText)findViewById(R.id.txtItemWeight);
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

        /*if(ItemKey!=null)
            txtvid.setText(ItemKey + ItemName);*/

    }
}