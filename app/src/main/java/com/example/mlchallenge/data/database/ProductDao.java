package com.example.mlchallenge.data.database;

import com.example.mlchallenge.data.models.Product;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertProducts(List<Product> productList);

    @Query("SELECT * FROM `Product`")
    LiveData<List<Product>> getProductsByPage();

    @Query("SELECT * FROM `Product` WHERE id = :id")
    LiveData<Product> getProductByIdDb(String id);

    @Update
    void updateProduct(Product product);

    @Query("SELECT * FROM `Product`")
    List<Product> getAllProducts();

    @Query("DELETE FROM `Product`")
    void deleteAll();

}
