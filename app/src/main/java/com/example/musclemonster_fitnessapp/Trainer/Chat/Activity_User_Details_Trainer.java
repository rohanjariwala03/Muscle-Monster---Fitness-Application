package com.example.musclemonster_fitnessapp.Trainer.Chat;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.R;

public class Activity_User_Details_Trainer extends AppCompatActivity {

    TextView txtName, txtEmail,txtPhone;
    ImageView img;
    String nam,Emai,Num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_trainer);

        getSupportActionBar().setTitle("User Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName=findViewById(R.id.txtUserNameDetailsTrainer);
        txtEmail=findViewById(R.id.txtUserEmailDetailsTrainer);
        txtPhone=findViewById(R.id.txtUserNumberDetailsTrainer);
        img=findViewById(R.id.imgViewUserProfileDetailsTrainer);

        img.setImageResource(R.drawable.ic_baseline_account_circle_24);

        nam=getIntent().getStringExtra("UsrName");
        Emai=getIntent().getStringExtra("UsrEmail");

        txtName.setText(nam);
        txtEmail.setText(Emai);
    }
}