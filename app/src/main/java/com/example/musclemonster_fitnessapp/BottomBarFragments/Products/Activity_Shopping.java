package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.musclemonster_fitnessapp.AdapterClasses.Adapter_Prod_Shopping;
import com.example.musclemonster_fitnessapp.MainActivity;
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

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Activity_Shopping extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Adapter_Prod_Shopping AdapterShopping;
    ArrayList<ProductUpload_POJO> list;
    Button BtnFiter;
    String SEARCH = "N";
    String RBChecked = "Unisex", status,PuserKey,CurUserKey;

    private final int ID_Home=1;
    private final int ID_Message=2;
    private final int ID_Note=3;
    private final int ID_More=4;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.support_toolbar);

        MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Home,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Message,R.drawable.ic_baseline_message_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Note,R.drawable.ic_baseline_add_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_More,R.drawable.ic_baseline_more_horiz_24));

        firebaseDatabase=FirebaseDatabase.getInstance();
        //database=firebaseDatabase.getReference();
        recyclerView=findViewById(R.id.recyclerviewProduct);
        database=FirebaseDatabase.getInstance().getReference("Product_Detail_Database");
        recyclerView.setHasFixedSize(true);

        BtnFiter = (Button) findViewById(R.id.BtnFilt);

        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_Shopping.this));

        list=new ArrayList<ProductUpload_POJO>();
        AdapterShopping=new Adapter_Prod_Shopping(Activity_Shopping.this,list);
        recyclerView.setAdapter(AdapterShopping);

        CurUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Activity_Shopping.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        bottomNavigation.show(getIntent().getIntExtra("fragmentNumber",3),true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
                // Toast.makeText(MainActivity.this,"Clicked item : " + item.getId(),Toast.LENGTH_SHORT).show();

                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
                FragmentChoice(item.getId());
                return null;
            }
        });

        SEARCH = "N";

        DefaultData();

        PopupMenu popup = new PopupMenu(getApplicationContext(),  BtnFiter);
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
                    default:
                        DefaultData();
                        break;

                }
                Toast.makeText(getApplicationContext(),"Item " + menuItem.toString(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void SortData(String SelectedItem) {
        if(SEARCH == "N") {
            list.clear();
            Query query = database.orderByChild("productCat").equalTo(SelectedItem.toLowerCase());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        if (dataSnapshot.exists()) {
                            if (ValidateData(dataSnapshot)) {
                                list.add(AddDataToPOJO(dataSnapshot, PuserKey));
                            }
                        } else {
                            Log.i("user View Prod", "NO Data : ");
                        }
                    }
                    AdapterShopping.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
        else
        {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getProductCat().equalsIgnoreCase(SelectedItem)) {
                list.remove(i);
                AdapterShopping.notifyDataSetChanged();
            }
        }
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
            for(int i=0;i< list.size();i++)
            {
                if(!list.get(i).getProdGen().equalsIgnoreCase(SelectedItem))
                {
                    list.remove(i);
                    AdapterShopping.notifyDataSetChanged();
                }
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
                AdapterShopping.notifyDataSetChanged();
                SEARCH = "N";
                Log.i("Product Adapter ", "Product Binded ");
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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

    public void FragmentChoice(int item)
    {
        String name;
        Intent intent;
        switch(item){
            case ID_Home:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("fragmentNumber",1);
                startActivity(intent);
                break;
            case ID_Message:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("fragmentNumber",2);
                startActivity(intent);
                break;
            case ID_Note:
                break;
            case ID_More:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("fragmentNumber",4);
                startActivity(intent);
                break;
            default:
                name="";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = (SearchView) item.getActionView();
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
                SEARCH = "N";
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void StartSearch(String SQuery) {
        Query query=FirebaseDatabase.getInstance().getReference("Product_Detail_Database")
                .orderByChild("productName").startAt(SQuery).endAt(SQuery + "\uf8ff");
        recyclerView.removeAllViews();
        recyclerView.removeAllViewsInLayout();
        list=new ArrayList<ProductUpload_POJO>();
        AdapterShopping.notifyDataSetChanged();
        //Database event listner for success or failure
        query.addValueEventListener(new ValueEventListener() {
            //If database get some data then this will fire
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
                        Log.i("Frag_Shopping : ", "NO Data : Q" );
                    }
                }
                AdapterShopping = new Adapter_Prod_Shopping(getApplicationContext(),list);
                recyclerView.setAdapter(AdapterShopping);
                AdapterShopping.notifyDataSetChanged();
                SEARCH = "Y";
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.i("Frag_Shopping : ", "NO Data : Q" );
            }
        });
    }
}