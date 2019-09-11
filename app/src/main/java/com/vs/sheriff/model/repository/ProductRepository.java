package com.vs.sheriff.model.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.vs.sheriff.model.dao.ProductDao;
import com.vs.sheriff.model.database.ProductDatabase;
import com.vs.sheriff.model.entity.ProductEntity;

import java.util.List;

public class ProductRepository {

    private ProductDao productDao;

    private LiveData<List<ProductEntity>> allProducts;

    public ProductRepository(Application application) {
        ProductDatabase database = ProductDatabase.getInstance(application);

        productDao = database.productDao();
    }

    public void insert(ProductEntity productEntity) {
        new InsertProductAsyncTask(productDao).execute(productEntity);
    }

    public void update(ProductEntity productEntity) {
        new UpdateProductAsyncTask(productDao).execute(productEntity);
    }

    public void delete(ProductEntity productEntity) {
        new DeleteProductAsyncTask(productDao).execute(productEntity);
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return allProducts;
    }

    private static class InsertProductAsyncTask extends AsyncTask<ProductEntity, Void, Void> {
        private ProductDao productDao;

        private InsertProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(ProductEntity... productEntities) {
            productDao.insert(productEntities[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<ProductEntity, Void, Void> {
        private ProductDao productDao;

        private UpdateProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(ProductEntity... productEntities) {
            productDao.update(productEntities[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<ProductEntity, Void, Void> {
        private ProductDao productDao;

        private DeleteProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(ProductEntity... productEntities) {
            productDao.delete(productEntities[0]);
            return null;
        }
    }
}
