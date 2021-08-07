package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RateTrainerActivity extends AppCompatActivity {

    ImageView timage;
    TextView trainername,tvtraineremail;
    EditText etname,etreview,etemail;
    RatingBar rv_rating;
    Button btn_submit;
    String imgUri;
    ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_trainer);
        getSupportActionBar().setTitle("Give Rating");
        loadingBar = new ProgressDialog(RateTrainerActivity.this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        timage=(ImageView)findViewById(R.id.timage);
        trainername=(TextView)findViewById(R.id.trainername);
        trainername.setText("Trainer Name : "+getIntent().getStringExtra("TrainerFName"));
        tvtraineremail=(TextView)findViewById(R.id.tvtraineremail);
        tvtraineremail.setText("Trainer Email : "+getIntent().getStringExtra("TrainerEmail"));

        imgUri=getIntent().getStringExtra("TrainerImageUrl");
        Glide.with(RateTrainerActivity.this)
                .load(imgUri)
                .into(timage);

        etname=(EditText) findViewById(R.id.etname);
        etreview=(EditText)findViewById(R.id.etreview);
        etemail=(EditText)findViewById(R.id.etemail);
        rv_rating=(RatingBar)findViewById(R.id.rv_rating);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Rating");
                loadingBar.setMessage("Please wait, while we are Adding the Rating.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                giveRating();
            }
        });
    }

    private void giveRating() {

        String name = etname.getText().toString();
        String email = etemail.getText().toString();
        String msg = etreview.getText().toString();

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());
                String rating = String.valueOf(rv_rating.getRating());
                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("Name", name);
                userdataMap.put("firstname", getIntent().getStringExtra("TrainerFName"));
                userdataMap.put("traineremail",getIntent().getStringExtra("TrainerEmail"));
                userdataMap.put("email", email);
                userdataMap.put("msg", msg);
                userdataMap.put("date", date);
                userdataMap.put("rating", rating);

                RootRef.child("Rating").child(name+date).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RateTrainerActivity.this, "Rating Submited SuccessFully.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent = new Intent(RateTrainerActivity.this, Find_Trainer_Activity.class);
                                    startActivity(intent);
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(RateTrainerActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}