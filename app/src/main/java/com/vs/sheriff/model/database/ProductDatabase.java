package com.vs.sheriff.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.vs.sheriff.model.dao.ProductDao;
import com.vs.sheriff.model.entity.ProductEntity;

@Database(entities = {ProductEntity.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
}