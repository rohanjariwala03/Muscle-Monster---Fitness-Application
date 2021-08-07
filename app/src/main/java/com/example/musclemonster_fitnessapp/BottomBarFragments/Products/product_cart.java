package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnSuccessListener;
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
    private String ItemPrice,ItemKey,UserKey , CurrDate, FBCouponKey;
    private String Database_Path , CouponKey, CouponDiscount , CouponExpiryDate;
    private FirebaseAuth myAuth;
    private DatabaseReference databaseReference;
    private Calendar calendar;
    private SimpleDateFormat sdf;
    private ArrayList<CouponPOJO> lstCoupons = new ArrayList<>();
    private ProgressDialog progressDialog ;
    private Float DiscountedPrice;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
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

        CouponKey = "NULL";
        CouponDiscount = "NULL";

        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        CurrDate = sdf.format(calendar.getTime());

        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);
        btnCoupon = findViewById(R.id.btnCoupon);
        txtPrice = findViewById(R.id.TxtAmount);

        txtPrice.setText("AMOUNT : $" + ItemPrice);

        progressDialog = new ProgressDialog(product_cart.this);

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
                    if(CouponDiscount.equals("NULL"))
                        alertBuilder.setMessage("Amount : $" + ItemPrice + "\n\n" +
                            "Confirm Purchase?");
                    else
                        alertBuilder.setMessage("Amount : $" + DiscountedPrice + "\n\n" +
                                "Confirm Purchase?");
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseReference.child("status").setValue("1");
                            databaseReference.child("buyer").setValue(UserKey);
                            databaseReference.child("buyDate").setValue(CurrDate);

                            try {
                                FirebaseDatabase.getInstance().getReference("Coupons").child(FBCouponKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        System.out.println("Coupon Deleted");
                                    }
                                });
                            }catch (Exception e) {
                                    System.out.println("Error While Deleting Coupon!! " + e.getMessage());
                            }

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
                if(cardForm.isValid()) {
                if(btnCoupon.getText().equals("APPLY COUPON"))
                {
                    openDialog();
                }
                else
                {
                    txtPrice.setText("AMOUNT : $" + ItemPrice);
                    btnCoupon.setText("APPLY COUPON");
                    CouponDiscount="NULL";
                }

                } else {
                    Toast.makeText(product_cart.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openDialog() {
        CouponDialog couponDialog = new CouponDialog();
        couponDialog.show(getSupportFragmentManager(), "coupon dialog");
    }

    @Override
    public void applyTexts(String Str) {
        // Setting progressDialog Title.
        progressDialog.setTitle("Applying Coupon....");

        // Showing progressDialog.
        progressDialog.show();

        CouponKey = Str;
        databaseReference=FirebaseDatabase.getInstance().getReference("Coupons");
        Query query=databaseReference.orderByChild("Code").equalTo(CouponKey);

        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        FBCouponKey = dataSnapshot.getKey();
                        CouponDiscount = dataSnapshot.child("Discount").getValue(String.class);
                        CouponExpiryDate = dataSnapshot.child("ExpiryDate").getValue(String.class);
                        DiscountedPrice = Float.parseFloat(ItemPrice) - Float.parseFloat(CouponDiscount.substring(1));
                        txtPrice.setText("      Amount : $" + ItemPrice +
                                "\n - Coupon Discount :" + CouponDiscount +
                                "\n --------------------------------------\n" +
                                "Discounted Price : " + DiscountedPrice);
                        btnCoupon.setText("CANCLE COUPON");
                        Toast.makeText(getApplicationContext(),"Coupon Applied.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                txtPrice.setText("AMOUNT : $" + ItemPrice);
                btnCoupon.setText("APPLY COUPON");
            }
        });
        Log.i("Product_CART1" , CouponKey + "   " + CouponDiscount + "  " + CouponExpiryDate);
        progressDialog.dismiss();
        /*ApplyCoupon(CouponDiscount.substring(1));*/
        /*if(CurrDate.compareTo(CouponExpiryDate) < 0)
            ApplyCoupon();
        else
        {
            Toast.makeText(getApplicationContext(),"Coupon is Expired.", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }*/
    }

    @SuppressLint("SetTextI18n")
    private void ApplyCoupon(String Price)
    {

        /*try {*/

        /*} catch(NumberFormatException nfe) {
            Toast.makeText(getApplicationContext(),"Something Went Wrong!" + nfe.getMessage(), Toast.LENGTH_LONG).show();
        }*/

    }
}