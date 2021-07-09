package com.example.musclemonster_fitnessapp.Trainer.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Find_Trainer;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_TrainerList_Chat_User;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_chat_Activity;
import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Exercise_Sub_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Find_Trainer_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.TrainerList_Chat_user_pojo;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Chat_List_Trainer extends RecyclerView.Adapter<Adapter_Chat_List_Trainer.MyViewHolder> {

    Context context;
    ArrayList<Chat_List_pojo> list;

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

        holder.txtTrainerName.setText(list.get(position).getTrainerName());
        /*Glide.with(context)
                .load(list.get(position).getTimgUrl())
                .into(holder.ImgV);*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chat_Activity.class);
                intent.putExtra("TrainerFName", list.get(position).getTrainerName());
                intent.putExtra("TrainerID", list.get(position).getTrainerID());
                intent.putExtra("TrainerImageUrl", list.get(position).getTimgUrl());
                intent.putExtra("TrainerFkey", list.get(position).getFKey());
                intent.putExtra("TrainerEmail", list.get(position).getTEmail());
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