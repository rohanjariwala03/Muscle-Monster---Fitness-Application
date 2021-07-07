package com.example.musclemonster_fitnessapp.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musclemonster_fitnessapp.AdapterClasses.MyAdapter_Exercise;
import com.example.musclemonster_fitnessapp.Admin_Home_Activity;
import com.example.musclemonster_fitnessapp.MainActivity;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Trainer.trainer_home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ActivityLogIn extends AppCompatActivity {


    EditText email;
    TextInputEditText password;
    Button SignIn,SignUp;
    TextView forgotPassword;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    TextView txtInvalid;

    String flg;
    private static String Flag = "NON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        
        forgotPassword=findViewById(R.id.SignInForgetPassword);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        database=firebaseDatabase.getReference("Users");

        txtInvalid=findViewById(R.id.Invalid);
        Flag = "NON";
        email = findViewById(R.id.SignInEmail);
        password = findViewById(R.id.SignInPasswor);
        SignIn = findViewById(R.id.SignInButtonLogin);
        SignUp = findViewById(R.id.SignInButtonSignup);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = findViewById(R.id.SignInForgetPassword);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogIn.this, ActivitySignUp.class));
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

    }


    protected void onStart() {
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            Query query = FirebaseDatabase.getInstance().getReference("Users").orderByKey();
            //Database event listner for success or failure
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            flg="User";
                            if (dataSnapshot.child("email").getValue(String.class).toLowerCase().trim().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString())) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        } else {
                            Log.i("There is No User", "NO Data : ");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });

            Query quer = FirebaseDatabase.getInstance().getReference("Trainer").orderByKey();
            //Database event listner for success or failure
            quer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.child("email").getValue(String.class).toLowerCase().trim().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                startActivity(new Intent(getApplicationContext(), trainer_home.class));
                                finish();
                            }
                        } else {
                            Log.i("There is No User", "NO Data : ");
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });
            Query que = FirebaseDatabase.getInstance().getReference("Admin").orderByKey();
            //Database event listner for success or failure
            que.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.child("email").getValue(String.class).toLowerCase().trim().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString())) {
                                startActivity(new Intent(getApplicationContext(), Admin_Home_Activity.class));
                                finish();
                            }
                        } else {
                            Log.i("There is No User", "NO Data : ");
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });
        }

        Flag = "NON";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Flag="NON";
    }

    private void SignIn() {

                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){

                    email.requestFocus();
                    email.setError("Enter a valid email");
                }else if(Password.length() == 0){
                    password.setError("Enter valid email id");
                    password.requestFocus();
                }else {



                    //Query to look for user
                    Query query=FirebaseDatabase.getInstance().getReference("Users").orderByKey();
                    //Database event listner for success or failure
                    String K;
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                if (dataSnapshot.exists()) {
                                    if(dataSnapshot.child("email").getValue(String.class).toLowerCase().trim().equals(Email.toLowerCase().trim()))
                                    { Flag = "User";
                                        Log.i("LOGIN ACTIVITY", "FLAG " + Flag + dataSnapshot.child("email").getValue(String.class) + Email);
                                        Call_MyAuth(Email,Password);
                                    }
                                }
                                else {/* Log.i(getTag(), "NO Data : " );*/}
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        }
                    });


                    if (!Flag.equals("User"))
                    {
                        query=FirebaseDatabase.getInstance().getReference("Trainer").orderByKey();
                        //Database event listner for success or failure
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                    if (dataSnapshot.exists()) {

                                        if(dataSnapshot.child("email").getValue(String.class).toLowerCase().trim().equals(Email.toLowerCase().trim()))
                                        { Flag = "Trainer";
                                            Log.i("LOGIN ACTIVITY", "FLAG " + Flag + dataSnapshot.child("email").getValue(String.class) + Email);
                                            Call_MyAuth(Email,Password);}
                                    }
                                    else {/* Log.i(getTag(), "NO Data : " );*/}
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            }
                        });
                    }

                    if(!Flag.equalsIgnoreCase("Trainer"))
                    {
                        query=FirebaseDatabase.getInstance().getReference("Admin").orderByKey();
                        //Database event listner for success or failure
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                    if (dataSnapshot.exists()) {
                                        if(dataSnapshot.child("email").getValue(String.class).toLowerCase().trim().equals(Email.toLowerCase().trim()))
                                        { Flag = "Admin";
                                            Log.i("LOGIN ACTIVITY", "FLAG " + Flag +dataSnapshot.child("email").getValue(String.class));
                                            Call_MyAuth(Email,Password);}
                                    }
                                    else {/* Log.i(getTag(), "NO Data : " );*/}
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            }
                        });
                    }
                }

    }

    private void Call_MyAuth(String Email, String Pass)
    {
        mAuth.signInWithEmailAndPassword(Email,Pass)
                .addOnCompleteListener(ActivityLogIn.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(Flag.toLowerCase().trim().equals("Admin".toLowerCase().trim()))
                            {
                                Toast.makeText(ActivityLogIn.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityLogIn.this, Admin_Home_Activity.class);
                                Log.i("LOGIN ACTIVITY", "FLAG " + Flag );
                                startActivity(intent);
                            }
                            else if(Flag.toLowerCase().trim().equals("Trainer".toLowerCase().trim()))
                            {
                                Toast.makeText(ActivityLogIn.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityLogIn.this, trainer_home.class);
                                Log.i("LOGIN ACTIVITY", "Entered FLAG " + Flag );
                                startActivity(intent);
                            }
                            else
                            {
                                Log.i("LOGIN ACTIVITY", "FLAG " + Flag );
                                Toast.makeText(ActivityLogIn.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityLogIn.this, MainActivity.class);
                                startActivity(intent);
                            }


                        } else {
                            Log.i("LOGIN ACTIVITY", "FLAG " + Flag );
                            txtInvalid.setVisibility(View.VISIBLE);
                            txtInvalid.setText("Wrong Email id or Password");
                            Toast.makeText(ActivityLogIn.this, "Login UnSuccessfull", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogIn.this,ForgotPasswordActivity.class));
            }
        });
    }

}
