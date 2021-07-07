package com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise_Video;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_video_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Activity_Exercise_Video extends AppCompatActivity {

    private ArrayList<Exercise_video_pojo> videoArrayList;
    private MyAdapter_Exercise_Video adapterVideo;
    private RecyclerView videoRv;
    Intent intent=new Intent();
    TextView txtNoVideo;
    String exName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_video);

        videoRv=findViewById(R.id.videoRV);
        exName=getIntent().getStringExtra("ExerName");
        txtNoVideo=findViewById(R.id.NoVideo);
        loadVideo();
    }

    private void loadVideo() {
        //Init array list
        videoArrayList=new ArrayList<>();

        //database Reference

        Query query=FirebaseDatabase.getInstance().getReference("ExerciseVideos")
                .orderByChild("exerciseName").equalTo(exName);
        //DatabaseReference ref= FirebaseDatabase.getInstance().getReference("ExerciseVideos");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //clear List before adding data
                for(DataSnapshot ds:snapshot.getChildren()){
                    //get Data
                    txtNoVideo.setVisibility(View.GONE);
                    txtNoVideo.setVisibility(View.INVISIBLE);
                    Exercise_video_pojo obj=new Exercise_video_pojo();
                    obj.setId(ds.getKey());
                    obj.setTitle((ds.child("exerciseName").getValue(String.class)));
                    obj.setVideoUrl((ds.child("videoUri").getValue(String.class)));
                    //add data into list
                    videoArrayList.add(obj);
                }
                //set Adapter
                adapterVideo=new MyAdapter_Exercise_Video(Activity_Exercise_Video.this,videoArrayList);
                //set Adapter to recyclerview
                videoRv.setAdapter(adapterVideo);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}