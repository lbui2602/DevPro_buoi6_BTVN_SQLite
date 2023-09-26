package com.example.devpro_buoi6_sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductModel> list;
    private Context context;

    public ProductAdapter(List<ProductModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductModel productModel=list.get(position);
        holder.tvTittle.setText(productModel.getTitle());
        holder.tvPrice.setText(productModel.getPrice().toString());
        holder.tvStock.setText(productModel.getStock().toString());
        Glide.with(context).load(productModel.getThumbnail()).into(holder.imgAnh);

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llAction;
        ImageView imgAnh;
        TextView tvTittle,tvPrice,tvStock;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            llAction=itemView.findViewById(R.id.llAction);
            imgAnh=itemView.findViewById(R.id.imgAnh);
            tvTittle=itemView.findViewById(R.id.tvTittle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvStock=itemView.findViewById(R.id.tvStock);
        }
    }
}
