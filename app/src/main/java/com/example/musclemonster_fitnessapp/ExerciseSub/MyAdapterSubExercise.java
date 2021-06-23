package com.example.musclemonster_fitnessapp.ExerciseSub;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Prod_Shopping;
import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.POJOClasses.Exercise_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapterSubExercise extends RecyclerView.Adapter<MyAdapterSubExercise.MyViewHolder>{

    //Initializing variable
    Context context;
    ArrayList<ExerciseSub_Pojo> list;

    public MyAdapterSubExercise(Context context, ArrayList<ExerciseSub_Pojo> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @NotNull
    @Override
    public MyAdapterSubExercise.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.exercise_sub_list,parent,false);
        return new MyAdapterSubExercise.MyViewHolder(v);
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
                Toast.makeText(context,""+list.get(position).getExerciseName(),Toast.LENGTH_SHORT).show();

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
