package com.example.musclemonster_fitnessapp.Admin.AdapterClasses;

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

import com.example.musclemonster_fitnessapp.Admin_Home_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.FeedbackPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_View_Feedbacks extends RecyclerView.Adapter<Adapter_View_Feedbacks.MyViewHolder> {

    Context context;
    ArrayList<FeedbackPojo> list;
    String defaultUrl;

    public Adapter_View_Feedbacks(Context context, ArrayList<FeedbackPojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.child_user_feedback,parent,false);
        return new MyViewHolder(v);
    }
    String ema;
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        holder.tvname.setText(list.get(position).getName());
        holder.tvemail.setText(list.get(position).getEmail());
        holder.tvmsg.setText(list.get(position).getMsg());
        holder.tvdate.setText(list.get(position).getDate());

        holder.imgdeletefeedback.setVisibility(View.VISIBLE);
        holder.imgdeletefeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Feedback").orderByChild("email").equalTo(holder.tvemail.getText().toString());
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                        Toast.makeText(context,"Feedback Deleted Successfully",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(context, Admin_Home_Activity.class);
                        context.startActivity(i);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvname,tvemail,tvmsg,tvdate;
        ImageView imgdeletefeedback;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvname=itemView.findViewById(R.id.tvname);
            tvemail=itemView.findViewById(R.id.tvemail);
            tvmsg=itemView.findViewById(R.id.tvmsg);
            tvdate=itemView.findViewById(R.id.tvdate);
            imgdeletefeedback=itemView.findViewById(R.id.imgdeletefeedback);
        }
    }



}