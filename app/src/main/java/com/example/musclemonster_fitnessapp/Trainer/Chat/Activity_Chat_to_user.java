package com.example.musclemonster_fitnessapp.Trainer.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_chat_Activity;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Chat_Activity;
import com.example.musclemonster_fitnessapp.POJOClasses.Chat_pojo;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    String mRecieverName,SenderName,mRecieverUID,mSenderUID;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String Senderroom,Recieverroom;

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
        setContentView(R.layout.activity_chat_to_user);

        mGetMessage=findViewById(R.id.Trainergetmessage);
        mSendMessageCardview=findViewById(R.id.Trainercarviewofsendmessage);
        mbtnSendMessage=findViewById(R.id.Trainerimageviewsendmessage);
        mToolbarofChat=findViewById(R.id.Trainertoolbarofspecificchat);
        mNameOfSpecificUser=findViewById(R.id.TrainerNameofspecificuser);
        mImageviewOfSpecificUser=findViewById(R.id.Trainerspecificuserimageinimageview);
        mbtnBackofSpecificChat=findViewById(R.id.Trainerbackbuttonofspecificchat);

        messagesArrayList=new ArrayList<>();
        mMessageRecyclerview=findViewById(R.id.Trainerrecyclerviewofspecific);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerview.setLayoutManager(linearLayoutManager);
        messagesAdapter=new Adapter_chat_Activity(Activity_Chat_to_user.this,messagesArrayList);
        mMessageRecyclerview.setAdapter(messagesAdapter);
        intent=getIntent();


        mGetMessage.setText(null);


        //setSupportActionBar(mtoolbarofspecificchat);
       /* mtoolbarofspecificchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Toolbar is Clicked",Toast.LENGTH_SHORT).show();

            }
        });*/

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        //calendar=Calendar.getInstance();
        //simpleDateFormat=new SimpleDateFormat("hh:mm a");

        mSenderUID=firebaseAuth.getUid();
        mRecieverUID=getIntent().getStringExtra("UserFKey");
        mRecieverName=getIntent().getStringExtra("UserFName");



        Senderroom=mSenderUID+mRecieverUID;
        Recieverroom=mRecieverUID+mSenderUID;



        DatabaseReference databaseReference=firebaseDatabase.getReference().child("chats").child(Senderroom).child("messages");
        messagesAdapter=new Adapter_chat_Activity(Activity_Chat_to_user.this,messagesArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Chat_pojo messages=snapshot1.getValue(Chat_pojo.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




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

                                }
                            });
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