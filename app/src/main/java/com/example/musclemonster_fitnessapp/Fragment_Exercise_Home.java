package com.example.musclemonster_fitnessapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Exercise_Home extends Fragment {
    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;
    TextView textView;

    public Fragment_Exercise_Home() {
        // Required empty public constructor
    }

    private void setContentView(int activity_main) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment__exercise__home, container, false);
        // initializing our object class variable.

        textView = v.findViewById(R.id.textView2);
        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference("Exercises").child("Abs").child("Ex01");
//

//
//        // calling method
//        // for getting data.
        getdata();



    }

    private void getdata() {
        try {


//             calling add value event listener method
//             for getting the values from database.
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            List<String> list = new ArrayList<>();
                            String Description = snapshot.child("Exercise Description").getValue(String.class);
                            String name = snapshot.child("Exercise Name").getValue(String.class);
                            String id = snapshot.child("Exercise ID").getValue(String.class);
                            String send = "ID: " + id + " name: " + name + " Description: " + Description;

                            textView.setText(send);
                        }

                    }else{
                        Toast.makeText(getActivity(),"The data do not exist",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // calling on cancelled method when we receive
                    // any error or we are not able to get the data.
                    Toast.makeText(getActivity(), "Fail to get data.", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}