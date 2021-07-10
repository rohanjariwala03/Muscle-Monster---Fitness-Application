package com.example.musclemonster_fitnessapp.AdapterClasses;

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
import com.example.musclemonster_fitnessapp.Admin.ActivityUserViewDescription;
import com.example.musclemonster_fitnessapp.POJOClasses.UsersPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_View_Users_Admin extends RecyclerView.Adapter<Adapter_View_Users_Admin.MyViewHolder> {

    Context context;
    ArrayList<UsersPojo> list;
    String defaultUrl;

    public Adapter_View_Users_Admin(Context context, ArrayList<UsersPojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.view_user_admin_item,parent,false);
        return new MyViewHolder(v);
    }
    String ema;
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        defaultUrl="https://firebasestorage.googleapis.com/v0/b/muscle-monster-fitnessap-8b451.appspot.com/o/DefaultImage%2Fcircular.png?alt=media&token=783c1888-61d2-40fe-82aa-9f62c184e5ec";
        holder.fitstName.setText(list.get(position).getFirstName());
        if(list.get(position).getImageUrl().equals("null")){
            Glide.with(context)
                    .load(defaultUrl)
                    .into(holder.ImgV);
        }else{
            Glide.with(context)
                    .load(list.get(position).getImageUrl())
                    .into(holder.ImgV);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,""+list.get(position).getFirstName(),Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(context, ActivityUserViewDescription.class);
                //  intent.putExtra("ItemKey",list.get(position).getFKey());
                intent.putExtra("UserName",list.get(position).getFirstName());
                intent.putExtra("UserLastName",list.get(position).getLastName());
                intent.putExtra("UserEmail",list.get(position).getEmail());
                intent.putExtra("Phone",list.get(position).getPhoneNumber());
                intent.putExtra("UserImageUri",list.get(position).getImageUrl());
                //intent.putExtra("ItemWeight",list.get(position).getProductWeight());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fitstName;
        ImageView ImgV;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ImgV = itemView.findViewById(R.id.UserImgViewAdmin);
            fitstName=itemView.findViewById(R.id.tvfitstName);
        }
    }



}