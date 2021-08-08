package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Activity_My_Appointment;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_MyCoupons;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.CouponPOJO;
import com.example.musclemonster_fitnessapp.POJOClasses.Pojo_Activity_My_Appointments;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity_My_Appointments extends AppCompatActivity {

    DatabaseReference databaseReference,dataRef;
    String userID,room;
    ImageButton btnDelete;
    private RecyclerView recyclerView;
    Adapter_Activity_My_Appointment adapter;
    ArrayList<Pojo_Activity_My_Appointments> list;
    TextView TxtAlert;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        btnDelete=findViewById(R.id.dltAppointment);
        databaseReference=FirebaseDatabase.getInstance().getReference("Appointments");
        dataRef=FirebaseDatabase.getInstance().getReference("Trainer");
        recyclerView=findViewById(R.id.recyclerviewAppointment);
        TxtAlert=findViewById(R.id.TxtAlert);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list=new ArrayList<Pojo_Activity_My_Appointments>();
        adapter = new Adapter_Activity_My_Appointment(Activity_My_Appointments.this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        userID= FirebaseAuth.getInstance().getUid();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                    if(dataSnapshot1.exists()){
                       room=dataSnapshot1.getKey();
                        databaseReference.child(userID).child(room).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                list.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                    if (dataSnapshot.exists()) {
                                        String flg=dataSnapshot.child("flag").getValue(String.class);
                                        if(flg.equals("0")) {
                                            TxtAlert.setVisibility(View.GONE);
                                            Pojo_Activity_My_Appointments ob = dataSnapshot.getValue(Pojo_Activity_My_Appointments.class);
                                            ob.setUserID(dataSnapshot1.getKey());
                                            ob.setFKey(dataSnapshot.getKey());
                                            recyclerView.setLayoutManager(new LinearLayoutManager(Activity_My_Appointments.this));
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