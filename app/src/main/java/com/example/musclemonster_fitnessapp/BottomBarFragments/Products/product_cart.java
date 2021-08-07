package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.cardform.view.CardForm;
import com.example.musclemonster_fitnessapp.MainActivity;
import com.example.musclemonster_fitnessapp.POJOClasses.CouponPOJO;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class product_cart extends AppCompatActivity  implements CouponDialog.CouponDialogListener{

    private CardForm cardForm;
    private Button buy, btnCoupon;
    private AlertDialog.Builder alertBuilder , alertBuilder1;
    private TextView txtPrice;
    private String ItemPrice,ItemKey,UserKey , CurrDate;
    private String Database_Path;
    private FirebaseAuth myAuth;
    private DatabaseReference databaseReference;
    private Calendar calendar;
    private SimpleDateFormat sdf;
    private ArrayList<CouponPOJO> lstCoupons = new ArrayList<>();

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


        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        CurrDate = sdf.format(calendar.getTime());

        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);
        btnCoupon = findViewById(R.id.btnCoupon);
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
                    databaseReference = FirebaseDatabase.getInstance().getReference().child(Database_Path).child(ItemKey);

                    alertBuilder = new AlertDialog.Builder(product_cart.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Amount : $" + ItemPrice + "\n\n" +
                            "Confirm Purchase?");
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseReference.child("status").setValue("1");
                            databaseReference.child("buyer").setValue(UserKey);
                            databaseReference.child("buyDate").setValue(CurrDate);

                            alertBuilder1 = new AlertDialog.Builder(product_cart.this);
                            alertBuilder1.setTitle("Purchase Confirmed");
                            alertBuilder1.setMessage("CONGRATULATIONS,\n YOUR PURCHASE IS COMPLETED SUCCESSFULLY.");
                            alertBuilder1.setCancelable(false);
                            alertBuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(product_cart.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(product_cart.this, MainActivity.class);
                                    intent.putExtra("fragmentNumber",3);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            AlertDialog alertDialog1 = alertBuilder1.create();
                            alertDialog1.show();
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

        btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(cardForm.isValid()) {*/
                    Query DataQuery = FirebaseDatabase.getInstance().getReference("Coupons")
                            .orderByChild("UserKey").equalTo(UserKey);
                    DataQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if(dataSnapshot.exists()) {
                                    CouponPOJO couponPOJO = new CouponPOJO();
                                    couponPOJO.setSKey(dataSnapshot.getKey());
                                    couponPOJO.setCode((dataSnapshot.child("Code").getValue(String.class)));
                                    couponPOJO.setCreateDate((dataSnapshot.child("CreateDate").getValue(String.class)));
                                    couponPOJO.setDiscount((dataSnapshot.child("Discount").getValue(String.class)));
                                    couponPOJO.setExpiryDate((dataSnapshot.child("ExpiryDate").getValue(String.class)));
                                    couponPOJO.setUserKey((dataSnapshot.child("UserKey").getValue(String.class)));
                                    Log.i("PRODUCT CART : ", couponPOJO.getDiscount());
                                    lstCoupons.add(couponPOJO);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                    openDialog();

               /* } else {
                    Toast.makeText(product_cart.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    public void openDialog() {
        CouponDialog couponDialog = new CouponDialog(lstCoupons);
        couponDialog.show(getSupportFragmentManager(), "coupon dialog");
    }

    @Override
    public void applyTexts(CouponPOJO couponPOJO) {

    }
}