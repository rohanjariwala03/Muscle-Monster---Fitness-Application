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

import com.example.musclemonster_fitnessapp.Admin_Home_Activity;
import com.example.musclemonster_fitnessapp.MainActivity;
import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ActivityLogIn extends AppCompatActivity {


    EditText email,password;
    Button SignIn,SignUp;
    TextView forgotPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

                email = findViewById(R.id.SignInEmail);
                password = findViewById(R.id.SignInPassword);

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

            private void SignIn() {

                String Email = email.getText().toString();
                String Password = password.getText().toString();
                int flag=1;

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){

                    email.requestFocus();
                    email.setError("Enter a valid email");
                }else if(Password.length() == 0){
                    password.setError("Enter valid email id");
                    password.requestFocus();
                }else {
                    mAuth.signInWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(ActivityLogIn.this,new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if(flag==1){
                                        Intent intent = new Intent(ActivityLogIn.this, Admin_Home_Activity.class);
                                        startActivity(intent);
                                    }else {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(ActivityLogIn.this, MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(ActivityLogIn.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ActivityLogIn.this, "Login UnSuccessfull", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }

            }


    }
