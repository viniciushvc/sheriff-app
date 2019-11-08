package com.vs.sheriff.controller.database_room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.vs.sheriff.controller.database_room.dao.StockDao;
import com.vs.sheriff.controller.database_room.entity.StockEntity;

@Database(entities = {StockEntity.class}, version = 1, exportSchema = false)
public abstract class StockDatabase extends RoomDatabase {

    public abstract StockDao stockDao();
}