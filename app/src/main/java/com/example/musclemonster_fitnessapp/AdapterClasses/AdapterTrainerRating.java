package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.POJOClasses.RatingPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class AdapterTrainerRating extends RecyclerView.Adapter<AdapterTrainerRating.MyViewHolder> {

    Context context;
    ArrayList<RatingPojo> list;
    String defaultUrl;

    public AdapterTrainerRating(Context context, ArrayList<RatingPojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.child_trainer_rating,parent,false);
        return new MyViewHolder(v);
    }
    String ema;
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        holder.tvusername.setText(list.get(position).getName());
        holder.tvemail.setText(list.get(position).getEmail());
        holder.tvmsg.setText(list.get(position).getMsg());
        holder.rv_rating.setRating(Float.parseFloat(list.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvusername,tvemail,tvmsg;
        RatingBar rv_rating;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            rv_rating= itemView.findViewById(R.id.getrv_rating);
            tvusername=itemView.findViewById(R.id.tvusername);
            tvemail=itemView.findViewById(R.id.tvemail);
            tvmsg=itemView.findViewById(R.id.tvmsg);
        }
    }



}