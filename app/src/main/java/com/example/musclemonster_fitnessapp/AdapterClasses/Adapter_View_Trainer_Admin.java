package com.example.musclemonster_fitnessapp.AdapterClasses;

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

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.Admin.ActivityTrainerViewDescription;
import com.example.musclemonster_fitnessapp.POJOClasses.TrainerPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_View_Trainer_Admin extends RecyclerView.Adapter<Adapter_View_Trainer_Admin.MyViewHolder> {

    Context context;
    ArrayList<TrainerPojo> list;

    public Adapter_View_Trainer_Admin(Context context, ArrayList<TrainerPojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter_View_Trainer_Admin.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.view_trainer_admin_item,parent,false);
        return new Adapter_View_Trainer_Admin.MyViewHolder(v);
    }
    String ema;
    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_View_Trainer_Admin.MyViewHolder holder, int position) {

        holder.TrainerName.setText(list.get(position).getFirstname());
        Glide.with(context)
                .load(list.get(position).getImgUri())
                .into(holder.ImgV);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,""+list.get(position).getFirstname(),Toast.LENGTH_SHORT).show();

                //ema=list.get(position).getEmail();

                // Fragment ExerciseSubFragment=new ExerciseSubFragment();
                //FragmentManager fragmentManager=context.getSupportFragmentManager();
                Intent intent = new Intent(context, ActivityTrainerViewDescription.class);
                //  intent.putExtra("ItemKey",list.get(position).getFKey());
                intent.putExtra("TrainerName",list.get(position).getFirstname());
                intent.putExtra("TrainerLastName",list.get(position).getLastName());
                intent.putExtra("TrainerID",list.get(position).getTrainerID());
                intent.putExtra("TrainerEmail",list.get(position).getEmail());
                intent.putExtra("TrainerExperience",list.get(position).getExperience());
                intent.putExtra("TrainerWeight",list.get(position).getWeight());
                intent.putExtra("TrainerHeight",list.get(position).getHeight());
                intent.putExtra("TrainerAge",list.get(position).getAge());
                intent.putExtra("TrainerContact",list.get(position).getContact());
                intent.putExtra("TrainerImageUri",list.get(position).getImgUri());
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

        TextView TrainerName;
        ImageView ImgV;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ImgV = itemView.findViewById(R.id.TrainerImgViewAdmin);
            TrainerName=itemView.findViewById(R.id.TxtTrainerNameAdmin);
        }
    }
}