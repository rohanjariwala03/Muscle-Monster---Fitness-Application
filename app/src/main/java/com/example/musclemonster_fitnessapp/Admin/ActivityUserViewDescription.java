package com.example.musclemonster_fitnessapp.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.POJOClasses.CouponPOJO;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class ActivityUserViewDescription extends AppCompatActivity implements ExampleDialog.ExampleDialogListener{

    String CurrDate, ExpiryDate;
    String TrainerName,TrainerLastName,TrainerEmail,TrainerCon,UserKey,ItemImageUri;
    ImageView ImgView;
    TextView EditFirstName,EditLastName, EditEmail,EditPhone,EditAge,EditExp;
    ImageButton imgbtn;
    String defaultUrl;
    FloatingActionButton fab;
    DatabaseReference drRef;
    private Calendar calendar;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_description);

        fab = findViewById(R.id.fab);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.support_toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b134f")));

        EditFirstName = (TextView)findViewById(R.id.txtTrainerNameAd);
        EditLastName = (TextView)findViewById(R.id.txtTrainerLastNameAd);
        EditEmail = (TextView)findViewById(R.id.txtTrainerEmailAd);
        EditPhone = (TextView)findViewById(R.id.txtTrainerContactAd);



        ImgView = (ImageView) findViewById(R.id.TrainerImageViewAd);
        imgbtn=(ImageButton)findViewById(R.id.btnDeleteUserAdmin);

        TrainerName = getIntent().getStringExtra("UserName");
        TrainerLastName = getIntent().getStringExtra("UserLastName");
        TrainerEmail = getIntent().getStringExtra("UserEmail");
        ItemImageUri = getIntent().getStringExtra("UserImageUri");
        TrainerCon = getIntent().getStringExtra("Phone");
        UserKey = getIntent().getStringExtra("UserKey");

        EditFirstName.setText(TrainerName);
        EditLastName.setText(TrainerLastName);
        EditEmail.setText(TrainerEmail);
        EditPhone.setText(TrainerCon);

        defaultUrl="https://firebasestorage.googleapis.com/v0/b/muscle-monster-fitnessap-8b451.appspot.com/o/DefaultImage%2Fcircular.png?alt=media&token=783c1888-61d2-40fe-82aa-9f62c184e5ec";
        if(ItemImageUri.equals("null")){
            Glide.with(this)
                    .load(defaultUrl)
                    .into(ImgView);
        }else{
            Glide.with(this)
                    .load(ItemImageUri)
                    .into(ImgView);
        }

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Users").orderByChild("email").equalTo(EditEmail.getText().toString());

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }

                        Toast.makeText(getApplicationContext(),"User Profile Deleted Successfully",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),ActivityViewUsers.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void applyTexts(String Discount) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Coupons");
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        CurrDate = sdf.format(calendar.getTime());

        calendar.add(Calendar.MONTH, 6);
        ExpiryDate =  sdf.format(calendar.getTime());

        CouponPOJO couponPOJO = new CouponPOJO(UserKey,Discount,CurrDate,ExpiryDate,UUID.randomUUID().toString());
        ref.push().setValue(couponPOJO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext() , "Coupon Added To " + TrainerName + " " + TrainerLastName, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
