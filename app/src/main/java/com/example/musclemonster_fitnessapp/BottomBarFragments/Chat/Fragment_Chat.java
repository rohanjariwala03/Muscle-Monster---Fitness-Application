package com.example.musclemonster_fitnessapp.BottomBarFragments.Chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Find_Trainer;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_TrainerList_Chat_User;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Find_Trainer_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Find_Trainer_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.TrainerList_Chat_user_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Fragment_Chat extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_TrainerList_Chat_User AdapterTrainerList;
    ArrayList<TrainerList_Chat_user_pojo> list;
    ImageButton imgChat;

    public Fragment_Chat() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=  inflater.inflate(R.layout.fragment__chat, container, false);

        imgChat=view.findViewById(R.id.AddTrainer);
        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=view.findViewById(R.id.recyclerView_chat_Trainer_List_user);
        database=FirebaseDatabase.getInstance().getReference("Trainer");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<TrainerList_Chat_user_pojo>();

        AdapterTrainerList=new Adapter_TrainerList_Chat_User(getContext(),list);
        recyclerView.setAdapter(AdapterTrainerList);

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Find_Trainer_Activity.class);
                startActivity(intent);
            }
        });

        Query query=FirebaseDatabase.getInstance().getReference("Trainer");
        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        TrainerList_Chat_user_pojo Obj = new TrainerList_Chat_user_pojo();

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
                                    TrainerList_Chat_user_pojo Ob = new TrainerList_Chat_user_pojo();
                                    if (dataSnapshot2.exists()) {
                                        //Log.i("User",Obj.getUserID());
                                        //Log.i("User",dataSnapshot2.getKey());
                                        if(dataSnapshot2.getKey().toLowerCase().equals(room.toLowerCase())){
                                            TextView txt;
                                            txt=view.findViewById(R.id.NoChatUserSide);
                                            txt.setVisibility(View.GONE);
                                            Ob.setTrainerName(dataSnapshot.child("firstname").getValue(String.class)+ " " + dataSnapshot.child("lastName").getValue(String.class));
                                            Ob.setFKey(dataSnapshot.getKey());
                                            Ob.setTEmail(dataSnapshot.child("email").getValue(String.class));
                                            Ob.setTimgUrl(dataSnapshot.child("imgUri").getValue(String.class));
                                            list.add(Ob);
                                            Log.i("USerP:" , Ob.getTrainerName());


                                        }
                                    }
                                }
                                AdapterTrainerList=new Adapter_TrainerList_Chat_User(getContext(),list);
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
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return view;
    }
}