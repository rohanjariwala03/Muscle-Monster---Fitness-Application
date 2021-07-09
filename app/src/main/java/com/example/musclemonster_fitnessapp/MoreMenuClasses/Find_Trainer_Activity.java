package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

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

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=findViewById(R.id.recyclerView_Find_Trainer);
        database=FirebaseDatabase.getInstance().getReference("Trainer");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(Find_Trainer_Activity.this));

        list=new ArrayList<Find_Trainer_pojo>();

        /*AdapterChat_list=new Adapter_Fragment_Chat(getContext(),list);
        recyclerView.setAdapter(AdapterChat_list);
*/
        Query query=FirebaseDatabase.getInstance().getReference("Trainer");

        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        Find_Trainer_pojo Obj = new Find_Trainer_pojo();

                        Obj.setFKey(dataSnapshot.getKey());
                        // Obj.setTid(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        Obj.setTFName((dataSnapshot.child("firstname").getValue(String.class)));
                        Obj.setTLName((dataSnapshot.child("lastName").getValue(String.class)));
                        Obj.setTEmail((dataSnapshot.child("email").getValue(String.class)));
                        Obj.setTphone((dataSnapshot.child("contact").getValue(String.class)));
                        //Obj.setTid((dataSnapshot.child("trainerId").getValue(String.class)));
                        Obj.setTimgUrl((dataSnapshot.child("imgUri").getValue(String.class)));
                        list.add(Obj);
                        /*Log.i("Chat Adapter ", "Chat List Binded ");
                        Log.i("Chat Adapter ", String.valueOf(list.size()));
                        Log.i("Chat Name ", Obj.getTid());*/
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
}