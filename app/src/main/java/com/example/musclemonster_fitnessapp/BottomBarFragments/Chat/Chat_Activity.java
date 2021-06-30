package com.example.musclemonster_fitnessapp.BottomBarFragments.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_chat_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Chat_pojo;
import com.example.musclemonster_fitnessapp.POJOClasses.TrainerPojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat_Activity extends AppCompatActivity {

    TextView txtName;
    ImageButton imgBack,btnSend;
    EditText etWriteMessage;

    //data to take from intent
    String TrainerID;
    String UserID;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    Adapter_chat_Activity adapter_chat_activity;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtName=findViewById(R.id.txt_trainer_name_chat);
        imgBack=findViewById(R.id.btnback_trainer);
        btnSend=findViewById(R.id.btnSendMessage);
        etWriteMessage=findViewById(R.id.txtChatMessage);

        recyclerView=(RecyclerView) findViewById(R.id.recylerView_Message);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter_chat_activity=new Adapter_chat_Activity(getDatasetChat(),this);
        recyclerView.setAdapter(adapter_chat_activity);

        Intent intent=new Intent();
        intent=getIntent();
        txtName.setText((String) intent.getStringExtra("TrainerFName"));
        TrainerID=(String) intent.getStringExtra("TrainerFkey");
        UserID=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Chats");

        getMsg();

        //Log.i("aaasd","ada " + UserID+" " + TrainerID);
        //Log.i("Data a: " , UserID + "   " + TrainerID);

        final String senderRoom=UserID+TrainerID;
        final String receiverRoom= TrainerID+UserID;
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sendMessage(UserID,TrainerID,msg);
                sendMsg();
            }
        });


    }

    private void sendMsg() {
        String messageText = etWriteMessage.getText().toString();
        if(!messageText.isEmpty()){
            DatabaseReference newMessageDb = databaseReference.push();
            Map newMessage = new HashMap();

            newMessage.put("createdBy",UserID);
            newMessage.put("text",messageText);


            newMessageDb.setValue(newMessage);
        }
        etWriteMessage.setText(null);
    }


    private void getMsg() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    String message = null;
                    String createdBy = null;

                    if(snapshot.child("text").getValue() != null){
                        message = snapshot.child("text").getValue().toString();
                    }

                    if(snapshot.child("createdBy").getValue() != null){
                        createdBy = snapshot.child("createdBy").getValue().toString();
                    }

                    if(message != null && createdBy != null){
                        Boolean currentUserBool = false;
                        if(createdBy.equals(UserID)){
                            currentUserBool = true;
                        }
                        Chat_pojo newMessage = new Chat_pojo(UserID,message);
                        resultChat.add(newMessage);
                        adapter_chat_activity.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private ArrayList<Chat_pojo> resultChat = new ArrayList<Chat_pojo>();

    private List<Chat_pojo> getDatasetChat() {
        return resultChat;
    }

    /*private void sendMessage(String sender,String receiver, String message){

        databaseReference=FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        databaseReference.child("Chats").push().setValue(hashMap);

    }

    private void receiveMessage(String UserID,String TrainerID){
        databaseReference=FirebaseDatabase.getInstance().getReference("Chats");
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.get(UserID);
        hashMap.get(TrainerID);
        databaseReference.child("Chats").get().getResult();
    }*/

}