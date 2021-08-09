package com.example.musclemonster_fitnessapp.Trainer;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Activity_My_Appointments_With_Users extends AppCompatActivity {

    DatabaseReference databaseReference,dataRef;
    String userID,room,trainerId,date;
    ImageButton btnDelete;
    private RecyclerView recyclerView;
    Adapter_Activity_My_Appointments_With_Users adapter;
    ArrayList<Pojo_Activity_My_Appointments_With_Users> list;
    TextView TxtAlert;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments_with_users);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        btnDelete=findViewById(R.id.dltAppointmentTrainer);
        databaseReference= FirebaseDatabase.getInstance().getReference("Appointments");
        dataRef=FirebaseDatabase.getInstance().getReference("Users");
        recyclerView=findViewById(R.id.recyclerviewAppointmentTrainer);
        TxtAlert=findViewById(R.id.TxtAlertTrainer);
        calendarView=findViewById(R.id.SearchDateCalendarTrainer);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list=new ArrayList<Pojo_Activity_My_Appointments_With_Users>();
        adapter = new Adapter_Activity_My_Appointments_With_Users(Activity_My_Appointments_With_Users.this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        trainerId= FirebaseAuth.getInstance().getUid();

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        calendarView.setMinDate(now);
        date=  formattedDate;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date=dayOfMonth+"/"+(month+1)+"/"+year;
                getData();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_My_Appointments_With_Users.this));
        recyclerView.setAdapter(adapter);
        list.clear();
        adapter.notifyDataSetChanged();
        getData();
    }

    public void getData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_My_Appointments_With_Users.this));
        recyclerView.setAdapter(adapter);
        list.clear();
        adapter.notifyDataSetChanged();
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                recyclerView.setLayoutManager(new LinearLayoutManager(Activity_My_Appointments_With_Users.this));
                recyclerView.setAdapter(adapter);
                list.clear();
                adapter.notifyDataSetChanged();
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                    if(dataSnapshot1.exists()){
                        room=dataSnapshot1.getKey();
                        databaseReference.child(room).child(trainerId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                    if (dataSnapshot.exists()) {
                                        String flg=dataSnapshot.child("flag").getValue(String.class);
                                        String Date=dataSnapshot.child("date").getValue(String.class);
                                        if(flg.equals("0")) {
                                            if(Date.equals(date)) {
                                                TxtAlert.setVisibility(View.GONE);
                                                Pojo_Activity_My_Appointments_With_Users ob = dataSnapshot.getValue(Pojo_Activity_My_Appointments_With_Users.class);
                                                ob.setUserID(dataSnapshot1.getKey());
                                                ob.setFKey(dataSnapshot.getKey());
                                                recyclerView.setLayoutManager(new LinearLayoutManager(Activity_My_Appointments_With_Users.this));
                                                recyclerView.setAdapter(adapter);
                                                list.add(ob);
                                                adapter.notifyDataSetChanged();
                                            }
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
                if(list.size()==0){
                    TxtAlert.setVisibility(View.VISIBLE);
                    TxtAlert.setText("You Don't Have Any Appointment On " + date);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}