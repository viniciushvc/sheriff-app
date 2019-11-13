package com.vs.sheriff.controller.database_room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vs.sheriff.controller.database_room.entity.StockEntity;

import java.util.List;

@Dao
public interface StockDao {

    @Query("select s.*, p.name as name_product from stock s, product p where s.id_product = p.id")
    public List<StockEntity> getAll();

    @Query("select * from stock where id = :id")
    public StockEntity selectById(Long id);

    @Insert
    void insert(StockEntity stockEntity);

    @Update
    void update(StockEntity stockEntity);

    @Delete
    void delete(StockEntity stockEntity);
}
