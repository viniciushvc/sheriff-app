package com.vs.sheriff.controller.database_room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.vs.sheriff.controller.database_room.dao.ProductDao;
import com.vs.sheriff.controller.database_room.entity.ProductEntity;

@Database(entities = {ProductEntity.class}, version = 10)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
}