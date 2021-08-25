package com.example.musclemonster_fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Fragment_Chat;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.Fragment_Exercise_Home;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Fragment_More;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Products.Activity_Shopping;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Products.Fragment_Shopping;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

//import com.example.musclemonster_fitnessapp.ExerciseSub.ExerciseSubFragment;

public class MainActivity extends AppCompatActivity {

    private final int ID_Home=1;
    private final int ID_Message=2;
    private final int ID_Note=3;
    private final int ID_More=4;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Fragment_Exercise_Home FragmentExerciseHome;
    Fragment_Chat FragmentChat;
    Fragment_Shopping Fragmentshopping;
    Fragment_More Fragmentmore;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.support_toolbar);


        /*TextView selected_page=findViewById(R.id.selected_page);*/
        MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Home,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Message,R.drawable.ic_baseline_message_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Note,R.drawable.ic_baseline_add_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_More,R.drawable.ic_baseline_more_horiz_24));

        bottomNavigation.show(getIntent().getIntExtra("fragmentNumber",4),true);

        viewPager = findViewById(R.id.viewpager);

       /* bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
               // Toast.makeText(MainActivity.this,"Clicked item : " + item.getId(),Toast.LENGTH_SHORT).show();

                return null;
            }
        });*/

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
                FragmentChoice(item.getId());

               /* selected_page.setText(getString(R.string.main_page_selected,name));*/
                return null;
            }

        });

        /*bottomNavigation.setCount(ID_Note,"4");*/


    }

    public void FragmentChoice(int item)
    {
        String name;
        switch(item){
            case ID_Home:
                name = "Home";
                //Creating Instance of Fragment
                FragmentExerciseHome = new Fragment_Exercise_Home();
                //Calling Method to load Fragment in our Frame
                LoadFragment(FragmentExerciseHome);
                break;
            case ID_Message:
                name = "Message";
                FragmentChat = new Fragment_Chat();
                LoadFragment(FragmentChat);
                break;
            case ID_Note:
                Intent intent = new Intent(getApplicationContext(), Activity_Shopping.class);
                intent.putExtra("fragmentNumber",3);
                startActivity(intent);

                /*name = "Shopping";
                Fragmentshopping = new Fragment_Shopping();
                LoadFragment(Fragmentshopping);*/
               /* ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                viewPagerAdapter.AddFragment(new Fragment_Shopping(), "Shopping");
                viewPager.showContextMenu();
                viewPager.setAdapter(viewPagerAdapter);*/
                break;
            case ID_More:
                name = "More";
                Fragmentmore = new Fragment_More();
                LoadFragment(Fragmentmore);
                break;
            default:
                name="";
        }
    }


    //Method to Load Fragment in a Frame
    public void LoadFragment(Fragment fragment)
    {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}