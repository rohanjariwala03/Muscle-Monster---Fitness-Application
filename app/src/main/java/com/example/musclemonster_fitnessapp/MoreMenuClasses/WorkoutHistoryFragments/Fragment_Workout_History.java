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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Activity_My_Appointment;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_activity_workout_history;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_History_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.Pojo_Activity_My_Appointments;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Fragment_Workout_History extends Fragment {

    TextView txtExName;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_activity_workout_history myAdapter_exercise;
    ArrayList<Exercise_History_pojo> list;
    String currentUser,Date;

    public Fragment_Workout_History() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment__workout__history, container, false);

        currentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        txtExName=v.findViewById(R.id.history_exerciseName);
        recyclerView=v.findViewById(R.id.recycler_workoutHistory);
        firebaseDatabase=FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance().getReference("WorkoutHistory").child(currentUser);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        list=new ArrayList<>();
        myAdapter_exercise=new Adapter_activity_workout_history(getContext(),list);
        recyclerView.setAdapter(myAdapter_exercise);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        TextView txt=v.findViewById(R.id.noHistory);
                        txt.setVisibility(View.GONE);
                        Exercise_History_pojo obj=new Exercise_History_pojo();
                        obj.setExName((dataSnapshot.child("exName").getValue(String.class)));
                        obj.setExTime((dataSnapshot.child("exTime").getValue(String.class)));
                        obj.setExDate((dataSnapshot.child("exDate").getValue(String.class)));
                        obj.setTime((dataSnapshot.child("time").getValue(String.class)));
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

        return v;
    }
}