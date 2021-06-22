package com.example.musclemonster_fitnessapp.ExerciseSub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapterSubExercise extends RecyclerView.Adapter<MyAdapterSubExercise.MyViewHolder>{


    Context context;
    ArrayList<String> list;

    public MyAdapterSubExercise(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyAdapterSubExercise.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.exercise_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyAdapterSubExercise.MyViewHolder holder, int position) {
        String p =list.get(position);
        holder.EName.setText(p.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a="Abs";
                Toast.makeText(context,""+p,Toast.LENGTH_SHORT).show();

                // Fragment ExerciseSubFragment=new ExerciseSubFragment();
                //FragmentManager fragmentManager=context.getSupportFragmentManager();

                if(p==a){
                    /**/


                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView EName;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            EName=itemView.findViewById(R.id.exercise_data);
        }
    }
}
