package com.vs.sheriff.controller.database_room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vs.sheriff.controller.database_room.dao.ProductDao;
import com.vs.sheriff.controller.database_room.entity.ProductEntity;

@Database(entities = {ProductEntity.class}, version = 1)
public abstract class DatabaseRoom extends RoomDatabase {
    private static final String DATABASE_NAME = "sheriff";

    public abstract ProductDao productDao();

    private static DatabaseRoom instance;

    public synchronized static DatabaseRoom getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseRoom.class, DATABASE_NAME).build();
        }

        return instance;
    }
}
