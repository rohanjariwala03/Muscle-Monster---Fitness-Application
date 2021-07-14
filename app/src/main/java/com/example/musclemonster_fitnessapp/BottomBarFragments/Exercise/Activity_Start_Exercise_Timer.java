package com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_History_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity_Start_Exercise_Timer extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    FirebaseAuth mAuth;
    String currentUser,ExerciseName,ExerciseImageUri;
    FirebaseDatabase firebaseDatabase;
    Task<Void> databaseReference;
    int Flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise_timer);

        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        //databaseReference=FirebaseDatabase.getInstance().getReference();
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        ExerciseName=getIntent().getStringExtra("exName");
        ExerciseImageUri=getIntent().getStringExtra("imgUri");

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();

    }

    private void startTimer() {

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
        String date=dateformat.format(new Date());
        String time=timeformat.format(new Date());
        //Toast.makeText(Activity_Start_Exercise_Timer.this, "Time : " + time, Toast.LENGTH_LONG).show();

        if(Flag==0) {
            Exercise_History_pojo exercise_history_pojo = new Exercise_History_pojo(ExerciseName, date,time,ExerciseImageUri);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("WorkoutHistory").child(currentUser)
                    /*.child(his)*/
                    .push().setValue(exercise_history_pojo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Flag = 1;
                            //Toast.makeText(Activity_Start_Exercise_Timer.this, "Success : ", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(Activity_Start_Exercise_Timer.this, "FAil : ", Toast.LENGTH_LONG).show();
                        }
                    });
        }

        //Toast.makeText(Activity_Start_Exercise_Timer.this,"Current Time : "+ dateFormat.format(new Date()).toString(),Toast.LENGTH_SHORT).show();
        /*databaseReference=FirebaseDatabase.getInstance().getReference().child("WorkoutHistory").child(currentUser)
                .child(his)
                .push().setValue(ExerciseName).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        databaseReference=FirebaseDatabase.getInstance().getReference().child("WorkoutHistory").child(currentUser)
                                .child(his)
                                .push().setValue(date).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                                    }
                                });
                       // Toast.makeText(Activity_Start_Exercise_Timer.this,"Success : ",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(Activity_Start_Exercise_Timer.this,"Fail : ",Toast.LENGTH_LONG).show();
                    }
                });*/

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

}