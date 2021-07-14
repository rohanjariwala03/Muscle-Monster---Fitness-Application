package com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.ExerciseDescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.Activity_Exercise_Video;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.Activity_Start_Exercise_Timer;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.InfoDialog;
import com.example.musclemonster_fitnessapp.R;

public class Exercise_Description_Activity extends AppCompatActivity {

    String ExerciseCat,ExerciseName,ExerciseDesc,ItemImageUri,steps;
    TextView Name,Catagory,Description;
    ImageView ImgView;
    ImageButton playbtn,info,imgBtnstartWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_description);


        ExerciseName = getIntent().getStringExtra("ExerciseName");
        ExerciseCat = getIntent().getStringExtra("ExerciseCat");
        ExerciseDesc = getIntent().getStringExtra("ExerciseDesc");
        ItemImageUri = getIntent().getStringExtra("ItemImageUri");
        steps=getIntent().getStringExtra("ExerciseSteps");
        imgBtnstartWorkout=findViewById(R.id.imgBtnStartWorkout);

        getSupportActionBar().setTitle(ExerciseName + " Exercise");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3E50")));

        info=findViewById(R.id.Info);
        //Toast.makeText(this,"" + ExerciseCat,Toast.LENGTH_LONG).show();
        /*Log.i("asd", "Data : " + ExerciseCat);
        Log.i("asd", "Data : " + ExerciseName);
        Log.i("asd", "Data : " + ExerciseDesc);
*/
        /*Catagory=findViewById(R.id.txtExerciseType);
        Name=findViewById(R.id.txtExerciseName);*/
        Description =findViewById(R.id.txtExerciseDescription);
        /*Name.setText(ExerciseName);
        Catagory.setText(ExerciseCat);*/
        Description.setText(ExerciseDesc);
        ImgView = (ImageView) findViewById(R.id.imgView);
        playbtn=findViewById(R.id.PlayVideo);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise_Description_Activity.this, Activity_Exercise_Video.class);
               intent.putExtra("ExerName", ExerciseName);
                startActivity(intent);

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        Glide.with(this)
                .load(ItemImageUri)
                .into(ImgView);

        imgBtnstartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Exercise_Description_Activity.this, Activity_Start_Exercise_Timer.class);
                intent.putExtra("exName",ExerciseName);
                intent.putExtra("imgUri",ItemImageUri);
                startActivity(intent);
            }
        });

    }

    private void openDialog() {
        InfoDialog infoDialog=new InfoDialog(steps);
        infoDialog.show(getSupportFragmentManager(),"tSteps");
    }
}