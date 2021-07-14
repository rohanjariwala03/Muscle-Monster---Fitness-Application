package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_activity_workout_history;
import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_History_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity_Workout_History extends AppCompatActivity {

    TextView txtExName;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_activity_workout_history myAdapter_exercise;
    ArrayList<Exercise_History_pojo> list;
    String currentUser,Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        getSupportActionBar().setTitle("Workout History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        txtExName=findViewById(R.id.history_exerciseName);
        recyclerView=findViewById(R.id.recycler_workoutHistory);
        firebaseDatabase=FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance().getReference("WorkoutHistory").child(currentUser);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        list=new ArrayList<>();
        myAdapter_exercise=new Adapter_activity_workout_history(getApplicationContext(),list);
        recyclerView.setAdapter(myAdapter_exercise);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        TextView txt=findViewById(R.id.noHistory);
                        txt.setVisibility(View.GONE);
                        Exercise_History_pojo obj=new Exercise_History_pojo();
                        obj.setExName((dataSnapshot.child("exName").getValue(String.class)));
                        obj.setExTime((dataSnapshot.child("exTime").getValue(String.class)));
                        obj.setExDate((dataSnapshot.child("exDate").getValue(String.class)));
                        obj.setExImageUri((dataSnapshot.child("exImageUri").getValue(String.class)));
                        list.add(obj);

                        //Log.i(getTag(), "Data : " + dataSnapshot.getValue(String.class));
                    }
                    else
                    {
                        Log.i("Data Are below", "NO Data : " );
                    }
                    myAdapter_exercise.notifyDataSetChanged();
                }
                myAdapter_exercise.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}