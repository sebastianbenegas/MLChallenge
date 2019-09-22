package com.example.mlchallenge.data.network;

import com.example.mlchallenge.data.models.Product;
import com.example.mlchallenge.data.models.SearchResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Endpoints {

    @GET("sites/MLA/search")
    Call<SearchResponse> searchProducts(@Query("q") String search,
                                        @Query("offset") Integer offset,
                                        @Query("limit") Integer limit);


    @GET("items/{id}")
    Call<Product> searchProductById(@Path("id") String id);


}
