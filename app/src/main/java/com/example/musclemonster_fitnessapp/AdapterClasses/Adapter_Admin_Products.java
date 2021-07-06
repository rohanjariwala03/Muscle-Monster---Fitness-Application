package com.example.musclemonster_fitnessapp.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclemonster_fitnessapp.Admin.Admin_Del_Prod;
import com.example.musclemonster_fitnessapp.Admin.Admin_Update_Prod;
import com.example.musclemonster_fitnessapp.BottomBarFragments.Products.Del_Product;
import com.example.musclemonster_fitnessapp.MoreMenuClasses.UpdateMyProduct;
import com.example.musclemonster_fitnessapp.POJOClasses.ProductUpload_POJO;
import com.example.musclemonster_fitnessapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Admin_Products extends RecyclerView.Adapter<Adapter_Admin_Products.MyViewHolder>
{
    Context context;
    ArrayList<ProductUpload_POJO> list;


    public Adapter_Admin_Products(Context context, ArrayList<ProductUpload_POJO> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @NotNull
    @Override
    public Adapter_Admin_Products.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.upd_del_prod_list_user,parent,false);
        return new Adapter_Admin_Products.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_Admin_Products.MyViewHolder holder, int position) {
        //ProductUpload_POJO Obj = list.get(position);
        holder.ProdName.setText(list.get(position).getProductName());
        holder.ProdPrice.setText(list.get(position).getProductPrice());
        Glide.with(context)
                .load(list.get(position).getImageUri())
                .into(holder.ImgV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,""+list.get(position).getProductName(),Toast.LENGTH_SHORT).show();

                PassData(position,v);
            }
        });

        holder.ImgBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassData(position,view);
            }
        });

        holder.ImgBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Deleting......" ,Toast.LENGTH_SHORT).show();
                Log.i("AdpMyProAdmin" ,list.get(position).getFKey());
                Intent intent = new Intent(context, Admin_Del_Prod.class);
                intent.putExtra("ItemKey",list.get(position).getFKey());
                view.getContext().startActivity(intent);

            }
        });
    }

    private void PassData(int position, View v)
    {
        Intent intent = new Intent(context, Admin_Update_Prod.class);
        intent.putExtra("ItemKey",list.get(position).getFKey());
        intent.putExtra("ItemName",list.get(position).getProductName());
        intent.putExtra("ItemPrice",list.get(position).getProductPrice());
        intent.putExtra("ItemCat",list.get(position).getProductCat());
        intent.putExtra("ItemDesc",list.get(position).getProductDesc());
        intent.putExtra("ItemImageUri",list.get(position).getImageUri());
        intent.putExtra("ItemWeight",list.get(position).getProductWeight());
        intent.putExtra("UserKey",list.get(position).getUserKey());
        intent.putExtra("ItemGen",list.get(position).getProdGen());
        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ProdName,ProdPrice;
        ImageView ImgV;
        ImageButton ImgBtnUpdate,ImgBtnDel;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ImgV = itemView.findViewById(R.id.ProdImgView);
            ProdName=itemView.findViewById(R.id.TxtProductName);
            ProdPrice=itemView.findViewById(R.id.TxtProductPrice);
            ImgBtnUpdate=itemView.findViewById(R.id.ImgBtnUpdate);
            ImgBtnDel=itemView.findViewById(R.id.ImgBtnDel);
        }
    }
}
