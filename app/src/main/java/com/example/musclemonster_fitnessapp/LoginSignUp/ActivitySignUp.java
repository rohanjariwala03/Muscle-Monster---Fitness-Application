package com.example.musclemonster_fitnessapp.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musclemonster_fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ActivitySignUp extends AppCompatActivity {

    EditText firstName,lastName,email,phone,password,confirmPassword;
    Button signup;
    int Sflag=0;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.SignUpFirstName);
        lastName = findViewById(R.id.SignUpLastName);
        email = findViewById(R.id.SignUpEmailId);
        phone = findViewById(R.id.SignUpPhone);
        password = findViewById(R.id.SignUpPassword);
        confirmPassword = findViewById(R.id.SignUpConfirmPassword);

        signup = findViewById(R.id.SignUpButtonSignUp);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });
    }

    private void Signup() {

        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String Semail = email.getText().toString();
        String Sphone = phone.getText().toString();
        String Spassword = password.getText().toString();
        String SCPassword = confirmPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(Semail, Spassword)

                .addOnCompleteListener(ActivitySignUp.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Model model = new Model(fName,lName,Semail,Sphone,Sflag);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NotNull Task<Void> task) {
                                    Toast.makeText(ActivitySignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ActivitySignUp.this,ActivityLogIn.class));
                                }
                            });
                            /*String UserId = mAuth.getCurrentUser().getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UserId);

                            Map UserInfo = new HashMap<>();
                            UserInfo.put("firstName",firstName);
                            UserInfo.put("lastName",lastName);
                            UserInfo.put("email",email);
                            UserInfo.put("phone",phone);
                            UserInfo.put("password",password);
                            UserInfo.put("type","user");

                            databaseReference.updateChildren(UserInfo);*/
                        }else{
                            Toast.makeText(ActivitySignUp.this, "User Created Unsuccessfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}