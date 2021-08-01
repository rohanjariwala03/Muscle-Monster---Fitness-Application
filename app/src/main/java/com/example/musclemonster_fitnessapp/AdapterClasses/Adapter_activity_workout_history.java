package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Exercise_Sub_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_History_pojo;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_activity_workout_history extends RecyclerView.Adapter<Adapter_activity_workout_history.MyViewHolder>  {

    Context context;
    ArrayList<Exercise_History_pojo> list;
    String imgUri;

    public Adapter_activity_workout_history(Context context, ArrayList<Exercise_History_pojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_workout_history_item,parent,false);

        return new Adapter_activity_workout_history.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        Exercise_History_pojo p =list.get(position);
        holder.ExerciseName.setText(list.get(position).getExName());
        holder.ExerciseTime.setText(list.get(position).getExTime());
        holder.ExerciseDate.setText(list.get(position).getExDate());
        imgUri=list.get(position).getExImageUri();
        holder.exerciseTime.setText(list.get(position).getTime());

        Glide.with(context)
                .load(imgUri)
                .into(holder.imgView);

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,""+p,Toast.LENGTH_SHORT).show();

                // Fragment ExerciseSubFragment=new ExerciseSubFragment();
                //FragmentManager fragmentManager=context.getSupportFragmentManager();
                *//*AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new ExerciseSubFragment())
                        .addToBackStack(null).commit();*//*
                Intent intent = new Intent(context, Exercise_Sub_Activity.class);
                intent.putExtra("ExerciseCat",list.get(position));
                v.getContext().startActivity(intent);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ExerciseName,ExerciseTime,ExerciseDate,exerciseTime;
        ImageView imgView;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ExerciseName=itemView.findViewById(R.id.history_exerciseName);
            ExerciseDate=itemView.findViewById(R.id.history_exerciseDate);
            ExerciseTime=itemView.findViewById(R.id.history_exerciseTime);
            imgView=itemView.findViewById(R.id.history_exerciseImage);
            exerciseTime=itemView.findViewById(R.id.txtExerciseTime);
        }
    }
}
