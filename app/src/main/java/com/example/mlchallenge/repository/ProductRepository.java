package com.example.mlchallenge.repository;


import com.example.mlchallenge.data.NetworkBoundResource;
import com.example.mlchallenge.data.Resource;
import com.example.mlchallenge.data.database.ProductDao;
import com.example.mlchallenge.data.models.Product;
import com.example.mlchallenge.data.models.SearchResponse;
import com.example.mlchallenge.data.network.Endpoints;

import java.util.List;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;

@Singleton
public class ProductRepository {

    private ProductDao productDao;
    private Endpoints endpoints;

    public ProductRepository(ProductDao productDao, Endpoints endpoints) {
        this.productDao = productDao;
        this.endpoints = endpoints;
    }

    public LiveData<Resource<List<Product>>> searchProducts(String search, Integer offset, Integer limit) {
        return new NetworkBoundResource<List<Product>, SearchResponse>() {

            @Override
            protected void saveCallResult(@NonNull SearchResponse item) {
                if (!productDao.getAllProducts().isEmpty() && offset == 0) {
                    productDao.deleteAll();
                }
                    productDao.insertProducts(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<Product>> loadFromDb() {
                return productDao.getProductsByPage();
            }

            @NonNull
            @Override
            protected Call<SearchResponse> createCall() {
                return endpoints.searchProducts(search, offset, limit);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Product>> searchProductDetail(String id) {
        return new NetworkBoundResource<Product, Product>() {

            @Override
            protected void saveCallResult(@NonNull Product item) {
                productDao.updateProduct(item);
            }

            @NonNull
            @Override
            protected LiveData<Product> loadFromDb() {
                return productDao.getProductByIdDb(id);
            }

            @NonNull
            @Override
            protected Call<Product> createCall() {
                return endpoints.searchProductById(id);
            }
        }.getAsLiveData();
    }

    public List<Product> getProductsFromDb() {
        return productDao.getAllProducts();
    }
}




