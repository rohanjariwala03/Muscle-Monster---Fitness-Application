package com.example.musclemonster_fitnessapp.Trainer.Chat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;

public class Activity_User_Details_Trainer extends AppCompatActivity {

    TextView txtName, txtEmail,txtPhone;
    ImageView img;
    String nam,Emai,Num,ImageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_trainer);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.support_toolbar);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));

        txtName=findViewById(R.id.txtUserNameDetailsTrainer);
        txtEmail=findViewById(R.id.txtUserEmailDetailsTrainer);
        txtPhone=findViewById(R.id.txtUserNumberDetailsTrainer);
        img=findViewById(R.id.imgViewUserProfileDetailsTrainerChat2);

        nam=getIntent().getStringExtra("UsrName");
        Emai=getIntent().getStringExtra("UsrEmail");
       /* if(getIntent().getStringExtra("TreinaerImage").equals(null))
        {
            Log.i("abc","xyz");
        }else if(getIntent().getStringExtra("TreinaerImage").equals("null")){
            Log.i("abc","LLPOIJS");
        }*/
        ImageUrl=getIntent().getStringExtra("TreinaerImage");


        String defaultUrl="https://firebasestorage.googleapis.com/v0/b/muscle-monster-fitnessap-8b451.appspot.com/o/DefaultImage%2Fcircular.png?alt=media&token=783c1888-61d2-40fe-82aa-9f62c184e5ec";
        if(ImageUrl.equals("null")){
            Glide.with(this)
                    .load(defaultUrl)
                    .into(img);
        }else {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(img);
        }
        txtName.setText(nam);
        txtEmail.setText(Emai);
    }

}