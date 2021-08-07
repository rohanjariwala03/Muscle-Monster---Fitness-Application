package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclemonster_fitnessapp.POJOClasses.CouponPOJO;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Adapter_MyCoupons extends RecyclerView.Adapter<Adapter_MyCoupons.MyViewHolder>
{
    Context context;
    ArrayList<CouponPOJO> list;
    private AlertDialog.Builder alertBuilder1;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    public Adapter_MyCoupons(Context context, ArrayList<CouponPOJO> list) {
        this.context = context;
        this.list = list;
        myClipboard = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE);
    }


    @NonNull
    @NotNull
    @Override
    public Adapter_MyCoupons.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.coupons_list,parent,false);
        return new Adapter_MyCoupons.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_MyCoupons.MyViewHolder holder, int position) {
        //CouponPOJO Obj = list.get(position);
        holder.TCouponCode.setText(list.get(position).getCode());
        holder.TDiscount.setText(list.get(position).getDiscount());
        holder.TExpiryDate.setText(list.get(position).getExpiryDate());

        holder.ImgBtnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClip = ClipData.newPlainText("text", list.get(position).getCode());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(view.getContext(), "Text Copied",Toast.LENGTH_SHORT).show();
            }
        });

        holder.ImgBtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDetail(position,view);
            }
        });
    }

    private void ViewDetail(int position, View v)
    {
        alertBuilder1 = new AlertDialog.Builder(v.getContext());
        alertBuilder1.setTitle("Coupon Information");
        alertBuilder1.setMessage("Code : " + list.get(position).getCode() +
                "\nDiscount : " + list.get(position).getDiscount() + "%" +
                "\nAllocate Date : " + list.get(position).getCreateDate() +
                "\nExpiry Date : " + list.get(position).getExpiryDate());
        alertBuilder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBuilder1.setCancelable(true);
        AlertDialog alertDialog1 = alertBuilder1.create();
        alertDialog1.setCancelable(true);
        alertDialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                alertDialog1.cancel();
            }
        });
        alertDialog1.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView TCouponCode,TDiscount,TExpiryDate;
        ImageButton ImgBtnInfo,ImgBtnCopy;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            TCouponCode=itemView.findViewById(R.id.TxtCouponCode);
            TDiscount=itemView.findViewById(R.id.TxtDiscount);
            TExpiryDate=itemView.findViewById(R.id.TxtExpiryDate);
            ImgBtnInfo=itemView.findViewById(R.id.ImgBtnInfo);
            ImgBtnCopy=itemView.findViewById(R.id.ImgBtnCopy);
        }
    }
}
