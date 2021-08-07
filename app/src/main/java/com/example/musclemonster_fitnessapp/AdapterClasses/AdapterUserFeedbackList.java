package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.Admin.ActivityUserViewDescription;
import com.example.musclemonster_fitnessapp.Admin.AdapterClasses.Adapter_View_Users_Admin;
import com.example.musclemonster_fitnessapp.POJOClasses.FeedbackPojo;
import com.example.musclemonster_fitnessapp.POJOClasses.UsersPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class AdapterUserFeedbackList extends RecyclerView.Adapter<AdapterUserFeedbackList.MyViewHolder> {

    Context context;
    ArrayList<FeedbackPojo> list;
    String defaultUrl;

    public AdapterUserFeedbackList(Context context, ArrayList<FeedbackPojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterUserFeedbackList.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.child_user_feedback,parent,false);
        return new AdapterUserFeedbackList.MyViewHolder(v);
    }
    String ema;
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterUserFeedbackList.MyViewHolder holder, int position) {

        holder.tvname.setText(list.get(position).getName());
        holder.tvemail.setText(list.get(position).getEmail());
        holder.tvmsg.setText(list.get(position).getMsg());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvname,tvemail,tvmsg;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvname=itemView.findViewById(R.id.tvname);
            tvemail=itemView.findViewById(R.id.tvemail);
            tvmsg=itemView.findViewById(R.id.tvmsg);
        }
    }



}