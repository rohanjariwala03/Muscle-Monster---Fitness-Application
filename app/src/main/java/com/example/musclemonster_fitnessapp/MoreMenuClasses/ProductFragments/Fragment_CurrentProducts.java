package com.example.musclemonster_fitnessapp.MoreMenuClasses.ProductFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_MyProducts;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.MyProducts_User;
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

public class Fragment_CurrentProducts extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_MyProducts AdapterShopping;
    ArrayList<ProductUpload_POJO> list;
    String CurUser;
    TextView TxtAlert;

    public Fragment_CurrentProducts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__current_products, container, false);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=view.findViewById(R.id.recyclerviewProduct);
        TxtAlert = (TextView) view.findViewById(R.id.TxtAlert);
        database=FirebaseDatabase.getInstance().getReference("Product_Detail_Database");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<ProductUpload_POJO>();
        AdapterShopping=new Adapter_MyProducts(getContext(),list);
        recyclerView.setAdapter(AdapterShopping);
        recyclerView.setHasFixedSize(true);
       /* GridLayoutManager gridLayoutManager = new GridLayoutManager(MyProducts_User.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);*/
        CurUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        //Log.i("Usr My Prod id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString() );
        Query query=FirebaseDatabase.getInstance().getReference("Product_Detail_Database").orderByKey();

        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {
                        String UserKey = dataSnapshot.child("userKey").getValue(String.class);
                        String status = dataSnapshot.child("status").getValue(String.class);
                        //Log.i("MyProducts_User : ", "Key Matched" );
                        if(UserKey.equals(CurUser) && status == null) {
                            Log.i("MyProducts_User : ", "Key Matched" );
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
        return Obj;
    }

    private void CheckAL(ArrayList<ProductUpload_POJO> AL)
    {
        if(AL.size() == 0)
            TxtAlert.setVisibility(View.VISIBLE);
        else
            TxtAlert.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = (SearchView) item.getActionView();
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.removeAllViews();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                StartSearch(newText);
                return false;
            }
        });
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
                        String UserKey = dataSnapshot.child("userKey").getValue(String.class);
                        String status = dataSnapshot.child("status").getValue(String.class);
                        //Log.i("MyProducts_User : ", "Key Matched" );
                        if(UserKey.equals(CurUser) && status == null) {
                            Alist.add(AddData_ToList(dataSnapshot));
                        }
                    }
                    else
                    {
                        Log.i("MyProducts_User : ", "NO Data : Q" );
                    }
                }
                AdapterShopping = new Adapter_MyProducts(getContext(),Alist);
                recyclerView.setAdapter(AdapterShopping);
                AdapterShopping.notifyDataSetChanged();
                CheckAL(Alist);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.i("MyProducts_User : ", "NO Data : Q" );
                TxtAlert.setVisibility(View.VISIBLE);
            }
        });
    }
}