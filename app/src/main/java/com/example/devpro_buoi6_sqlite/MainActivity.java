package com.example.devpro_buoi6_sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ProductModel> list;
    ProductAdapter productAdapter;
    private DBHelper dbHelper;
    RecyclerView rcvProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initView() {
        rcvProduct=findViewById(R.id.rcvProduct);
        productAdapter=new ProductAdapter(list);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        rcvProduct.setLayoutManager(layoutManager);
        rcvProduct.setAdapter(productAdapter);
    }

    private void initData() {
        dbHelper=new DBHelper(this);
        list=new ArrayList<>();
//        for(int i=0;i<10;i++){
//            ProductModel productModel = new ProductModel();
//            productModel.setTitle("iPhone 9"+i);
//            productModel.setDescription("An apple mobile which is nothing like apple");
//            productModel.setPrice(549+i*100);
//            productModel.setDiscountPercentage(12.96);
//            productModel.setRating(4.69);
//            productModel.setStock(94+i*10);
//            productModel.setBrand("Apple"+i);
//            productModel.setCategory("smartphones");
//            productModel.setThumbnail("https://i.dummyjson.com/data/products/1/thumbnail.jpg");
//            boolean i2=dbHelper.addProduct(productModel);
//        }
        list=dbHelper.getProductsByTittle("iPhone 91");
    }
}