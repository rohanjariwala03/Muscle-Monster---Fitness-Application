package com.example.musclemonster_fitnessapp.BottomBarFragments.Chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_Chat extends Fragment {


    TextView txtName;
    ImageButton imgBack,btnSend;
    RecyclerView recView;
    EditText etWriteMessage;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    public Fragment_Chat() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=  inflater.inflate(R.layout.fragment__chat, container, false);

        txtName=view.findViewById(R.id.txt_trainer_name_chat);
        imgBack=view.findViewById(R.id.btnback_trainer);
        btnSend=view.findViewById(R.id.btnSendMessage);
        recView=view.findViewById(R.id.recylerview_Message);
        etWriteMessage=view.findViewById(R.id.txtChatMessage);

        Intent intent=new Intent();
        String name;
      /*  name=intent.getStringExtra("TrainerFName" + "TrainerLName");
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");*/

        return view;
    }
}