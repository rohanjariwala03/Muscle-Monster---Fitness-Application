package com.example.musclemonster_fitnessapp.Trainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Activity_My_Appointment;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Activity_My_Appointments;
import com.example.musclemonster_fitnessapp.POJOClasses.Pojo_Activity_My_Appointments;
import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Trainer.AdapterClasses.Adapter_Activity_My_Appointments_With_Users;
import com.example.musclemonster_fitnessapp.Trainer.PojoClasses.Pojo_Activity_My_Appointments_With_Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Activity_My_Appointments_With_Users extends AppCompatActivity {

    DatabaseReference databaseReference,dataRef;
    String userID,room,trainerId;
    ImageButton btnDelete;
    private RecyclerView recyclerView;
    Adapter_Activity_My_Appointments_With_Users adapter;
    ArrayList<Pojo_Activity_My_Appointments_With_Users> list;
    TextView TxtAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments_with_users);

        btnDelete=findViewById(R.id.dltAppointmentTrainer);
        databaseReference= FirebaseDatabase.getInstance().getReference("Appointments");
        dataRef=FirebaseDatabase.getInstance().getReference("Users");
        recyclerView=findViewById(R.id.recyclerviewAppointmentTrainer);
        TxtAlert=findViewById(R.id.TxtAlertTrainer);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list=new ArrayList<Pojo_Activity_My_Appointments_With_Users>();
        adapter = new Adapter_Activity_My_Appointments_With_Users(Activity_My_Appointments_With_Users.this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        trainerId= FirebaseAuth.getInstance().getUid();

    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                    if(dataSnapshot1.exists()){
                        room=dataSnapshot1.getKey();
                        databaseReference.child(room).child(trainerId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                    if (dataSnapshot.exists()) {
                                        String flg=dataSnapshot.child("flag").getValue(String.class);
                                        if(flg.equals("0")) {
                                            TxtAlert.setVisibility(View.GONE);
                                            Pojo_Activity_My_Appointments_With_Users ob = dataSnapshot.getValue(Pojo_Activity_My_Appointments_With_Users.class);
                                            ob.setUserID(dataSnapshot1.getKey());
                                            ob.setFKey(dataSnapshot.getKey());
                                            recyclerView.setLayoutManager(new LinearLayoutManager(Activity_My_Appointments_With_Users.this));
                                            recyclerView.setAdapter(adapter);
                                            list.add(ob);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }else {
                                        adapter.notifyDataSetChanged();
                                        TxtAlert.setVisibility(View.VISIBLE);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}