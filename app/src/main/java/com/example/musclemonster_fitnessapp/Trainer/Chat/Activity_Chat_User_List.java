package com.example.musclemonster_fitnessapp.Trainer.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Trainer.AdapterClasses.Adapter_Chat_List_Trainer;
import com.example.musclemonster_fitnessapp.Trainer.PojoClasses.Chat_List_pojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Chat_User_List extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_Chat_List_Trainer AdapterTrainerList;
    ArrayList<Chat_List_pojo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_list);

        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=findViewById(R.id.recyclerviewUserListTrainer);
        database=FirebaseDatabase.getInstance().getReference("Trainer");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_Chat_User_List.this));

        list=new ArrayList<Chat_List_pojo>();

        AdapterTrainerList=new Adapter_Chat_List_Trainer(Activity_Chat_User_List.this,list);
        recyclerView.setAdapter(AdapterTrainerList);

        /*list.clear();
        Query query=FirebaseDatabase.getInstance().getReference("Users");
        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        Chat_List_pojo Obj = new Chat_List_pojo();

                        //Obj.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        //Obj.setTrainerID(dataSnapshot.getKey());
                        String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String TID=dataSnapshot.getKey();

                        //Obj.setTFName((dataSnapshot.child("firstname").getValue(String.class)));
                        String room=UID+TID;
                        Query que=FirebaseDatabase.getInstance().getReference("chats");
                        que.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot2:snapshot.getChildren()) {
                                    Chat_List_pojo Ob = new Chat_List_pojo();
                                    if (dataSnapshot2.exists()) {
                                        //Log.i("User",Obj.getUserID());
                                        //Log.i("User",dataSnapshot2.getKey());
                                        if(dataSnapshot2.getKey().toLowerCase().equals(room.toLowerCase())){
                                            Ob.setTrainerName(dataSnapshot.child("firstName").getValue(String.class)+ " " + dataSnapshot.child("lastName").getValue(String.class));
                                            Ob.setFKey(dataSnapshot.getKey());
                                            Ob.setTEmail(dataSnapshot.child("email").getValue(String.class));
                                            if(dataSnapshot.child("imageUri").getValue(String.class).equals("null")){
                                                Ob.setTimgUrl("null");
                                            }else {
                                                Ob.setTimgUrl(dataSnapshot.child("imageUri").getValue(String.class));
                                            }
                                            list.add(Ob);
                                            //Log.i("USerP:" , Ob.getTrainerName());
                                            TextView txt;
                                            txt=findViewById(R.id.NoChatTrainerSide);
                                            txt.setVisibility(View.GONE);

                                        }
                                    }
                                }
                                AdapterTrainerList=new Adapter_Chat_List_Trainer(Activity_Chat_User_List.this,list);
                                recyclerView.setAdapter(AdapterTrainerList);
                                AdapterTrainerList.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                            }
                        });
                        *//*Log.i("Chat Adapter ", String.valueOf(list.size()));
                        Log.i("Chat Name ", Obj.getTid());*//*
                        AdapterTrainerList.notifyDataSetChanged();
                    }
                    else
                    {
                        Log.i("Data : ", "NO Data : " );
                    }
                }

                // set the Adapter to RecyclerView

            }

            @Override
            public void onCancelled(@NonNull @com.google.firebase.database.annotations.NotNull DatabaseError error) {

            }
        });*/

    }

    @Override
    protected void onStop() {
        super.onStop();
        list.clear();
        AdapterTrainerList = new Adapter_Chat_List_Trainer(Activity_Chat_User_List.this, list);
        recyclerView.setAdapter(AdapterTrainerList);
        AdapterTrainerList.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        AdapterTrainerList = new Adapter_Chat_List_Trainer(Activity_Chat_User_List.this, list);
        recyclerView.setAdapter(AdapterTrainerList);
        AdapterTrainerList.notifyDataSetChanged();
        getData();
    }

    private void getData() {
        list.clear();
        Query query=FirebaseDatabase.getInstance().getReference("Users");
        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        Chat_List_pojo Obj = new Chat_List_pojo();

                        //Obj.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        //Obj.setTrainerID(dataSnapshot.getKey());
                        String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String TID=dataSnapshot.getKey();

                        //Obj.setTFName((dataSnapshot.child("firstname").getValue(String.class)));
                        String room=UID+TID;
                        Query que=FirebaseDatabase.getInstance().getReference("chats");
                        que.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot2:snapshot.getChildren()) {
                                    Chat_List_pojo Ob = new Chat_List_pojo();
                                    if (dataSnapshot2.exists()) {
                                        //Log.i("User",Obj.getUserID());
                                        //Log.i("User",dataSnapshot2.getKey());
                                        if(dataSnapshot2.getKey().toLowerCase().equals(room.toLowerCase())){
                                            Ob.setTrainerName(dataSnapshot.child("firstName").getValue(String.class)+ " " + dataSnapshot.child("lastName").getValue(String.class));
                                            Ob.setFKey(dataSnapshot.getKey());
                                            Ob.setTEmail(dataSnapshot.child("email").getValue(String.class));
                                            if(dataSnapshot.child("imageUri").getValue(String.class).equals("null")){
                                                Ob.setTimgUrl("null");
                                            }else {
                                                Ob.setTimgUrl(dataSnapshot.child("imageUri").getValue(String.class));
                                            }
                                            list.add(Ob);
                                            //Log.i("USerP:" , Ob.getTrainerName());
                                            TextView txt;
                                            txt=findViewById(R.id.NoChatTrainerSide);
                                            txt.setVisibility(View.GONE);

                                        }
                                    }
                                }
                                AdapterTrainerList=new Adapter_Chat_List_Trainer(Activity_Chat_User_List.this,list);
                                recyclerView.setAdapter(AdapterTrainerList);
                                AdapterTrainerList.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                            }
                        });
                        /*Log.i("Chat Adapter ", String.valueOf(list.size()));
                        Log.i("Chat Name ", Obj.getTid());*/
                        AdapterTrainerList.notifyDataSetChanged();
                    }
                    else
                    {
                        Log.i("Data : ", "NO Data : " );
                    }
                }

                // set the Adapter to RecyclerView

            }

            @Override
            public void onCancelled(@NonNull @com.google.firebase.database.annotations.NotNull DatabaseError error) {

            }
        });

    }
}