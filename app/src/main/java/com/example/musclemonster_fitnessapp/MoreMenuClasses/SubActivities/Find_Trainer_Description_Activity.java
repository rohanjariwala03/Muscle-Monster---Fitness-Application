package com.example.musclemonster_fitnessapp.MoreMenuClasses.SubActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Find_Trainer_Description_Activity extends AppCompatActivity {

    TextView txtFName, txtLName, txtContact,txtEmail;
    String imgUri,TrainerID,FKey;
    ImageView img;
    ImageButton imgChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_trainer_description);

        imgChat=findViewById(R.id.imgChatFindTrainer);
        txtFName=findViewById(R.id.txtUserNameProfile);
        txtLName=findViewById(R.id.txtUserLastNameAdProfile);
        txtEmail=findViewById(R.id.txtUserEmailAdProfile);
        txtContact=findViewById(R.id.txtUserContactAdProfile);
        img=findViewById(R.id.imgTrainerDetailProfile);

        txtFName.setText(getIntent().getStringExtra("TrainerFName"));
        txtLName.setText(getIntent().getStringExtra("TrainerLName"));
        txtEmail.setText(getIntent().getStringExtra("TrainerEmail"));
        txtContact.setText(getIntent().getStringExtra("TrainerPhone"));
        TrainerID= getIntent().getStringExtra("TrainerID");
        FKey= getIntent().getStringExtra("TrainerFkey");

        imgUri=getIntent().getStringExtra("TrainerImageUrl");
        Glide.with(Find_Trainer_Description_Activity.this)
                .load(imgUri)
                .into(img);

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Find_Trainer_Description_Activity.this, Chat_Activity.class);
                intent.putExtra("TrainerFName",txtFName.getText());
                intent.putExtra("TrainerID", TrainerID);
                intent.putExtra("TrainerImageUrl",imgUri);
                intent.putExtra("TrainerFkey",FKey);
                intent.putExtra("TrainerEmail",txtEmail.getText());
                startActivity(intent);
            }
        });

    }

}