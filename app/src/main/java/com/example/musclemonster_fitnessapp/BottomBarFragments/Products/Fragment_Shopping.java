package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Prod_Shopping;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.RBTreeSortedMap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment_Shopping extends Fragment {


    /*SearchView mySearchView;*/
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_Prod_Shopping AdapterShopping;
    ArrayList<ProductUpload_POJO> list;
    EditText EditSearch;
    Button BtnFiter;
    String RBChecked = "Unisex";
    Spinner SpinnerCat;


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
                        String status = dataSnapshot.child("status").getValue(String.class);
                        String PuserKey = dataSnapshot.child("userKey").getValue(String.class);
                        if(status == null && !FirebaseAuth.getInstance().getCurrentUser().getUid().equalsIgnoreCase(PuserKey)) {
                            ProductUpload_POJO Obj = new ProductUpload_POJO();
                            Obj.setFKey(dataSnapshot.getKey());
                            Obj.setProductName((dataSnapshot.child("productName").getValue(String.class)));
                            Obj.setProductWeight((dataSnapshot.child("productWeight").getValue(String.class)));
                            Obj.setProductPrice((dataSnapshot.child("productPrice").getValue(String.class)));
                            Obj.setProductCat((dataSnapshot.child("productCat").getValue(String.class)));
                            Obj.setProductDesc((dataSnapshot.child("productDesc").getValue(String.class)));
                            Obj.setImageUri((dataSnapshot.child("imageUri").getValue(String.class)));
                            Obj.setUserKey(PuserKey);
                            Obj.setProdGen((dataSnapshot.child("prodGen").getValue(String.class)));
                            list.add(Obj);
                        }
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment__shopping, container, false);

        /*BtnSearch = (Button) view.findViewById(R.id.Btnsearch);*/
        BtnFiter = (Button) view.findViewById(R.id.BtnFilt);
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

        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.inflate(R.menu.cat_menu);
        popup.setGravity(Gravity.TOP);
        BtnFiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    // This activity implements OnMenuItemClickListener
                    /*popup.getMenu().getItem(RBChecked).setChecked(true);*/
                    popup.show();

            }
        });

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                RBChecked = menuItem.toString();
                menuItem.setChecked(true);
                Toast.makeText(getContext(),"Item " + menuItem.toString(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });



        /*final Spinner spinner = (Spinner)view.findViewById(R.id.SpinnerCat);
        List<Map<String, String>> items = new ArrayList<Map<String, String>>();

        Map<String, String> item0 = new HashMap<String, String>(2);
        item0.put("text", "Browse aisles...");
        item0.put("subText", "(Upgrade required)");
        items.add(item0);

        Map<String, String> item1 = new HashMap<String, String>(2);
        item1.put("text", "Option 1");
        item1.put("subText", "(sub text 1)");
        items.add(item1);

        Map<String, String> item2 = new HashMap<String, String>(2);
        item2.put("text", "Option 2");
        item2.put("subText", "(sub text 2)");
        items.add(item2);

        SimpleAdapter adapter1 = new SimpleAdapter(getActivity(), items,
                android.R.layout.simple_spinner_item, // This is the layout that will be used for the standard/static part of the spinner. (You can use android.R.layout.simple_list_item_2 if you want the subText to also be shown here.)
                new String[] {"text", "subText"},
                new int[] {android.R.id.text1, android.R.id.text2}
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_2);

        spinner.setAdapter(adapter1);*/


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*switch (item.getItemId()) {
            case R.id.vibrate:
            case R.id.dont_vibrate:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        Log.i("Item" , String.valueOf(item.getItemId()));
        return false;
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
                        String status = dataSnapshot.child("status").getValue(String.class);
                        String PuserKey = dataSnapshot.child("userKey").getValue(String.class);
                        if(status == null && !FirebaseAuth.getInstance().getCurrentUser().getUid().equalsIgnoreCase(PuserKey)) {
                            ProductUpload_POJO Obj = new ProductUpload_POJO();
                            // DtSnapshot.getValue(ProductUpload_POJO.class);
                            Obj.setFKey(dataSnapshot.getKey());
                            Obj.setProductName((dataSnapshot.child("productName").getValue(String.class)));
                            Obj.setProductWeight((dataSnapshot.child("productWeight").getValue(String.class)));
                            Obj.setProductPrice((dataSnapshot.child("productPrice").getValue(String.class)));
                            Obj.setProductCat((dataSnapshot.child("productCat").getValue(String.class)));
                            Obj.setProductDesc((dataSnapshot.child("productDesc").getValue(String.class)));
                            Obj.setImageUri((dataSnapshot.child("imageUri").getValue(String.class)));
                            Obj.setUserKey(PuserKey);
                            Obj.setProdGen((dataSnapshot.child("prodGen").getValue(String.class)));
                            Alist.add(Obj);
                        }
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