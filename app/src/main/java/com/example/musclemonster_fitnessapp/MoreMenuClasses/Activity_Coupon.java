package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_MyCoupons;
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

import java.util.ArrayList;

public class Activity_Coupon extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference database;
    private Adapter_MyCoupons adapter_myCoupons;
    private ArrayList<CouponPOJO> list;
    private TextView TxtAlert;
    private String CurUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        firebaseDatabase=FirebaseDatabase.getInstance();
        recyclerView=findViewById(R.id.recyclerviewCoupon);
        TxtAlert = (TextView) findViewById(R.id.TxtAlert);
        database=FirebaseDatabase.getInstance().getReference("Coupons");

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        list=new ArrayList<CouponPOJO>();
        adapter_myCoupons = new Adapter_MyCoupons(Activity_Coupon.this,list);
        recyclerView.setAdapter(adapter_myCoupons);
        recyclerView.setHasFixedSize(true);

        CurUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        Query query=database.orderByChild("userKey").equalTo(CurUser);

        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {
                        list.add(AddData_ToList(dataSnapshot));
                    }
                    else
                    {
                        TxtAlert.setVisibility(View.VISIBLE);
                    }
                }
                // set the Adapter to RecyclerView
                adapter_myCoupons.notifyDataSetChanged();
                CheckAL(list);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                TxtAlert.setVisibility(View.VISIBLE);
            }
        });
    }

    private CouponPOJO AddData_ToList(DataSnapshot dataSnapshot)
    {
        CouponPOJO Obj = new CouponPOJO();
        Obj.setSKey(dataSnapshot.getKey());
        Obj.setUserKey(dataSnapshot.child("userKey").getValue(String.class));
        Obj.setExpiryDate((dataSnapshot.child("expiryDate").getValue(String.class)));
        Obj.setDiscount((dataSnapshot.child("discount").getValue(String.class)));
        Obj.setCreateDate((dataSnapshot.child("createDate").getValue(String.class)));
        Obj.setCode((dataSnapshot.child("code").getValue(String.class)));
        return Obj;
    }

    private void CheckAL(ArrayList<CouponPOJO> AL)
    {
        if(AL.size() == 0)
            TxtAlert.setVisibility(View.VISIBLE);
        else
            TxtAlert.setVisibility(View.GONE);
    }
}