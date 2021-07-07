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
import com.example.musclemonster_fitnessapp.Admin.ActivityUserViewDescription;
import com.example.musclemonster_fitnessapp.Admin.ActivityViewUser;
import com.example.musclemonster_fitnessapp.LoginSignUp.Model;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_View_User_Admin extends RecyclerView.Adapter<Adapter_View_User_Admin.MyViewHolder>{
    Context context;
    ArrayList<Model> list;



    public Adapter_View_User_Admin(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter_View_User_Admin.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.view_user_admin_item,parent,false);
        return new Adapter_View_User_Admin.MyViewHolder(v);
    }
    String ema;
    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_View_User_Admin.MyViewHolder holder, int position) {

        holder.userName.setText(list.get(position).getFirstName());
        holder.userEmail.setText(list.get(position).getEmail());
        holder.userPhone.setText(list.get(position).getPhoneNumber());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,""+list.get(position).getFirstName(),Toast.LENGTH_SHORT).show();

                //ema=list.get(position).getEmail();

                // Fragment ExerciseSubFragment=new ExerciseSubFragment();
                //FragmentManager fragmentManager=context.getSupportFragmentManager();
                Intent intent = new Intent(context, ActivityUserViewDescription.class);
                //  intent.putExtra("ItemKey",list.get(position).getFKey());
                intent.putExtra("firstName",list.get(position).getFirstName());
                intent.putExtra("lastName",list.get(position).getLastName());
                intent.putExtra("email",list.get(position).getEmail());
                intent.putExtra("contact",list.get(position).getPhoneNumber());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userName;
        TextView userEmail;
        TextView userPhone;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.userEmail);
            userPhone = itemView.findViewById(R.id.userPhone);

        }
    }
}
