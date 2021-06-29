package com.example.musclemonster_fitnessapp.BottomBarFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import com.example.musclemonster_fitnessapp.LoginSignUp.ActivityLogIn;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Find_Trainer_Activity;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.MyProducts_User;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.Product_Add_Home;
import com.example.musclemonster_fitnessapp.R;
import com.example.musclemonster_fitnessapp.Update_Profile;
import com.google.firebase.auth.FirebaseAuth;


public class Fragment_More extends Fragment {

    Button btn_addItem;
    Button btn_logout;
    Button btn_MyProfile, btn_MyProducts;
    Button btn_findTrainer;
    Button addTrainer;


    FirebaseAuth mAuth;

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

        btn_MyProfile =view.findViewById(R.id.btnMyProfile);
        btn_MyProducts =view.findViewById(R.id.btnViewMyProduct);

        btn_findTrainer=view.findViewById(R.id.btnFindTrainer);

        btn_MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Update_Profile.class);
                startActivity(intent);
            }
        });

        btn_MyProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyProducts_User.class);
                startActivity(intent);
            }
        });

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
                //mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),ActivityLogIn.class));
            }
        });

        btn_findTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mAuth.signOut();
                Intent intent = new Intent(getContext(), Find_Trainer_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}