package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.SubActivities.Find_Trainer_Description_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Find_Trainer_pojo;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Find_Trainer extends RecyclerView.Adapter<Adapter_Find_Trainer.MyViewHolder> {

    Context context;
    ArrayList<Find_Trainer_pojo> list;
    ImageButton imgBtn;


    public Adapter_Find_Trainer(Context context, ArrayList<Find_Trainer_pojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter_Find_Trainer.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_find_trainer_item,parent,false);
        //imgBtn=v.findViewById(R.id.imgChatFindTrainer);
        return new Adapter_Find_Trainer.MyViewHolder(v);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_Find_Trainer.MyViewHolder holder, int position) {

        String url;
        holder.TrName_chat.setText(list.get(position).getTFName() + " " + list.get(position).getTLName());

        Glide.with(context)
                .load(list.get(position).getTimgUrl())
                .into(holder.ImgV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context,""+position, Toast.LENGTH_SHORT).show();
                /*imgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("CLicked", "Clicked");
                        Intent intent = new Intent(context, Chat_Activity.class);
                        intent.putExtra("TrainerFName",list.get(position).getTFName() + " " + list.get(position).getTLName());
                        intent.putExtra("TrainerID",list.get(position).getTid());
                        intent.putExtra("TrainerImageUrl",list.get(position).getTimgUrl());
                        intent.putExtra("TrainerLName",list.get(position).getTLName());
                        intent.putExtra("TrainerFkey",list.get(position).getFKey());
                        intent.putExtra("TrainerEmail",list.get(position).getTEmail());
                        intent.putExtra("TrainerPhone",list.get(position).getTphone());
                    }
                });*/

                Intent intent = new Intent(context, Find_Trainer_Description_Activity.class);
                intent.putExtra("TrainerFName",list.get(position).getTFName() + " " + list.get(position).getTLName());
                intent.putExtra("TrainerID",list.get(position).getTid());
                intent.putExtra("TrainerImageUrl",list.get(position).getTimgUrl());
                intent.putExtra("TrainerLName",list.get(position).getTLName());
                intent.putExtra("TrainerFkey",list.get(position).getFKey());
                intent.putExtra("TrainerEmail",list.get(position).getTEmail());
                intent.putExtra("TrainerPhone",list.get(position).getTphone());

                v.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView TrName_chat,TrLName_chat,imgUrl;
        ImageView ImgV;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ImgV = itemView.findViewById(R.id.TrainerImageInFindTrainerList);
            TrName_chat=itemView.findViewById(R.id.trainerNameChat);
        }
    }

}
