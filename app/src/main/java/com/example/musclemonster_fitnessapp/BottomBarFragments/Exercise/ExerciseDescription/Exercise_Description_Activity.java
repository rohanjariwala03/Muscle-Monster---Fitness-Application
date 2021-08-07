package com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.ExerciseDescription;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_TrainerList_Chat_User;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.Activity_Exercise_Video;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.Activity_Start_Exercise_Timer;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.InfoDialog;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_History_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.MyWorkout_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.TrainerList_Chat_user_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.HashMap;

public class Exercise_Description_Activity extends AppCompatActivity {

    String ExerciseCat,ExerciseName,ExerciseDesc,ItemImageUri,steps,userId,room,Flg;
    TextView Name,Catagory,Description;
    ImageView ImgView;
    ImageButton playbtn,info,imgBtnstartWorkout,save;
    Task<Void> databaseReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Query query;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_description);
        ExerciseName = getIntent().getStringExtra("ExerciseName");
        ExerciseCat = getIntent().getStringExtra("ExerciseCat");
        ExerciseDesc = getIntent().getStringExtra("ExerciseDesc");
        ItemImageUri = getIntent().getStringExtra("ItemImageUri");
        steps=getIntent().getStringExtra("ExerciseSteps");
        save=findViewById(R.id.saveWorkout);
        imgBtnstartWorkout=findViewById(R.id.imgBtnStartWorkout);
        firebaseDatabase=FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance().getReference("SavedWorkouts");
        userId= FirebaseAuth.getInstance().getUid();
        Flg="Delete";
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3E50")));
            getSupportActionBar().setTitle(ExerciseName + " Exercise");
        }

        info=findViewById(R.id.Info);
        //Toast.makeText(this,"" + ExerciseCat,Toast.LENGTH_LONG).show();
        /*Log.i("asd", "Data : " + ExerciseCat);
        Log.i("asd", "Data : " + ExerciseName);
        Log.i("asd", "Data : " + ExerciseDesc);
*/
        /*Catagory=findViewById(R.id.txtExerciseType);
        Name=findViewById(R.id.txtExerciseName);*/
        Description =findViewById(R.id.txtExerciseDescription);
        /*Name.setText(ExerciseName);
        Catagory.setText(ExerciseCat);*/
        Description.setText(ExerciseDesc);
        ImgView = (ImageView) findViewById(R.id.imgView);
        playbtn=findViewById(R.id.PlayVideo);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise_Description_Activity.this, Activity_Exercise_Video.class);
                intent.putExtra("ExerName", ExerciseName);
                startActivity(intent);

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        Glide.with(this)
                .load(ItemImageUri)
                .into(ImgView);

        imgBtnstartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Exercise_Description_Activity.this, Activity_Start_Exercise_Timer.class);
                intent.putExtra("exName",ExerciseName);
                intent.putExtra("imgUri",ItemImageUri);
                startActivity(intent);
            }
        });

        room=ExerciseName+userId;
        query=FirebaseDatabase.getInstance().getReference("SavedWorkouts").child(room);
        getSavedWorkout();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSaveWorkouts();
            }
        });
    }

    private void openDialog() {
        InfoDialog infoDialog=new InfoDialog(steps);
        infoDialog.show(getSupportFragmentManager(),"tSteps");
    }

    //This method will fire when user click on the Save button
    private void setSaveWorkouts() {
        if(Flg.equals("Delete")) {
            //If user didn't save workout yet then this will Save Workout in Firebase Database
            MyWorkout_pojo obj = new MyWorkout_pojo(userId, ExerciseName, Flg);
            FirebaseDatabase.getInstance().getReference("SavedWorkouts")
                    .child(room).setValue(obj)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @SuppressLint("UseCompatLoadingForDrawables")
                        @Override
                        public void onSuccess(Void unused) {
                            //Data successfully Saved
                            getSavedWorkout();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    //Task have some errors
                }
            });
        }else if(Flg.equals("Saved")){
            //If user saved workout and after that user unsaved that workout
            //then it will change the value if flag from saved to delete in Firebase Database
            MyWorkout_pojo obj = new MyWorkout_pojo(userId, ExerciseName, Flg);
            database.child(room)
                    .child("flag").setValue(Flg)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //Data Successfully Updated in FirebaseDatabase
                        }
                    });
        }
    }

    //get Data from Firebase Database of Saved Workout
    private void getSavedWorkout(){
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //Creating Pojo class object to save data which we got from Firebase
                MyWorkout_pojo obj=new MyWorkout_pojo();
                obj.setFlag(snapshot.child("flag").getValue(String.class));
                if(obj.getFlag() == null){
                    //This will fire when they didn't find any data from Firebase.
                    //For this We'll set default value to the Flg
                    Flg="Delete";
                }else if(obj.getFlag().equals("Saved")){
                    //If flag value is Saved that means user already saved one time
                    //So, that we are changing the Flag value to the delete if user click again
                    Flg="Delete";
                    //We are Changing Icon because we found data.
                    save.setBackground(getResources().getDrawable(R.drawable.save));

                }else if(obj.getFlag().equals("Delete")){
                    //If flag value is Delete that means user already saved one time
                    //And after that user unsaved that data
                    //So, that we are changing the Flag value to the Saved if user click again
                    Flg="Saved";
                    save.setBackground(getResources().getDrawable(R.drawable.saved));
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

    }
}