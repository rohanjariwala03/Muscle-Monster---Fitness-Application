package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.R;

public class ChatViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMessaage;
    public LinearLayout mContainer;

    public ChatViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMessaage = itemView.findViewById(R.id.msg);
        mContainer = itemView.findViewById(R.id.container);
    }

    @Override
    public void onClick(View v) {

    }
}
