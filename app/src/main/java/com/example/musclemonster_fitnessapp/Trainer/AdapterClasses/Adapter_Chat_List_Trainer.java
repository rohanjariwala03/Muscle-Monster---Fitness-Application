package com.example.musclemonster_fitnessapp.Trainer.AdapterClasses;

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
import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Trainer.Chat.Activity_Chat_to_user;
import com.example.musclemonster_fitnessapp.Trainer.PojoClasses.Chat_List_pojo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Chat_List_Trainer extends RecyclerView.Adapter<Adapter_Chat_List_Trainer.MyViewHolder> {

    Context context;
    ArrayList<Chat_List_pojo> list;
    String defaultUrl;

    public Adapter_Chat_List_Trainer(Context context, ArrayList<Chat_List_pojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter_Chat_List_Trainer.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_chat_user_list_item, parent, false);

        return new Adapter_Chat_List_Trainer.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_Chat_List_Trainer.MyViewHolder holder, int position) {

        defaultUrl="https://firebasestorage.googleapis.com/v0/b/muscle-monster-fitnessap-8b451.appspot.com/o/DefaultImage%2Fcircular.png?alt=media&token=783c1888-61d2-40fe-82aa-9f62c184e5ec";
        holder.txtTrainerName.setText(list.get(position).getTrainerName());
        if(list.get(position).getTimgUrl().equals("null")){
            Glide.with(context)
                    .load(defaultUrl)
                    .into(holder.ImgV);
        }else {
            Glide.with(context)
                    .load(list.get(position).getTimgUrl())
                    .into(holder.ImgV);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Chat_to_user.class);
                intent.putExtra("UserFName", list.get(position).getTrainerName());
                intent.putExtra("UserID", list.get(position).getTrainerID());
                intent.putExtra("UserImageUrl", list.get(position).getTimgUrl());
                intent.putExtra("UserFkey", list.get(position).getFKey());
                intent.putExtra("UserEmail", list.get(position).getTEmail());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTrainerName;
        ImageView ImgV;

        public MyViewHolder(View itemView) {
            super(itemView);
            ImgV = itemView.findViewById(R.id.Trainerspecificuserimageinimageview);
            txtTrainerName = itemView.findViewById(R.id.TxtUserNameTrainer);

        }
    }
}