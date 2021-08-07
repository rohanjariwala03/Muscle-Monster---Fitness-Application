package com.example.musclemonster_fitnessapp.MoreMenuClasses.SubActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.AdapterClasses.AdapterTrainerRating;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.RateTrainerActivity;
import com.example.musclemonster_fitnessapp.POJOClasses.RatingPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Find_Trainer_Description_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    AdapterTrainerRating AdapterUser;
    ArrayList<RatingPojo> list;

    TextView txtFName, txtLName, txtContact,txtEmail;
    String imgUri,TrainerID,FKey;
    ImageView img;
    ImageButton imgChat;
    Button btnratenow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_trainer_description);

        imgChat=findViewById(R.id.imgChatFindTrainer);
        txtFName=findViewById(R.id.txtUserNameProfile);
        txtLName=findViewById(R.id.txtUserLastNameAdProfile);
        txtEmail=findViewById(R.id.txtUserEmailAdProfile);
        txtContact=findViewById(R.id.txtUserContactAdProfile);
        img=findViewById(R.id.imgTrainerDetailProfile);
        btnratenow=(Button)findViewById(R.id.btnratenow);
        btnratenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Find_Trainer_Description_Activity.this, RateTrainerActivity.class);
                intent.putExtra("TrainerFName",txtFName.getText());
                intent.putExtra("TrainerImageUrl",imgUri);
                intent.putExtra("TrainerEmail",txtEmail.getText());
                startActivity(intent);
            }
        });

        txtFName.setText(getIntent().getStringExtra("TrainerFName"));
        txtLName.setText(getIntent().getStringExtra("TrainerLName"));
        txtEmail.setText(getIntent().getStringExtra("TrainerEmail"));
        txtContact.setText(getIntent().getStringExtra("TrainerPhone"));
        TrainerID= getIntent().getStringExtra("TrainerID");
        FKey= getIntent().getStringExtra("TrainerFkey");

        imgUri=getIntent().getStringExtra("TrainerImageUrl");
        Glide.with(Find_Trainer_Description_Activity.this)
                .load(imgUri)
                .into(img);

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Find_Trainer_Description_Activity.this, Chat_Activity.class);
                intent.putExtra("TrainerFName",txtFName.getText());
                intent.putExtra("TrainerID", TrainerID);
                intent.putExtra("TrainerImageUrl",imgUri);
                intent.putExtra("TrainerFkey",FKey);
                intent.putExtra("TrainerEmail",txtEmail.getText());
                startActivity(intent);
            }
        });


        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=findViewById(R.id.ratingview);
        database=FirebaseDatabase.getInstance().getReference("Rating");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<RatingPojo>();

        AdapterUser=new AdapterTrainerRating(this,list);
        recyclerView.setAdapter(AdapterUser);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

    }

    @Override
    protected void onStop() {
        super.onStop();
        list.clear();
        AdapterUser.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        AdapterUser.notifyDataSetChanged();
        getData();
    }

    private void getData() {
        //  Query applesQuery = ref.child("Users").orderByChild("email").equalTo(EditEmail.getText().toString());

        Query query=FirebaseDatabase.getInstance().getReference("Rating").orderByChild("traineremail").equalTo(getIntent().getStringExtra("TrainerEmail"));
        String ema ;
        FirebaseUser firebaseUser;
        FirebaseAuth firebaseAuth;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        RatingPojo Obj = new RatingPojo();
                        Obj.setName((dataSnapshot.child("Name").getValue(String.class)));
                        Obj.setEmail((dataSnapshot.child("email").getValue(String.class)));
                        Obj.setMsg((dataSnapshot.child("msg").getValue(String.class)));
                        Obj.setRating((dataSnapshot.child("rating").getValue(String.class)));
                        list.add(Obj);
                    } else {
                        Log.i("Result UnSuccessfull", "NO Data : ");
                    }
                }
                // set the Adapter to RecyclerView
                AdapterUser.notifyDataSetChanged();

                Log.i("Rating Adapter ", "Product Binded ");
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
            }
        });

    }

}