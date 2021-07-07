package com.example.musclemonster_fitnessapp.BottomBarFragments.Chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;

public class Activity_Trainer_Details_User extends AppCompatActivity {

    TextView txtName, txtEmail,txtPhone;
    String nam,Emai,Num,ImgUrl;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_details_user);

        getSupportActionBar().setTitle("Trainer Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName=findViewById(R.id.txtTrainerNameDetailsUser);
        txtEmail=findViewById(R.id.txtTrainerEmailDetailsUser);
        txtPhone=findViewById(R.id.txtTrainerNumberDetailsUser);
        imgView=findViewById(R.id.imgViewTrainerProfileDetailsUser);

        nam=getIntent().getStringExtra("TrainerName");
        Emai=getIntent().getStringExtra("TrainerEmail");
        ImgUrl=getIntent().getStringExtra("ImageUrl");

        Glide.with(this)
                .load(ImgUrl)
                .into(imgView);

        txtName.setText(nam);
        txtEmail.setText(Emai);

    }
}