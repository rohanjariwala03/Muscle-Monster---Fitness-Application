package com.example.musclemonster_fitnessapp.ExerciseDescription;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.R;

public class Exercise_Description_Activity extends AppCompatActivity {

    String ExerciseCat;
    TextView Catagory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_description);

        ExerciseCat = getIntent().getStringExtra("ExerciseName");
        Toast.makeText(this,"" + ExerciseCat,Toast.LENGTH_LONG).show();
        Log.i("asd", "Data : " + ExerciseCat);


    }
}