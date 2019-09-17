package com.vs.sheriff.controller.database_room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.vs.sheriff.controller.database_room.entity.ProductEntity;
import java.util.List;

@Dao
public interface ProductDao {

    @Query("select * from product order by id")
    public List<ProductEntity> allProducts();

    @Query("select * from product where id = :id")
    public ProductEntity selectById(Long id);

    @Insert
    void insert(ProductEntity productEntity);

    @Update
    void update(ProductEntity productEntity);

    @Delete
    void delete(ProductEntity productEntity);
}
