package com.example.musclemonster_fitnessapp.BottomBarFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.QuickContactBadge;

import androidx.fragment.app.Fragment;

import com.example.musclemonster_fitnessapp.LoginSignUp.ActivityLogIn;
import com.example.musclemonster_fitnessapp.LoginSignUp.ActivitySignUp;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Product_Add_Home;
import com.example.musclemonster_fitnessapp.R;


public class Fragment_More extends Fragment {

    Button btn_addItem;
    Button btn_logout;

    public Fragment_More() {
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
        View view=inflater.inflate(R.layout.fragment__more, container, false);
        btn_addItem =view.findViewById(R.id.btnSellProducts);
        btn_logout =view.findViewById(R.id.btnLogout);

        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Product_Add_Home.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivitySignUp.class);
                startActivity(intent);
            }
        });
        return view;
    }
}