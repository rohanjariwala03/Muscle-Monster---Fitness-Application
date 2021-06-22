package com.example.musclemonster_fitnessapp.BottomBarFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Prod_Shopping;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class Fragment_Shopping extends Fragment {


    SearchView mySearchView;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_Prod_Shopping AdapterShopping;
    ArrayList<ProductUpload_POJO> list;


    public Fragment_Shopping() {
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

        View view=inflater.inflate(R.layout.fragment__shopping, container, false);
        mySearchView = (SearchView) view.findViewById(R.id.SearchView);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=view.findViewById(R.id.recyclerviewProduct);
        database=FirebaseDatabase.getInstance().getReference("Product_Detail_Database");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<ProductUpload_POJO>();
        /*AdapterShopping=new Adapter_Prod_Shopping(getContext(),list);
        recyclerView.setAdapter(AdapterShopping);*/


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        AdapterShopping=new Adapter_Prod_Shopping(getContext(),list);
        recyclerView.setAdapter(AdapterShopping); // set the Adapter to RecyclerView

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        //String K = dataSnapshot.getKey();
                        //list.add(Description);
                        /*for (DataSnapshot DtSnapshot : dataSnapshot.getChildren()) {*/
                            ProductUpload_POJO Obj = new ProductUpload_POJO();
                                   // DtSnapshot.getValue(ProductUpload_POJO.class);
                            Obj.setFKey(dataSnapshot.getKey());
                            Obj.setProductName((dataSnapshot.child("productName").getValue(String.class)));
                            Obj.setProductWeight((dataSnapshot.child("productWeight").getValue(String.class)));
                            Obj.setProductPrice((dataSnapshot.child("productPrice").getValue(String.class)));
                            Obj.setProductCat((dataSnapshot.child("productCat").getValue(String.class)));
                            Obj.setProductDesc((dataSnapshot.child("productDesc").getValue(String.class)));
                            Obj.setImageUri((dataSnapshot.child("imageUri").getValue(String.class)));
                            list.add(Obj);
                       /* }*/
                        //Log.i(getTag(), "Data : " + dataSnapshot.getValue(String.class));
                    }
                    else
                    {
                        Log.i(getTag(), "NO Data : " );
                    }
                }
                AdapterShopping.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return view;
    }
}