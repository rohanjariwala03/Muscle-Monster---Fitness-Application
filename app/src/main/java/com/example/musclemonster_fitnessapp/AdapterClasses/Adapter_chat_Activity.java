package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.graphics.Color;
import android.text.style.AlignmentSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Chat_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Adapter_chat_Activity extends RecyclerView.Adapter<ChatViewHolders>  {

    private List<Chat_pojo> chatList;
    private Context context;
    FirebaseAuth firebaseAuth;

    public Adapter_chat_Activity(List<Chat_pojo> chatList, Context context){
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ChatViewHolders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_item_left , null , false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatViewHolders rcv = new ChatViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatViewHolders holder, int position) {

        holder.mMessaage.setText(chatList.get(position).getMsg());
        if(chatList.get(position).getUserID() == FirebaseAuth.getInstance().getCurrentUser().getUid()){
            holder.mMessaage.setGravity(Gravity.END);
            holder.mMessaage.setTextColor(Color.parseColor("#404040"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#F4F4F4"));
        }else{
            holder.mMessaage.setGravity(Gravity.START);
            holder.mMessaage.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
