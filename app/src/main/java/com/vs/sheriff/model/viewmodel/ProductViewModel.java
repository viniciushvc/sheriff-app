package com.vs.sheriff.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vs.sheriff.model.entity.ProductEntity;
import com.vs.sheriff.model.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository repository;

    private LiveData<List<ProductEntity>> allProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);

        repository = new ProductRepository(application);

        allProducts = repository.getAllProducts();
    }

    public void insert(ProductEntity productEntity) {
        repository.insert(productEntity);
    }

    public void update(ProductEntity productEntity) {
        repository.update(productEntity);
    }

    public void delete(ProductEntity productEntity) {
        repository.delete(productEntity);
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return allProducts;
    }
}
