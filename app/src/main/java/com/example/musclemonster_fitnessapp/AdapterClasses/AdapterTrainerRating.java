package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.MoreMenuClasses.RateTrainerActivity;
import com.example.musclemonster_fitnessapp.POJOClasses.RatingPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class AdapterTrainerRating extends RecyclerView.Adapter<AdapterTrainerRating.MyViewHolder> {

    Context context;
    ArrayList<RatingPojo> list;
    String defaultUrl, UserEmail;

    public AdapterTrainerRating(Context context, ArrayList<RatingPojo> list) {
        this.context = context;
        this.list = list;

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Query query = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("email");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                UserEmail = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
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

        if(list.get(position).getEmail().equalsIgnoreCase(UserEmail))
        {
            holder.ImgEdit.setVisibility(View.VISIBLE);
            holder.ImgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), RateTrainerActivity.class);
                    intent.putExtra("UserEmail",list.get(position).getEmail());
                    intent.putExtra("UserName",list.get(position).getName());
                    intent.putExtra("TrainerName",list.get(position).getFirstname());
                    intent.putExtra("UserMsg",list.get(position).getMsg());
                    intent.putExtra("Rating",list.get(position).getRating());
                    intent.putExtra("TrainerEmail",list.get(position).getTraineremail());
                    intent.putExtra("RatingKey",list.get(position).getDate());
                    view.getContext().startActivity(intent);
                }
            });
        }
        else
            holder.ImgEdit.setVisibility(View.GONE);

        holder.tvusername.setText(list.get(position).getName());
        holder.tvmsg.setText(list.get(position).getMsg());
        holder.rv_rating.setRating(Float.parseFloat(list.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvusername,tvmsg;
        ImageView ImgEdit;
        RatingBar rv_rating;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            rv_rating= itemView.findViewById(R.id.getrv_rating);
            tvusername=itemView.findViewById(R.id.tvusername);
            tvmsg=itemView.findViewById(R.id.tvmsg);
            ImgEdit = itemView.findViewById(R.id.ImgEdit);
        }
    }



}