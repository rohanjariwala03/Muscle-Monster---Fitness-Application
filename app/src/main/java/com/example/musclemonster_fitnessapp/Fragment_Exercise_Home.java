package com.example.musclemonster_fitnessapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Exercise_Home extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    MyAdapter_Exercise myAdapter_exercise;
    ArrayList<String> list;

    public Fragment_Exercise_Home() {
        // Required empty public constructor
    }

    private void setContentView(int activity_main) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=  inflater.inflate(R.layout.fragment__exercise__home, container, false);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=v.findViewById(R.id.recycler1);
       database=FirebaseDatabase.getInstance().getReference("ExerciseCategory");
        recyclerView.setHasFixedSize(true);



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<>();
        myAdapter_exercise=new MyAdapter_Exercise(getContext(),list);
        recyclerView.setAdapter(myAdapter_exercise);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        String Description = dataSnapshot.getValue(String.class);
                        list.add(Description);

                        //Log.i(getTag(), "Data : " + dataSnapshot.getValue(String.class));
                    }
                    else
                    {
                        Log.i(getTag(), "NO Data : " );
                    }
                }
                myAdapter_exercise.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }


}