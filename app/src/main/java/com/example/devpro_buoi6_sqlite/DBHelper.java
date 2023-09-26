package com.example.devpro_buoi6_sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="product.db";
    public static final  int VERSION=1;
    public static final String TABLE_NAME="product";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_TITLE = "title";
    public static final String PRODUCT_DES = "description";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_DISCOUNT = "discountPercentage";
    public static final String PRODUCT_STOCK = "stock";
    public static final String PRODUCT_BRAND = "brand";
    public static final String PRODUCT_CATEGORY = "category";
    public static final String PRODUCT_THUMBNAIL = "thumbnail";
    public static final String PRODUCT_IMAGES = "images";
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + PRODUCT_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + PRODUCT_TITLE + " TEXT NOT NULL,"
                + PRODUCT_DES + " TEXT NOT NULL,"
                + PRODUCT_PRICE + " TEXT NOT NULL,"
                + PRODUCT_DISCOUNT + " TEXT NOT NULL,"
                + PRODUCT_STOCK + " TEXT NOT NULL,"
                + PRODUCT_BRAND + " TEXT NOT NULL,"
                + PRODUCT_CATEGORY + " TEXT NOT NULL,"
                + PRODUCT_THUMBNAIL + " TEXT NOT NULL,"
                + PRODUCT_IMAGES + " TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addProduct(ProductModel product) {
        if (product != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PRODUCT_TITLE, product.getTitle());
            contentValues.put(PRODUCT_DES, product.getDescription());
            contentValues.put(PRODUCT_PRICE, product.getPrice());
            contentValues.put(PRODUCT_DISCOUNT, product.getDiscountPercentage());
            contentValues.put(PRODUCT_STOCK, product.getStock());
            contentValues.put(PRODUCT_BRAND, product.getBrand());
            contentValues.put(PRODUCT_CATEGORY, product.getCategory());
            contentValues.put(PRODUCT_THUMBNAIL, product.getThumbnail());
            // convert list sang gson
            Gson gson = new Gson();
            Type typeToken = new TypeToken<List<String>>(){}.getType();
            String data = gson.toJson(product.getImages(), typeToken);
            contentValues.put(PRODUCT_IMAGES, data);

            long response = db.insert(TABLE_NAME, null, contentValues);
            db.close();

            if (response > -1) {
                return false;
            }
            return  true;
        }
        return false;
    }
    public boolean updateProduct(int productId,ProductModel product){
        if(productId>=0 && product != null){
            SQLiteDatabase db=this.getWritableDatabase();
            String whereClause =PRODUCT_ID+" =?";
            String[] whereArg={productId+""};

            ContentValues contentValues=new ContentValues();
            contentValues.put(PRODUCT_TITLE,product.getTitle());
            contentValues.put(PRODUCT_DES,product.getDescription());
            contentValues.put(PRODUCT_DISCOUNT,product.getDiscountPercentage());
            contentValues.put(PRODUCT_STOCK,product.getStock());
            contentValues.put(PRODUCT_BRAND,product.getBrand());
            contentValues.put(PRODUCT_CATEGORY,product.getCategory());
            contentValues.put(PRODUCT_THUMBNAIL,product.getThumbnail());

            Gson gson=new Gson();
            Type typeToken =new TypeToken<List<String>>(){}.getType();
            String data =gson.toJson(product.getImages(),typeToken);

            contentValues.put(PRODUCT_IMAGES,data);
            db.update(TABLE_NAME,contentValues,whereClause,whereArg);
            db.close();
            return true;
        }
        return false;
    }

    public boolean deleteProduct(int productId){
        if(productId>=0){
            SQLiteDatabase db=getWritableDatabase();

            String whereClause =PRODUCT_ID+" =?";
            String[] whereArg={productId+""};

            db.delete(TABLE_NAME,whereClause,whereArg);
            db.close();
            return true;
        }
        return false;
    }
    @SuppressLint("Range")
    public List<ProductModel> getProducts(){
        SQLiteDatabase db=getReadableDatabase();
        String sql="select * from "+TABLE_NAME;
        List<ProductModel> list=new ArrayList<>();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                ProductModel productModel=new ProductModel();
                productModel.setId(cursor.getInt(cursor.getColumnIndex(PRODUCT_ID)));
                productModel.setTitle(cursor.getString(cursor.getColumnIndex(PRODUCT_TITLE)));
                productModel.setDescription(cursor.getString(2));

                int price=Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE)));
                productModel.setPrice(price);

                double discount=Double.parseDouble(cursor.getString(cursor.getColumnIndex(PRODUCT_DISCOUNT)));
                productModel.setDiscountPercentage(discount);

                int stock=Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_STOCK)));
                productModel.setStock(stock);

                productModel.setBrand(cursor.getString(cursor.getColumnIndex(PRODUCT_BRAND)));
                productModel.setCategory(cursor.getString(cursor.getColumnIndex(PRODUCT_CATEGORY)));
                productModel.setThumbnail(cursor.getString(cursor.getColumnIndex(PRODUCT_THUMBNAIL)));

                Gson gson =new Gson();
                Type typeToken = new TypeToken<List<String>>(){}.getType();
                List<String> data =gson.fromJson(cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGES)),typeToken);
                productModel.setImages(data);
                list.add(productModel);
            }
            return list;
        }
        return null;
    }
    @SuppressLint("Range")
    public List<ProductModel> getProductsByTittle(String tittle){
        SQLiteDatabase db=getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + PRODUCT_TITLE + " LIKE '%" + tittle + "'";
        List<ProductModel> list=new ArrayList<>();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                ProductModel productModel=new ProductModel();
                productModel.setId(cursor.getInt(cursor.getColumnIndex(PRODUCT_ID)));
                productModel.setTitle(cursor.getString(cursor.getColumnIndex(PRODUCT_TITLE)));
                productModel.setDescription(cursor.getString(2));

                int price=Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE)));
                productModel.setPrice(price);

                double discount=Double.parseDouble(cursor.getString(cursor.getColumnIndex(PRODUCT_DISCOUNT)));
                productModel.setDiscountPercentage(discount);

                int stock=Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_STOCK)));
                productModel.setStock(stock);

                productModel.setBrand(cursor.getString(cursor.getColumnIndex(PRODUCT_BRAND)));
                productModel.setCategory(cursor.getString(cursor.getColumnIndex(PRODUCT_CATEGORY)));
                productModel.setThumbnail(cursor.getString(cursor.getColumnIndex(PRODUCT_THUMBNAIL)));

                Gson gson =new Gson();
                Type typeToken = new TypeToken<List<String>>(){}.getType();
                List<String> data =gson.fromJson(cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGES)),typeToken);
                productModel.setImages(data);
                list.add(productModel);
            }
            return list;
        }
        return null;
    }
}
