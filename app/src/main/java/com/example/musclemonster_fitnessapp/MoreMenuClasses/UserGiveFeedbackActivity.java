package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class UserGiveFeedbackActivity extends AppCompatActivity {


    EditText etemail,etname,etmsg;
    Button btn_submit;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    ProgressDialog loadingBar;
    LinearLayout LLGender;
    String uid;
    private Uri resultUri;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_user_give_feedback);
        loadingBar = new ProgressDialog(UserGiveFeedbackActivity.this);
        etemail=(EditText)findViewById(R.id.etemail);
        etname=(EditText)findViewById(R.id.etname);
        etmsg=(EditText)findViewById(R.id.etmsg);
        btn_submit=(Button) findViewById(R.id.btn_submit);

        databaseReference=FirebaseDatabase.getInstance().getReference("Users");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Add Feedback");
                loadingBar.setMessage("Please wait, while we are Adding the Feedback.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                submitFeedback();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uid= FirebaseAuth.getInstance().getUid();

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                etemail.setText(snapshot.child("email").getValue(String.class));
                etname.setText(snapshot.child("firstName").getValue(String.class) + " "
                        + snapshot.child("lastName").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void submitFeedback() {

        String name = etname.getText().toString();
        String email = etemail.getText().toString();
        String msg = etmsg.getText().toString();

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());

                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("name", name);
                userdataMap.put("email", email);
                userdataMap.put("msg", msg);
                userdataMap.put("date", date);

                RootRef.child("Feedback").child(name+date).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UserGiveFeedbackActivity.this, "Feedback Submited SuccessFully.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
//                                    Intent intent = new Intent(UserGiveFeedbackActivity.this, UserFeedbackListActivity.class);
//                                    startActivity(intent);
                                    finish();
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(UserGiveFeedbackActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}