package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.ExerciseDescription.Exercise_Description_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.MyWorkout_pojo;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Fragment_Saved_Workouts extends RecyclerView.Adapter<Adapter_Fragment_Saved_Workouts.MyViewHolder> {

    Context context;
    ArrayList<MyWorkout_pojo> list;

    public Adapter_Fragment_Saved_Workouts(Context context, ArrayList<MyWorkout_pojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter_Fragment_Saved_Workouts.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.fragment__saved__workouts_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        holder.txtName.setText(list.get(position).getWorkoutName());
        Glide.with(context)
                .load(list.get(position).getImgUrl())
                .into(holder.imgV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Exercise_Description_Activity.class);
                intent.putExtra("ExerciseName",list.get(position).getWorkoutName());
                intent.putExtra("ExerciseCat",list.get(position).gettCat());
                intent.putExtra("ExerciseDesc",list.get(position).gettDesc());
                intent.putExtra("ItemImageUri",list.get(position).getImgUrl());
                intent.putExtra("ExerciseSteps",list.get(position).gettSteps());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgV;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.saved_exerciseName);
            imgV=itemView.findViewById(R.id.saved_exerciseImage);
        }
    }
}
