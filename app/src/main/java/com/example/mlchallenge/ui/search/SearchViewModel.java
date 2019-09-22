package com.example.mlchallenge.ui.search;

import com.example.mlchallenge.data.Resource;
import com.example.mlchallenge.data.database.ProductDao;
import com.example.mlchallenge.data.models.Product;
import com.example.mlchallenge.data.network.Endpoints;
import com.example.mlchallenge.repository.ProductRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    private ProductRepository productRepository;

    public MutableLiveData<Resource<List<Product>>> searchResult = new MutableLiveData<>();
    public MutableLiveData<Resource<Product>> product = new MutableLiveData<>();

    private LiveData<Resource<List<Product>>> productsMediatorLiveData = null;
    private LiveData<Resource<Product>> productDetailsMediatorLiveData = null;

    @Inject
    public SearchViewModel(ProductDao productDao, Endpoints endpoints) {
        productRepository = new ProductRepository(productDao, endpoints);
    }

    public void searchProducts(String query, Integer offset, Integer limit) {
        if (productsMediatorLiveData != null && productsMediatorLiveData.hasObservers()) {
            return;
        }
        productsMediatorLiveData = productRepository.searchProducts(query, offset, limit);
        productsMediatorLiveData.observeForever(new Observer<Resource<List<com.example.mlchallenge.data.models.Product>>>() {
            @Override
            public void onChanged(Resource<List<com.example.mlchallenge.data.models.Product>> resource) {
                searchResult.postValue(resource);
                if (resource.isSuccess() || resource.isLoaded()) {
                    productsMediatorLiveData.removeObserver(this);
                }
            }
        });
    }

    public void searchProductDetails(String id) {
        if (productDetailsMediatorLiveData != null && productDetailsMediatorLiveData.hasObservers()) {
            return;
        }
        productDetailsMediatorLiveData = productRepository.searchProductDetail(id);
        productDetailsMediatorLiveData.observeForever(new Observer<Resource<Product>>() {
            @Override
            public void onChanged(Resource<Product> resource) {
                product.postValue(resource);
                if (resource.isSuccess() || resource.isLoaded()) {
                    productDetailsMediatorLiveData.removeObserver(this);
                }
            }
        });
    }


    public List<Product> getProducts() {
        return productRepository.getProductsFromDb();
    }
}
