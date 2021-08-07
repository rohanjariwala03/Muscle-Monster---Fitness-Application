package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.musclemonster_fitnessapp.POJOClasses.CouponPOJO;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CouponDialog extends AppCompatDialogFragment {

    private CouponDialog.CouponDialogListener listener;
    private ArrayList<CouponPOJO> lstCoupons = new ArrayList<>();
    private ArrayList<String> lstStr = new ArrayList<>();
    private Spinner spinner;

    public CouponDialog(ArrayList<CouponPOJO> lstCoupon) {
        this.lstCoupons = lstCoupon;
        Log.i("COUPON DIALOG" , String.valueOf(lstCoupon.size()));
        Log.i("COUPON DIALOG1" , String.valueOf(lstCoupons.size()));
        lstStr = new ArrayList<>();
        for (int i=0;i< lstCoupons.size();i++)
        {
            lstStr.add(lstCoupons.get(i).getDiscount());
        }
        Log.i("COUPON DIALOG2" , String.valueOf(lstStr.size()));
        /*Log.i("COUPON DIALOG3" , lstStr.get(0).toString());*/

    }

    public CouponDialog() {
        lstStr.add("No Coupons");
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_coupon_dialog, null);

        spinner = view.findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,lstStr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*SELECTED = adapterView.getSelectedItem().toString();*/
                /*textView.setText("Select Discount : " + SELECTED);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
               /* SELECTED = "$5";*/
            }
        });

        builder.setView(view)
                .setTitle("Apply Coupon")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyTexts(new CouponPOJO());
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (CouponDialog.CouponDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CouponDialogListener");
        }
    }

    public interface CouponDialogListener {
        void applyTexts(CouponPOJO couponPOJO);
    }
}
