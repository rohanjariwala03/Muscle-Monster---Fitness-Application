package com.example.musclemonster_fitnessapp.Trainer.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Find_Trainer;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_chat_Activity;
import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Exercise_Sub_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Find_Trainer_pojo;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Chat_List_Trainer extends RecyclerView.Adapter<Adapter_Chat_List_Trainer.MyViewHolder>  {

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
        View v= LayoutInflater.from(context).inflate(R.layout.activity_chat_user_list_item,parent,false);

        return new Adapter_Chat_List_Trainer.MyViewHolder(v);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_Chat_List_Trainer.MyViewHolder holder, int position) {

        holder.UsrName_chat.setText(list.get(position).getUserFName() + " " + list.get(position).getUserLName()) ;


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context,""+position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, Activity_Chat_to_user.class);
                intent.putExtra("UserFName",list.get(position).getUserFName() + " " + list.get(position).getUserLName());
                intent.putExtra("UserID",list.get(position).getUserID());
                intent.putExtra("UserFKey",list.get(position).getFKey());
                v.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView UsrName_chat;
        ImageView ImgV;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            //ImgV = itemView.findViewById(R.id.ProdImgView);
            UsrName_chat=itemView.findViewById(R.id.TxtUserNameTrainer);
        }
    }

}