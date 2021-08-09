package com.example.musclemonster_fitnessapp.BottomBarFragments.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_chat_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Chat_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class Chat_Activity extends AppCompatActivity {
    EditText mGetMessage;
    ImageButton mbtnSendMessage;

    CardView mSendMessageCardview;
    androidx.appcompat.widget.Toolbar mToolbarofChat;

    ImageView mImageviewOfSpecificUser;
    TextView mNameOfSpecificUser;

    private String EnteredMessage;
    Intent intent;
    String mRecieverName,SenderName,mRecieverUID,mSenderUID;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String Senderroom,Recieverroom,ImageUrl,mReceiverEmail,imageUrl,chatSize;

    ImageButton mbtnBackofSpecificChat;

    RecyclerView mMessageRecyclerview;

    //String currenttime;
    //Calendar calendar;
    //SimpleDateFormat simpleDateFormat;

    Adapter_chat_Activity messagesAdapter;
    ArrayList<Chat_pojo> messagesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mGetMessage=findViewById(R.id.getmessage);
        mSendMessageCardview=findViewById(R.id.carviewofsendmessage);
        mbtnSendMessage=findViewById(R.id.imageviewsendmessage);
        mNameOfSpecificUser=findViewById(R.id.Nameofspecificuser);
        mImageviewOfSpecificUser=findViewById(R.id.specificTrainerImageview);
        mbtnBackofSpecificChat=findViewById(R.id.backbuttonofspecificchat);
        mToolbarofChat=findViewById(R.id.toolbarofspecificchat);

        messagesArrayList=new ArrayList<>();
        mMessageRecyclerview=findViewById(R.id.recyclerviewofspecific);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerview.setLayoutManager(linearLayoutManager);
        messagesAdapter=new Adapter_chat_Activity(Chat_Activity.this,messagesArrayList);
        mMessageRecyclerview.setAdapter(messagesAdapter);
        imageUrl="https://firebasestorage.googleapis.com/v0/b/muscle-monster-fitnessap-8b451.appspot.com/o/DefaultImage%2Fcircular.png?alt=media&token=783c1888-61d2-40fe-82aa-9f62c184e5ec";

        intent=getIntent();
        mReceiverEmail=intent.getStringExtra("TrainerEmail");


        mGetMessage.setText(null);

        //setSupportActionBar(mtoolbarofspecificchat);

        mToolbarofChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Chat_Activity.this, Activity_Trainer_Details_User.class);
                intent.putExtra("TrainerName",mRecieverName);
                intent.putExtra("TrainerEmail",mReceiverEmail);
                intent.putExtra("ImageUrl",ImageUrl);
                chatSize= String.valueOf(messagesArrayList.size());
                intent.putExtra("ChatSizes",chatSize);
                intent.putExtra("TrainerFKey",getIntent().getStringExtra("TrainerFkey"));
                //messagesArrayList.clear();
                startActivity(intent);
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        //calendar=Calendar.getInstance();
        //simpleDateFormat=new SimpleDateFormat("hh:mm a");


        mSenderUID=firebaseAuth.getUid();
        mRecieverUID=getIntent().getStringExtra("TrainerFkey");
        mRecieverName=getIntent().getStringExtra("TrainerFName");
        ImageUrl=getIntent().getStringExtra("TrainerImageUrl");

        if(ImageUrl.equals("null")){
            Glide.with(this)
                    .load(imageUrl)
                    .into(mImageviewOfSpecificUser);
        }else {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(mImageviewOfSpecificUser);
        }

        Senderroom=mSenderUID+mRecieverUID;
        Recieverroom=mRecieverUID+mSenderUID;



        /*DatabaseReference databaseReference=firebaseDatabase.getReference().child("chats").child(Senderroom).child("messages");
        messagesAdapter=new Adapter_chat_Activity(Chat_Activity.this,messagesArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Chat_pojo messages=snapshot1.getValue(Chat_pojo.class);
                    messagesArrayList.add(messages);
                    mMessageRecyclerview.setLayoutManager(new LinearLayoutManager(Chat_Activity.this));
                    mMessageRecyclerview.setAdapter(messagesAdapter);
                    *//* messagesArrayList.add(messages);*//*
                    messagesAdapter.notifyDataSetChanged();
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/




        mbtnBackofSpecificChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mNameOfSpecificUser.setText(mRecieverName);
        String uri=intent.getStringExtra("TrainerImageUrl");
        /*if(uri.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"null is recieved",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Picasso.get().load(uri).into(mimageviewofspecificuser);
        }*/


        mbtnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EnteredMessage=mGetMessage.getText().toString();
                if(EnteredMessage.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter message first",Toast.LENGTH_SHORT).show();
                }

                else

                {
                    Date date=new Date();
                    //currenttime=simpleDateFormat.format(calendar.getTime());
                    Chat_pojo messages=new Chat_pojo(EnteredMessage,firebaseAuth.getUid());
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("chats")
                            .child(Senderroom)
                            .child("messages")
                            .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            firebaseDatabase.getReference()
                                    .child("chats")
                                    .child(Recieverroom)
                                    .child("messages")
                                    .push()
                                    .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    messagesAdapter.notifyDataSetChanged();
                                }
                            });

                            messagesAdapter.notifyDataSetChanged();
                        }
                    });
                    mGetMessage.setText(null);

                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.notifyDataSetChanged();

        firebaseDatabase.getReference().child("chats")
                .child(Senderroom)
                .child("messages")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        messagesAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();

        messagesAdapter.notifyDataSetChanged();
        messagesArrayList.clear();
        firebaseDatabase.getReference().child("chats")
                .child(Senderroom)
                .child("messages")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        Chat_pojo messages=snapshot.getValue(Chat_pojo.class);
                        messagesArrayList.add(messages);
                        mMessageRecyclerview.setLayoutManager(new LinearLayoutManager(Chat_Activity.this));
                        mMessageRecyclerview.setAdapter(messagesAdapter);
                        /* messagesArrayList.add(messages);*/
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        /*Chat_pojo messages=snapshot.getValue(Chat_pojo.class);
                        messagesArrayList.add(messages);
                        mMessageRecyclerview.setLayoutManager(new LinearLayoutManager(Chat_Activity.this));
                        mMessageRecyclerview.setAdapter(messagesAdapter);
                        *//* messagesArrayList.add(messages);*//*
                        messagesAdapter.notifyDataSetChanged();*/
                    }

                    @Override
                    public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        messagesAdapter.notifyDataSetChanged();
                    }
                });


    }

    @Override
    public void onStop() {
        super.onStop();
        if(messagesAdapter!=null)
        {
            messagesAdapter.notifyDataSetChanged();
        }
    }



}