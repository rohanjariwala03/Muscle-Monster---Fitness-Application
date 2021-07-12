package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.musclemonster_fitnessapp.MainActivity;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class product_cart extends AppCompatActivity {

    private CardForm cardForm;
    private Button buy;
    private AlertDialog.Builder alertBuilder;
    private TextView txtPrice;
    private String ItemPrice,ItemKey,UserKey;
    private String Database_Path;
    private FirebaseAuth myAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);

        getSupportActionBar().setTitle("Purchase");

        Database_Path = "Product_Detail_Database";
        ItemPrice = getIntent().getStringExtra("ItemPrice");
        ItemKey = getIntent().getStringExtra("ItemKey");

        myAuth =FirebaseAuth.getInstance();
        UserKey = myAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path).child(ItemKey);

        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);
        txtPrice = findViewById(R.id.TxtAmount);

        txtPrice.setText("AMOUNT : $" + ItemPrice);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(product_cart.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(product_cart.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Amount : $" + ItemPrice + "\n\n" +
                            "Confirm Purchase?");
                   /* alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());*/
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(product_cart.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                            databaseReference.child("status").setValue("1");
                            databaseReference.child("buyer").setValue(UserKey);
                            Intent intent = new Intent(product_cart.this, MainActivity.class);
                            intent.putExtra("fragmentNumber",3);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(product_cart.this, "Purchase Not Confirmed !", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(product_cart.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });
        Log.i("Cart",getIntent().getStringExtra("ItemName"));
    }
}