package com.example.musclemonster_fitnessapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DatabaseReference;

public class ActivityTrainerViewDescription extends AppCompatActivity {

    String ItemKey;
    String TrainerName,TrainerLastName,TrainerEmail,TrainerCon,TrainerAge,TrainerExper,ItemImageUri;
    ImageView ImgView;
    TextView EditFirstName,EditLastName, EditEmail,EditPhone,EditAge,EditExp;
    ImageButton imgbtn;

    DatabaseReference drRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_view_description);
        EditFirstName = (TextView)findViewById(R.id.txtTrainerNameAd);
        EditLastName = (TextView)findViewById(R.id.txtTrainerLastNameAd);
        EditEmail = (TextView)findViewById(R.id.txtTrainerEmailAd);
        EditAge = (TextView)findViewById(R.id.txtTrainerAgeAd);
        EditPhone = (TextView)findViewById(R.id.txtTrainerContactAd);
        EditExp = (TextView)findViewById(R.id.txtTrainerExperienceAd);


        ImgView = (ImageView) findViewById(R.id.TrainerImageViewAd);
        imgbtn=(ImageButton)findViewById(R.id.btnDeleteTrainerAdmin);


        ItemKey = getIntent().getStringExtra("ItemKey");
        TrainerName = getIntent().getStringExtra("TrainerName");
        TrainerLastName = getIntent().getStringExtra("TrainerLastName");
        //ItemPrice = getIntent().getStringExtra("TrainerID");
        TrainerEmail = getIntent().getStringExtra("TrainerEmail");
        ItemImageUri = getIntent().getStringExtra("TrainerImageUri");
        TrainerCon = getIntent().getStringExtra("TrainerContact");
        TrainerAge = getIntent().getStringExtra("TrainerAge");
        TrainerExper = getIntent().getStringExtra("TrainerExperience");



        EditFirstName.setText(TrainerName);
        EditLastName.setText(TrainerLastName);
        EditEmail.setText(TrainerEmail);
        EditAge.setText(TrainerAge);
        EditPhone.setText(TrainerCon);
        EditExp.setText(TrainerExper);


        Glide.with(this)
                .load(ItemImageUri)
                .into(ImgView);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* AlertDialog.Builder dialog=new AlertDialog.Builder(ActivityTrainerViewDescription_A.this);
                dialog.setTitle("Are you sure");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {

                            }
                        });
                    }
                });*/

            }
        });
    }

}