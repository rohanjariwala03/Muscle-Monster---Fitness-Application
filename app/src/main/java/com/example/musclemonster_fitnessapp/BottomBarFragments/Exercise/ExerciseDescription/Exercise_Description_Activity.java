package com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.ExerciseDescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.Activity_Exercise_Video;
import com.example.musclemonster_fitnessapp.R;

public class Exercise_Description_Activity extends AppCompatActivity {

    String ExerciseCat,ExerciseName,ExerciseDesc,ItemImageUri;
    TextView Name,Catagory,Description;
    ImageView ImgView;
    ImageButton playbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_description);

        ExerciseName = getIntent().getStringExtra("ExerciseName");
        ExerciseCat = getIntent().getStringExtra("ExerciseCat");
        ExerciseDesc = getIntent().getStringExtra("ExerciseDesc");
        ItemImageUri = getIntent().getStringExtra("ItemImageUri");
        //Toast.makeText(this,"" + ExerciseCat,Toast.LENGTH_LONG).show();
        /*Log.i("asd", "Data : " + ExerciseCat);
        Log.i("asd", "Data : " + ExerciseName);
        Log.i("asd", "Data : " + ExerciseDesc);
*/
        Catagory=findViewById(R.id.txtExerciseType);
        Name=findViewById(R.id.txtExerciseName);
        Description =findViewById(R.id.txtExerciseDescription);
        Name.setText(ExerciseName);
        Catagory.setText(ExerciseCat);
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

        Glide.with(this)
                .load(ItemImageUri)
                .into(ImgView);


    }
}