package com.example.musclemonster_fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityUserViewDescription extends AppCompatActivity {

    String ItemKey;
    String TrainerName,TrainerLastName,TrainerEmail,TrainerCon,TrainerAge,TrainerExper,ItemImageUri;
    ImageView ImgView;
    TextView EditFirstName,EditLastName, EditEmail,EditPhone,EditAge,EditExp;
    ImageButton imgbtn;

    DatabaseReference drRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_description);
        EditFirstName = (TextView)findViewById(R.id.txtTrainerNameAd);
        EditLastName = (TextView)findViewById(R.id.txtTrainerLastNameAd);
        EditEmail = (TextView)findViewById(R.id.txtTrainerEmailAd);
        EditPhone = (TextView)findViewById(R.id.txtTrainerContactAd);



        ImgView = (ImageView) findViewById(R.id.TrainerImageViewAd);
        imgbtn=(ImageButton)findViewById(R.id.btnDeleteUserAdmin);

        TrainerName = getIntent().getStringExtra("UserName");
        TrainerLastName = getIntent().getStringExtra("UserLastName");
        TrainerEmail = getIntent().getStringExtra("UserEmail");
        ItemImageUri = getIntent().getStringExtra("UserImageUri");
        TrainerCon = getIntent().getStringExtra("Phone");

        EditFirstName.setText(TrainerName);
        EditLastName.setText(TrainerLastName);
        EditEmail.setText(TrainerEmail);
        EditPhone.setText(TrainerCon);

        Glide.with(this)
                .load(ItemImageUri)
                .into(ImgView);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Users").orderByChild("email").equalTo(EditEmail.getText().toString());

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }

                        Toast.makeText(getApplicationContext(),"User Deleted Successfully",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),ActivityViewUsers.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

}
