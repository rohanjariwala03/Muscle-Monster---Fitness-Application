package com.example.musclemonster_fitnessapp.BottomBarFragments.Products;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

public class CouponDialog extends AppCompatDialogFragment {

    private CouponDialog.CouponDialogListener listener;
    private EditText EditCouponCode;

    public CouponDialog() {
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_coupon_dialog, null);

        EditCouponCode = view.findViewById(R.id.editCouponCode);

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
                        listener.applyTexts(String.valueOf(EditCouponCode.getText()));
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
        void applyTexts(String Str);
    }
}
