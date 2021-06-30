package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Find_Trainer_pojo;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Find_Trainer extends RecyclerView.Adapter<Adapter_Find_Trainer.MyViewHolder> {

    Context context;
    ArrayList<Find_Trainer_pojo> list;


    public Adapter_Find_Trainer(Context context, ArrayList<Find_Trainer_pojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter_Find_Trainer.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.fragment_find_trainer_item,parent,false);

        return new Adapter_Find_Trainer.MyViewHolder(v);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_Find_Trainer.MyViewHolder holder, int position) {

        holder.TrName_chat.setText(list.get(position).getTFName() + " " + list.get(position).getTLName()) ;


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context,""+position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, Chat_Activity.class);
                intent.putExtra("TrainerFName",list.get(position).getTFName() + " " + list.get(position).getTLName());
                intent.putExtra("TrainerID",list.get(position).getTid());
                intent.putExtra("TrainerImageUrl",list.get(position).getTimgUrl());
                intent.putExtra("TrainerLName",list.get(position).getTLName());
                intent.putExtra("TrainerFkey",list.get(position).getFKey());
                intent.putExtra("TrainerEmail",list.get(position).getTEmail());
                v.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView TrName_chat,TrLName_chat;
        ImageView ImgV;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            //ImgV = itemView.findViewById(R.id.ProdImgView);
            TrName_chat=itemView.findViewById(R.id.trainerNameChat);
        }
    }

}
