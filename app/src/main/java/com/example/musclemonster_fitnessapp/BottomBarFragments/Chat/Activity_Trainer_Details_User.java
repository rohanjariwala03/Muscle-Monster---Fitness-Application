package com.example.musclemonster_fitnessapp.BottomBarFragments.Chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.POJOClasses.Pojo_Activity_My_Appointments;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class Activity_Trainer_Details_User extends AppCompatActivity {

    TextView txtName, txtEmail,txtPhone;
    String nam,Emai,Num,ImgUrl,chatSize,date,userID,trainerID,room,Flag="0";
    ImageView imgView;
    CalendarView calendarView;
    TextView myAppointment;
    int size;
    Button btnBookAppointment;
    FirebaseDatabase firebaseDatabase;
    Task<Void> databaseReference;
    DatabaseReference dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_details_user);

        getSupportActionBar().setTitle("Trainer Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName=findViewById(R.id.txtTrainerNameDetailsUser);
        txtEmail=findViewById(R.id.txtTrainerEmailDetailsUser);
        txtPhone=findViewById(R.id.txtTrainerNumberDetailsUser);
        imgView=findViewById(R.id.imgViewTrainerProfileDetailsUser);
        btnBookAppointment=findViewById(R.id.btnBookAppointment);
        calendarView=findViewById(R.id.BookDateCalendar);
        myAppointment=findViewById(R.id.myDate);

        nam=getIntent().getStringExtra("TrainerName");
        Emai=getIntent().getStringExtra("TrainerEmail");
        ImgUrl=getIntent().getStringExtra("ImageUrl");
        chatSize=getIntent().getStringExtra("ChatSizes");
        trainerID=getIntent().getStringExtra("TrainerFKey");
        size= Integer.parseInt(chatSize);
        dataref=FirebaseDatabase.getInstance().getReference("Appointment");

        Glide.with(this)
                .load(ImgUrl)
                .into(imgView);

        txtName.setText(nam);
        txtEmail.setText(Emai);

        if(size>5){
           /* databaseReference=FirebaseDatabase.getInstance().getReference("Appointments");
            databaseReference.child(room).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            calendarView.setVisibility(View.GONE);
                            myAppointment.setVisibility(View.GONE);
                            btnBookAppointment.setVisibility(View.GONE);
                        }
                    }
                }@Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });*/
            calendarView.setVisibility(View.VISIBLE);
            myAppointment.setVisibility(View.VISIBLE);
            btnBookAppointment.setVisibility(View.VISIBLE);
        }else{
            calendarView.setVisibility(View.GONE);
            myAppointment.setVisibility(View.GONE);
            btnBookAppointment.setVisibility(View.GONE);
        }

        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        calendarView.setMinDate(now);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date=dayOfMonth+"/"+(month+1)+"/"+year;
                myAppointment.setText(date);
            }
        });

        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID=FirebaseAuth.getInstance().getUid();
                room=userID+trainerID;
                if(date==null){
                    Toast.makeText(getApplicationContext(),"You cannot select today's date",Toast.LENGTH_SHORT).show();
                }else {
                   Pojo_Activity_My_Appointments obj=new Pojo_Activity_My_Appointments(nam,date,Flag);
                    databaseReference=FirebaseDatabase.getInstance().getReference("Appointments")
                            .child(userID).child(trainerID).push().setValue(obj)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Snackbar snackbar=Snackbar.make(findViewById(R.id.layoutC),
                                            "Appointment Taken Successfully",
                                            Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    date=null;
                                    myAppointment.setText("");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {

                                }
                            });

                }
            }
        });
    }
}