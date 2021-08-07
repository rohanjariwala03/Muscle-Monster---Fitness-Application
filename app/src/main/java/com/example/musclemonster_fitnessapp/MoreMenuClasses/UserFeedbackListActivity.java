package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.musclemonster_fitnessapp.AdapterClasses.AdapterUserFeedbackList;
import com.example.musclemonster_fitnessapp.POJOClasses.FeedbackPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class UserFeedbackListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    AdapterUserFeedbackList AdapterUser;
    ArrayList<FeedbackPojo> list;
    ImageButton btnDelete;
    Button btn_submitfeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback_list);
        getSupportActionBar().setTitle("View Feedbacks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));
        btn_submitfeedback=(Button)findViewById(R.id.btn_submitfeedback);
        btn_submitfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserFeedbackListActivity.this, UserGiveFeedbackActivity.class));

            }
        });

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=findViewById(R.id.recyclerUserAdmin);
        database=FirebaseDatabase.getInstance().getReference("Feedback");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<FeedbackPojo>();

        AdapterUser=new AdapterUserFeedbackList(this,list);
        recyclerView.setAdapter(AdapterUser);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

    }
    @Override
    protected void onStop() {
        super.onStop();
        list.clear();
        AdapterUser.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        AdapterUser.notifyDataSetChanged();
        getData();
    }

    private void getData() {

        Query query=FirebaseDatabase.getInstance().getReference("Feedback").orderByKey();
        String ema ;
        FirebaseUser firebaseUser;
        FirebaseAuth firebaseAuth;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        FeedbackPojo Obj = new FeedbackPojo();
                        Obj.setName((dataSnapshot.child("name").getValue(String.class)));
                        Obj.setEmail((dataSnapshot.child("email").getValue(String.class)));
                        Obj.setMsg((dataSnapshot.child("msg").getValue(String.class)));

                        list.add(Obj);
                    } else {
                        Log.i("Result UnSuccessfull", "NO Data : ");
                    }
                }
                // set the Adapter to RecyclerView
                AdapterUser.notifyDataSetChanged();

                Log.i("Feedback Adapter ", "Product Binded ");
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
            }
        });

    }
}