package com.example.musclemonster_fitnessapp.Trainer.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Activity_My_Appointment;
import com.example.musclemonster_fitnessapp.POJOClasses.Pojo_Activity_My_Appointments;
import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Trainer.PojoClasses.Pojo_Activity_My_Appointments_With_Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Activity_My_Appointments_With_Users extends RecyclerView.Adapter<Adapter_Activity_My_Appointments_With_Users.MyViewHolder>{

    Context context;
    ArrayList<Pojo_Activity_My_Appointments_With_Users> list;
    DatabaseReference databaseReference;
    String userID,trainerId;

    public Adapter_Activity_My_Appointments_With_Users(Context context, ArrayList<Pojo_Activity_My_Appointments_With_Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_my_appointment_with_users_items,parent,false);
        return new Adapter_Activity_My_Appointments_With_Users.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        databaseReference= FirebaseDatabase.getInstance().getReference("Appointments");
        userID= list.get(position).getUserID();
        trainerId=FirebaseAuth.getInstance().getUid();
        holder.txtName.setText(list.get(position).getName());
        holder.txtDate.setText(list.get(position).getDate());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dlt=list.get(position).getFKey();
                FirebaseDatabase.getInstance().getReference("Appointments").child(userID)
                        .child(trainerId).child(dlt).child("flag").setValue("1");
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
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtTrainerNameAppointmentTrainer);
            txtDate=itemView.findViewById(R.id.txtAppointmentDateTrainer);
            btnDelete=itemView.findViewById(R.id.dltAppointmentTrainer);
        }
    }
}
