package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.MoreMenuClasses.Exercise_Sub_Activity;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.ViewProductDetail;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter_Exercise extends RecyclerView.Adapter<MyAdapter_Exercise.MyViewHolder>  {

    Context context;
    ArrayList<String> list;

    public MyAdapter_Exercise(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.exercise_list,parent,false);

        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        String p =list.get(position);
        holder.EName.setText(p);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a="Abs";
                Toast.makeText(context,""+p,Toast.LENGTH_SHORT).show();

                // Fragment ExerciseSubFragment=new ExerciseSubFragment();
                //FragmentManager fragmentManager=context.getSupportFragmentManager();
                /*AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new ExerciseSubFragment())
                        .addToBackStack(null).commit();*/
                Intent intent = new Intent(context, Exercise_Sub_Activity.class);
                intent.putExtra("ExerciseCat",list.get(position));
                v.getContext().startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView EName;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            EName=itemView.findViewById(R.id.exercise_data);
        }
    }
}