package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Find_Trainer_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.TrainerList_Chat_user_pojo;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Adapter_TrainerList_Chat_User extends RecyclerView.Adapter<Adapter_TrainerList_Chat_User.MyViewHolder> {

    Context context;
    ArrayList<TrainerList_Chat_user_pojo> list;

    public Adapter_TrainerList_Chat_User(Context context, ArrayList<TrainerList_Chat_user_pojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_chat_item,parent,false);

        return new Adapter_TrainerList_Chat_User.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        holder.txtTrainerName.setText(list.get(position).getTrainerName());

        Log.i("Trainer NJaem" , "AJXHYAHAHA");
        Log.i("Trainer NJaem" , list.get(position).getTrainerName());
        Glide.with(context)
                .load(list.get(position).getTimgUrl())
                .into(holder.ImgV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chat_Activity.class);
                intent.putExtra("TrainerFName",list.get(position).getTrainerName());
                intent.putExtra("TrainerID",list.get(position).getTrainerID());
                intent.putExtra("TrainerImageUrl",list.get(position).getTimgUrl());
                intent.putExtra("TrainerFkey",list.get(position).getFKey());
                intent.putExtra("TrainerEmail",list.get(position).getTEmail());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        int s=list.size();
        Log.i("ABCBCBB" , String.valueOf(s));
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTrainerName;
        ImageView ImgV;
        public MyViewHolder(View itemView) {
            super(itemView);
            ImgV = itemView.findViewById(R.id.TrainerImageInFindTrainerListS);
            txtTrainerName=itemView.findViewById(R.id.trainer_Chat_list_user);

        }
    }
}
