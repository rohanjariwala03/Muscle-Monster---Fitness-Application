package com.example.musclemonster_fitnessapp.Trainer.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Trainer.PojoClasses.Activity_Chat_to_user_pojo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Adapter_Activity_Chat_to_user extends RecyclerView.Adapter {

    Context context;
    ArrayList<Activity_Chat_to_user_pojo> messagesArrayList;

    int ITEM_SEND=1;
    int ITEM_RECIEVE=2;

    public Adapter_Activity_Chat_to_user(Context context, ArrayList<Activity_Chat_to_user_pojo> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.activity_chat_item_left,parent,false);
            return new Adapter_Activity_Chat_to_user.SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.activity_chat_item_right,parent,false);
            return new Adapter_Activity_Chat_to_user.RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Activity_Chat_to_user_pojo messages=messagesArrayList.get(position);
        if(holder.getClass()== Adapter_Activity_Chat_to_user.SenderViewHolder.class)
        {
            Adapter_Activity_Chat_to_user.SenderViewHolder viewHolder=(Adapter_Activity_Chat_to_user.SenderViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());

        }
        else
        {
            Adapter_Activity_Chat_to_user.RecieverViewHolder viewHolder=(Adapter_Activity_Chat_to_user.RecieverViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
        }


    }


    @Override
    public int getItemViewType(int position) {
        Activity_Chat_to_user_pojo messages=messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId()))

        {
            return  ITEM_SEND;
        }
        else
        {
            return ITEM_RECIEVE;
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }


    class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewmessaage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
        }
    }

    class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView textViewmessaage;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
        }
    }

}