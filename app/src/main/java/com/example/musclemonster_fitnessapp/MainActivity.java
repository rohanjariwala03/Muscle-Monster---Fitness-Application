package com.example.musclemonster_fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

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



        TextView selected_page=findViewById(R.id.selected_page);
        MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Home,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Message,R.drawable.ic_baseline_message_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_Note,R.drawable.ic_baseline_add_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_More,R.drawable.ic_baseline_more_horiz_24));


        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
                Toast.makeText(MainActivity.this,"Clicked item : " + item.getId(),Toast.LENGTH_SHORT).show();

                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
                String name;
                switch(item.getId()){
                    case ID_Home:
                        name = "Home";
                        break;
                    case ID_Message:
                        name = "Message";
                        break;
                    case ID_Note:
                        name = "Note";
                        break;
                    case ID_More:
                        name = "More";
                        break;
                    default:
                        name="";
                }
                selected_page.setText(getString(R.string.main_page_selected,name));
                return null;
            }
        });



        bottomNavigation.setCount(ID_Note,"4");
        bottomNavigation.show(ID_Home,true);




    }
}