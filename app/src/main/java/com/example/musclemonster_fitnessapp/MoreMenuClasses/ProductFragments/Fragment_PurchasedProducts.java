package com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Sold_Pur_Prods_User;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
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

public class Fragment_PurchasedProducts extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference database;
    private Adapter_Sold_Pur_Prods_User AdapterShopping;
    private ArrayList<ProductUpload_POJO> list;
    private String CurUser;
    private TextView TxtAlert;

   public Fragment_PurchasedProducts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__purchased_products, container, false);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=view.findViewById(R.id.recyclerviewProduct);
        TxtAlert = (TextView) view.findViewById(R.id.TxtAlert);
        database=FirebaseDatabase.getInstance().getReference("Product_Detail_Database");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<ProductUpload_POJO>();
        AdapterShopping=new Adapter_Sold_Pur_Prods_User(getContext(),list);
        recyclerView.setAdapter(AdapterShopping);
        recyclerView.setHasFixedSize(true);
       /* GridLayoutManager gridLayoutManager = new GridLayoutManager(MyProducts_User.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);*/
        CurUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        //Log.i("Usr My Prod id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString() );
        Query query=FirebaseDatabase.getInstance().getReference("Product_Detail_Database").orderByKey();

        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {
                        String buyerKey = dataSnapshot.child("buyer").getValue(String.class);
                        Log.i("Purchased User : ", buyerKey + " a " );
                        if(CurUser.equals(buyerKey)) {
                            list.add(AddData_ToList(dataSnapshot));
                        }
                    }
                    else
                    {
                        TxtAlert.setVisibility(View.VISIBLE);
                        Log.i("User My Prod", "NO Data : " );
                    }
                }
                // set the Adapter to RecyclerView
                AdapterShopping.notifyDataSetChanged();
                CheckAL(list);
                Log.i("Product Adapter ", "Product Binded ");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                TxtAlert.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private ProductUpload_POJO AddData_ToList(DataSnapshot dataSnapshot)
    {
        ProductUpload_POJO Obj = new ProductUpload_POJO();
        Obj.setFKey(dataSnapshot.getKey());
        Obj.setProductName((dataSnapshot.child("productName").getValue(String.class)));
        Obj.setProductWeight((dataSnapshot.child("productWeight").getValue(String.class)));
        Obj.setProductPrice((dataSnapshot.child("productPrice").getValue(String.class)));
        Obj.setProductCat((dataSnapshot.child("productCat").getValue(String.class)));
        Obj.setProductDesc((dataSnapshot.child("productDesc").getValue(String.class)));
        Obj.setImageUri((dataSnapshot.child("imageUri").getValue(String.class)));
        Obj.setUserKey((dataSnapshot.child("userKey").getValue(String.class)));
        Obj.setProdGen((dataSnapshot.child("prodGen").getValue(String.class)));
        Obj.setBuyDate((dataSnapshot.child("Buy Date").getValue(String.class)));
        return Obj;
    }

    private void CheckAL(ArrayList<ProductUpload_POJO> AL)
    {
        if(AL.size() == 0)
            TxtAlert.setVisibility(View.VISIBLE);
        else
            TxtAlert.setVisibility(View.GONE);
    }
}