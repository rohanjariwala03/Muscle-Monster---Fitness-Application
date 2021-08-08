package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musclemonster_fitnessapp.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Activity_My_Appointments extends AppCompatActivity {

    CalendarView calendarView;
    DatabaseReference databaseReference;
    String userID,TrainerID="9r9DRb6eIgVL14Hpi0S5AYQD2tA3",room;
    ArrayList<String> appointmentBooked;
    String s;

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        calendarView=(CalendarView) findViewById(R.id.AppointmentCalendar);
        databaseReference=FirebaseDatabase.getInstance().getReference("Appointments");

        userID= FirebaseAuth.getInstance().getUid();
        room=userID+TrainerID;

        databaseReference.child(room).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                         s = dataSnapshot.getValue(String.class);
                        Toast.makeText(getApplicationContext(), "Dates:" + s, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}