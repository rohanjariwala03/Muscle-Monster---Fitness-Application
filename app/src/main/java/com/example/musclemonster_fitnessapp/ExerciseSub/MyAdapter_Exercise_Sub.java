package com.example.musclemonster_fitnessapp.ExerciseSub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.ExerciseDescription.Exercise_Description_Activity;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Exercise_Sub_Activity;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter_Exercise_Sub extends RecyclerView.Adapter<MyAdapter_Exercise_Sub.MyViewHolder> {

    //Initializing variable
    Context context;
    ArrayList<Exercise_Sub_Pojo> list;

    public MyAdapter_Exercise_Sub(Context context, ArrayList<Exercise_Sub_Pojo> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @NotNull
    @Override
    public MyAdapter_Exercise_Sub.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.exercise_sub_list,parent,false);
        return new MyAdapter_Exercise_Sub.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        //Calling variable and objects from method
        holder.ExerName.setText(list.get(position).getExerciseName());

        //On click listner for View
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Displaying a message to get to know on which card or
                //item did you click
                /*Toast.makeText(context,""+list.get(position).getExerciseName(),Toast.LENGTH_SHORT).show();
                Toast.makeText(context,""+list.get(position).getExerciseCat(),Toast.LENGTH_SHORT).show();
                Toast.makeText(context,""+list.get(position).getExerciseDesc(),Toast.LENGTH_SHORT).show();
*/
                Intent intent = new Intent(context, Exercise_Description_Activity.class);
                intent.putExtra("ExerciseName",list.get(position).getExerciseName());
                intent.putExtra("ExerciseCat",list.get(position).getExerciseCat());
                intent.putExtra("ExerciseDesc",list.get(position).getExerciseDesc());
                intent.putExtra("ItemImageUri",list.get(position).getImageUri());
                v.getContext().startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    //Methods for Data
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ExerName;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ExerName=itemView.findViewById(R.id.exercise_sub_data);
        }
    }
}
