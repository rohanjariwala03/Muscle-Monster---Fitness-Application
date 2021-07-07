package com.example.musclemonster_fitnessapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Chat.Fragment_Chat;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Exercise.Fragment_Exercise_Home;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Fragment_More;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Products.Fragment_Shopping;
//import com.example.musclemonster_fitnessapp.ExerciseSub.ExerciseSubFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    private final int ID_Home=1;
    private final int ID_Message=2;
    private final int ID_Note=3;
    private final int ID_More=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*TextView selected_page=findViewById(R.id.selected_page);*/
        MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Home,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Message,R.drawable.ic_baseline_message_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Note,R.drawable.ic_baseline_add_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_More,R.drawable.ic_baseline_more_horiz_24));

        /*try {
            FragmentChoice(getIntent().getIntExtra("fragmentNumber",1));
        }catch (Exception e)
        {*/
            bottomNavigation.show(getIntent().getIntExtra("fragmentNumber",1),true);



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
                Fragment_Exercise_Home FragmentExerciseHome = new Fragment_Exercise_Home();
                //Calling Method to load Fragment in our Frame
                LoadFragment(FragmentExerciseHome);
                break;
            case ID_Message:
                name = "Message";

                Fragment_Chat FragmentChat = new Fragment_Chat();
                LoadFragment(FragmentChat);
                break;
            case ID_Note:
                name = "Shopping";
                Fragment_Shopping Fragmentshopping = new Fragment_Shopping();
                LoadFragment(Fragmentshopping);
                break;
            case ID_More:
                name = "More";
                Fragment_More Fragmentmore = new Fragment_More();
                LoadFragment(Fragmentmore);
                break;
            default:
                name="";
        }
    }


    //Method to Load Fragment in a Frame
    public void LoadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout,fragment);
        transaction.commit();

    }
}