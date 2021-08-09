package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Products.Del_Product;
import com.example.musclemonster_fitnessapp.POJOClasses.CouponPOJO;
import com.example.musclemonster_fitnessapp.POJOClasses.Pojo_Activity_My_Appointments;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Activity_My_Appointment extends RecyclerView.Adapter<Adapter_Activity_My_Appointment.MyViewHolder>{

    Context context;
    ArrayList<Pojo_Activity_My_Appointments> list;
    DatabaseReference databaseReference;
    String userID,trainerID;

    public Adapter_Activity_My_Appointment(Context context, ArrayList<Pojo_Activity_My_Appointments> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_my_appointment_item,parent,false);
        return new Adapter_Activity_My_Appointment.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Appointments");
        userID= FirebaseAuth.getInstance().getUid();
        trainerID=list.get(position).getUserID();
        holder.txtName.setText(list.get(position).getName());
        holder.txtDate.setText(list.get(position).getDate());
        String imguri=list.get(position).getImageUri();

        Glide.with(context)
                .load(imguri)
                .into(holder.imgView);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dlt=list.get(position).getFKey();
                FirebaseDatabase.getInstance().getReference("Appointments").child(userID)
                        .child(trainerID).child(dlt).child("flag").setValue("1");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtDate;
        ImageButton btnDelete;
        ImageView imgView;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtTrainerNameAppointment);
            txtDate=itemView.findViewById(R.id.txtAppointmentDate);
            btnDelete=itemView.findViewById(R.id.dltAppointment);
            imgView=itemView.findViewById(R.id.Appointment_Trainer_Image);
        }
    }
}
