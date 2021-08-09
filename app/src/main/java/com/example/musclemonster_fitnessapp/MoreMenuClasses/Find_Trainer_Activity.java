package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Find_Trainer;
import com.example.musclemonster_fitnessapp.POJOClasses.Find_Trainer_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Find_Trainer_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_Find_Trainer AdapterFindTrainer_list;
    ArrayList<Find_Trainer_pojo> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_trainer);

        getSupportActionBar().setTitle("Trainers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=findViewById(R.id.recyclerView_Find_Trainer);
        database=FirebaseDatabase.getInstance().getReference("Trainer");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(Find_Trainer_Activity.this));

        list=new ArrayList<Find_Trainer_pojo>();

        /*Displaying list of trainer
        which are in this app registered by admin or owner
*/
        list.clear();
        Query query=FirebaseDatabase.getInstance().getReference("Trainer");

        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {
                        list.add(SetPOJO(dataSnapshot));
                    }
                    else
                    {
                        Log.i("Data : ", "NO Data : " );
                    }
                }
                // set the Adapter to RecyclerView
                AdapterFindTrainer_list=new Adapter_Find_Trainer(Find_Trainer_Activity.this,list);
                recyclerView.setAdapter(AdapterFindTrainer_list);
                AdapterFindTrainer_list.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = (SearchView) item.getActionView();
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.removeAllViews();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                StartSearch(newText.toLowerCase());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private Find_Trainer_pojo SetPOJO(DataSnapshot dataSnapshot)
    {
        Find_Trainer_pojo Obj = new Find_Trainer_pojo();
        Obj.setFKey(dataSnapshot.getKey());
        // Set all the data in pojo class from Firebase Database
        Obj.setTFName((dataSnapshot.child("firstname").getValue(String.class)));
        Obj.setTLName((dataSnapshot.child("lastName").getValue(String.class)));
        Obj.setTEmail((dataSnapshot.child("email").getValue(String.class)));
        Obj.setTphone((dataSnapshot.child("contact").getValue(String.class)));
        //Only passing Trainer ImageUrl in realtime database and storing image in Firebase Storage
        Obj.setTimgUrl((dataSnapshot.child("imgUri").getValue(String.class)));
        return Obj;
    }

    private void StartSearch(String SQuery) {
        Query query=FirebaseDatabase.getInstance().getReference("Trainer");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {
                        String fname = dataSnapshot.child("firstname").getValue(String.class);
                        String lname = dataSnapshot.child("lastName").getValue(String.class);
                        if(fname.startsWith(SQuery) || lname.startsWith(SQuery))
                            list.add(SetPOJO(dataSnapshot));
                    }
                    else
                    {
                        Log.i("Data : ", "NO Data : " );
                    }
                }
                // set the Adapter to RecyclerView
                AdapterFindTrainer_list=new Adapter_Find_Trainer(Find_Trainer_Activity.this,list);
                recyclerView.setAdapter(AdapterFindTrainer_list);
                AdapterFindTrainer_list.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Log.i("Trainers : ", "NO Data : Q" );
            }
        });
    }
}