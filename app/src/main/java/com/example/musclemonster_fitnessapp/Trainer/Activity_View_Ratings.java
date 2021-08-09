package com.example.musclemonster_fitnessapp.Trainer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.AdapterTrainerRating;
import com.example.musclemonster_fitnessapp.POJOClasses.RatingPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Activity_View_Ratings extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseAuth myAuth;
    private AdapterTrainerRating AdapterUser;
    private ArrayList<RatingPojo> list;
    private String CurrUser, TrainerEmail;
    private Query query;
    TextView TxtAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ratings);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.ratingview1);
        myAuth = FirebaseAuth.getInstance();
        CurrUser = Objects.requireNonNull(myAuth.getCurrentUser().getUid()).toString();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<RatingPojo>();
        TxtAlert = (TextView) findViewById(R.id.TxtAlert);

        AdapterUser=new AdapterTrainerRating(this,list);
        recyclerView.setAdapter(AdapterUser);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        getEmail();
    }

    private void CheckAL(ArrayList<RatingPojo> AL)
    {
        if(AL.size() == 0)
            TxtAlert.setVisibility(View.VISIBLE);
        else
            TxtAlert.setVisibility(View.GONE);
    }

    private void getEmail()
    {
        //Getting Trainer Email
        query = FirebaseDatabase.getInstance().getReference("Trainer").child(CurrUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                TrainerEmail = snapshot.child("email").getValue(String.class);
                getData();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                CheckAL(list);
            }
        });

    }

    private void getData()
    {
        //Gettting User Data
       query=FirebaseDatabase.getInstance().getReference("Rating").orderByChild("traineremail").equalTo(TrainerEmail);
       query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        RatingPojo Obj = new RatingPojo();
                        Obj.setName((dataSnapshot.child("Name").getValue(String.class)));
                        Obj.setEmail((dataSnapshot.child("email").getValue(String.class)));
                        Obj.setMsg((dataSnapshot.child("msg").getValue(String.class)));
                        Obj.setRating((dataSnapshot.child("rating").getValue(String.class)));
                        Toast.makeText(Activity_View_Ratings.this, Obj.getName(),Toast.LENGTH_LONG).show();
                        list.add(Obj);
                    } else {
                        Log.i("Result UnSuccessfull", "NO Data : ");
                    }
                }
                CheckAL(list);
                // set the Adapter to RecyclerView
                AdapterUser.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                CheckAL(list);
            }
        });

    }
}