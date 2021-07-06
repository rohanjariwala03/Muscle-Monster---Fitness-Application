package com.example.musclemonster_fitnessapp.Trainer.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_View_Trainer_Admin;
import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.POJOClasses.TrainerPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Activity_Chat_User_List extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_Chat_List_Trainer AdapterTrainer;
    ArrayList<Chat_List_pojo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_list);

        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.recyclerviewUserListTrainer);
        database = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Chat_List_pojo>();

        AdapterTrainer = new Adapter_Chat_List_Trainer(this, list);
        recyclerView.setAdapter(AdapterTrainer);

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByKey();
        String ema;
        FirebaseUser firebaseUser;
        FirebaseAuth firebaseAuth;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        //String K = dataSnapshot.getKey();
                        //list.add(Description);
                        //*for (DataSnapshot DtSnapshot : dataSnapshot.getChildren()) {
                        Chat_List_pojo Obj = new Chat_List_pojo();
                        // DtSnapshot.getValue(ProductUpload_POJO.class);

                        Obj.setUserFName((dataSnapshot.child("firstName").getValue(String.class)
                                + " "+ dataSnapshot.child("lastName").getValue(String.class)));
                        //Obj.setUserEmail((dataSnapshot.child("lastName").getValue(String.class)));
                        Obj.setUserEmail((dataSnapshot.child("email").getValue(String.class)));
                        Obj.setFKey(dataSnapshot.getKey());
                        list.add(Obj);
                        //Log.i("First Name ", dataSnapshot.child("firstName").getValue(String.class) );
                        //Log.i("Last Name ", dataSnapshot.child("lastName").getValue(String.class) );
                    } else {
                        Log.i("Result UnSuccessfull", "NO Data : ");
                    }
                }
                // set the Adapter to RecyclerView
                AdapterTrainer.notifyDataSetChanged();

                //Log.i("Chat List Adapter ", "List of User Binded ");
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }


        });

    }
}