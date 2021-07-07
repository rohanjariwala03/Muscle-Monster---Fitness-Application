package com.example.musclemonster_fitnessapp.Admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclemonster_fitnessapp.R;

public class ActivityUserViewDescription extends AppCompatActivity {

    String firstName, lastName, email, phonenumber;
    TextView TextFirstName, TextLastName, TextEmail, TextPhonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_description);
        TextFirstName = (TextView) findViewById(R.id.txtUserName);
        TextLastName = (TextView) findViewById(R.id.txtUserLastNameAd);
        TextEmail = (TextView) findViewById(R.id.txtUserEmailAd);
        TextPhonenumber = (TextView) findViewById(R.id.txtUserContactAd);

        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        email = getIntent().getStringExtra("email");
        phonenumber = getIntent().getStringExtra("contact");

        TextFirstName.setText(firstName);
        TextLastName.setText(lastName);
        TextEmail.setText(email);
        TextPhonenumber.setText(phonenumber);

    }

}
