package com.example.musclemonster_fitnessapp.MoreMenuClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Prod_Shopping;
import com.example.musclemonster_fitnessapp.AdapterClasses.Admin_Adp_Prod_Shopping;
import com.example.musclemonster_fitnessapp.Admin.MyProducts_Admin;
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

public class MyProducts_User extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_Prod_Shopping AdapterShopping;
    ArrayList<ProductUpload_POJO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products_user);
        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=findViewById(R.id.recyclerviewProduct);
        database=FirebaseDatabase.getInstance().getReference("Product_Detail_Database");

        recyclerView.setLayoutManager(new LinearLayoutManager(MyProducts_User.this));

        list=new ArrayList<ProductUpload_POJO>();
        AdapterShopping=new Adapter_Prod_Shopping(MyProducts_User.this,list);
        recyclerView.setAdapter(AdapterShopping);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyProducts_User.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);


        //Log.i("Usr My Prod id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString() );
        Query query=FirebaseDatabase.getInstance().getReference("Product_Detail_Database").orderByKey();

        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {
                        //Log.i("MyProducts_User : ", "Key Matched" );
                        if((dataSnapshot.child("userKey")).getValue(String.class).equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                            Log.i("MyProducts_User : ", "Key Matched" );
                            list.add(AddData_ToList(dataSnapshot));
                        }
                    }
                    else
                    {
                        Log.i("User My Prod", "NO Data : " );
                    }
                }
                // set the Adapter to RecyclerView
                AdapterShopping.notifyDataSetChanged();

                Log.i("Product Adapter ", "Product Binded ");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = (SearchView) item.getActionView();
        /*SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());*/
        /*MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);*/
        /*MenuItemCompat.setActionView(item, sv);*/
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.removeAllViews();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                /*list.clear();
                StartSearch(query);*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                StartSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
           return Obj;
    }


    private void StartSearch(String SQuery) {
        Query query=FirebaseDatabase.getInstance().getReference("Product_Detail_Database")
                .orderByChild("productName").startAt(SQuery).endAt(SQuery + "\uf8ff");
        recyclerView.removeAllViews();
        recyclerView.removeAllViewsInLayout();
        ArrayList<ProductUpload_POJO> Alist=new ArrayList<ProductUpload_POJO>();
        AdapterShopping.notifyDataSetChanged();
        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            //If database get some data then this will fire
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        if((dataSnapshot.child("userKey")).getValue(String.class).equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {

                            list.add(AddData_ToList(dataSnapshot));
                        }
                    }
                    else
                    {
                        Log.i("MyProducts_User : ", "NO Data : Q" );
                    }
                }
                AdapterShopping = new Adapter_Prod_Shopping(MyProducts_User.this,Alist);
                recyclerView.setAdapter(AdapterShopping);
                AdapterShopping.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.i("MyProducts_User : ", "NO Data : Q" );
            }
        });
    }
}