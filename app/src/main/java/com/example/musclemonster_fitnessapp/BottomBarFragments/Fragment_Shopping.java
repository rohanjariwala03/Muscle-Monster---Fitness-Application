package com.example.musclemonster_fitnessapp.BottomBarFragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Prod_Shopping;
import com.example.musclemonster_fitnessapp.ExerciseSub.Exercise_Sub_Pojo;
import com.example.musclemonster_fitnessapp.MainActivity;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class Fragment_Shopping extends Fragment {


    /*SearchView mySearchView;*/
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_Prod_Shopping AdapterShopping;
    ArrayList<ProductUpload_POJO> list;
    EditText EditSearch;
    Button BtnSearch;


    public Fragment_Shopping() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        Query query=FirebaseDatabase.getInstance().getReference("Product_Detail_Database").orderByKey();

        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {

                        //String K = dataSnapshot.getKey();
                        //list.add(Description);
                        //*for (DataSnapshot DtSnapshot : dataSnapshot.getChildren()) {
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
                    }
                    else
                    {
                        Log.i(getTag(), "NO Data : " );
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        /*list.clear();
        AdapterShopping.notifyDataSetChanged();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment__shopping, container, false);

        /*BtnSearch = (Button) view.findViewById(R.id.Btnsearch);*/
        /*EditSearch = (EditText) view.findViewById(R.id.search);*/

        /*mySearchView = (SearchView) view.findViewById(R.id.SearchView);*/
        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=view.findViewById(R.id.recyclerviewProduct);
        database=FirebaseDatabase.getInstance().getReference("Product_Detail_Database");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<ProductUpload_POJO>();
        /*AdapterShopping=new Adapter_Prod_Shopping(getContext(),list);
        recyclerView.setAdapter(AdapterShopping);*/
        AdapterShopping=new Adapter_Prod_Shopping(getContext(),list);
        recyclerView.setAdapter(AdapterShopping);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter





       /* mySearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartSearch("");
                Log.i(getTag(), "Clicked : " );
            }
        });*/

        /*EditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StartSearch(s.toString());
            }
        });*/

       /* mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                StartSearch(query);
                Log.i(getTag(), "submit : " );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                StartSearch(newText);
                Log.i(getTag(), "Changed : " );
                return false;
            }
        });*/


        return view;
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

                        ProductUpload_POJO Obj = new ProductUpload_POJO();
                        // DtSnapshot.getValue(ProductUpload_POJO.class);
                        Obj.setFKey(dataSnapshot.getKey());
                        Obj.setProductName((dataSnapshot.child("productName").getValue(String.class)));
                        Obj.setProductWeight((dataSnapshot.child("productWeight").getValue(String.class)));
                        Obj.setProductPrice((dataSnapshot.child("productPrice").getValue(String.class)));
                        Obj.setProductCat((dataSnapshot.child("productCat").getValue(String.class)));
                        Obj.setProductDesc((dataSnapshot.child("productDesc").getValue(String.class)));
                        Obj.setImageUri((dataSnapshot.child("imageUri").getValue(String.class)));
                        Alist.add(Obj);
                        Log.i("Frag_Shopping : ", Obj.getProductName() );
                        //Toast.makeText(Exercise_Sub_Activity.this,"Connect",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.i("Frag_Shopping : ", "NO Data : Q" );
                    }
                }
                AdapterShopping = new Adapter_Prod_Shopping(getContext(),Alist);
                recyclerView.setAdapter(AdapterShopping);
                AdapterShopping.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.i("Frag_Shopping : ", "NO Data : Q" );
            }


        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

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

        /*sv.setSubmitButtonEnabled(true);*/

        /*sv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Entered");
                String q = sv.getQuery().toString();
                list.clear();
                AdapterShopping.notifyDataSetChanged();
                StartSearch(q);
            }
        });*/
    }
}