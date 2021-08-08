package com.example.musclemonster_fitnessapp.MoreMenuClasses.WorkoutHistoryFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Fragment_Saved_Workouts;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_activity_workout_history;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Activity_My_Appointments;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_History_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.MyWorkout_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Fragment_Saved_Workouts extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database,db;
    Adapter_Fragment_Saved_Workouts myAdapter_exercise;
    ArrayList<MyWorkout_pojo> list;
    String currentUser,room;
    TextView noSavedWorkouts;

    public Fragment_Saved_Workouts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment__saved__workouts, container, false);

        currentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        noSavedWorkouts=v.findViewById(R.id.noSavedWorkouts);
        recyclerView=v.findViewById(R.id.recycler_SavedWorkout);
        firebaseDatabase=FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance().getReference("SavedWorkouts");
        db=FirebaseDatabase.getInstance().getReference("Exercise");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list=new ArrayList<>();
        myAdapter_exercise=new Adapter_Fragment_Saved_Workouts(getContext(),list);
        recyclerView.setAdapter(myAdapter_exercise);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                    if(dataSnapshot1.exists()){
                        /*Log.i("Value : ", dataSnapshot.child("tName").getValue(String.class));*/
                        room=dataSnapshot1.child("tName").getValue(String.class)+currentUser;
                        database.child(room).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        MyWorkout_pojo obj=new MyWorkout_pojo();
                                        obj.setFlag(snapshot.child("flag").getValue(String.class));
                                        if(obj.getFlag().equals("Delete")) {
                                            noSavedWorkouts.setVisibility(View.GONE);
                                            obj.setWorkoutName((snapshot.child("workoutName").getValue(String.class)));
                                            obj.settCat((dataSnapshot1.child("tCat").getValue(String.class)));
                                            obj.settDesc((dataSnapshot1.child("tDesc").getValue(String.class)));
                                            obj.settSteps((dataSnapshot1.child("tSteps").getValue(String.class)));
                                            obj.setImgUrl(dataSnapshot1.child("imgUri1").getValue(String.class));
                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                            recyclerView.setAdapter(myAdapter_exercise);
                                            list.add(obj);
                                            myAdapter_exercise.notifyDataSetChanged();
                                        }
                                    }
                                myAdapter_exercise.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            }
                        });
                        myAdapter_exercise.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });

        return v;
    }
}