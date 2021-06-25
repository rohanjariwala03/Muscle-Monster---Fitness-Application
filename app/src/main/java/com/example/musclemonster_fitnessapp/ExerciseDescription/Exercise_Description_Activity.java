package com.example.musclemonster_fitnessapp.ExerciseDescription;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;

public class Exercise_Description_Activity extends AppCompatActivity {

    String ExerciseCat,ExerciseName,ExerciseDesc,ItemImageUri;
    TextView Name,Catagory,Description;
    ImageView ImgView;

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

        Glide.with(this)
                .load(ItemImageUri)
                .into(ImgView);


    }
}