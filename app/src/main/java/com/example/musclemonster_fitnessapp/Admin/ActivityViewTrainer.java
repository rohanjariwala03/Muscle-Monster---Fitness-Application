package com.example.musclemonster_fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.musclemonster_fitnessapp.Admin.AdapterClasses.Adapter_View_Trainer_Admin;
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
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class ActivityViewTrainer extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_View_Trainer_Admin AdapterTrainer;
    ArrayList<TrainerPojo> list;
    ImageButton btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trainer);

        getSupportActionBar().setTitle("Trainers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));

        /*BtnSearch = (Button) view.findViewById(R.id.Btnsearch);*/
        /*EditSearch = (EditText) view.findViewById(R.id.search);*/


        /*mySearchView = (SearchView) view.findViewById(R.id.SearchView);*/
        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=findViewById(R.id.recyclerTrainerAdmin);
        database=FirebaseDatabase.getInstance().getReference("Trainer");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<TrainerPojo>();
    /*AdapterShopping=new Adapter_Prod_Shopping(getContext(),list);
    recyclerView.setAdapter(AdapterShopping);*/
        AdapterTrainer=new Adapter_View_Trainer_Admin(this,list);
        recyclerView.setAdapter(AdapterTrainer);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter



        /*Query query=FirebaseDatabase.getInstance().getReference("Trainer").orderByKey();
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
                        TrainerPojo Obj = new TrainerPojo();
                        // DtSnapshot.getValue(ProductUpload_POJO.class);
                        // Obj.setFKey(dataSnapshot.getKey());

                        Obj.setFirstname((dataSnapshot.child("firstname").getValue(String.class)));
                        Obj.setEmail((dataSnapshot.child("email").getValue(String.class)));
                        Obj.setLastName((dataSnapshot.child("lastName").getValue(String.class)));
                        Obj.setContact((dataSnapshot.child("contact").getValue(String.class)));
                        Obj.setAge((dataSnapshot.child("age").getValue(String.class)));
                        Obj.setExperience((dataSnapshot.child("experience").getValue(String.class)));
                        Obj.setImgUri((dataSnapshot.child("imgUri").getValue(String.class)));
                        list.add(Obj);
                    } else {
                        Log.i("Result UnSuccessfull", "NO Data : ");
                    }
                }
                // set the Adapter to RecyclerView
                AdapterTrainer.notifyDataSetChanged();

                Log.i("Product Adapter ", "Product Binded ");
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }


        });*/

    }

    @Override
    protected void onStop() {
        super.onStop();
        list.clear();
        AdapterTrainer.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        AdapterTrainer.notifyDataSetChanged();
        getData();
    }

    private void getData() {
        Query query=FirebaseDatabase.getInstance().getReference("Trainer").orderByKey();
        String ema ;
        FirebaseUser firebaseUser;
        FirebaseAuth firebaseAuth;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        //String K = dataSnapshot.getKey();
                        //list.add(Description);
                        //*for (DataSnapshot DtSnapshot : dataSnapshot.getChildren()) {
                        TrainerPojo Obj = new TrainerPojo();
                        // DtSnapshot.getValue(ProductUpload_POJO.class);
                        // Obj.setFKey(dataSnapshot.getKey());

                        Obj.setFirstname((dataSnapshot.child("firstname").getValue(String.class)));
                        Obj.setEmail((dataSnapshot.child("email").getValue(String.class)));
                        Obj.setLastName((dataSnapshot.child("lastName").getValue(String.class)));
                        Obj.setContact((dataSnapshot.child("contact").getValue(String.class)));
                        Obj.setAge((dataSnapshot.child("age").getValue(String.class)));
                        Obj.setExperience((dataSnapshot.child("experience").getValue(String.class)));
                        Obj.setImgUri((dataSnapshot.child("imgUri").getValue(String.class)));
                        list.add(Obj);
                    } else {
                        Log.i("Result UnSuccessfull", "NO Data : ");
                    }
                }
                // set the Adapter to RecyclerView
                AdapterTrainer.notifyDataSetChanged();

                Log.i("Product Adapter ", "Product Binded ");
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }


        });
    }
}