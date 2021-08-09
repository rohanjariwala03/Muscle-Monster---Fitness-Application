package com.example.musclemonster_fitnessapp.Trainer.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Trainer.AdapterClasses.Adapter_Activity_Chat_to_user;
import com.example.musclemonster_fitnessapp.Trainer.PojoClasses.Activity_Chat_to_user_pojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class Activity_Chat_to_user extends AppCompatActivity {

    EditText mGetMessage;
    ImageButton mbtnSendMessage;

    CardView mSendMessageCardview;
    androidx.appcompat.widget.Toolbar mToolbarofChat;
    ImageView mImageviewOfSpecificUser;
    TextView mNameOfSpecificUser;

    private String EnteredMessage;
    Intent intent;
    String mRecieverName,mRecieverUID,mSenderUID,mReceiverEmail;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String Senderroom,Recieverroom,ImageUrl,imageUrl;

    ImageButton mbtnBackofSpecificChat;

    RecyclerView mMessageRecyclerview;

    //String currenttime;
    //Calendar calendar;
    //SimpleDateFormat simpleDateFormat;

    Adapter_Activity_Chat_to_user messagesAdapter;
    ArrayList<Activity_Chat_to_user_pojo> messagesArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_to_user);

        mGetMessage=findViewById(R.id.Trainergetmessage);
        mSendMessageCardview=findViewById(R.id.Trainercarviewofsendmessage);
        mbtnSendMessage=findViewById(R.id.Trainerimageviewsendmessage);
        mToolbarofChat=findViewById(R.id.Trainertoolbarofspecificchat);
        mNameOfSpecificUser=findViewById(R.id.TrainerNameofspecificuser);
        mImageviewOfSpecificUser=findViewById(R.id.TrainerspecificuserimageinimageviewChat1);
        mbtnBackofSpecificChat=findViewById(R.id.Trainerbackbuttonofspecificchat);

        messagesArrayList=new ArrayList<>();
        mMessageRecyclerview=findViewById(R.id.Trainerrecyclerviewofspecific);
        imageUrl="https://firebasestorage.googleapis.com/v0/b/muscle-monster-fitnessap-8b451.appspot.com/o/DefaultImage%2Fcircular.png?alt=media&token=783c1888-61d2-40fe-82aa-9f62c184e5ec";
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerview.setLayoutManager(linearLayoutManager);
        messagesAdapter=new Adapter_Activity_Chat_to_user(Activity_Chat_to_user.this,messagesArrayList);
        mMessageRecyclerview.setAdapter(messagesAdapter);
        intent=getIntent();

        //Image Display

        /*ImageUrl=getIntent().getStringExtra("TrainerImageUrl");
        Glide.with(this)
                .load(ImageUrl)
                .into(mImageviewOfSpecificUser);*/


        mGetMessage.setText(null);
        mReceiverEmail=getIntent().getStringExtra("UserEmail");
        mSenderUID=FirebaseAuth.getInstance().getUid();
        mRecieverUID=getIntent().getStringExtra("UserFkey");
        mRecieverName=getIntent().getStringExtra("UserFName");
        ImageUrl=getIntent().getStringExtra("UserImageUrl");

        //setSupportActionBar(mToolbarofChat);
        mToolbarofChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Toolbar is Clicked",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Activity_Chat_to_user.this,Activity_User_Details_Trainer.class);
                intent.putExtra("UsrName",mRecieverName);
                intent.putExtra("UsrEmail",mReceiverEmail);
                intent.putExtra("TreinaerImage",ImageUrl);
                startActivity(intent);
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        //calendar=Calendar.getInstance();
        //simpleDateFormat=new SimpleDateFormat("hh:mm a");


        Log.i("Image nathi", ImageUrl);


        String defaultUrl="https://firebasestorage.googleapis.com/v0/b/muscle-monster-fitnessap-8b451.appspot.com/o/DefaultImage%2Fcircular.png?alt=media&token=783c1888-61d2-40fe-82aa-9f62c184e5ec";
        if(ImageUrl.equals("null")){
            Glide.with(this)
                    .load(defaultUrl)
                    .into(mImageviewOfSpecificUser);
        }else {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(mImageviewOfSpecificUser);
        }

        Senderroom=mSenderUID+mRecieverUID;
        Recieverroom=mRecieverUID+mSenderUID;



        /*DatabaseReference databaseReference=firebaseDatabase.getReference().child("chats").child(Senderroom).child("messages");
        messagesAdapter=new Adapter_Activity_Chat_to_user(Activity_Chat_to_user.this,messagesArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Activity_Chat_to_user_pojo messages=snapshot1.getValue(Activity_Chat_to_user_pojo.class);
                    messagesArrayList.add(messages);
                    mMessageRecyclerview.setLayoutManager(new LinearLayoutManager(Activity_Chat_to_user.this));
                    mMessageRecyclerview.setAdapter(messagesAdapter);
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
                    Activity_Chat_to_user_pojo messages=new Activity_Chat_to_user_pojo(EnteredMessage,firebaseAuth.getUid());
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
    public void onStop() {
        super.onStop();
        if(messagesAdapter!=null)
        {
            messagesAdapter.notifyDataSetChanged();
        }
        messagesArrayList.clear();
    }

    public void onResume() {
        super.onResume();

        messagesAdapter.notifyDataSetChanged();

        firebaseDatabase.getReference().child("chats")
                .child(Senderroom)
                .child("messages")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        Activity_Chat_to_user_pojo messages=snapshot.getValue(Activity_Chat_to_user_pojo.class);
                        messagesArrayList.add(messages);
                        mMessageRecyclerview.setLayoutManager(new LinearLayoutManager(Activity_Chat_to_user.this));
                        mMessageRecyclerview.setAdapter(messagesAdapter);
                        /* messagesArrayList.add(messages);*/
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        /*Activity_Chat_to_user_pojo messages=snapshot.getValue(Activity_Chat_to_user_pojo.class);
                        messagesArrayList.add(messages);
                        mMessageRecyclerview.setLayoutManager(new LinearLayoutManager(Activity_Chat_to_user.this));
                        mMessageRecyclerview.setAdapter(messagesAdapter);*/
                        /* messagesArrayList.add(messages);*/
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

}