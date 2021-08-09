package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RateTrainerActivity extends AppCompatActivity {

    private ImageView timage;
    private TextView trainername,tvtraineremail;
    private EditText etname,etreview,etemail;
    private RatingBar rv_rating;
    private Button btn_submit;
    private String imgUri,CurUser,RatingKey;
    private ProgressDialog loadingBar;
    private Query query;
    private FirebaseAuth myAuth;
    private String FLAG = "INSERT";
    private Menu mMenu;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_trainer);
        getSupportActionBar().setTitle("Give Rating");

        loadingBar = new ProgressDialog(RateTrainerActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAuth =FirebaseAuth.getInstance();
        CurUser = myAuth.getCurrentUser().getUid().toString();

        timage=(ImageView)findViewById(R.id.timage);
        trainername=(TextView)findViewById(R.id.trainername);
        tvtraineremail=(TextView)findViewById(R.id.tvtraineremail);

        etname=(EditText) findViewById(R.id.etname);
        etreview=(EditText)findViewById(R.id.etreview);
        etemail=(EditText)findViewById(R.id.etemail);
        rv_rating=(RatingBar)findViewById(R.id.rv_rating);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        String s =getIntent().getStringExtra("TrainerFName");
        if(s!=null) {
            // For User To Give Rating
            FLAG = "INSERT";

            trainername.setText("Trainer Name : " + getIntent().getStringExtra("TrainerFName"));
            tvtraineremail.setText("Trainer Email : " + getIntent().getStringExtra("TrainerEmail"));
            imgUri = getIntent().getStringExtra("TrainerImageUrl");
            Glide.with(RateTrainerActivity.this)
                    .load(imgUri)
                    .into(timage);
            query=FirebaseDatabase.getInstance().getReference("Users").child(CurUser);
            query.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        etname.setText(snapshot.child("firstName").getValue(String.class)
                                + " " + snapshot.child("lastName").getValue(String.class));
                        etemail.setText(snapshot.child("email").getValue(String.class));
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
        else{
            //For User to Update Rating
            FLAG = "UPDATE";

            trainername.setText("Trainer Name : " + getIntent().getStringExtra("TrainerName"));
            tvtraineremail.setText("Trainer Email : " + getIntent().getStringExtra("TrainerEmail"));
            RatingKey = getIntent().getStringExtra("RatingKey");
            etname.setText(getIntent().getStringExtra("UserName"));
            etemail.setText(getIntent().getStringExtra("UserEmail"));
            etreview.setText(getIntent().getStringExtra("UserMsg"));
            rv_rating.setRating(Float.parseFloat(getIntent().getStringExtra("Rating")));

            query=FirebaseDatabase.getInstance().getReference("Trainer").orderByChild("email").equalTo(getIntent().getStringExtra("TrainerEmail"));
            query.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            imgUri = dataSnapshot.child("imgUri").getValue(String.class);
                            System.out.println(imgUri);
                            Glide.with(RateTrainerActivity.this)
                                    .load(imgUri)
                                    .into(timage);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateData()) {
                    giveRating();
                }
                else
                {
                    Toast.makeText(RateTrainerActivity.this,"Please Fill All the Fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rating_del_menu, menu);
        mMenu = menu;
        if(FLAG.equalsIgnoreCase("INSERT"))
            mMenu.findItem(R.id.mybutton).setVisible(false);
        else
            mMenu.findItem(R.id.mybutton).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.mybutton :
                loadingBar.setTitle("Rating");
                 loadingBar.setMessage("Please wait, while we are Deleting the Rating.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                FirebaseDatabase.getInstance().getReference("Rating").child(RatingKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull @NotNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Rating Deleted" ,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Find_Trainer_Activity.class);
                    startActivity(intent);
                    finish();
                 }
                 });
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void giveRating() {

        String name = etname.getText().toString();
        String email = etemail.getText().toString();
        String msg = etreview.getText().toString();
        String rating = String.valueOf(rv_rating.getRating());

        if(FLAG.equalsIgnoreCase("INSERT")) {
            loadingBar.setTitle("Rating");
            loadingBar.setMessage("Please wait, while we are Adding the Rating.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                    @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
                    String date = df.format(Calendar.getInstance().getTime());
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Name", name);
                    userdataMap.put("firstname", getIntent().getStringExtra("TrainerFName"));
                    userdataMap.put("traineremail", getIntent().getStringExtra("TrainerEmail"));
                    userdataMap.put("email", email);
                    userdataMap.put("msg", msg);
                    userdataMap.put("date", date);
                    userdataMap.put("rating", rating);

                    RootRef.child("Rating").child(name + date).updateChildren(userdataMap)
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
        else
        {
            loadingBar.setTitle("Rating");
            loadingBar.setMessage("Please wait, while we are updating the Rating.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rating").child(RatingKey);
            databaseReference.child("msg").setValue(msg);
            databaseReference.child("rating").setValue(rating);
            loadingBar.dismiss();
            Toast.makeText(RateTrainerActivity.this,"Rating Updated Successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RateTrainerActivity.this, Find_Trainer_Activity.class);
            startActivity(intent);
        }
        finish();
    }

    private Boolean ValidateData()
    {
        String str = etemail.getText().toString();
        String str1 =  etname.getText().toString();
        String str2 =  etreview.getText().toString();
        if(TextUtils.isEmpty(str)) {
            etemail.setError("Please Enter Data");
        }if(TextUtils.isEmpty(str1)){
            etname.setError("Please Enter name");
        }if(TextUtils.isEmpty(str2)) {
            etreview.setError("Please Enter review");
           return false;
        }
        return true;
    }
}