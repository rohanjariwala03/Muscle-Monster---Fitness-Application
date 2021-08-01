package com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_History_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class Activity_Start_Exercise_Timer extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    Button btnStart,btnPause,btnReset;
    boolean Flag=false,Flg=false;
    String currentUser,ExerciseName,ExerciseImageUri;
    String stopTime,date,time;
    Task<Void> databaseReference;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise_timer);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        btnPause=findViewById(R.id.btnPause);
        btnStart=findViewById(R.id.btnStart);
        btnReset=findViewById(R.id.btnReset);
        ExerciseName=getIntent().getStringExtra("exName");
        ExerciseImageUri=getIntent().getStringExtra("imgUri");
        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        imgView=findViewById(R.id.imgViewStartWorkout);
        if(ExerciseImageUri!=null) {
            Glide.with(this).load(ExerciseImageUri).into(imgView);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3E50")));
        getSupportActionBar().setTitle(ExerciseName + " Exercise");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
        date=dateformat.format(new Date());
        time=timeformat.format(new Date());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 100000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(Activity_Start_Exercise_Timer.this, "Time Over!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startChronometer(View v) {
        if (!running) {
            if(!Flg) {
                Glide.with(this).load(ExerciseImageUri).into(imgView);
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                running = true;
                btnStart.setVisibility(View.GONE);
                btnReset.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.VISIBLE);
            }else{
                chronometer.start();
                chronometer.setBase(SystemClock.elapsedRealtime());
                running = true;
                btnStart.setVisibility(View.GONE);
                btnReset.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.VISIBLE);
            }
        }
        btnStart.setVisibility(View.GONE);
        btnReset.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.VISIBLE);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pauseChronometer(View v) {
        if (running) {
            Glide.with(this).load(getDrawable(R.drawable.rest)).into(imgView);
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            Flg=false;
            btnStart.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.GONE);
            btnReset.setVisibility(View.VISIBLE);
        }
        btnStart.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);
        btnReset.setVisibility(View.VISIBLE);
    }

    public void resetChronometer(View v) {
        stopTime= (String) chronometer.getText();
        running = false;
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        pauseOffset = 0;
        btnStart.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
        if(!Flag){
            Exercise_History_pojo exercise_history_pojo = new Exercise_History_pojo(ExerciseName, date,time,ExerciseImageUri,stopTime);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("WorkoutHistory").child(currentUser)
                    .push().setValue(exercise_history_pojo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Flag=true;
                            Flg=true;
                            //Toast.makeText(Activity_Start_Exercise_Timer.this, "Success : ", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(Activity_Start_Exercise_Timer.this, "Fail : ", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTime= (String) chronometer.getText();
        if(!Flag){
            Exercise_History_pojo exercise_history_pojo = new Exercise_History_pojo(ExerciseName, date,time,ExerciseImageUri,stopTime);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("WorkoutHistory").child(currentUser)
                    .push().setValue(exercise_history_pojo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Flag=true;
                            //Toast.makeText(Activity_Start_Exercise_Timer.this, "Success : ", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(Activity_Start_Exercise_Timer.this, "Fail : ", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}