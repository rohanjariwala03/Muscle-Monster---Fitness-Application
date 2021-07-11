package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Prod_Shopping;
import com.example.musclemonster_fitnessapp.AdapterClasses.Admin_Adp_Prod_Shopping;
import com.example.musclemonster_fitnessapp.Admin.View_Prod_Admin;
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

import static java.lang.System.in;


public class Fragment_Shopping extends Fragment {


    /*SearchView mySearchView;*/
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_Prod_Shopping AdapterShopping;
    ArrayList<ProductUpload_POJO> list, Alist;
    MenuItem item;
    SearchView sv;
    Button BtnFiter;
    String SEARCHED = "N";
    String CATEGORIZED = "N";
    String RBChecked = "Unisex", status,PuserKey,CurUserKey;
    TextView TxtAlert;

    public Fragment_Shopping() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        Log.i("Freg Shop" , "Attached");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.i("Freg Shop" , "on create");
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Freg Shop" , "on view created");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Freg Shop" , "REsumed");
        /*list.clear();
        AdapterShopping.notifyDataSetChanged();*/
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Freg Shop" , "Paused");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Freg Shop" , "Stopped");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment__shopping, container, false);
        Log.i("Freg Shop" , "Create View");
        BtnFiter = (Button) view.findViewById(R.id.BtnFilt);
        TxtAlert = (TextView) view.findViewById(R.id.TxtAlert);

        firebaseDatabase=FirebaseDatabase.getInstance();
        recyclerView=view.findViewById(R.id.recyclerviewProduct);
        database=FirebaseDatabase.getInstance().getReference("Product_Detail_Database");
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<ProductUpload_POJO>();
        Alist=new ArrayList<ProductUpload_POJO>();
        AdapterShopping=new Adapter_Prod_Shopping(getContext(),list);
        recyclerView.setAdapter(AdapterShopping);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter

        SEARCHED = "N";
        CATEGORIZED = "N";
        CurUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DefaultData();

        /*BtnFiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Activity_Shopping.class);
                startActivity(intent);
            }
        });*/

        PopupMenu popup = new PopupMenu(view.getContext(),  BtnFiter);
        popup.inflate(R.menu.cat_menu);
        popup.setGravity(Gravity.TOP);
        BtnFiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // This activity implements OnMenuItemClickListener
                    popup.show();
            }
        });

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                CATEGORIZED = "Y";
                RBChecked = menuItem.toString();
                menuItem.setChecked(true);
                switch(menuItem.toString()) {
                    case "Machines":
                    case "Dumbbell":
                    case "Bench":
                    case "Barbell":
                    case "Bicycle":
                    case "Balanceball":
                    case "Kettlebell":
                    case "Plates":
                    case "Treadmill":
                    case "Clothing":
                        SortData(menuItem.toString());
                        break;

                    case "Male":
                    case "Female":
                        SortGen(menuItem.toString());
                        break;

                    case "Clear Filter":
                        CATEGORIZED = "N";
                        SEARCHED = "N";
                        DefaultData();
                        sv.setQuery(null,false);
                        break;

                    default:
                        SEARCHED = "N";
                        CATEGORIZED = "N";
                        DefaultData();
                        break;

                }
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

    private void SetMyAdapter(ArrayList<ProductUpload_POJO> AL)
    {
        AdapterShopping=new Adapter_Prod_Shopping(getContext(),AL);
        recyclerView.setAdapter(AdapterShopping);
        AdapterShopping.notifyDataSetChanged();
    }

    private void SortData(String SelectedItem) {
        if(SEARCHED == "N") {
            Alist.clear();
            Query query = database.orderByChild("productCat").equalTo(SelectedItem.toLowerCase());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        if (dataSnapshot.exists()) {
                            if (ValidateData(dataSnapshot)) {
                                Alist.add(AddDataToPOJO(dataSnapshot, PuserKey));
                            }
                        } else {
                            Log.i("user View Prod", "NO Data : ");
                        }
                    }
                    CheckAL(Alist);
                    SetMyAdapter(Alist);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    TxtAlert.setVisibility(View.VISIBLE);
                }
            });
        }
        else
        {
            Alist.clear();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getProductCat().equalsIgnoreCase(SelectedItem)) {
                    Alist.add(list.get(i));
                }
            }
            CheckAL(Alist);
            SetMyAdapter(Alist);
        }
    }

    private boolean ValidateData(DataSnapshot dataSnapshot)
    {
        status = dataSnapshot.child("status").getValue(String.class);
        PuserKey = dataSnapshot.child("userKey").getValue(String.class);
        if(status == null && !CurUserKey.equalsIgnoreCase(PuserKey))
            return true;
        else
            return false;
    }


    private void SortGen(String SelectedItem)
    {
        for(int i=0;i< Alist.size();i++)
        {
            if(!Alist.get(i).getProdGen().equalsIgnoreCase(SelectedItem))
            {
                Alist.remove(i);
                AdapterShopping.notifyDataSetChanged();
            }
            CheckAL(Alist);
        }

    }

    private void DefaultData()
    {
        list.clear();
        Query query=FirebaseDatabase.getInstance().getReference("Product_Detail_Database");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {
                        if(ValidateData(dataSnapshot)) {
                            list.add(AddDataToPOJO(dataSnapshot,PuserKey));
                        }
                    }
                    else
                    {
                        Log.i("user View Prod", "NO Data : " );
                    }
                }
                CheckAL(list);
                SetMyAdapter(list);
                Log.i("Product Adapter ", "Product Binded ");
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                TxtAlert.setVisibility(View.VISIBLE);
            }
        });
    }

    private ProductUpload_POJO AddDataToPOJO(DataSnapshot dataSnapshot, String UKey)
    {
        ProductUpload_POJO Obj = new ProductUpload_POJO();
        Obj.setFKey(dataSnapshot.getKey());
        Obj.setProductName((dataSnapshot.child("productName").getValue(String.class)));
        Obj.setProductWeight((dataSnapshot.child("productWeight").getValue(String.class)));
        Obj.setProductPrice((dataSnapshot.child("productPrice").getValue(String.class)));
        Obj.setProductCat((dataSnapshot.child("productCat").getValue(String.class)));
        Obj.setProductDesc((dataSnapshot.child("productDesc").getValue(String.class)));
        Obj.setImageUri((dataSnapshot.child("imageUri").getValue(String.class)));
        Obj.setUserKey(UKey);
        Obj.setProdGen((dataSnapshot.child("prodGen").getValue(String.class)));
        return Obj;
    }

    private void StartSearch(String SQuery) {
        if(SQuery.length()!=0)
            SEARCHED = "Y";
        else
            SEARCHED = "N";

        Query query = FirebaseDatabase.getInstance().getReference("Product_Detail_Database")
                .orderByChild("productName").startAt(SQuery).endAt(SQuery + "\uf8ff");
        recyclerView.removeAllViews();
        recyclerView.removeAllViewsInLayout();
        list = new ArrayList<ProductUpload_POJO>();
        Alist.clear();
        AdapterShopping.notifyDataSetChanged();
        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            //If database get some data then this will fire
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (dataSnapshot.exists()) {
                        if (ValidateData(dataSnapshot)) {
                            if(CATEGORIZED == "N")
                                list.add(AddDataToPOJO(dataSnapshot, PuserKey));
                            else
                            {
                                if(dataSnapshot.child("productCat").getValue(String.class).equalsIgnoreCase(RBChecked) ||
                                        RBChecked.equalsIgnoreCase(dataSnapshot.child("prodGen").getValue(String.class)))
                                    Alist.add(AddDataToPOJO(dataSnapshot, PuserKey));
                            }
                        }
                    } else {
                        Log.i("Frag_Shopping : ", "NO Data : Q");
                    }
                }
                if(CATEGORIZED == "N") {
                    CheckAL(list);
                    SetMyAdapter(list);
                }else {
                    CheckAL(Alist);
                    SetMyAdapter(Alist);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                TxtAlert.setVisibility(View.VISIBLE);
            }
        });
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
        Log.i("Freg Shop" , "oncreateOptionMenu");
        inflater.inflate(R.menu.search_menu, menu);
        item = menu.findItem(R.id.search);
        sv = (SearchView) item.getActionView();
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

        sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                SEARCHED = "N";
                return false;
            }
        });
    }
}