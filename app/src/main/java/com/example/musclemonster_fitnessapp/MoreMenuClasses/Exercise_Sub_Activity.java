package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.ExerciseSub.Exercise_Sub_Pojo;
import com.example.musclemonster_fitnessapp.ExerciseSub.MyAdapter_Exercise_Sub;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Exercise_Sub_Activity extends AppCompatActivity {

    String ExerciseCat;
    TextView Catagory;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    MyAdapter_Exercise_Sub AdapterSubExercise;
    ArrayList<Exercise_Sub_Pojo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_sub);

        ExerciseCat = getIntent().getStringExtra("ExerciseCat");
        Toast.makeText(this,"" + ExerciseCat,Toast.LENGTH_LONG).show();
        Log.i("asd", "Data : " + ExerciseCat);

        //Giving drefernce to Firebase database References
        firebaseDatabase=FirebaseDatabase.getInstance();

        recyclerView=findViewById(R.id.recycler2);

        //Giving path to taking data
        //database=FirebaseDatabase.getInstance().getReference("Exercise");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initializing list view to print
        list=new ArrayList<Exercise_Sub_Pojo>();

        AdapterSubExercise=new MyAdapter_Exercise_Sub(this,list);
        recyclerView.setAdapter(AdapterSubExercise);


        Query query=FirebaseDatabase.getInstance().getReference("Exercise")
                .orderByChild("tCat").equalTo(ExerciseCat);

        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            //If database get some data then this will fire
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        Exercise_Sub_Pojo Obj = new Exercise_Sub_Pojo();

                        //Final collection name to get some data data.
                        //It will be singular or multiple

                        Obj.setExerciseName((dataSnapshot.child("tName").getValue(String.class)));
                        Obj.setExerciseCat((dataSnapshot.child("tCat").getValue(String.class)));
                        Obj.setExerciseDesc((dataSnapshot.child("tDesc").getValue(String.class)));
                        Obj.setImageUri((dataSnapshot.child("imgUri1").getValue(String.class)));

                        list.add(Obj);
                        //Toast.makeText(Exercise_Sub_Activity.this,"Connect",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.i("Tag : ", "NO Data : " );
                    }
                }
                AdapterSubExercise.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}

/* Query query=FirebaseDatabase.getInstance().getReference("Exercise")
                .orderByChild("tCat").equalTo("Chest");
        */
//query.addValue......