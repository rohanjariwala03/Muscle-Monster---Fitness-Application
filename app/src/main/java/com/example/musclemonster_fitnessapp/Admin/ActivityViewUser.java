package com.example.musclemonster_fitnessapp.Admin;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_View_User_Admin;
import com.example.musclemonster_fitnessapp.LoginSignUp.Model;
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

public class ActivityViewUser extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_View_User_Admin adapterUser;
    ArrayList<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=findViewById(R.id.recyclerUserAdmin);
        database=FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list=new ArrayList<Model>();

        adapterUser = new Adapter_View_User_Admin(this,list);



        recyclerView.setAdapter(adapterUser);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        Query query=FirebaseDatabase.getInstance().getReference("Users").orderByKey();
        String ema ;
        FirebaseUser firebaseUser;
        FirebaseAuth firebaseAuth;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        //String K = dataSnapshot.getKey();
                        //list.add(Description);
                        //*for (DataSnapshot DtSnapshot : dataSnapshot.getChildren()) {
                        Model Obj = new Model();
                        // DtSnapshot.getValue(ProductUpload_POJO.class);
                        // Obj.setFKey(dataSnapshot.getKey());
                        Obj.setFirstName(dataSnapshot.child("firstname").getValue(String.class));
                        Obj.setLastName(dataSnapshot.child("lastname").getValue(String.class));
                        Obj.setEmail(dataSnapshot.child("lastname").getValue(String.class));
                        Obj.setPhoneNumber(dataSnapshot.child("email").getValue(String.class));


//                        Obj.setFirstname((dataSnapshot.child("firstname").getValue(String.class)));
//                        Obj.setEmail((dataSnapshot.child("email").getValue(String.class)));
//                        Obj.setLastName((dataSnapshot.child("lastName").getValue(String.class)));
//                        Obj.setContact((dataSnapshot.child("contact").getValue(String.class)));
//                        Obj.setAge((dataSnapshot.child("age").getValue(String.class)));
//                        Obj.setExperience((dataSnapshot.child("experience").getValue(String.class)));
//                        Obj.setImgUri((dataSnapshot.child("imgUri").getValue(String.class)));
                        list.add(Obj);
                    } else {
                        Log.i("Result UnSuccessfull", "NO Data : ");
                    }
                }
                // set the Adapter to RecyclerView
                adapterUser.notifyDataSetChanged();

                Log.i("User Adapter ", "User Binded ");
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }


        });

    }


}
