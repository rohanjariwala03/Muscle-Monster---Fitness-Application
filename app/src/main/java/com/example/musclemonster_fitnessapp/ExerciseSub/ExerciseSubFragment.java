package com.example.musclemonster_fitnessapp.ExerciseSub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Prod_Shopping;
import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ExerciseSubFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    MyAdapterSubExercise AdapterSubExercise;
    ArrayList<ExerciseSub_Pojo> list;

    public ExerciseSubFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_exercise__sub_, container, false);
        //mySearchView = (SearchView) view.findViewById(R.id.SearchView);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=view.findViewById(R.id.recycler2);
        database=FirebaseDatabase.getInstance().getReference("Exercise");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<ExerciseSub_Pojo>();
        AdapterSubExercise=new MyAdapterSubExercise(getContext(),list);
        recyclerView.setAdapter(AdapterSubExercise);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {


                        ExerciseSub_Pojo Obj = new ExerciseSub_Pojo();

                        Obj.setExerciseName((dataSnapshot.child("tName").getValue(String.class)));

                        list.add(Obj);
                        /* }*/
                        Toast.makeText(getContext(),"Connect",Toast.LENGTH_SHORT).show();
                        //Log.i(getTag(), "Data : " + dataSnapshot.getValue(String.class));
                    }
                    else
                    {
                        Log.i(getTag(), "NO Data : " );
                    }
                }
                AdapterSubExercise.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }

}