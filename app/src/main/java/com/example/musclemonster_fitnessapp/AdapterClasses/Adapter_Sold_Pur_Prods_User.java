package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Sold_Pur_Prods_User extends RecyclerView.Adapter<Adapter_Sold_Pur_Prods_User.MyViewHolder>
{
    Context context;
    ArrayList<ProductUpload_POJO> list;
    private AlertDialog.Builder alertBuilder1;

    public Adapter_Sold_Pur_Prods_User(Context context, ArrayList<ProductUpload_POJO> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @NotNull
    @Override
    public Adapter_Sold_Pur_Prods_User.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.sold_purchased_prod_list,parent,false);
        return new Adapter_Sold_Pur_Prods_User.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_Sold_Pur_Prods_User.MyViewHolder holder, int position) {

        holder.ProdName.setText(list.get(position).getProductName());
        holder.ProdPrice.setText(list.get(position).getProductPrice());
        Glide.with(context)
                .load(list.get(position).getImageUri())
                .into(holder.ImgV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewDetail(position,v);
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
        alertBuilder1.setTitle("Product Information");
        alertBuilder1.setMessage("Item Name : " + list.get(position).getProductName() +
                "\nItem Price : $" + list.get(position).getProductPrice() +
                "\nCategory : " + list.get(position).getProductCat() +
                "\nDescription : " + list.get(position).getProductDesc() +
                "\nWeight : " + list.get(position).getProductWeight() +
                "\nGender : " + list.get(position).getProdGen() +
                "\nListed Date : " + list.get(position).getUploadDate() +
                "\nPurchase Date : " + list.get(position).getBuyDate());
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

        TextView ProdName,ProdPrice;
        ImageView ImgV;
        ImageButton ImgBtnInfo;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ImgV = itemView.findViewById(R.id.ProdImgView);
            ProdName=itemView.findViewById(R.id.TxtProductName);
            ProdPrice=itemView.findViewById(R.id.TxtProductPrice);
            ImgBtnInfo=itemView.findViewById(R.id.ImgBtnINFO);
        }
    }
}
